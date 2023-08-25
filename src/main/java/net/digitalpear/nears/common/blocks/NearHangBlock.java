package net.digitalpear.nears.common.blocks;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class NearHangBlock extends PlantBlock implements Fertilizable{

    public static final int MAX_AGE = 5;
    public static final IntProperty AGE = Properties.AGE_5;
    public static final BooleanProperty MATURED = BooleanProperty.of("matured");

    private Block stemBlock = NBlocks.NEAR_HANG_STEM;


    protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    protected static final VoxelShape OUTLINE_SHAPE = VoxelShapes.union(COLLISION_SHAPE, Block.createCuboidShape(3.0D, 8.0D, 3.0D, 13.0D, 16.0D, 13.0D));
    public static final int baseGrowthLength = 12;

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    public NearHangBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(MATURED, false));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canPlantOnTop(world.getBlockState(blockPos), world, pos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(NBlockTags.NEAR_HANG_PLANTABLE_ON) || floor.isOf(stemBlock);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (random.nextInt(100) < 30) {
            if (state.get(AGE) >= MAX_AGE){
                if (!state.get(MATURED) && world.getBlockState(pos.down()).isAir()) {
                    int growthChance = baseGrowthLength;
                    for (BlockPos pos1 : BlockPos.iterate(pos, pos.add(0, 6, 0))) {
                        if (world.getBlockState(pos1).isOf(stemBlock)) {
                            growthChance--;
                        }
                    }
                    if (random.nextInt(baseGrowthLength) < growthChance) {
                        world.setBlockState(pos, stemBlock.getDefaultState().with(NearHangStemBlock.SUPPORTED, world.getBlockState(pos.up()).isOf(stemBlock)), 3);
                        world.setBlockState(pos.down(), state.with(AGE, random.nextBetween(1, 2)).with(MATURED, random.nextFloat() < 0.1), 3);
                    }
                }
            }
            else {
                world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), 3);
            }
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !state.get(MATURED);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() instanceof ShearsItem && !state.get(MATURED)){
            BlockState finalState = state.with(MATURED, true);
            if (stack.isDamageable()){
                stack.damage(1, player, player1 -> player1.sendToolBreakStatus(hand));
            }
            if (player instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)player, pos, stack);
            }

            world.playSound(player, pos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos, finalState);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, finalState));

            return ActionResult.success(world.isClient());
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        builder.add(MATURED);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.down()).isAir() && !state.get(MATURED);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.get(AGE) < MAX_AGE){
            int growth = random.nextBetween(1, 3);
            if (state.get(AGE) + growth > MAX_AGE){
                growth = state.get(AGE) - growth;
            }
            world.setBlockState(pos, state.with(AGE, state.get(AGE) + growth));
        }
        else{
            world.setBlockState(pos, stemBlock.getDefaultState().with(NearHangStemBlock.SUPPORTED, world.getBlockState(pos.up()).isOf(stemBlock)), 3);
            world.setBlockState(pos.down(), state.with(AGE, 0), 3);
        }
    }
}

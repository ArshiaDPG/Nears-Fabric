package net.digitalpear.nears.common.blocks;

import com.mojang.serialization.MapCodec;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class NearHangStemBlock extends PlantBlock implements Fertilizable {
    public static final MapCodec<NearHangStemBlock> CODEC = createCodec(NearHangStemBlock::new);
    public static final BooleanProperty SUPPORTED = BooleanProperty.of("supported");
    public static final IntProperty AGE = Properties.AGE_3;

    protected static final VoxelShape SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);

    public NearHangStemBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(SUPPORTED, false));
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(SUPPORTED);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        return this.getDefaultState().with(SUPPORTED, world.getBlockState(pos.up()).isOf(this));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(AGE) < 3 && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } if (state.get(AGE) > 1){
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            player.swingHand(hand);
            dropStack(world, pos, new ItemStack(NItems.NEAR, getNearCount(state.get(AGE), world.getRandom())));
            world.setBlockState(pos, state.with(AGE, 1), 3);

            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    private static int getNearCount(int age, Random random){
        if (age == 2){
            return 1;
        }
        return random.nextBetween(2, 3);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient() && random.nextInt(100) < 50 && state.get(AGE) < 3){
            world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1));
        }
    }


    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlantOnTop(world.getBlockState(pos.up()), world, pos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(NBlockTags.NEAR_HANG_PLANTABLE_ON) || floor.isOf(this);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SUPPORTED);
        builder.add(AGE);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(SUPPORTED);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return state.get(SUPPORTED);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1));
    }
}

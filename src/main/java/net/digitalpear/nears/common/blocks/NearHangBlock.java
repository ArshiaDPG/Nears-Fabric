package net.digitalpear.nears.common.blocks;

import com.mojang.serialization.MapCodec;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NearHangBlock extends VegetationBlock implements BonemealableBlock {
    public static final MapCodec<NearHangBlock> CODEC = simpleCodec(NearHangBlock::new);
    public static final int MAX_AGE = 5;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
    public static final BooleanProperty MATURED = BooleanProperty.create("matured");

    private final Block STEM_BLOCK = NBlocks.NEAR_HANG_STEM;


    protected static final VoxelShape COLLISION_SHAPE = Block.box(1.0D, 1.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    protected static final VoxelShape OUTLINE_SHAPE = Shapes.or(COLLISION_SHAPE, Block.box(3.0D, 8.0D, 3.0D, 13.0D, 16.0D, 13.0D));
    public static final int baseGrowthLength = 12;
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }
    
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    public NearHangBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0).setValue(MATURED, false));
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockPos = pos.above();
        return mayPlaceOn(level.getBlockState(blockPos), level, pos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter level, BlockPos pos) {
        return floor.is(NBlockTags.NEAR_HANG_PLANTABLE_ON) || floor.is(STEM_BLOCK);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (random.nextInt(100) < 30) {
            if (state.getValue(AGE) >= MAX_AGE){
                if (!state.getValue(MATURED) && level.getBlockState(pos.below()).isAir()) {
                    int growthChance = baseGrowthLength;
                    for (BlockPos pos1 : BlockPos.betweenClosed(pos, pos.offset(0, 6, 0))) {
                        if (level.getBlockState(pos1).is(STEM_BLOCK)) {
                            growthChance--;
                        }
                    }
                    if (random.nextInt(baseGrowthLength) < growthChance) {
                        level.setBlock(pos, STEM_BLOCK.defaultBlockState().setValue(NearHangStemBlock.SUPPORTED, level.getBlockState(pos.above()).is(STEM_BLOCK)), 3);
                        level.setBlock(pos.below(), state.setValue(AGE, random.nextIntBetweenInclusive(1, 2)).setValue(MATURED, random.nextFloat() < 0.1), 3);
                    }
                }
            }
            else {
                level.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), 3);
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(MATURED);
    }
    
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        InteractionHand hand = player.getUsedItemHand();
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof ShearsItem && !state.getValue(MATURED)){
            BlockState finalState = state.setValue(MATURED, true);
            if (stack.isDamageableItem()){
                stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            }

            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, stack);
            }

            level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlockAndUpdate(pos, finalState);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, finalState));

            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        builder.add(MATURED);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.below()).isAir() && !state.getValue(MATURED);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.getValue(AGE) < MAX_AGE){
            int growth = random.nextIntBetweenInclusive(1, 3);
            if (state.getValue(AGE) + growth > MAX_AGE){
                growth = state.getValue(AGE) - growth;
            }
            level.setBlockAndUpdate(pos, state.setValue(AGE, state.getValue(AGE) + growth));
        }
        else{
            level.setBlock(pos, STEM_BLOCK.defaultBlockState().setValue(NearHangStemBlock.SUPPORTED, level.getBlockState(pos.below()).is(STEM_BLOCK)), 3);
            level.setBlock(pos.below(), state.setValue(AGE, 0), 3);
        }
    }
}

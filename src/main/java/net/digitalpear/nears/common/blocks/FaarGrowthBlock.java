package net.digitalpear.nears.common.blocks;

import com.mojang.serialization.MapCodec;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FaarGrowthBlock extends VegetationBlock implements BonemealableBlock {
    public static final MapCodec<FaarGrowthBlock> CODEC = simpleCodec(FaarGrowthBlock::new);
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape SMALL_SHAPE = Block.box(1.0D, 8.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private static final VoxelShape LARGE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);


    public FaarGrowthBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0));
    }
    
    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }
    
    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(NItems.FAAR_SEEDS);
    }
    
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(AGE) <= 1) {
            return SMALL_SHAPE;
        } else {
            return state.getValue(AGE) < MAX_AGE ? LARGE_SHAPE : super.getCollisionShape(state, level, pos, context);
        }
    }
    
    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return false;
    }
    
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            performBonemeal(level, random, pos, state);
        }
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }
    
    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) <= MAX_AGE;
    }
    
    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.getValue(AGE) < MAX_AGE){
            int i = state.getValue(AGE) + random.nextIntBetweenInclusive(1, 2);
            i = Math.min(i, MAX_AGE);
            BlockState blockState = state.setValue(AGE, i);
            level.setBlock(pos, blockState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockState));
        }
        else{
            level.setBlock(pos, NBlocks.FAAR_BUNDLE.defaultBlockState(), 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(this.defaultBlockState()));
        }
    }
    
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockPos = pos.above();
        return this.mayPlaceOn(level.getBlockState(blockPos), level, blockPos);
    }
    
    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter level, BlockPos pos) {
        return floor.is(NBlockTags.FAAR_GROWTH_PLANTABLE_ON);
    }
}

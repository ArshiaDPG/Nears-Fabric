package net.digitalpear.nears.common.blocks;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableBiMap;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.stream.Stream;

public class CInderGrainCropBlock extends CropBlock {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public static final Supplier<ImmutableBiMap<Object, Object>> SHAPES = Suppliers.memoize(() -> ImmutableBiMap.builder()
            .put(Direction.NORTH, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D))
            .put(Direction.SOUTH, Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D))
            .put(Direction.EAST, Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D))
            .put(Direction.WEST, Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D)).build());

    public CInderGrainCropBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(AGE, 0));
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        Direction direction = ctx.getNearestLookingDirection();
        BlockState blockState = this.defaultBlockState().setValue(FACING, direction);


        if (!level.getFluidState(blockPos).isEmpty()){
            return null;
        }

        if (blockState.canSurvive(level, blockPos)) {
            return blockState.setValue(FACING, direction);
        }


        Stream<Direction> directionStream = Direction.stream()
                .filter(direction1 -> direction1.getAxis().isHorizontal())
                .filter(direction1 -> direction1 != direction);


        for (Direction direction1 : directionStream.toList()){
            BlockState state = blockState.setValue(FACING, direction1);
            if (state.canSurvive(level, blockPos)) {
                return blockState.setValue(FACING, direction1);
            }
        }

        return null;
    }
    
    
    
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int i = this.getAge(state);
        if (i < this.getMaxAge()) {
            float f = getAvailableMoisture(this, world, pos);
            if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                world.setBlock(pos, this.getStateForAge(i + 1).setValue(FACING, state.getValue(FACING)), 2);
            }
        }

    }

    protected static float getAvailableMoisture(Block block, Level world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockPos = pos.below();

        for(int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int y = -1; y <= 1; ++y) {
                    float g = 0.0F;
                    BlockState blockState = world.getBlockState(blockPos.offset(i, y, j));
                    if (blockState.is(NBlockTags.CINDER_WHEAT_PLANTABLE_ON)) {
                        g = 0.75F;
                    }

                    if (i != 0 || j != 0 || y != 0) {
                        g /= 4.0F;
                    }
                    f += g;
                }
            }
        }

        BlockPos blockPos2 = pos.north();
        BlockPos blockPos3 = pos.south();
        BlockPos blockPos4 = pos.west();
        BlockPos blockPos5 = pos.east();
        boolean bl = world.getBlockState(blockPos4).is(block) || world.getBlockState(blockPos5).is(block);
        boolean bl2 = world.getBlockState(blockPos2).is(block) || world.getBlockState(blockPos3).is(block);
        if (bl && bl2) {
            f /= 2.0F;
        } else {
            boolean bl3 = world.getBlockState(blockPos4.north()).is(block) || world.getBlockState(blockPos5.north()).is(block) || world.getBlockState(blockPos5.south()).is(block) || world.getBlockState(blockPos4.south()).is(block);
            if (bl3) {
                f /= 2.0F;
            }
        }

        return f;
    }
    
    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(level);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }
        
        level.setBlock(pos, this.getStateForAge(i).setValue(FACING, state.getValue(FACING)), 2);
    }
    
    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return false;
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (VoxelShape) SHAPES.get().getOrDefault(state.getValue(FACING), super.getShape(state, level, pos, context));
    }
    
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockPos = pos.relative(state.getValue(FACING));
        return this.mayPlaceOn(level.getBlockState(blockPos), level, blockPos);
    }
    
    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(NItems.CINDER_SEEDS);
    }

    
    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter level, BlockPos pos) {
        return floor.is(NBlockTags.CINDER_WHEAT_PLANTABLE_ON);
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AGE);
    }
}

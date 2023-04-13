package net.digitalpear.nears.common.blocks;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.include.com.google.common.collect.ImmutableMap;

import java.util.Map;

public class CInderWheatCropBlock extends CropBlock {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

//    private static Map<Direction, VoxelShape> DIRECTION_SHAPE_MAP =
//            Map.of(Direction.NORTH, Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D));


    public static final Supplier<ImmutableBiMap<Object, Object>> DIRECTION_SHAPE_MAP = Suppliers.memoize(() -> {
        return ImmutableBiMap.builder()
                .put(Direction.NORTH, Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D))
                .put(Direction.SOUTH, Block.createCuboidShape(0.0D, 0.0D, 16.0D, 16.0D, 16.0D, 12.0D))
                .put(Direction.EAST, Block.createCuboidShape(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D))
                .put(Direction.WEST, Block.createCuboidShape(0.0D, 0.0D, 16.0D, 12.0D, 16.0D, 16.0D)).build();
    });
    public CInderWheatCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(AGE, 0));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(AGE, ctx.getWorld().getBlockState(ctx.getBlockPos()).get(AGE))
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (VoxelShape) DIRECTION_SHAPE_MAP.get().getOrDefault(state.get(FACING), super.getOutlineShape(state, world, pos, context));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.offset(state.get(FACING));
        return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.MAGMA_BLOCK);
    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, AGE);
    }

}

package net.digitalpear.nears.common.blocks;

import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
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
import net.minecraft.world.event.GameEvent;

public class NearBulbBlock extends PlantBlock implements Fertilizable {
    public static final IntProperty AGE = Properties.AGE_3;
    public static final int MAX_AGE = 3;
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D);

    public NearBulbBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(NItems.NEAR_SEEDS);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!(state.get(AGE) < MAX_AGE)){
            dropStack(world, pos, new ItemStack(NItems.NEAR));
            world.setBlockState(pos, state.with(AGE, 0), 2);
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(NBlockTags.NEAR_PLANTABLE_ON);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(AGE) < 3 && random.nextFloat() > 0.6){
            BlockState blockState = state.with(AGE, state.get(AGE) + 1);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
        }
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.get(AGE) < 3) {
            int i = state.get(AGE);
            world.setBlockState(pos, state.with(AGE, i + random.nextBetween(1, 2)), 2);
        }
    }

}

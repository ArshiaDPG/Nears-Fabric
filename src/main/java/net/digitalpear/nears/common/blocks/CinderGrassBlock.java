package net.digitalpear.nears.common.blocks;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.RootsBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CinderGrassBlock extends RootsBlock implements Fertilizable {
    public CinderGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(NBlockTags.CINDER_GRASS_PLANTABLE_ON);
    }
    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos.iterate(pos.add(-2, -2, -2), pos.add(2, 2, 2)).forEach(currentPos -> {
            if (world.getBlockState(currentPos).isIn(NBlockTags.CINDER_GRASS_PLANTABLE_ON) && world.getBlockState(currentPos.up()).isAir() && world.getRandom().nextInt(10) == 1){
                world.setBlockState(currentPos.up(), NBlocks.CINDER_GRASS.getDefaultState(), 3);

            }
        });
    }
}

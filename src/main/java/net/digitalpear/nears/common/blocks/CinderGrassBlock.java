package net.digitalpear.nears.common.blocks;

import net.digitalpear.nears.init.tags.NBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.RootsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class CinderGrassBlock extends RootsBlock {
    public CinderGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(NBlockTags.CINDER_GRASS_PLANTABLE_ON);
    }
}

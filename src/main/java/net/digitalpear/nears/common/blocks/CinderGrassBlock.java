package net.digitalpear.nears.common.blocks;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MangroveRootsBlock;
import net.minecraft.world.level.block.NetherRootsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

//TODO: RootsBlock was just straight up removed, used the closest thing i could find
public class CinderGrassBlock extends NetherRootsBlock implements BonemealableBlock {
    public CinderGrassBlock(BlockBehaviour.Properties properties) {
        super(NBlockTags.CINDER_GRASS_PLANTABLE_ON, properties);
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
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos.betweenClosed(pos.offset(-2, -2, -2), pos.offset(2, 2, 2)).forEach(currentPos -> {
            if (level.getBlockState(currentPos).is(NBlockTags.CINDER_GRASS_PLANTABLE_ON) && level.getBlockState(currentPos.above()).isAir() && random.nextInt(10) == 1){
                level.setBlock(currentPos.above(), NBlocks.CINDER_GRASS.defaultBlockState(), 3);
            }
        });
    }
}

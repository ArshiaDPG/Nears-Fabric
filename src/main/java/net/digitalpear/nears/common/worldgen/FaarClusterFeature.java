package net.digitalpear.nears.common.worldgen;

import com.mojang.serialization.Codec;
import net.digitalpear.nears.common.blocks.FaarGrowthBlock;
import net.digitalpear.nears.common.worldgen.config.FaarClusterFeatureConfig;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class FaarClusterFeature extends Feature<FaarClusterFeatureConfig> {
    public FaarClusterFeature(Codec<FaarClusterFeatureConfig> codec) {
        super(codec);
    }
    
    @Override
    public boolean place(FeaturePlaceContext<FaarClusterFeatureConfig> context) {
        FaarClusterFeatureConfig config = context.config();

        int spreadVertical = config.spreadVertical;
        int spreadHorizontal = config.spreadHorizontal;
        int rarity = config.rarity;

        BlockPos initialPos = context.origin();
        WorldGenLevel world = context.level();
        RandomSource random = context.random();

        Iterable<BlockPos> positions = BlockPos.betweenClosed(initialPos.offset(-spreadHorizontal, -spreadVertical, -spreadHorizontal), initialPos.offset(spreadHorizontal, spreadVertical, spreadHorizontal));
        int i = 0;
        for (BlockPos pos : positions){
            if (world.getBlockState(pos).is(NBlockTags.FAAR_CLUSTER_REPLACEABLE) && world.getBlockState(pos.above()).is(Blocks.WARPED_WART_BLOCK) && random.nextInt(rarity) == 0){
                if (random.nextFloat() < 0.2){
                    world.setBlock(pos, NBlocks.FAAR_BUNDLE.defaultBlockState(), 2);
                }
                else{
                    int age = random.nextIntBetweenInclusive(1, 3);
                    world.setBlock(pos, NBlocks.FAAR_GROWTH.defaultBlockState().setValue(FaarGrowthBlock.AGE, age), 2);
                }
                i++;
            }
        }

        return i > 0;
    }

}

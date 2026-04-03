package net.digitalpear.nears.common.worldgen;

import com.mojang.serialization.Codec;
import net.digitalpear.nears.common.blocks.NearHangBlock;
import net.digitalpear.nears.common.blocks.NearHangStemBlock;
import net.digitalpear.nears.common.worldgen.config.NearHangFeatureConfig;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;


public class NearHangFeature extends Feature<NearHangFeatureConfig> {
    public NearHangFeature(Codec<NearHangFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NearHangFeatureConfig> context) {
        NearHangFeatureConfig config = context.config();
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        boolean generated = false;
        int radius = config.radius;
        
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -radius, -radius), origin.offset(radius, radius, radius))) {
            if (isSupported(level, pos) && (random.nextFloat() > 0.93)){
                if (!generated){
                    level.registryAccess().lookup(Registries.CONFIGURED_FEATURE).flatMap((registry) -> registry.get(config.accompanyingFeature.unwrapKey().get())).ifPresent((mossPatch) -> mossPatch.value().place(level, context.chunkGenerator(), random, pos));
                    generated = true;
                }
                generateNearHang(level, pos, random);
            }
        }

        return generated;
    }

    public static void generateNearHang(WorldGenLevel level, BlockPos pos, RandomSource random){
        int length = random.nextIntBetweenInclusive(6, 8);
        
        level.setBlock(pos, Blocks.NETHER_WART_BLOCK.defaultBlockState(), 3);
        for (int i = 1; i < length; i++){
            if (level.getBlockState(pos.below(i + 1)).isAir() && (pos.below().getY() > level.getMinY())){
                level.setBlock(pos.below(i), NBlocks.NEAR_HANG_STEM.defaultBlockState()
                        .setValue(NearHangStemBlock.AGE, random.nextInt(3))
                        .setValue(NearHangStemBlock.SUPPORTED, level.getBlockState(pos.below(i-1)).is(NBlocks.NEAR_HANG_STEM)), 3);
            }
            else{
                level.setBlock(pos.below(i), NBlocks.NEAR_HANG.defaultBlockState().setValue(NearHangBlock.MATURED, true).setValue(NearHangBlock.AGE, 5), 3);
                return;
            }
        }
        level.setBlock(pos.below(length), NBlocks.NEAR_HANG.defaultBlockState().setValue(NearHangBlock.MATURED, true).setValue(NearHangBlock.AGE, 5), 3);
    }
    public static boolean isSupported(WorldGenLevel level, BlockPos pos){
        for (BlockPos pos1 : BlockPos.betweenClosed(pos.below(), pos.below(5))) {
            if (!level.getBlockState(pos1).isAir()) {
                return false;
            }
        }
        return isBlockStable(level, pos) && isBlockStable(level, pos.above());
    }

    public static boolean isBlockStable(WorldGenLevel level, BlockPos pos){
        return (level.getBlockState(pos.above()).is(BlockTags.BASE_STONE_NETHER) || level.getBlockState(pos.above()).is(NBlockTags.NEAR_HANG_PLANTABLE_ON));
    }
}

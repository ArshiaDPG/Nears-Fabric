package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.blocks.NearBulbBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class NConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_NEARS = of("patch_nears");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SOUL_BERRY_BUSH = of("patch_soul_berry_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FAAR_GROWTH = of("patch_faar_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_CINDER_GRASS = of("patch_cinder_grass");

    public static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Nears.id(id));
    }

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        register(featureRegisterable, PATCH_NEARS, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(BlockStateProvider.of(NBlocks.NEAR_BULB.getDefaultState().with(NearBulbBlock.AGE, 3)), 96));
        register(featureRegisterable, PATCH_FAAR_GROWTH, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(BlockStateProvider.of(NBlocks.FAAR_GROWTH.getDefaultState().with(NearBulbBlock.AGE, 3)), 96));
        register(featureRegisterable, PATCH_SOUL_BERRY_BUSH, Feature.RANDOM_PATCH, createRandomPatchFeatureConfig(BlockStateProvider.of(NBlocks.SOUL_BERRY_BUSH.getDefaultState().with(NearBulbBlock.AGE, 3)), 96));
        register(featureRegisterable, PATCH_CINDER_GRASS, Feature.RANDOM_PATCH, createCinderGrassPatchFeatureConfig(BlockStateProvider.of(NBlocks.CINDER_GRASS), 120));
    }
    private static RandomPatchFeatureConfig createCinderGrassPatchFeatureConfig(BlockStateProvider block, int tries) {
        return new RandomPatchFeatureConfig(tries, 7, 32, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }
    private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }
    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> registerable, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        registerable.register(key, new ConfiguredFeature(feature, config));
    }
}

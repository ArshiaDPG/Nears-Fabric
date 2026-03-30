package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.blocks.SoulBerryBushBlock;
import net.digitalpear.nears.common.worldgen.NFeature;
import net.digitalpear.nears.common.worldgen.config.FaarClusterFeatureConfig;
import net.digitalpear.nears.common.worldgen.config.NearHangFeatureConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;

import java.util.ArrayList;
import java.util.List;

public class NConfiguredFeatures {

    public static List<ResourceKey<ConfiguredFeature<?, ?>>> features = new ArrayList<>();



    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_NEAR_HANG = of("patch_near_hang");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SOUL_BERRY_BUSH = of("patch_soul_berry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FAAR_GROWTH = of("patch_faar_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CINDER_GRASS = of("patch_cinder_grass");


    public static ResourceKey<ConfiguredFeature<?, ?>> of(String id) {
        ResourceKey<ConfiguredFeature<?, ?>> feature = ResourceKey.create(Registries.CONFIGURED_FEATURE, Nears.id(id));
        features.add(feature);
        return feature;
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureBootstrapContext) {
        Holder.Reference<ConfiguredFeature<?, ?>> nearHangFeature = featureBootstrapContext.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(NetherFeatures.WEEPING_VINES);

        register(featureBootstrapContext, PATCH_NEAR_HANG, NFeature.NEAR_HANG, new NearHangFeatureConfig(3, nearHangFeature));
        register(featureBootstrapContext, PATCH_FAAR_GROWTH, NFeature.FAAR_CLUSTER, new FaarClusterFeatureConfig(6, 12, 5));
        //TODO: i have no idea if these are correct
        register(featureBootstrapContext, PATCH_SOUL_BERRY_BUSH, Feature.SIMPLE_RANDOM_SELECTOR, createRandomPatchFeatureConfig(BlockStateProvider.simple(NBlocks.SOUL_BERRY_BUSH.defaultBlockState().setValue(SoulBerryBushBlock.AGE, 3)), 96));
        register(featureBootstrapContext, PATCH_CINDER_GRASS, Feature.SIMPLE_RANDOM_SELECTOR, createCinderGrassPatchFeatureConfig(BlockStateProvider.simple(NBlocks.CINDER_GRASS), 120));
    }
    private static SimpleRandomFeatureConfiguration createCinderGrassPatchFeatureConfig(BlockStateProvider block, int tries) {
        return new SimpleRandomFeatureConfiguration(HolderSet.direct(
            PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(block),
                CountPlacement.of(tries),
                //TODO: the ySpread was previously 32, no idea why, but that's no longer allowed/i couldn't figure out how to do it
                RandomOffsetPlacement.ofTriangle(7, 16)
            )
        ));
    }
    private static SimpleRandomFeatureConfiguration createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return new SimpleRandomFeatureConfiguration(HolderSet.direct(
            PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(block),
                CountPlacement.of(tries)
            )
        ));
    }
    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> bootstrapContext, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        bootstrapContext.register(key, new ConfiguredFeature<>(feature, config));
    }
}
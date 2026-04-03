package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.init.data.tags.NBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.ArrayList;
import java.util.List;

public class NPlacedFeatures {

    public static List<ResourceKey<PlacedFeature>> features = new ArrayList<>();


    public static final ResourceKey<PlacedFeature> PATCH_NEAR_HANG = of("patch_near_hang");
    public static final ResourceKey<PlacedFeature> PATCH_SOUL_BERRY_BUSH = of("patch_soul_berry_bush");
    public static final ResourceKey<PlacedFeature> PATCH_FAAR_GROWTH = of("patch_faar_growth");
    public static final ResourceKey<PlacedFeature> PATCH_CINDER_GRASS = of("patch_cinder_grass");


    public static ResourceKey<PlacedFeature> of(String id) {
        ResourceKey<PlacedFeature> feature = ResourceKey.create(Registries.PLACED_FEATURE, Nears.id(id));
        features.add(feature);
        return feature;
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.lookup(Registries.CONFIGURED_FEATURE);
        Holder.Reference<ConfiguredFeature<?, ?>> patchNears = registryEntryLookup.getOrThrow(NConfiguredFeatures.PATCH_NEAR_HANG);
        Holder.Reference<ConfiguredFeature<?, ?>> patchFaars = registryEntryLookup.getOrThrow(NConfiguredFeatures.PATCH_FAAR_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> patchSoulBerries = registryEntryLookup.getOrThrow(NConfiguredFeatures.PATCH_SOUL_BERRY_BUSH);
        Holder.Reference<ConfiguredFeature<?, ?>> patchCinderGrass = registryEntryLookup.getOrThrow(NConfiguredFeatures.PATCH_CINDER_GRASS);
        
        
        PlacementUtils.register(featureRegisterable, PATCH_NEAR_HANG, patchNears, makePatchPlacements(RarityFilter.onAverageOnceEvery(1)));
        PlacementUtils.register(featureRegisterable, PATCH_FAAR_GROWTH, patchFaars, makePatchPlacements(CountPlacement.of(UniformInt.of(0, 5))));
        PlacementUtils.register(featureRegisterable, PATCH_SOUL_BERRY_BUSH, patchSoulBerries, makePatchPlacements(CountPlacement.of(UniformInt.of(0, 3))));
        PlacementUtils.register(featureRegisterable, PATCH_CINDER_GRASS, patchCinderGrass, makePatchPlacements(CountPlacement.of(UniformInt.of(0, 4))));
    }

    public static List<PlacementModifier> makePatchPlacements(PlacementModifier countOrRarity){
        return List.of(countOrRarity, InSquarePlacement.spread(), PlacementUtils.RANGE_4_4, BiomeFilter.biome());
    }

    public static void init() {
        BiomeModifications.addFeature(BiomeSelectors.tag(NBiomeTags.CAN_NEARS_SPAWN), GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_NEAR_HANG);
        BiomeModifications.addFeature(BiomeSelectors.tag(NBiomeTags.CAN_FAARS_SPAWN), GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_FAAR_GROWTH);
        BiomeModifications.addFeature(BiomeSelectors.tag(NBiomeTags.CAN_SOUL_BERRIES_SPAWN), GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_SOUL_BERRY_BUSH);
        BiomeModifications.addFeature(BiomeSelectors.tag(NBiomeTags.CAN_CINDER_GRASS_SPAWN), GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_CINDER_GRASS);
    }
}

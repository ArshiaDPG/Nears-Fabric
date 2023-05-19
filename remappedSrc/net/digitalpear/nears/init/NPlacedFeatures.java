package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.init.data.tags.NBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;

public class NPlacedFeatures {

    public static final RegistryKey<PlacedFeature> PATCH_NEARS = of("patch_nears");
    public static final RegistryKey<PlacedFeature> PATCH_SOUL_BERRY_BUSH = of("patch_soul_berry_bush");
    public static final RegistryKey<PlacedFeature> PATCH_FAAR_GROWTH = of("patch_faar_growth");
    public static final RegistryKey<PlacedFeature> PATCH_CINDER_GRASS = of("patch_cinder_grass");

    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Nears.id(id));
    }

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry<ConfiguredFeature<?, ?>> patchNears = registryEntryLookup.getOrThrow(NConfiguredFeatures.PATCH_NEARS);
        RegistryEntry<ConfiguredFeature<?, ?>> patchFaars = registryEntryLookup.getOrThrow(NConfiguredFeatures.PATCH_FAAR_GROWTH);
        RegistryEntry<ConfiguredFeature<?, ?>> patchSoulBerries = registryEntryLookup.getOrThrow(NConfiguredFeatures.PATCH_SOUL_BERRY_BUSH);
        RegistryEntry<ConfiguredFeature<?, ?>> patchCinderGrass = registryEntryLookup.getOrThrow(NConfiguredFeatures.PATCH_CINDER_GRASS);

        PlacedFeatures.register(featureRegisterable, PATCH_NEARS, patchNears, PlacedFeatures.BOTTOM_TO_TOP_RANGE, RarityFilterPlacementModifier.of(4), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SOUL_BERRY_BUSH, patchFaars, PlacedFeatures.BOTTOM_TO_TOP_RANGE, RarityFilterPlacementModifier.of(4), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_FAAR_GROWTH, patchSoulBerries, PlacedFeatures.BOTTOM_TO_TOP_RANGE, RarityFilterPlacementModifier.of(4), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_CINDER_GRASS, patchCinderGrass, PlacedFeatures.BOTTOM_TO_TOP_RANGE, RarityFilterPlacementModifier.of(4), BiomePlacementModifier.of());

    }

    public static void init() {
        BiomeModifications.addFeature(BiomeSelectors.tag(NBiomeTags.CAN_NEARS_SPAWN), GenerationStep.Feature.VEGETAL_DECORATION, PATCH_NEARS);
        BiomeModifications.addFeature(BiomeSelectors.tag(NBiomeTags.CAN_FAARS_SPAWN), GenerationStep.Feature.VEGETAL_DECORATION, PATCH_FAAR_GROWTH);
        BiomeModifications.addFeature(BiomeSelectors.tag(NBiomeTags.CAN_SOUL_BERRIES_SPAWN), GenerationStep.Feature.VEGETAL_DECORATION, PATCH_SOUL_BERRY_BUSH);
        BiomeModifications.addFeature(BiomeSelectors.tag(NBiomeTags.CAN_CINDER_GRASS_SPAWN), GenerationStep.Feature.VEGETAL_DECORATION, PATCH_CINDER_GRASS);
    }
}

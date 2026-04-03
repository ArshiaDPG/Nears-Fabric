package net.digitalpear.nears.common.datagen.provider.worldgen;

import net.digitalpear.nears.init.NPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.concurrent.CompletableFuture;

public class NPlacedFeatureProvider extends FabricDynamicRegistryProvider {

    public NPlacedFeatureProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
//        add(registries, entries, NPlacedFeatures.PATCH_NEARS);
//        add(registries, entries, NPlacedFeatures.PATCH_FAAR_GROWTH);
//        add(registries, entries, NPlacedFeatures.PATCH_SOUL_BERRY_BUSH);
//        add(registries, entries, NPlacedFeatures.PATCH_CINDER_GRASS);

        NPlacedFeatures.features.forEach(placedFeatureRegistryKey -> {
            add(registries, entries, placedFeatureRegistryKey);
        });
    }


    private void add(HolderLookup.Provider registries, Entries entries, ResourceKey<PlacedFeature> resourceKey) {
        HolderLookup.RegistryLookup<PlacedFeature> configuredFeatureRegistryLookup = registries.lookupOrThrow(Registries.PLACED_FEATURE);

        entries.add(resourceKey, configuredFeatureRegistryLookup.getOrThrow(resourceKey).value());
    }
    @Override
    public String getName() {
        return "worldgen/placed_feature";
    }
}
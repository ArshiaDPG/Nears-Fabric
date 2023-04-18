package net.digitalpear.nears.common.datagen.worldgen;

import net.digitalpear.nears.init.NPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.concurrent.CompletableFuture;

public class NPlacedFeatureProvider extends FabricDynamicRegistryProvider {

    public NPlacedFeatureProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        add(registries, entries, NPlacedFeatures.PATCH_NEARS);
        add(registries, entries, NPlacedFeatures.PATCH_FAAR_GROWTH);
        add(registries, entries, NPlacedFeatures.PATCH_SOUL_BERRY_BUSH);
        add(registries, entries, NPlacedFeatures.PATCH_CINDER_GRASS);
    }


    private void add(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryKey<PlacedFeature> resourceKey) {
        RegistryWrapper.Impl<PlacedFeature> configuredFeatureRegistryLookup = registries.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE);

        entries.add(resourceKey, configuredFeatureRegistryLookup.getOrThrow(resourceKey).value());
    }
    @Override
    public String getName() {
        return "worldgen/placed_feature";
    }
}
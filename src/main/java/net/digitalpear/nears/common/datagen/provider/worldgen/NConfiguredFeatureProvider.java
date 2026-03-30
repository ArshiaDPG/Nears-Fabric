package net.digitalpear.nears.common.datagen.provider.worldgen;

import net.digitalpear.nears.init.NConfiguredFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.concurrent.CompletableFuture;

public class NConfiguredFeatureProvider extends FabricDynamicRegistryProvider {
    public NConfiguredFeatureProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
//        add(registries, entries, NConfiguredFeatures.PATCH_NEARS);
//        add(registries, entries, NConfiguredFeatures.PATCH_FAAR_GROWTH);
//        add(registries, entries, NConfiguredFeatures.PATCH_SOUL_BERRY_BUSH);
//        add(registries, entries, NConfiguredFeatures.PATCH_CINDER_GRASS);
//
        NConfiguredFeatures.features.forEach(configuredFeatureRegistryKey -> {
            add(registries, entries, configuredFeatureRegistryKey);
        });
    }


    private void add(HolderLookup.Provider registries, Entries entries, ResourceKey<ConfiguredFeature<?, ?>> resourceKey) {
        HolderLookup.RegistryLookup<ConfiguredFeature<?, ?>> configuredFeatureRegistryLookup = registries.lookupOrThrow(Registries.CONFIGURED_FEATURE);

        entries.add(resourceKey, configuredFeatureRegistryLookup.getOrThrow(resourceKey).value());
    }
    @Override
    public String getName() {
        return "worldgen/configured_feature";
    }
}
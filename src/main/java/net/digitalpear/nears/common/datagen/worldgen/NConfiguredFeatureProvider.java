package net.digitalpear.nears.common.datagen.worldgen;

import net.digitalpear.nears.init.NConfiguredFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.concurrent.CompletableFuture;

public class NConfiguredFeatureProvider extends FabricDynamicRegistryProvider {
    public NConfiguredFeatureProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
//        add(registries, entries, NConfiguredFeatures.PATCH_NEARS);
//        add(registries, entries, NConfiguredFeatures.PATCH_FAAR_GROWTH);
//        add(registries, entries, NConfiguredFeatures.PATCH_SOUL_BERRY_BUSH);
//        add(registries, entries, NConfiguredFeatures.PATCH_CINDER_GRASS);
//
        NConfiguredFeatures.features.forEach(configuredFeatureRegistryKey -> {
            add(registries, entries, configuredFeatureRegistryKey);
        });
    }


    private void add(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryKey<ConfiguredFeature<?, ?>> resourceKey) {
        RegistryWrapper.Impl<ConfiguredFeature<?, ?>> configuredFeatureRegistryLookup = registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE);

        entries.add(resourceKey, configuredFeatureRegistryLookup.getOrThrow(resourceKey).value());
    }
    @Override
    public String getName() {
        return "worldgen/configured_feature";
    }
}
package net.digitalpear.nears;

import net.digitalpear.nears.common.datagen.*;
import net.digitalpear.nears.common.datagen.provider.NearsRecipeProvider;
import net.digitalpear.nears.common.datagen.tags.NearsBiomeTagProvider;
import net.digitalpear.nears.common.datagen.tags.NearsBlockTagProvider;
import net.digitalpear.nears.common.datagen.tags.NearsItemTagProvider;
import net.digitalpear.nears.common.datagen.provider.worldgen.NConfiguredFeatureProvider;
import net.digitalpear.nears.common.datagen.provider.worldgen.NPlacedFeatureProvider;
import net.digitalpear.nears.init.NConfiguredFeatures;
import net.digitalpear.nears.init.NPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class NearsDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

        fabricDataGenerator.createPack().addProvider(NearsModelProvider::new);
        fabricDataGenerator.createPack().addProvider(NearsBlockLootTableProvider::new);
        fabricDataGenerator.createPack().addProvider(NearsLanguageProvider::new);
        fabricDataGenerator.createPack().addProvider(NearsRecipeProvider::new);
        fabricDataGenerator.createPack().addProvider(NearsAdvancementProvider::new);

        fabricDataGenerator.createPack().addProvider(NearsBlockTagProvider::new);
        fabricDataGenerator.createPack().addProvider(NearsItemTagProvider::new);
        fabricDataGenerator.createPack().addProvider(NearsBiomeTagProvider::new);

        fabricDataGenerator.createPack().addProvider(NConfiguredFeatureProvider::new);
        fabricDataGenerator.createPack().addProvider(NPlacedFeatureProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, NConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, NPlacedFeatures::bootstrap);
    }
}

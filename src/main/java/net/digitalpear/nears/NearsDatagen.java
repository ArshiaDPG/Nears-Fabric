package net.digitalpear.nears;

import net.digitalpear.nears.common.datagen.NearsBlockLootTables;
import net.digitalpear.nears.common.datagen.NearsLanguageProvider;
import net.digitalpear.nears.common.datagen.NearsModelGen;
import net.digitalpear.nears.common.datagen.NearsRecipeGen;
import net.digitalpear.nears.common.datagen.tags.NearsBlockTagGen;
import net.digitalpear.nears.common.datagen.worldgen.NConfiguredFeatureProvider;
import net.digitalpear.nears.common.datagen.worldgen.NPlacedFeatureProvider;
import net.digitalpear.nears.init.NConfiguredFeatures;
import net.digitalpear.nears.init.NPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class NearsDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

        fabricDataGenerator.createPack().addProvider(NearsModelGen::new);
        fabricDataGenerator.createPack().addProvider(NearsBlockLootTables::new);
        fabricDataGenerator.createPack().addProvider(NearsLanguageProvider::new);
        fabricDataGenerator.createPack().addProvider(NearsRecipeGen::new);

        fabricDataGenerator.createPack().addProvider(NearsBlockTagGen::new);

        fabricDataGenerator.createPack().addProvider(NConfiguredFeatureProvider::new);
        fabricDataGenerator.createPack().addProvider(NPlacedFeatureProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, NConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, NPlacedFeatures::bootstrap);
    }
}

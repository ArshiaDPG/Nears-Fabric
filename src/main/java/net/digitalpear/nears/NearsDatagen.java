package net.digitalpear.nears;

import net.digitalpear.nears.common.datagen.NearsBlockLootTables;
import net.digitalpear.nears.common.datagen.NearsLanguageProvider;
import net.digitalpear.nears.common.datagen.NearsModelGen;
import net.digitalpear.nears.common.datagen.NearsRecipeGen;
import net.digitalpear.nears.common.datagen.tags.NearsBlockTagGen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class NearsDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.createPack().addProvider(NearsModelGen::new);
        fabricDataGenerator.createPack().addProvider(NearsBlockLootTables::new);
        fabricDataGenerator.createPack().addProvider(NearsLanguageProvider::new);
        fabricDataGenerator.createPack().addProvider(NearsRecipeGen::new);
        fabricDataGenerator.createPack().addProvider(NearsBlockTagGen::new);
    }
}

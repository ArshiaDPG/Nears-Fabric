package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class NearsLanguageProvider extends FabricLanguageProvider {
    public NearsLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {

        translationBuilder.add(NItems.NEAR, "Near");
        translationBuilder.add(NItems.NEAR_SEEDS, "Near Seeds");
        translationBuilder.add(NBlocks.NEAR_BULB, "Near Bulb");

        translationBuilder.add(NItems.FAAR, "Faar");
        translationBuilder.add(NItems.FAAR_SEEDS, "Faar Seeds");
        translationBuilder.add(NBlocks.FAAR_GROWTH, "Faar Growth");
        translationBuilder.add(NBlocks.FAAR_BUNDLE, "Bundle of Faars");


        translationBuilder.add(NItems.SOUL_BERRIES, "Soul Berries");
        translationBuilder.add(NItems.SOUL_BERRY_PIPS, "Soul Berry Pips");
        translationBuilder.add(NBlocks.SOUL_BERRY_BUSH, "Soul Berry Bush");
        translationBuilder.add(NItems.SOULLESS_PASTRY, "Soulless Pastry");


        translationBuilder.add(NItems.CINDER_GRAIN, "Cinder Grain");
        translationBuilder.add(NItems.CINDER_SEEDS, "Cinder Seeds");
        translationBuilder.add(NBlocks.CINDER_GRASS, "Cinder Grass");
        translationBuilder.add(NBlocks.POTTED_CINDER_GRASS, "Potted Cinder Grass");
        translationBuilder.add(NBlocks.CINDER_WHEAT, "Cinder Wheat");
    }
}

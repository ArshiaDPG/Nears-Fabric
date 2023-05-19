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

        /*
            Near Plants
         */
        translationBuilder.add(NItems.NEAR, "Near");
        translationBuilder.add(NItems.NEAR_SEEDS, "Near Seeds");
        translationBuilder.add(NBlocks.NEAR_BULB, "Near Bulb");


        /*
            Faar Plants
         */
        translationBuilder.add(NItems.FAAR, "Faar");
        translationBuilder.add(NItems.FAAR_SEEDS, "Faar Seeds");
        translationBuilder.add(NBlocks.FAAR_GROWTH, "Faar Growth");
        translationBuilder.add(NBlocks.FAAR_BUNDLE, "Bundle of Faars");


        /*
            Soul Crops
         */
        translationBuilder.add(NItems.SOUL_BERRIES, "Soul Berries");
        translationBuilder.add(NItems.SOUL_BERRY_PIPS, "Soul Berry Pips");
        translationBuilder.add(NBlocks.SOUL_BERRY_BUSH, "Soul Berry Bush");



        /*
            Cinder Plants
         */
        translationBuilder.add(NItems.CINDER_GRAIN, "Cinder Grain");
        translationBuilder.add(NItems.CINDER_SEEDS, "Cinder Seeds");
        translationBuilder.add(NBlocks.CINDER_GRASS, "Cinder Grass");
        translationBuilder.add(NBlocks.POTTED_CINDER_GRASS, "Potted Cinder Grass");
        translationBuilder.add(NBlocks.CINDER_WHEAT, "Cinder Wheat");
        translationBuilder.add(NBlocks.CINDER_BALE, "Bale of Cinder");

        /*
            Foods
         */
        translationBuilder.add(NItems.SOULLESS_PASTRY, "Soulless Pastry");
        translationBuilder.add(NItems.NETHER_STEW, "Nether Stew");



        /*
            Advancements
         */
        translationBuilder.add("advancements.nether.symbiotic.title", "Symbiotic");
        translationBuilder.add("advancements.nether.symbiotic.description", "Obtain any nether fruit.");

        translationBuilder.add("advancements.nether.oh_how_faar_we_go.title", "Oh How Faar We Go");
        translationBuilder.add("advancements.nether.oh_how_faar_we_go.description", "Obtain every nether fruit.");


        translationBuilder.add("advancements.nether.a_pie_for_the_soul.title", "A Pie For the Soul");
        translationBuilder.add("advancements.nether.a_pie_for_the_soul.description", "Craft a Soulless Pastry.");
    }
}

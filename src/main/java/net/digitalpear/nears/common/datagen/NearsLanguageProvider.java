package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class NearsLanguageProvider extends FabricLanguageProvider {

    public NearsLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }


    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
                /*
            Near Plants
         */
        translationBuilder.add(NItems.NEAR, "Near");
        translationBuilder.add(NItems.NEAR_SPORES, "Near Spores");
        translationBuilder.add(NBlocks.NEAR_HANG, "Near Hang");
        translationBuilder.add(NBlocks.NEAR_HANG_STEM, "Near Hang Stem");
        translationBuilder.add(NBlocks.NEAR_TWIG_BLOCK, "Block of Near Twigs");
        translationBuilder.add(NItems.NEAR_TWIG, "Near Twig");



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
        translationBuilder.add(NItems.CINDER_SANGAK, "Cinder Sangak");
        translationBuilder.add(NItems.CINDER_SEEDS, "Cinder Seeds");
        translationBuilder.add(NBlocks.CINDER_GRASS, "Cinder Grass");
        translationBuilder.add(NBlocks.POTTED_CINDER_GRASS, "Potted Cinder Grass");
        translationBuilder.add(NBlocks.CINDER_GRAIN, "Cinder Grain");
        translationBuilder.add(NBlocks.CINDER_BALE, "Bale of Cinder");

        /*
            Foods
         */
        translationBuilder.add(NItems.SOULLESS_PASTRY, "Soulless Pastry");
        translationBuilder.add(NItems.NETHER_STEW, "Nether Stew");
        translationBuilder.add(NItems.GLOW_SALAD, "Glow Salad");



        /*
            Advancements
         */
        translationBuilder.add("advancements.nether.symbiotic.title", "Symbiotic");
        translationBuilder.add("advancements.nether.symbiotic.description", "Obtain any nether fruit.");

        translationBuilder.add("advancements.nether.oh_how_faar_we_go.title", "Oh How Faar We Go");
        translationBuilder.add("advancements.nether.oh_how_faar_we_go.description", "Obtain every nether fruit.");


        translationBuilder.add("advancements.nether.a_pie_for_the_soul.title", "A Pie For the Soul");
        translationBuilder.add("advancements.nether.a_pie_for_the_soul.description", "Craft a Soulless Pastry.");

        translationBuilder.add("advancements.nether.volcanic_botany.title", "Volcanic Botany");
        translationBuilder.add("advancements.nether.volcanic_botany.description", "Plant some Cinder Seeds against magma blocks or basalt.");
    }
}

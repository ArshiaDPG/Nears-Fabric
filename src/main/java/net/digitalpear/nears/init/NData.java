package net.digitalpear.nears.init;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class NData {

    public static void registerFuels(){
        FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
        fuelRegistry.add(NBlocks.FAAR_BUNDLE, 200);
    }
    public static void registerCompostibles(){
        CompostingChanceRegistry compostingChanceRegistry = CompostingChanceRegistry.INSTANCE;
        compostingChanceRegistry.add(NItems.FAAR_SEEDS, 0.3f);
        compostingChanceRegistry.add(NItems.SOUL_BERRY_PIPS, 0.3f);
        compostingChanceRegistry.add(NBlocks.FAAR_BUNDLE, 1.0f);
        compostingChanceRegistry.add(NItems.SOUL_BERRIES, 0.5f);
        compostingChanceRegistry.add(NItems.FAAR, 0.3f);
        compostingChanceRegistry.add(NItems.NEAR, 0.4f);
    }

    public static void init(){
        registerCompostibles();
        registerFuels();
    }
}

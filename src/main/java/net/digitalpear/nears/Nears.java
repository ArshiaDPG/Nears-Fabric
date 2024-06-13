package net.digitalpear.nears;

import net.digitalpear.nears.common.worldgen.NFeature;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.NPlacedFeatures;
import net.digitalpear.nears.init.data.NData;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Nears implements ModInitializer {

    public static final String MOD_ID = "nears";
    public static Identifier id(String name){return Identifier.of(MOD_ID, name);}

    @Override
    public void onInitialize() {
        NBlocks.init();
        NItems.init();
        NData.init();
        NFeature.init();
        NPlacedFeatures.init();
    }
}

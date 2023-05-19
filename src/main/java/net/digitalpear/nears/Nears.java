package net.digitalpear.nears;

import net.digitalpear.nears.init.*;
import net.digitalpear.nears.init.data.NData;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Nears implements ModInitializer {

    public static final String MOD_ID = "nears";
    public static Identifier id(String name){return new Identifier(MOD_ID, name);}
    public static String idString(String name){return MOD_ID +  ":" + name;}
    @Override
    public void onInitialize() {
        NBlocks.init();
        NItems.init();
        NData.init();

        NPlacedFeatures.init();
    }
}

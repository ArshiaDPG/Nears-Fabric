package net.digitalpear.nears;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NData;
import net.digitalpear.nears.init.NItems;
import net.fabricmc.api.ModInitializer;

public class Nears implements ModInitializer {

    public static final String MOD_ID = "nears";


    @Override
    public void onInitialize() {
        NBlocks.init();
        NItems.init();
        NData.init();
    }
}

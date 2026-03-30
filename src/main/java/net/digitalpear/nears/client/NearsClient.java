package net.digitalpear.nears.client;

import net.digitalpear.nears.init.NBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NearsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
		//No longer needed
        //BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT, NBlocks.SOUL_BERRY_BUSH, NBlocks.FAAR_GROWTH, NBlocks.CINDER_GRAIN, NBlocks.CINDER_GRASS, NBlocks.POTTED_CINDER_GRASS);
        //BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT, NBlocks.NEAR_HANG, NBlocks.NEAR_HANG_STEM);
    }
}

package net.digitalpear.nears.client;

import net.digitalpear.nears.init.NBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

@Environment(EnvType.CLIENT)
public class NearsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT, NBlocks.SOUL_BERRY_BUSH, NBlocks.FAAR_GROWTH, NBlocks.CINDER_GRAIN, NBlocks.CINDER_GRASS, NBlocks.POTTED_CINDER_GRASS);
        BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT_MIPPED, NBlocks.NEAR_HANG, NBlocks.NEAR_HANG_STEM);
    }
}

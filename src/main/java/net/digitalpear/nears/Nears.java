package net.digitalpear.nears;

import net.digitalpear.nears.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.UndergroundConfiguredFeatures;

public class Nears implements ModInitializer {

    public static final String MOD_ID = "nears";
    public static Identifier id(String name){return new Identifier(MOD_ID, name);}

    @Override
    public void onInitialize() {
        NBlocks.init();
        NItems.init();
        NData.init();

        NConfiguredFeatures.init();
        NPlacedFeatures.init();
    }


}

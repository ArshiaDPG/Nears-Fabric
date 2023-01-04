package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;

public class NearsModelGen extends FabricModelProvider {
    public NearsModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerSoulBerryBush(blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(NItems.NEAR, Models.GENERATED);
        itemModelGenerator.register(NItems.FAAR, Models.GENERATED);
        itemModelGenerator.register(NItems.SOULLESS_PASTRY, Models.GENERATED);
    }

    private void registerSoulBerryBush(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerItemModel(NItems.SOUL_BERRIES);
        blockStateModelGenerator.registerItemModel(NItems.SOUL_BERRY_PIPS);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(NBlocks.SOUL_BERRY_BUSH)
                .coordinate(BlockStateVariantMap.create(Properties.AGE_3).register((stage) -> BlockStateVariant.create().put(VariantSettings.MODEL,
                        blockStateModelGenerator.createSubModel(NBlocks.SOUL_BERRY_BUSH, "_stage" + stage, Models.CROSS, TextureMap::cross)))));
    }
}

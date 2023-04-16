package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class NearsModelGen extends FabricModelProvider {
    public NearsModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        registerSoulBerryBush(blockStateModelGenerator);

        registerFaarPlants(blockStateModelGenerator);

        registerNearPlants(blockStateModelGenerator);

        registerCinderPlants(blockStateModelGenerator);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(NItems.SOULLESS_PASTRY, Models.GENERATED);
    }

    private void registerFaarPlants(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerItemModel(NItems.FAAR_SEEDS);
        blockStateModelGenerator.registerItemModel(NItems.FAAR);
        blockStateModelGenerator.registerSimpleCubeAll(NBlocks.FAAR_BUNDLE);

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(NBlocks.FAAR_GROWTH)
                .coordinate(BlockStateVariantMap.create(Properties.AGE_3).register((stage) -> BlockStateVariant.create().put(VariantSettings.MODEL,
                        blockStateModelGenerator.createSubModel(NBlocks.FAAR_GROWTH, "_stage" + stage, Models.CROSS, TextureMap::cross)))));
    }

    private void registerSoulBerryBush(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerItemModel(NItems.SOUL_BERRIES);
        blockStateModelGenerator.registerItemModel(NItems.SOUL_BERRY_PIPS);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(NBlocks.SOUL_BERRY_BUSH)
                .coordinate(BlockStateVariantMap.create(Properties.AGE_3).register((stage) -> BlockStateVariant.create().put(VariantSettings.MODEL,
                        blockStateModelGenerator.createSubModel(NBlocks.SOUL_BERRY_BUSH, "_stage" + stage, Models.CROSS, TextureMap::cross)))));
    }


    private void registerNearPlants(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerItemModel(NItems.NEAR);
        blockStateModelGenerator.registerItemModel(NItems.NEAR_SEEDS);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(NBlocks.NEAR_BULB).coordinate(BlockStateVariantMap.create(Properties.AGE_3).register((stage) -> {
            Identifier var2 = null;
            if (stage < 4) {
                var2 = ModelIds.getBlockSubModelId(NBlocks.NEAR_BULB, "_stage" + stage);
            }
            return BlockStateVariant.create().put(VariantSettings.MODEL, var2);
        })));
    }

    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier(Nears.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }
    public static final Model CINDER_GRASS_BASE = block("cinder_grass_base", TextureKey.ALL);

    private void registerCinderPlants(BlockStateModelGenerator blockStateModelGenerator){
        blockStateModelGenerator.registerItemModel(NItems.CINDER_SEEDS);
        blockStateModelGenerator.registerItemModel(NItems.CINDER_GRAIN);

        /*
            This one block took 3 hours. I still don't understand most of it.
         */
        registerHorizontallyRotatingCrop(blockStateModelGenerator, NBlocks.CINDER_WHEAT);


        blockStateModelGenerator.registerFlowerPotPlant(NBlocks.CINDER_GRASS, NBlocks.POTTED_CINDER_GRASS, BlockStateModelGenerator.TintType.NOT_TINTED);
    }




    public static void registerHorizontallyRotatingCrop(BlockStateModelGenerator blockStateModelGenerator, Block block){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
                .coordinate(BlockStateVariantMap.create(Properties.AGE_7).register((stage) -> {

            Identifier var2 = ModelIds.getBlockSubModelId(block, "" + stage);

            /*
                Register model
             */
            CINDER_GRASS_BASE.upload(block, "" + stage,
            TextureMap.all(getId(block).withSuffixedPath("" + stage)),
            blockStateModelGenerator.modelCollector);

            return BlockStateVariant.create().put(VariantSettings.MODEL, var2);
        })));
    }
    public static void registerHorizontallyRotatingCrop(BlockStateModelGenerator blockStateModelGenerator, Block block, IntProperty age){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
                .coordinate(BlockStateVariantMap.create(age).register((stage) -> {

                    Identifier var2 = ModelIds.getBlockSubModelId(block, "" + stage);

            /*
                Register model
             */
                    CINDER_GRASS_BASE.upload(block, "" + stage,
                            TextureMap.all(getId(block).withSuffixedPath("" + stage)),
                            blockStateModelGenerator.modelCollector);

                    return BlockStateVariant.create().put(VariantSettings.MODEL, var2);
                })));
    }

    private static Identifier getId(Block block){
        return Registries.BLOCK.getId(block).withPrefixedPath("block/");
    }
}

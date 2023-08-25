package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.blocks.NearHangStemBlock;
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

public class NearsModelProvider extends FabricModelProvider {
    public NearsModelProvider(FabricDataOutput output) {
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
        itemModelGenerator.register(NItems.NETHER_STEW, Models.GENERATED);
        itemModelGenerator.register(NItems.GLOW_SALAD, Models.GENERATED);

    }


    private void registerFaarPlants(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerItemModel(NItems.FAAR_SEEDS);
        blockStateModelGenerator.registerItemModel(NItems.FAAR);
        blockStateModelGenerator.registerSimpleCubeAll(NBlocks.FAAR_BUNDLE);

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(NBlocks.FAAR_GROWTH)
                .coordinate(BlockStateVariantMap.create(Properties.AGE_3).register((stage) -> BlockStateVariant.create().put(VariantSettings.MODEL,
                        blockStateModelGenerator.createSubModel(NBlocks.FAAR_GROWTH, "" + stage, Models.CROSS, TextureMap::cross)))));
    }

    private void registerSoulBerryBush(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerItemModel(NItems.SOUL_BERRIES);
        blockStateModelGenerator.registerItemModel(NItems.SOUL_BERRY_PIPS);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(NBlocks.SOUL_BERRY_BUSH)
                .coordinate(BlockStateVariantMap.create(Properties.AGE_3).register((stage) -> BlockStateVariant.create().put(VariantSettings.MODEL,
                        blockStateModelGenerator.createSubModel(NBlocks.SOUL_BERRY_BUSH, "" + stage, Models.CROSS, TextureMap::cross)))));
    }

    public static final TextureKey CAP_TOP = TextureKey.of("cap_top");
    public static final TextureKey CAP_SIDE = TextureKey.of("cap_side");
    public static final TextureKey CAP_BOTTOM = TextureKey.of("cap_bottom");
    public static final Model TEMPLATE_NEAR_HANG = block("template_near_hang", TextureKey.STEM, CAP_TOP, CAP_SIDE, CAP_BOTTOM);

    private void registerNearPlants(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerItemModel(NItems.NEAR);
        blockStateModelGenerator.registerItemModel(NItems.NEAR_SPORES);

        blockStateModelGenerator.registerItemModel(NItems.NEAR_TWIG);
        blockStateModelGenerator.registerSimpleCubeAll(NBlocks.NEAR_TWIG_BLOCK);

        blockStateModelGenerator.registerSingleton(NBlocks.NEAR_HANG, new TextureMap()
                .put(CAP_TOP, getId(NBlocks.NEAR_HANG).withSuffixedPath("_cap_top"))
                .put(CAP_SIDE, getId(NBlocks.NEAR_HANG).withSuffixedPath("_cap_side"))
                .put(CAP_BOTTOM, getId(NBlocks.NEAR_HANG).withSuffixedPath("_cap_bottom"))
                .put(TextureKey.STEM, getId(NBlocks.NEAR_HANG).withSuffixedPath("_base")),
                TEMPLATE_NEAR_HANG);

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(NBlocks.NEAR_HANG_STEM)
                .coordinate(BlockStateVariantMap.create(NearHangStemBlock.SUPPORTED, NearHangStemBlock.AGE)
                        .register((supported, stage) -> {

                            String name = (supported ? "" : "_base") + stage;

                            Identifier modelName = ModelIds.getBlockSubModelId(NBlocks.NEAR_HANG_STEM, name);
                            /*
                                Register model
                             */
                            if (supported){
                                Models.CROSS.upload(NBlocks.NEAR_HANG_STEM, name,
                                        TextureMap.cross(getId(NBlocks.NEAR_HANG_STEM).withSuffixedPath(name)),
                                        blockStateModelGenerator.modelCollector);
                            }
                            else{
                                Models.CROSS.upload(NBlocks.NEAR_HANG_STEM, name,
                                        TextureMap.cross(getId(NBlocks.NEAR_HANG_STEM).withSuffixedPath("_base")),
                                        blockStateModelGenerator.modelCollector);
                            }

                            return BlockStateVariant.create().put(VariantSettings.MODEL, modelName);
                        })));
    }


    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier(Nears.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }
    public static final Model TEMPLATE_CINDER_WHEAT = block("template_cinder_wheat", TextureKey.ALL);

    private void registerCinderPlants(BlockStateModelGenerator blockStateModelGenerator){
        blockStateModelGenerator.registerItemModel(NItems.CINDER_SEEDS);
        blockStateModelGenerator.registerItemModel(NItems.CINDER_GRAIN);
        blockStateModelGenerator.registerItemModel(NItems.CINDER_SANGAK);

        /*
            This one block took 3 hours. I still don't understand most of it.
         */
        registerHorizontallyRotatingCrop(blockStateModelGenerator, NBlocks.CINDER_GRAIN, Properties.AGE_7);


        blockStateModelGenerator.registerFlowerPotPlant(NBlocks.CINDER_GRASS, NBlocks.POTTED_CINDER_GRASS, BlockStateModelGenerator.TintType.NOT_TINTED);

        blockStateModelGenerator.registerAxisRotated(NBlocks.CINDER_BALE, TexturedModel.CUBE_COLUMN, TexturedModel.CUBE_COLUMN_HORIZONTAL);
    }

    public static void registerHorizontallyRotatingCrop(BlockStateModelGenerator blockStateModelGenerator, Block block, IntProperty age){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
                .coordinate(BlockStateVariantMap.create(age).register((stage) -> {

                    Identifier var2 = ModelIds.getBlockSubModelId(block, "" + stage);
                /*
                    Register model
                 */
                    TEMPLATE_CINDER_WHEAT.upload(block, "" + stage,
                            TextureMap.all(getId(block).withSuffixedPath("" + stage)),
                            blockStateModelGenerator.modelCollector);

                    return BlockStateVariant.create().put(VariantSettings.MODEL, var2);
                })));
    }

    private static Identifier getId(Block block){
        return Registries.BLOCK.getId(block).withPrefixedPath("block/");
    }
}

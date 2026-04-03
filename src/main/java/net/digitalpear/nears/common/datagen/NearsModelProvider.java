package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.blocks.FaarGrowthBlock;
import net.digitalpear.nears.common.blocks.NearHangStemBlock;
import net.digitalpear.nears.common.blocks.SoulBerryBushBlock;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Optional;

public class NearsModelProvider extends FabricModelProvider {

    private static final PropertyDispatch<VariantMutator> NORTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS = PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
            .select(Direction.EAST, BlockModelGenerators.Y_ROT_90)
            .select(Direction.SOUTH, BlockModelGenerators.Y_ROT_180)
            .select(Direction.WEST, BlockModelGenerators.Y_ROT_270)
            .select(Direction.NORTH, BlockModelGenerators.NOP);



    public NearsModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        registerSoulBerryBush(blockModelGenerators);

        registerFaarPlants(blockModelGenerators);

        registerNearPlants(blockModelGenerators);

        registerCinderPlants(blockModelGenerators);
    }


    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        ItemModelOutput output = itemModelGenerators.itemModelOutput;
        flatItem(itemModelGenerators, output, NItems.SOULLESS_PASTRY);
        flatItem(itemModelGenerators, output, NItems.NETHER_STEW);
        flatItem(itemModelGenerators, output, NItems.GLOW_SALAD);
    }
    private void flatItem(ItemModelGenerators itemModelGenerators, ItemModelOutput modelOutput, Item item) {
        modelOutput.accept(item, ItemModelUtils.plainModel(itemModelGenerators.createFlatItemModel(item, ModelTemplates.FLAT_ITEM)));
    }


    private void registerFaarPlants(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleFlatItemModel(NItems.FAAR_SEEDS);
        blockStateModelGenerator.registerSimpleFlatItemModel(NItems.FAAR);
        blockStateModelGenerator.createTrivialCube(NBlocks.FAAR_BUNDLE);

        blockStateModelGenerator.blockStateOutput.accept(
            MultiVariantGenerator
                .dispatch(NBlocks.FAAR_GROWTH)
                .with(
                    PropertyDispatch
                        .initial(FaarGrowthBlock.AGE)
                        .generate((stage) -> BlockModelGenerators.plainVariant(blockStateModelGenerator.createSuffixedVariant(NBlocks.FAAR_GROWTH, "" + stage, ModelTemplates.CROSS, TextureMapping::cross)))
                )
        );
    }

    private void registerSoulBerryBush(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleFlatItemModel(NItems.SOUL_BERRIES);
        blockStateModelGenerator.registerSimpleFlatItemModel(NItems.SOUL_BERRY_PIPS);
        blockStateModelGenerator.blockStateOutput.accept(
            MultiVariantGenerator
                .dispatch(NBlocks.SOUL_BERRY_BUSH)
                .with(
                    PropertyDispatch
                        .initial(SoulBerryBushBlock.AGE)
                        .generate((stage) -> BlockModelGenerators.plainVariant(blockStateModelGenerator.createSuffixedVariant(NBlocks.SOUL_BERRY_BUSH, "" + stage, ModelTemplates.CROSS, TextureMapping::cross)))
                )
        );
    }

    public static final TextureSlot CAP_TOP = TextureSlot.create("cap_top");
    public static final TextureSlot CAP_SIDE = TextureSlot.create("cap_side");
    public static final TextureSlot CAP_BOTTOM = TextureSlot.create("cap_bottom");
    public static final ModelTemplate TEMPLATE_NEAR_HANG = block("template_near_hang", TextureSlot.STEM, CAP_TOP, CAP_SIDE, CAP_BOTTOM);

    private void registerNearPlants(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleFlatItemModel(NItems.NEAR);
        blockStateModelGenerator.registerSimpleFlatItemModel(NItems.NEAR_SPORES);

        blockStateModelGenerator.registerSimpleFlatItemModel(NItems.NEAR_TWIG);
        blockStateModelGenerator.createTrivialCube(NBlocks.NEAR_TWIG_BLOCK);

        blockStateModelGenerator.createTrivialBlock(
            NBlocks.NEAR_HANG,
            TexturedModel
                .createDefault(
                    _ -> new TextureMapping()
                        .put(CAP_TOP, getId(NBlocks.NEAR_HANG, "_cap_top"))
                        .put(CAP_SIDE, getId(NBlocks.NEAR_HANG, "_cap_side"))
                        .put(CAP_BOTTOM, getId(NBlocks.NEAR_HANG, "_cap_bottom"))
                        .put(TextureSlot.STEM, getId(NBlocks.NEAR_HANG, "_base")),
                    TEMPLATE_NEAR_HANG
                )
        );

        blockStateModelGenerator.blockStateOutput.accept(
            MultiVariantGenerator
                .dispatch(NBlocks.NEAR_HANG_STEM)
                .with(
                    PropertyDispatch
                        .initial(NearHangStemBlock.SUPPORTED, NearHangStemBlock.AGE)
                        .generate((supported, stage) -> {
                            String name = (supported ? "" : "_base") + stage;
                            Identifier modelName = ModelLocationUtils.getModelLocation(NBlocks.NEAR_HANG_STEM, name);
                            
                            //Register model
                            if (supported){
                                ModelTemplates.CROSS.createWithOverride(
                                    NBlocks.NEAR_HANG_STEM, name,
                                    TextureMapping.cross(getId(NBlocks.NEAR_HANG_STEM, name)),
                                    blockStateModelGenerator.modelOutput
                                );
                            }
                            else{
                                ModelTemplates.CROSS.createWithOverride(
                                    NBlocks.NEAR_HANG_STEM, name,
                                    TextureMapping.cross(getId(NBlocks.NEAR_HANG_STEM, "_base")),
                                    blockStateModelGenerator.modelOutput
                                );
                            }

                            return BlockModelGenerators.plainVariant(modelName);
                        })
                )
        );
    }


    private static ModelTemplate block(String parent, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(Optional.of(Nears.id("block/" + parent)), Optional.empty(), requiredTextureKeys);
    }
    public static final ModelTemplate TEMPLATE_CINDER_WHEAT = block("template_cinder_wheat", TextureSlot.ALL);

    private void registerCinderPlants(BlockModelGenerators blockStateModelGenerator){
        blockStateModelGenerator.registerSimpleFlatItemModel(NItems.CINDER_SEEDS);
        blockStateModelGenerator.registerSimpleFlatItemModel(NItems.CINDER_GRAIN);
        blockStateModelGenerator.registerSimpleFlatItemModel(NItems.CINDER_SANGAK);

        /*
            This one block took 3 hours. I still don't understand most of it.
         */
        registerHorizontallyRotatingCrop(blockStateModelGenerator, NBlocks.CINDER_GRAIN, BlockStateProperties.AGE_7);

        blockStateModelGenerator.createPlantWithDefaultItem(NBlocks.CINDER_GRASS, NBlocks.POTTED_CINDER_GRASS, BlockModelGenerators.PlantType.NOT_TINTED);
        blockStateModelGenerator.createRotatedPillarWithHorizontalVariant(NBlocks.CINDER_BALE, TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
    }

    public static void registerHorizontallyRotatingCrop(BlockModelGenerators blockStateModelGenerator, Block block, IntegerProperty age){
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
            .with(
                PropertyDispatch
                    .initial(age)
                    .generate((stage) -> {
                        String suffix = "" + stage;
                        
                        //Register model
                        Identifier modelName = TEMPLATE_CINDER_WHEAT.createWithSuffix(
                            block, suffix,
                            TextureMapping.cube(getId(block, suffix)),
                            blockStateModelGenerator.modelOutput
                        );
                        return BlockModelGenerators.plainVariant(modelName);
                    })
            )
            .with(NORTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS));
    }

    private static Material getId(Block block, String suffix){
        return new Material(BuiltInRegistries.BLOCK.getKey(block).withPrefix("block/").withSuffix(suffix));
    }

}

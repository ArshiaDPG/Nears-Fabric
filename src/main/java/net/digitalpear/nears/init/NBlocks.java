package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.blocks.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

public class NBlocks {

    private static ResourceKey<Block> keyOf(String id) {
        return ResourceKey.create(Registries.BLOCK, Nears.id(id));
    }

    public static Block register(String blockID, BlockBehaviour.Properties properties) {
        return register(blockID, Block::new, properties);
    }
    public static Block register(String blockID, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        Block block = Blocks.register(keyOf(blockID), factory, properties);
        NItems.registerBlock(block);
        return block;
    }

    public static Block createBlockWithoutItem(String blockID, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return Registry.register(BuiltInRegistries.BLOCK, Nears.id(blockID), factory.apply(settings.setId(keyOf(blockID))));
    }

    public static final Block NEAR_HANG_STEM = createBlockWithoutItem("near_hang_stem", NearHangStemBlock::new,
            BlockBehaviour.Properties.of().randomTicks().strength(0.8f, 0.6f).sound(SoundType.STEM)
                    .noCollision().noOcclusion());

    public static final Block NEAR_HANG = createBlockWithoutItem("near_hang", NearHangBlock::new,
            BlockBehaviour.Properties.of().randomTicks().strength(0.8f, 0.6f).sound(SoundType.WART_BLOCK)
                    .noCollision().noOcclusion());

    public static final Block NEAR_TWIG_BLOCK = register("near_twig_block", BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_HYPHAE).mapColor(MapColor.ICE));




    public static final Block FAAR_GROWTH = createBlockWithoutItem("faar_growth", FaarGrowthBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES));

    public static final Block FAAR_BUNDLE = register("faar_bundle", settings -> new FaarBundleBlock(new ColorRGBA(1622415), settings),BlockBehaviour.Properties.of()
            .mapColor(MapColor.WARPED_WART_BLOCK).strength(0.7F, 0.4F).jumpFactor(1.5f).sound(SoundType.WART_BLOCK));



    public static final Block SOUL_BERRY_BUSH = createBlockWithoutItem("soul_berry_bush", SoulBerryBushBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)
                    .mapColor(state -> state.getValue(SoulBerryBushBlock.AGE) == 3 ? MapColor.COLOR_ORANGE : MapColor.TERRACOTTA_BROWN)
                    .randomTicks()
                    .noCollision()
                    .sound(SoundType.SWEET_BERRY_BUSH)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .lightLevel(state -> state.getValue(SoulBerryBushBlock.AGE) * 2));


    public static final Block CINDER_GRASS = register("cinder_grass", CinderGrassBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.ROOTS)
                    .offsetType(BlockBehaviour.OffsetType.XZ).replaceable());

    public static final Block POTTED_CINDER_GRASS = createBlockWithoutItem("potted_cinder_grass", settings ->  new FlowerPotBlock(CINDER_GRASS, settings),
            BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));


    public static final Block CINDER_GRAIN = createBlockWithoutItem("cinder_grain", CInderGrainCropBlock::new,
            BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_GRAY).noCollision().instabreak().sound(SoundType.ROOTS));

    public static final Block CINDER_BALE = register("cinder_bale", HayBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK).mapColor(MapColor.COLOR_GRAY));

    
    public static void init() {
    }
}

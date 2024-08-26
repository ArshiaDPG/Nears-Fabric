package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ColorCode;

public class NBlocks {

    public static BlockItem createBlockItem(String blockID, Block block){
        return Registry.register(Registries.ITEM, Nears.id(blockID), new BlockItem(block, new Item.Settings()));
    }
    public static Block createBlockWithItem(String blockID, Block block){
        createBlockItem(blockID, block);
        return createBlockWithoutItem(blockID, block);
    }
    public static Block createBlockWithoutItem(String blockID, Block block){
        return Registry.register(Registries.BLOCK, Nears.id(blockID), block);
    }

    public static final Block NEAR_HANG_STEM = createBlockWithoutItem("near_hang_stem",
            new NearHangStemBlock(AbstractBlock.Settings.create().ticksRandomly().strength(0.8f, 0.6f).sounds(BlockSoundGroup.NETHER_STEM)
                    .noCollision().nonOpaque()));

    public static final Block NEAR_HANG = createBlockWithoutItem("near_hang",
            new NearHangBlock(AbstractBlock.Settings.create().ticksRandomly().strength(0.8f, 0.6f).sounds(BlockSoundGroup.WART_BLOCK)
                    .noCollision().nonOpaque()));

    public static final Block NEAR_TWIG_BLOCK = createBlockWithItem("near_twig_block", new Block(AbstractBlock.Settings.copy(Blocks.CRIMSON_HYPHAE).mapColor(MapColor.PALE_PURPLE)));




    public static final Block FAAR_GROWTH = createBlockWithoutItem("faar_growth",
            new FaarGrowthBlock(AbstractBlock.Settings.copy(Blocks.TWISTING_VINES)));

    public static final Block FAAR_BUNDLE = createBlockWithItem("faar_bundle", new FaarBundleBlock(new ColorCode(1622415),AbstractBlock.Settings.create()
            .mapColor(MapColor.BRIGHT_TEAL).strength(0.7F, 0.4F).jumpVelocityMultiplier(1.5f).sounds(BlockSoundGroup.WART_BLOCK)));





    public static final Block SOUL_BERRY_BUSH = createBlockWithoutItem("soul_berry_bush",
            new SoulBerryBushBlock(AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH)
                    .mapColor(state -> state.get(SoulBerryBushBlock.AGE) == 3 ? MapColor.ORANGE : MapColor.TERRACOTTA_BROWN)
                    .ticksRandomly()
                    .noCollision()
                    .sounds(BlockSoundGroup.SWEET_BERRY_BUSH)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .luminance(state -> state.get(SoulBerryBushBlock.AGE) * 2)));


    public static final Block CINDER_GRASS = createBlockWithItem("cinder_grass",
            new CinderGrassBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.GRAY)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.ROOTS)
                    .offset(AbstractBlock.OffsetType.XZ).replaceable()));

    public static final Block POTTED_CINDER_GRASS = createBlockWithoutItem("potted_cinder_grass", new FlowerPotBlock(CINDER_GRASS,
            AbstractBlock.Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));


    public static final Block CINDER_GRAIN = createBlockWithoutItem("cinder_grain",
            new CInderGrainCropBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.GRAY).noCollision().breakInstantly().sounds(BlockSoundGroup.ROOTS)));

    public static final Block CINDER_BALE = createBlockWithItem("cinder_bale",
            new HayBlock(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK).mapColor(MapColor.GRAY)));


    public static void init() {
    }
}

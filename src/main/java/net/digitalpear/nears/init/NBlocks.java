package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ColorCode;

import java.util.function.Function;

public class NBlocks {

    public static Item createBlockItem(Block block) {
        return Items.register(block);
    }

    private static RegistryKey<Block> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Nears.id(id));
    }
    public static Block createBlockWithItem(String blockID, AbstractBlock.Settings settings) {
        return createBlockWithItem(blockID, Block::new, settings);
    }
    public static Block createBlockWithItem(String blockID, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = Blocks.register(keyOf(blockID), factory, settings);
        createBlockItem(block);
        return block;
    }

    public static Block createBlockWithoutItem(String blockID, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return Registry.register(Registries.BLOCK, Nears.id(blockID), factory.apply(settings.registryKey(keyOf(blockID))));
    }

    public static final Block NEAR_HANG_STEM = createBlockWithoutItem("near_hang_stem", NearHangStemBlock::new,
            AbstractBlock.Settings.create().ticksRandomly().strength(0.8f, 0.6f).sounds(BlockSoundGroup.NETHER_STEM)
                    .noCollision().nonOpaque());

    public static final Block NEAR_HANG = createBlockWithoutItem("near_hang", NearHangBlock::new,
            AbstractBlock.Settings.create().ticksRandomly().strength(0.8f, 0.6f).sounds(BlockSoundGroup.WART_BLOCK)
                    .noCollision().nonOpaque());

    public static final Block NEAR_TWIG_BLOCK = createBlockWithItem("near_twig_block", AbstractBlock.Settings.copy(Blocks.CRIMSON_HYPHAE).mapColor(MapColor.PALE_PURPLE));




    public static final Block FAAR_GROWTH = createBlockWithoutItem("faar_growth", FaarGrowthBlock::new,
            AbstractBlock.Settings.copy(Blocks.TWISTING_VINES));

    public static final Block FAAR_BUNDLE = createBlockWithItem("faar_bundle", settings -> new FaarBundleBlock(new ColorCode(1622415), settings),AbstractBlock.Settings.create()
            .mapColor(MapColor.BRIGHT_TEAL).strength(0.7F, 0.4F).jumpVelocityMultiplier(1.5f).sounds(BlockSoundGroup.WART_BLOCK));





    public static final Block SOUL_BERRY_BUSH = createBlockWithoutItem("soul_berry_bush", SoulBerryBushBlock::new,
            AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH)
                    .mapColor(state -> state.get(SoulBerryBushBlock.AGE) == 3 ? MapColor.ORANGE : MapColor.TERRACOTTA_BROWN)
                    .ticksRandomly()
                    .noCollision()
                    .sounds(BlockSoundGroup.SWEET_BERRY_BUSH)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .luminance(state -> state.get(SoulBerryBushBlock.AGE) * 2));


    public static final Block CINDER_GRASS = createBlockWithItem("cinder_grass", CinderGrassBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.GRAY)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.ROOTS)
                    .offset(AbstractBlock.OffsetType.XZ).replaceable());

    public static final Block POTTED_CINDER_GRASS = createBlockWithoutItem("potted_cinder_grass", settings ->  new FlowerPotBlock(CINDER_GRASS, settings),
            AbstractBlock.Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY));


    public static final Block CINDER_GRAIN = createBlockWithoutItem("cinder_grain", CInderGrainCropBlock::new,
            AbstractBlock.Settings.create()
            .mapColor(MapColor.GRAY).noCollision().breakInstantly().sounds(BlockSoundGroup.ROOTS));

    public static final Block CINDER_BALE = createBlockWithItem("cinder_bale", HayBlock::new,
            AbstractBlock.Settings.copy(Blocks.HAY_BLOCK).mapColor(MapColor.GRAY));


    public static void init() {
    }
}

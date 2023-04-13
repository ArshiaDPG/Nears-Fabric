package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.blocks.FaarBundleBlock;
import net.digitalpear.nears.common.blocks.FaarGrowthBlock;
import net.digitalpear.nears.common.blocks.SoulBerryBushBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class NBlocks {

    public static BlockItem createBlockItem(String blockID, Block block){
        return Registry.register(Registries.ITEM, new Identifier(Nears.MOD_ID, blockID), new BlockItem(block, new Item.Settings()));
    }
    public static Block createBlockWithItem(String blockID, Block block){
        createBlockItem(blockID, block);
        return Registry.register(Registries.BLOCK, new Identifier(Nears.MOD_ID, blockID), block);
    }
    public static Block createBlockWithoutItem(String blockID, Block block){
        return Registry.register(Registries.BLOCK, new Identifier(Nears.MOD_ID, blockID), block);
    }


    public static final Block SOUL_BERRY_BUSH = createBlockWithoutItem("soul_berry_bush",
            new SoulBerryBushBlock(AbstractBlock.Settings.of(Material.PLANT,
                            state -> state.get(SoulBerryBushBlock.AGE) == 3 ? MapColor.ORANGE : MapColor.TERRACOTTA_BROWN)
                .ticksRandomly()
                .noCollision()
                .sounds(BlockSoundGroup.SWEET_BERRY_BUSH)
                .offsetType(AbstractBlock.OffsetType.XZ)
                .luminance(state -> state.get(SoulBerryBushBlock.AGE) == SoulBerryBushBlock.MAX_AGE ? 5 : 0)));

    public static final Block FAAR_GROWTH = createBlockWithoutItem("faar_growth",
            new FaarGrowthBlock(AbstractBlock.Settings.copy(Blocks.TWISTING_VINES)));

    public static final Block FAAR_BUNDLE = createBlockWithItem("faar_bundle", new FaarBundleBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC,
            MapColor.BRIGHT_TEAL).strength(0.7F, 0.4F).jumpVelocityMultiplier(1.5f).sounds(BlockSoundGroup.WART_BLOCK)));


    public static void init() {
    }
}

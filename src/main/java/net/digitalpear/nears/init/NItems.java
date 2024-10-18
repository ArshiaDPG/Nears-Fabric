package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.datagen.NearsRecipeGenerator;
import net.digitalpear.nears.common.items.FaarItem;
import net.digitalpear.nears.common.items.NetherStewItem;
import net.digitalpear.nears.init.data.NFoodComponents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;


public class NItems {
    private static Function<Item.Settings, Item> createBlockItemWithUniqueName(Block block) {
        return (settings) -> new BlockItem(block, settings.useItemPrefixedTranslationKey());
    }
    public static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Nears.id(id));
    }
    public static Item createItem(String id){
        return createItem(id, settings -> new Item(settings.useItemPrefixedTranslationKey()), new Item.Settings());
    }
    public static Item createItem(String id, Item.Settings settings){
        return createItem(id, Item::new, settings);
    }

    public static Item createBlockItem(String id, Block block){
        return Items.register(keyOf(id), createBlockItemWithUniqueName(block), new Item.Settings());
    }
    public static Item createItem(String id, Function<Item.Settings, Item> factory, Item.Settings settings){
        return Items.register(keyOf(id), factory, settings);
    }


    /*
        Farmables
     */
    public static final Item NEAR = createItem("near", new Item.Settings().food(NFoodComponents.NEAR));
    public static final Item FAAR = createItem("faar", FaarItem::new, new Item.Settings().food(NFoodComponents.FAAR));
    public static final Item SOUL_BERRIES = createItem("soul_berries", new Item.Settings().food(NFoodComponents.SOUL_BERRIES));
    public static final Item CINDER_GRAIN = createItem("cinder_grain");

    /*
        Foods
     */
    public static final Item SOULLESS_PASTRY = createItem("soulless_pastry", new Item.Settings().food(NFoodComponents.SOULLESS_PASTRY));
    public static final Item NETHER_STEW = createItem("nether_stew", NetherStewItem::new, new Item.Settings().food(NFoodComponents.NETHER_STEW).useRemainder(Items.BOWL).maxCount(1));
    public static final Item CINDER_SANGAK = createItem("cinder_sangak", new Item.Settings().food(NFoodComponents.CINDER_SANGAK));
    public static final Item GLOW_SALAD = createItem("glow_salad", new Item.Settings().food(NFoodComponents.GLOW_SALAD).useRemainder(Items.BOWL).maxCount(1));

    /*
        Seeds
     */
    public static final Item SOUL_BERRY_PIPS = createBlockItem("soul_berry_pips", NBlocks.SOUL_BERRY_BUSH);
    public static final Item FAAR_SEEDS = createBlockItem("faar_seeds", NBlocks.FAAR_GROWTH);
    public static final Item NEAR_SPORES = createBlockItem("near_spores", NBlocks.NEAR_HANG);
    public static final Item CINDER_SEEDS = createBlockItem("cinder_seeds", NBlocks.CINDER_GRAIN);

    /*
        Misc
     */
    public static final Item NEAR_TWIG = createItem("near_twig");


    public static void init() {
        NearsRecipeGenerator.COLOR_MELTING_MAP.put(NEAR, Items.ORANGE_DYE);
        NearsRecipeGenerator.COLOR_MELTING_MAP.put(FAAR, Items.CYAN_DYE);
        NearsRecipeGenerator.COLOR_MELTING_MAP.put(SOUL_BERRIES, Items.LIGHT_BLUE_DYE);


        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(NBlocks.NEAR_TWIG_BLOCK);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(Items.ENCHANTED_GOLDEN_APPLE, NEAR);
            entries.addAfter(NEAR, FAAR);
            entries.addAfter(Items.GLOW_BERRIES, SOUL_BERRIES);
            entries.addAfter(Items.PUMPKIN_PIE, SOULLESS_PASTRY);

            entries.addAfter(Items.BREAD, CINDER_SANGAK);
            entries.addBefore(Items.MUSHROOM_STEW, GLOW_SALAD);
            entries.addAfter(Items.RABBIT_STEW, NETHER_STEW);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Items.BEETROOT_SEEDS, NEAR_SPORES);
            entries.addAfter(NEAR_SPORES, FAAR_SEEDS);
            entries.addAfter(FAAR_SEEDS, SOUL_BERRY_PIPS);
            entries.addAfter(SOUL_BERRY_PIPS, CINDER_SEEDS);

            entries.addAfter(Items.SWEET_BERRIES, SOUL_BERRIES);

            entries.addAfter(Items.HAY_BLOCK, NBlocks.CINDER_BALE);

            entries.add(NBlocks.FAAR_BUNDLE);

            entries.addAfter(Items.NETHER_SPROUTS, NBlocks.CINDER_GRASS);
            entries.addAfter(Items.BAMBOO, NEAR_TWIG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.WHEAT, CINDER_GRAIN);
        });
    }
}

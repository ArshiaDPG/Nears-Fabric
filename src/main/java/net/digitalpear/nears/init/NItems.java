package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.datagen.NearsRecipeProvider;
import net.digitalpear.nears.common.items.FaarItem;
import net.digitalpear.nears.common.items.NetherStewItem;
import net.digitalpear.nears.init.data.NFoodComponents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class NItems {
    public static Item createItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Nears.MOD_ID, name), item);
    }

    /*
        Farmables
     */
    public static final Item NEAR = createItem("near", new Item(new Item.Settings().food(NFoodComponents.NEAR)));
    public static final Item FAAR = createItem("faar", new FaarItem(new Item.Settings().food(NFoodComponents.FAAR)));
    public static final Item SOUL_BERRIES = createItem("soul_berries", new Item(new Item.Settings().food(NFoodComponents.SOUL_BERRIES)));
    public static final Item CINDER_GRAIN = createItem("cinder_grain", new Item(new Item.Settings()));

    /*
        Foods
     */
    public static final Item SOULLESS_PASTRY = createItem("soulless_pastry", new Item(new Item.Settings().food(NFoodComponents.SOULLESS_PASTRY)));
    public static final Item NETHER_STEW = createItem("nether_stew", new NetherStewItem(new Item.Settings().food(NFoodComponents.NETHER_STEW).maxCount(1)));
    public static final Item CINDER_SANGAK = createItem("cinder_sangak", new Item(new Item.Settings().food(NFoodComponents.CINDER_SANGAK)));
    public static final Item GLOW_SALAD = createItem("glow_salad", new StewItem(new Item.Settings().food(NFoodComponents.GLOW_SALAD).maxCount(1)));

    /*
        Seeds
     */
    public static final Item SOUL_BERRY_PIPS = createItem("soul_berry_pips", new AliasedBlockItem(NBlocks.SOUL_BERRY_BUSH, new Item.Settings()));
    public static final Item FAAR_SEEDS = createItem("faar_seeds", new AliasedBlockItem(NBlocks.FAAR_GROWTH, new Item.Settings()));
    public static final Item NEAR_SPORES = createItem("near_spores", new AliasedBlockItem(NBlocks.NEAR_HANG, new Item.Settings()));
    public static final Item CINDER_SEEDS = createItem("cinder_seeds", new AliasedBlockItem(NBlocks.CINDER_GRAIN, new Item.Settings()));

    /*
        Misc
     */
    public static final Item NEAR_TWIG = createItem("near_twig", new Item(new Item.Settings()));


    public static void init() {
        NearsRecipeProvider.COLOR_MELTING_MAP.put(NEAR, Items.ORANGE_DYE);
        NearsRecipeProvider.COLOR_MELTING_MAP.put(FAAR, Items.CYAN_DYE);
        NearsRecipeProvider.COLOR_MELTING_MAP.put(SOUL_BERRIES, Items.LIGHT_BLUE_DYE);



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

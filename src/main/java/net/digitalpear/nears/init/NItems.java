package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.datagen.NearsRecipeGen;
import net.digitalpear.nears.common.items.FaarItem;
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
        Fruits
     */
    public static final Item NEAR = createItem("near", new Item(new Item.Settings().food(FoodComponents.APPLE)));
    public static final Item FAAR = createItem("faar", new FaarItem(new Item.Settings().food(FoodComponents.CARROT)));
    public static final Item SOUL_BERRIES = createItem("soul_berries", new Item(new Item.Settings().food(FoodComponents.SWEET_BERRIES)));
    public static final Item CINDER_GRAIN = createItem("cinder_grain", new Item(new Item.Settings()));

    /*
        Foods
     */
    public static final Item SOULLESS_PASTRY = createItem("soulless_pastry", new Item(new Item.Settings().food(FoodComponents.PUMPKIN_PIE)));


    /*
        Seeds
     */
    public static final Item SOUL_BERRY_PIPS = createItem("soul_berry_pips", new AliasedBlockItem(NBlocks.SOUL_BERRY_BUSH, new Item.Settings()));
    public static final Item FAAR_SEEDS = createItem("faar_seeds", new AliasedBlockItem(NBlocks.FAAR_GROWTH, new Item.Settings()));
    public static final Item NEAR_SEEDS = createItem("near_seeds", new AliasedBlockItem(NBlocks.NEAR_BULB, new Item.Settings()));
    public static final Item CINDER_SEEDS = createItem("cinder_seeds", new AliasedBlockItem(NBlocks.CINDER_WHEAT, new Item.Settings()));


    public static void init() {
        NearsRecipeGen.COLOR_MELTING_MAP.put(NEAR, Items.ORANGE_DYE);
        NearsRecipeGen.COLOR_MELTING_MAP.put(FAAR, Items.CYAN_DYE);
        NearsRecipeGen.COLOR_MELTING_MAP.put(SOUL_BERRIES, Items.LIGHT_BLUE_DYE);


        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(Items.ENCHANTED_GOLDEN_APPLE, NEAR);
            entries.addAfter(NEAR, FAAR);
            entries.addAfter(Items.GLOW_BERRIES, SOUL_BERRIES);
            entries.addAfter(Items.PUMPKIN_PIE, SOULLESS_PASTRY);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Items.MELON_SEEDS, NEAR_SEEDS);
            entries.addAfter(NEAR_SEEDS, FAAR_SEEDS);
            entries.addAfter(FAAR_SEEDS, SOUL_BERRY_PIPS);
            entries.addAfter(SOUL_BERRY_PIPS, CINDER_SEEDS);

            entries.addAfter(Items.JACK_O_LANTERN, NBlocks.FAAR_BUNDLE);

            entries.addAfter(Items.NETHER_SPROUTS, NBlocks.CINDER_GRASS);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.WHEAT, CINDER_GRAIN);

        });

    }
}

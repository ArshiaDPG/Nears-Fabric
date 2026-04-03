package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.datagen.NearsRecipeGenerator;
import net.digitalpear.nears.common.items.FaarItem;
import net.digitalpear.nears.init.data.NConsumables;
import net.digitalpear.nears.init.data.NFoodProperties;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;


public class NItems {
    private static Function<Item.Properties, Item> createBlockItemWithUniqueName(Block block) {
        return (properties) -> new BlockItem(block, properties.useItemDescriptionPrefix());
    }
    public static ResourceKey<Item> keyOf(String id) {
        return ResourceKey.create(Registries.ITEM, Nears.id(id));
    }
    public static Item createItem(String id){
        return createItem(id, new Item.Properties());
    }
    public static Item createItem(String id, Item.Properties properties){
        return createItem(id, Item::new, properties);
    }

    public static Item createBlockItem(String id, Block block){
        return registerItem(keyOf(id), createBlockItemWithUniqueName(block), new Item.Properties());
    }
    public static Item createItem(String id, Function<Item.Properties, Item> factory, Item.Properties properties){
        return registerItem(keyOf(id), factory, properties);
    }
    public static Item registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties properties){
        Item item = factory.apply(properties.setId(key));
        if(item instanceof BlockItem blockItem) blockItem.registerBlocks(Item.BY_BLOCK, item);
        
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }


    /*
        Farmables
     */
    public static final Item NEAR = createItem("near", new Item.Properties().food(NFoodProperties.NEAR));
    public static final Item FAAR = createItem("faar", FaarItem::new, new Item.Properties().food(NFoodProperties.FAAR));
    public static final Item SOUL_BERRIES = createItem("soul_berries", new Item.Properties().food(NFoodProperties.SOUL_BERRIES));
    public static final Item CINDER_GRAIN = createItem("cinder_grain");

    /*
        Foods
     */
    public static final Item SOULLESS_PASTRY = createItem("soulless_pastry", new Item.Properties().food(NFoodProperties.SOULLESS_PASTRY));
    public static final Item NETHER_STEW = createItem("nether_stew", new Item.Properties().food(NFoodProperties.NETHER_STEW, NConsumables.NETHER_STEW).usingConvertsTo(Items.BOWL).stacksTo(1));
    public static final Item CINDER_SANGAK = createItem("cinder_sangak", new Item.Properties().food(NFoodProperties.CINDER_SANGAK));
    public static final Item GLOW_SALAD = createItem("glow_salad", new Item.Properties().food(NFoodProperties.GLOW_SALAD).usingConvertsTo(Items.BOWL).stacksTo(1));

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


        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.accept(NBlocks.NEAR_TWIG_BLOCK);
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.insertAfter(Items.ENCHANTED_GOLDEN_APPLE, NEAR);
            entries.insertAfter(NEAR, FAAR);
            entries.insertAfter(Items.GLOW_BERRIES, SOUL_BERRIES);
            entries.insertAfter(Items.PUMPKIN_PIE, SOULLESS_PASTRY);

            entries.insertAfter(Items.BREAD, CINDER_SANGAK);
            entries.insertBefore(Items.MUSHROOM_STEW, GLOW_SALAD);
            entries.insertAfter(Items.RABBIT_STEW, NETHER_STEW);
        });
        
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.insertAfter(Items.BEETROOT_SEEDS, NEAR_SPORES);
            entries.insertAfter(NEAR_SPORES, FAAR_SEEDS);
            entries.insertAfter(FAAR_SEEDS, SOUL_BERRY_PIPS);
            entries.insertAfter(SOUL_BERRY_PIPS, CINDER_SEEDS);

            entries.insertAfter(Items.SWEET_BERRIES, SOUL_BERRIES);

            entries.insertAfter(Items.HAY_BLOCK, NBlocks.CINDER_BALE);

            entries.insertBefore(Items.MELON, NBlocks.FAAR_BUNDLE);

            entries.insertAfter(Items.NETHER_SPROUTS, NBlocks.CINDER_GRASS);
            entries.insertAfter(Items.BAMBOO, NEAR_TWIG);
        });
        
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.insertAfter(Items.WHEAT, CINDER_GRAIN);
        });
    }
}

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
    public static ResourceKey<Item> keyOf(String id) {
        return ResourceKey.create(Registries.ITEM, Nears.id(id));
    }
    public static Item register(String id){
        return register(id, new Item.Properties());
    }
    public static Item register(String id, Item.Properties properties){
        return register(id, Item::new, properties);
    }

    public static Item registerBlockWithUniqueName(String id, Block block){
        return registerItem(keyOf(id), (properties) -> new BlockItem(block, properties.useItemDescriptionPrefix()), new Item.Properties());
    }
    public static Item registerBlock(Block block){
        return register(BuiltInRegistries.BLOCK.getKey(block).getPath(), properties -> new BlockItem(block, properties), new Item.Properties().useBlockDescriptionPrefix().requiredFeatures(block.requiredFeatures()));
    }
    public static Item register(String id, Function<Item.Properties, Item> factory, Item.Properties properties){
        return registerItem(keyOf(id), factory, properties);
    }
    public static Item registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties properties){
        Item item = factory.apply(properties.setId(key));
        if (item instanceof BlockItem blockItem) blockItem.registerBlocks(Item.BY_BLOCK, item);
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }


    /*
        Farmables
     */
    public static final Item NEAR = register("near", new Item.Properties().food(NFoodProperties.NEAR));
    public static final Item FAAR = register("faar", FaarItem::new, new Item.Properties().food(NFoodProperties.FAAR));
    public static final Item SOUL_BERRIES = register("soul_berries", new Item.Properties().food(NFoodProperties.SOUL_BERRIES));
    public static final Item CINDER_GRAIN = register("cinder_grain");

    /*
        Foods
     */
    public static final Item SOULLESS_PASTRY = register("soulless_pastry", new Item.Properties().food(NFoodProperties.SOULLESS_PASTRY));
    public static final Item NETHER_STEW = register("nether_stew", new Item.Properties().food(NFoodProperties.NETHER_STEW, NConsumables.NETHER_STEW).usingConvertsTo(Items.BOWL).stacksTo(1));
    public static final Item CINDER_SANGAK = register("cinder_sangak", new Item.Properties().food(NFoodProperties.CINDER_SANGAK));
    public static final Item GLOW_SALAD = register("glow_salad", new Item.Properties().food(NFoodProperties.GLOW_SALAD).usingConvertsTo(Items.BOWL).stacksTo(1));

    /*
        Seeds
     */
    public static final Item SOUL_BERRY_PIPS = registerBlockWithUniqueName("soul_berry_pips", NBlocks.SOUL_BERRY_BUSH);
    public static final Item FAAR_SEEDS = registerBlockWithUniqueName("faar_seeds", NBlocks.FAAR_GROWTH);
    public static final Item NEAR_SPORES = registerBlockWithUniqueName("near_spores", NBlocks.NEAR_HANG);
    public static final Item CINDER_SEEDS = registerBlockWithUniqueName("cinder_seeds", NBlocks.CINDER_GRAIN);

    /*
        Misc
     */
    public static final Item NEAR_TWIG = register("near_twig");
    
    
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

package net.digitalpear.nears.init.data.tags;

import net.digitalpear.nears.Nears;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class NItemTags {
    public static final TagKey<Item> NETHER_FRUITS = of("nether_fruits");

    public static final TagKey<Item> SANDWICHABLE_BREAD_SLICES = compatTag("sandwichable", "bread_slices");

    public static final TagKey<Item> SUMMER_CROPS_COMPAT = compatTag("sereneseasons", "summer_crops");

    private static TagKey<Item> of(String id) {
        return compatTag(Nears.MOD_ID, id);
    }

    private static TagKey<Item> compatTag(String modid,String id) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(modid, id));
    }
}

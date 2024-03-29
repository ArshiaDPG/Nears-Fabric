package net.digitalpear.nears.init.data.tags;

import net.digitalpear.nears.Nears;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class NItemTags {
    public static final TagKey<Item> NETHER_FRUITS = of("nether_fruits");


    public static final TagKey<Item> FRUITS = commonTag("fruits");
    public static final TagKey<Item> FRUIT_SALADS = commonTag("fruit_salads");
    public static final TagKey<Item> SEEDS = commonTag("seeds");

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, Nears.id(id));
    }
    private static TagKey<Item> commonTag(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier("c", id));
    }
}

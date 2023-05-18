package net.digitalpear.nears.init.tags;

import net.digitalpear.nears.Nears;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class NItemTags {
    public static final TagKey<Item> NETHER_FRUITS = of("nether_fruits");

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, Nears.id(id));
    }
}

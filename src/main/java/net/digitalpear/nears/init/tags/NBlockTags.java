package net.digitalpear.nears.init.tags;

import net.digitalpear.nears.Nears;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class NBlockTags {

    public static final TagKey<Block> FAAR_GROWTH_BASE = of("faar_growth_base");
    public static final TagKey<Block> NEAR_PLANTABLE_ON = of("near_plantable_on");
    public static final TagKey<Block> CINDER_GRASS_PLANTABLE_ON = of("cinder_grass_plantable_on");
    public static final TagKey<Block> SOUL_BERRY_BUSH_PLANTABLE_ON = of("soul_berry_bush_plantable_on");
    public static final TagKey<Block> CINDER_WHEAT_PLANTABLE_ON = of("cinder_wheat_plantable_on");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Nears.id(id));
    }
}

package net.digitalpear.nears.init.data.tags;

import net.digitalpear.nears.Nears;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class NBiomeTags {

    public static final TagKey<Biome> CAN_NEARS_SPAWN = of("can_nears_spawn");
    public static final TagKey<Biome> CAN_FAARS_SPAWN = of("can_faars_spawn");
    public static final TagKey<Biome> CAN_SOUL_BERRIES_SPAWN = of("can_soul_berries_spawn");
    public static final TagKey<Biome> CAN_CINDER_GRASS_SPAWN = of("can_cinder_grass_spawn");


    private static TagKey<Biome> of(String id) {
        return TagKey.of(RegistryKeys.BIOME, Nears.id(id));
    }
}

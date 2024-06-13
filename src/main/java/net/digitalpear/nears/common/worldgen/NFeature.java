package net.digitalpear.nears.common.worldgen;

import net.digitalpear.nears.Nears;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class NFeature {
    public static final Feature<DefaultFeatureConfig> NEAR_HANG = register("near_hang", new NearHangFeature(DefaultFeatureConfig.CODEC));


    private static <C extends FeatureConfig, F extends Feature<C>> Feature register(String name, F feature) {
        return Registry.register(Registries.FEATURE, Nears.id(name), feature);
    }

    public static void init() {
    }
}

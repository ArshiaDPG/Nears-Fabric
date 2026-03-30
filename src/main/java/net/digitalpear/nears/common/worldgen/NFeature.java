package net.digitalpear.nears.common.worldgen;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.worldgen.config.FaarClusterFeatureConfig;
import net.digitalpear.nears.common.worldgen.config.NearHangFeatureConfig;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class NFeature {
    public static final Feature<NearHangFeatureConfig> NEAR_HANG = register("near_hang", new NearHangFeature(NearHangFeatureConfig.CODEC));
    public static final Feature<FaarClusterFeatureConfig> FAAR_CLUSTER = register("faar_cluster", new FaarClusterFeature(FaarClusterFeatureConfig.CODEC));


    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(BuiltInRegistries.FEATURE, Nears.id(name), feature);
    }

    public static void init() {
    }
}

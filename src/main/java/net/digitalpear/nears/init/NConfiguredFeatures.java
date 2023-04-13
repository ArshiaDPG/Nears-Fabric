package net.digitalpear.nears.init;

import net.digitalpear.nears.Nears;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class NConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_NEARS = of("patch_nears");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SOUL_BUSH = of("patch_soul_berry_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FAAR_GROWTH = of("patch_faar_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_CINDER_GRASS = of("patch_faar_growth");

    public static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Nears.id(id));
    }

    public static void init() {
    }
}

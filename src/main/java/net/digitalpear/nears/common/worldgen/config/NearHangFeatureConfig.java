package net.digitalpear.nears.common.worldgen.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class NearHangFeatureConfig implements FeatureConfig {

    public static final Codec<NearHangFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(Codec.intRange(0, 64).fieldOf("radius").forGetter(config -> {
            return config.radius;
        }), ConfiguredFeature.REGISTRY_CODEC.fieldOf("accompanying_feature").forGetter((config) -> {
            return config.accompanyingFeature;
        })).apply(instance, NearHangFeatureConfig::new);
    });


    public final int radius;
    public final RegistryEntry<ConfiguredFeature<?, ?>> accompanyingFeature;

    public NearHangFeatureConfig(int radius, RegistryEntry<ConfiguredFeature<?, ?>> accompanyingFeature){
        this.radius = radius;
        this.accompanyingFeature = accompanyingFeature;

    }
}

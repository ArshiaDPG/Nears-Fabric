package net.digitalpear.nears.common.worldgen.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class NearHangFeatureConfig implements FeatureConfiguration {
    public static final Codec<NearHangFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(Codec.intRange(0, 64).fieldOf("radius").forGetter(config -> {
            return config.radius;
        }), ConfiguredFeature.CODEC.fieldOf("accompanying_feature").forGetter((config) -> {
            return config.accompanyingFeature;
        })).apply(instance, NearHangFeatureConfig::new);
    });


    public final int radius;
    public final Holder<ConfiguredFeature<?, ?>> accompanyingFeature;

    public NearHangFeatureConfig(int radius, Holder<ConfiguredFeature<?, ?>> accompanyingFeature){
        this.radius = radius;
        this.accompanyingFeature = accompanyingFeature;

    }
}

package net.digitalpear.nears.common.worldgen.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public class FaarClusterFeatureConfig implements FeatureConfig {
    public static final Codec<FaarClusterFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.intRange(0, 64).fieldOf("spread_vertical").forGetter(config -> {
            return config.spreadVertical;
        }), Codec.intRange(0, 64).fieldOf("spread_horizontal").forGetter(config -> {
            return config.spreadHorizontal;
        }), Codec.intRange(0, 64).fieldOf("rarity").forGetter(config -> {
            return config.rarity;
        })).apply(instance, FaarClusterFeatureConfig::new);
    });

    public final int spreadVertical;
    public final int spreadHorizontal;
    public final int rarity;

    public FaarClusterFeatureConfig(int spreadVertical, int spreadHorizontal, int rarity){
        this.spreadVertical = spreadVertical;
        this.spreadHorizontal = spreadHorizontal;
        this.rarity = rarity;
    }
}

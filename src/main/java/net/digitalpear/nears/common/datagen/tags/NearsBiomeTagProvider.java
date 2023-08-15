package net.digitalpear.nears.common.datagen.tags;

import net.digitalpear.nears.init.data.tags.NBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.concurrent.CompletableFuture;

public class NearsBiomeTagProvider extends FabricTagProvider<Biome> {
    /**
     * Constructs a new {@link FabricTagProvider} with the default computed path.
     *
     * <p>Common implementations of this class are provided.
     *
     * @param output           the {@link FabricDataOutput} instance
     * @param registriesFuture the backing registry for the tag type
     */
    public NearsBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(NBiomeTags.CAN_NEARS_SPAWN).add(BiomeKeys.CRIMSON_FOREST).addOptional(new Identifier("gardens_of_the_dead", "whistling_woods"));
        getOrCreateTagBuilder(NBiomeTags.CAN_FAARS_SPAWN).add(BiomeKeys.WARPED_FOREST);
        getOrCreateTagBuilder(NBiomeTags.CAN_SOUL_BERRIES_SPAWN).add(BiomeKeys.SOUL_SAND_VALLEY).addOptional(new Identifier("gardens_of_the_dead", "soulblight_forest"));
        getOrCreateTagBuilder(NBiomeTags.CAN_CINDER_GRASS_SPAWN).add(BiomeKeys.BASALT_DELTAS);
    }
}

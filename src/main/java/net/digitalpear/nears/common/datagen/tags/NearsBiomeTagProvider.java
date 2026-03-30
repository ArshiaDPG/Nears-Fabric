package net.digitalpear.nears.common.datagen.tags;

import net.digitalpear.nears.init.data.tags.NBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class NearsBiomeTagProvider extends FabricTagsProvider<Biome> {
    //TODO: update or remove this description
    ///**
    // * Constructs a new {@link FabricTagProvider} with the default computed path.
    // *
    // * <p>Common implementations of this class are provided.
    // *
    // * @param output           the {@link FabricDataOutput} instance
    // * @param registriesFuture the backing registry for the tag type
    // */
    public NearsBiomeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.BIOME, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(NBiomeTags.CAN_NEARS_SPAWN).addElement(Biomes.CRIMSON_FOREST.identifier()).addOptionalElement(Identifier.fromNamespaceAndPath("gardens_of_the_dead", "whistling_woods"));
        getOrCreateRawBuilder(NBiomeTags.CAN_FAARS_SPAWN).addElement(Biomes.WARPED_FOREST.identifier());
        getOrCreateRawBuilder(NBiomeTags.CAN_SOUL_BERRIES_SPAWN).addElement(Biomes.SOUL_SAND_VALLEY.identifier()).addOptionalElement(Identifier.fromNamespaceAndPath("gardens_of_the_dead", "soulblight_forest"));
        getOrCreateRawBuilder(NBiomeTags.CAN_CINDER_GRASS_SPAWN).addElement(Biomes.BASALT_DELTAS.identifier());
    }
}

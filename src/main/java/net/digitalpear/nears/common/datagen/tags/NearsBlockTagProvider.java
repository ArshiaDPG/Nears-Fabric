package net.digitalpear.nears.common.datagen.tags;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class NearsBlockTagProvider extends FabricTagsProvider<Block> {
    //TODO: ditto of NearsBiomeTagProvider
    ///**
    // * Constructs a new {@link FabricTagProvider} with the default computed path.
    // *
    // * <p>Common implementations of this class are provided.
    // *
    // * @param output           the {@link FabricDataOutput} instance
    // * @param registriesFuture the backing registry for the tag type
    // */
    public NearsBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.BLOCK, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(BlockTags.MINEABLE_WITH_HOE).add(getId(NBlocks.FAAR_BUNDLE));
        getOrCreateRawBuilder(BlockTags.CROPS).add(getId(NBlocks.CINDER_GRAIN));
        getOrCreateRawBuilder(BlockTags.FLOWER_POTS).add(getId(NBlocks.POTTED_CINDER_GRASS));
        getOrCreateRawBuilder(BlockTags.PIGLIN_REPELLENTS).add(getId(NBlocks.SOUL_BERRY_BUSH));
        
        getOrCreateRawBuilder(NBlockTags.NEAR_HANG_PLANTABLE_ON).add(getId(Blocks.CRIMSON_NYLIUM)).add(getId(Blocks.NETHER_WART_BLOCK));
        
        getOrCreateRawBuilder(NBlockTags.FAAR_GROWTH_PLANTABLE_ON).add(getId(Blocks.WARPED_WART_BLOCK));
        
        getOrCreateRawBuilder(NBlockTags.FAAR_CLUSTER_REPLACEABLE).add(getId(Blocks.AIR));
        
        getOrCreateRawBuilder(NBlockTags.SOUL_BERRY_BUSH_PLANTABLE_ON).add(getId(Blocks.SOUL_SAND)).add(getId(Blocks.SOUL_SOIL));
        
        getOrCreateRawBuilder(NBlockTags.CINDER_GRASS_PLANTABLE_ON)
                .add(getId(Blocks.SOUL_SAND)).add(getId(Blocks.SOUL_SOIL)).add(getId(Blocks.BASALT)).add(getId(Blocks.BLACKSTONE)).add(getId(Blocks.NETHERRACK))
                .addOptionalTag(BlockTags.NYLIUM.location()).addTag(NBlockTags.CINDER_WHEAT_PLANTABLE_ON.location());
        
        getOrCreateRawBuilder(NBlockTags.CINDER_WHEAT_PLANTABLE_ON).add(getId(Blocks.MAGMA_BLOCK)).add(getId(Blocks.BASALT));
        
        getOrCreateRawBuilder(NBlockTags.SUMMER_CROPS_COMPAT)
                .add(getId(NBlocks.SOUL_BERRY_BUSH))
                .add(getId(NBlocks.FAAR_GROWTH))
                .add(getId(NBlocks.CINDER_GRAIN))
                .add(getId(NBlocks.NEAR_HANG))
                .add(getId(NBlocks.NEAR_HANG_STEM));
    }
    public static TagEntry getId(Block itemConvertible){
        return TagEntry.element(BuiltInRegistries.BLOCK.getKey(itemConvertible));
    }
}

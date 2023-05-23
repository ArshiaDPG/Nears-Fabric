package net.digitalpear.nears.common.datagen.tags;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class NearsBlockTagGen extends FabricTagProvider<Block> {
    /**
     * Constructs a new {@link FabricTagProvider} with the default computed path.
     *
     * <p>Common implementations of this class are provided.
     *
     * @param output           the {@link FabricDataOutput} instance
     * @param registriesFuture the backing registry for the tag type
     */
    public NearsBlockTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, Registries.BLOCK.getKey(), registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(NBlocks.FAAR_BUNDLE);
        getOrCreateTagBuilder(BlockTags.CROPS).add(NBlocks.CINDER_WHEAT);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(NBlocks.POTTED_CINDER_GRASS);
        getOrCreateTagBuilder(BlockTags.PIGLIN_REPELLENTS).add(NBlocks.SOUL_BERRY_BUSH);



        getOrCreateTagBuilder(NBlockTags.NEAR_BULB_PLANTABLE_ON).add(Blocks.CRIMSON_NYLIUM);

        getOrCreateTagBuilder(NBlockTags.FAAR_GROWTH_PLANTABLE_ON).add(Blocks.WARPED_WART_BLOCK);

        getOrCreateTagBuilder(NBlockTags.SOUL_BERRY_BUSH_PLANTABLE_ON).add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL);

        getOrCreateTagBuilder(NBlockTags.CINDER_GRASS_PLANTABLE_ON)
                .add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL).add(Blocks.BASALT).add(Blocks.BLACKSTONE).add(Blocks.NETHERRACK)
                .forceAddTag(BlockTags.NYLIUM).forceAddTag(NBlockTags.CINDER_WHEAT_PLANTABLE_ON).forceAddTag(NBlockTags.NETHERRACK);

        getOrCreateTagBuilder(NBlockTags.CINDER_WHEAT_PLANTABLE_ON).add(Blocks.MAGMA_BLOCK);
    }
}

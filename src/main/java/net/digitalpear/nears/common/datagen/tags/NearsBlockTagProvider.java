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
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class NearsBlockTagProvider extends FabricTagProvider<Block> {
    /**
     * Constructs a new {@link FabricTagProvider} with the default computed path.
     *
     * <p>Common implementations of this class are provided.
     *
     * @param output           the {@link FabricDataOutput} instance
     * @param registriesFuture the backing registry for the tag type
     */
    public NearsBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, Registries.BLOCK.getKey(), registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getTagBuilder(BlockTags.HOE_MINEABLE).add(getId(NBlocks.FAAR_BUNDLE));
        getTagBuilder(BlockTags.CROPS).add(getId(NBlocks.CINDER_GRAIN));
        getTagBuilder(BlockTags.FLOWER_POTS).add(getId(NBlocks.POTTED_CINDER_GRASS));
        getTagBuilder(BlockTags.PIGLIN_REPELLENTS).add(getId(NBlocks.SOUL_BERRY_BUSH));

        getTagBuilder(NBlockTags.NEAR_HANG_PLANTABLE_ON).add(getId(Blocks.CRIMSON_NYLIUM)).add(getId(Blocks.NETHER_WART_BLOCK));

        getTagBuilder(NBlockTags.FAAR_GROWTH_PLANTABLE_ON).add(getId(Blocks.WARPED_WART_BLOCK));

        getTagBuilder(NBlockTags.FAAR_CLUSTER_REPLACEABLE).add(getId(Blocks.AIR));

        getTagBuilder(NBlockTags.SOUL_BERRY_BUSH_PLANTABLE_ON).add(getId(Blocks.SOUL_SAND)).add(getId(Blocks.SOUL_SOIL));

        getTagBuilder(NBlockTags.CINDER_GRASS_PLANTABLE_ON)
                .add(getId(Blocks.SOUL_SAND)).add(getId(Blocks.SOUL_SOIL)).add(getId(Blocks.BASALT)).add(getId(Blocks.BLACKSTONE)).add(getId(Blocks.NETHERRACK))
                .addOptionalTag(BlockTags.NYLIUM.id()).addTag(NBlockTags.CINDER_WHEAT_PLANTABLE_ON.id());

        getTagBuilder(NBlockTags.CINDER_WHEAT_PLANTABLE_ON).add(getId(Blocks.MAGMA_BLOCK)).add(getId(Blocks.BASALT));

        getTagBuilder(NBlockTags.SUMMER_CROPS_COMPAT)
                .add(getId(NBlocks.SOUL_BERRY_BUSH))
                .add(getId(NBlocks.FAAR_GROWTH))
                .add(getId(NBlocks.CINDER_GRAIN))
                .add(getId(NBlocks.NEAR_HANG))
                .add(getId(NBlocks.NEAR_HANG_STEM));
    }
    public static Identifier getId(Block itemConvertible){
        return Registries.BLOCK.getId(itemConvertible);
    }
}

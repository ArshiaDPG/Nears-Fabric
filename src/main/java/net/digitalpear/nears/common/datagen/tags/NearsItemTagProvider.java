package net.digitalpear.nears.common.datagen.tags;

import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class NearsItemTagProvider extends FabricTagsProvider<Item> {
    //TODO: ditto of NearsBiomeTagProvider
    ///**
    // * Constructs a new {@link FabricTagProvider} with the default computed path.
    // *
    // * <p>Common implementations of this class are provided.
    // *
    // * @param output           the {@link FabricDataOutput} instance
    // * @param registriesFuture the backing registry for the tag type
    // */
    public NearsItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.ITEM, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(NItemTags.NETHER_FRUITS).add(getId(NItems.NEAR)).add(getId(NItems.FAAR)).add(getId(NItems.SOUL_BERRIES));
        
        getOrCreateRawBuilder(NItemTags.SANDWICHABLE_BREAD_SLICES).add(getId(NItems.CINDER_SANGAK));
        
        getOrCreateRawBuilder(ConventionalItemTags.FRUIT_FOODS).addTag(NItemTags.NETHER_FRUITS.location());
        getOrCreateRawBuilder(ConventionalItemTags.SEEDS).add(getId(NItems.NEAR_SPORES)).add(getId(NItems.FAAR_SEEDS)).add(getId(NItems.SOUL_BERRY_PIPS)).add(getId(NItems.CINDER_SEEDS));
        getOrCreateRawBuilder(ConventionalItemTags.BERRY_FOODS).add(getId(NItems.SOUL_BERRIES)).add(getId(NItems.GLOW_SALAD));
        getOrCreateRawBuilder(ConventionalItemTags.WHEAT_SEEDS).add(getId(NItems.CINDER_SEEDS));
        
        getOrCreateRawBuilder(ItemTags.PIGLIN_FOOD).add(getId(NItems.NEAR));
        
        getOrCreateRawBuilder(NItemTags.SUMMER_CROPS_COMPAT).add(getId(NItems.NEAR_SPORES)).add(getId(NItems.FAAR_SEEDS)).add(getId(NItems.FAAR_SEEDS)).add(getId(NItems.CINDER_SEEDS));

    }
    
    public static TagEntry getId(ItemLike itemConvertible){
        return TagEntry.element(BuiltInRegistries.ITEM.getKey(itemConvertible.asItem()));
    }
}

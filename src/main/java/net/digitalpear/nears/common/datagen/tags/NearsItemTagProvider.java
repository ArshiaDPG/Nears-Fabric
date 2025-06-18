package net.digitalpear.nears.common.datagen.tags;

import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class NearsItemTagProvider extends FabricTagProvider<Item> {
    /**
     * Constructs a new {@link FabricTagProvider} with the default computed path.
     *
     * <p>Common implementations of this class are provided.
     *
     * @param output           the {@link FabricDataOutput} instance
     * @param registriesFuture the backing registry for the tag type
     */
    public NearsItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getTagBuilder(NItemTags.NETHER_FRUITS).add(getId(NItems.NEAR)).add(getId(NItems.FAAR)).add(getId(NItems.SOUL_BERRIES));

        getTagBuilder(NItemTags.SANDWICHABLE_BREAD_SLICES).add(getId(NItems.CINDER_SANGAK));

        getTagBuilder(ConventionalItemTags.FRUIT_FOODS).addTag(NItemTags.NETHER_FRUITS.id());
        getTagBuilder(ConventionalItemTags.SEEDS).add(getId(NItems.NEAR_SPORES)).add(getId(NItems.FAAR_SEEDS)).add(getId(NItems.SOUL_BERRY_PIPS)).add(getId(NItems.CINDER_SEEDS));
        getTagBuilder(ConventionalItemTags.BERRY_FOODS).add(getId(NItems.SOUL_BERRIES)).add(getId(NItems.GLOW_SALAD));
        getTagBuilder(ConventionalItemTags.WHEAT_SEEDS).add(getId(NItems.CINDER_SEEDS));

        getTagBuilder(ItemTags.PIGLIN_FOOD).add(getId(NItems.NEAR));

        getTagBuilder(NItemTags.SUMMER_CROPS_COMPAT).add(getId(NItems.NEAR_SPORES)).add(getId(NItems.FAAR_SEEDS)).add(getId(NItems.FAAR_SEEDS)).add(getId(NItems.CINDER_SEEDS));

    }

    public static Identifier getId(ItemConvertible itemConvertible){
        return Registries.ITEM.getId(itemConvertible.asItem());
    }
}

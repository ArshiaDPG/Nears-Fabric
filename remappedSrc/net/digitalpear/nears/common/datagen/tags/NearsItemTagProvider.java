package net.digitalpear.nears.common.datagen.tags;

import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

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
        getOrCreateTagBuilder(NItemTags.NETHER_FRUITS).add(NItems.NEAR, NItems.FAAR, NItems.SOUL_BERRIES);


        getOrCreateTagBuilder(NItemTags.FRUITS).forceAddTag(NItemTags.NETHER_FRUITS);
        getOrCreateTagBuilder(NItemTags.FRUIT_SALADS).add(NItems.GLOW_SALAD);
        getOrCreateTagBuilder(NItemTags.SEEDS).add(NItems.NEAR_SEEDS, NItems.FAAR_SEEDS, NItems.SOUL_BERRY_PIPS, NItems.CINDER_SEEDS);

        getOrCreateTagBuilder(ItemTags.PIGLIN_FOOD).add(NItems.NEAR);

    }
}

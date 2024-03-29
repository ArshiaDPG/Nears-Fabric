package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NearsRecipeProvider extends FabricRecipeProvider {
    public static Map<Item, Item> COLOR_MELTING_MAP = new HashMap<>();
    public NearsRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, NItems.SOUL_BERRY_PIPS)
                .input(NItems.SOUL_BERRIES)
                .criterion("has_soul_berries", conditionsFromItem(NItems.SOUL_BERRIES))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, NItems.FAAR_SEEDS)
                .input(NItems.FAAR)
                .criterion("has_faar", conditionsFromItem(NItems.FAAR))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, NItems.NEAR_SEEDS)
                .input(NItems.NEAR)
                .criterion("has_near", conditionsFromItem(NItems.NEAR))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, NBlocks.FAAR_BUNDLE)
                .input('F', NItems.FAAR)
                .pattern("FFF")
                .pattern("FFF")
                .pattern("FFF")
                .criterion("has_faar", conditionsFromItem(NItems.FAAR))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, NItems.SOULLESS_PASTRY)
                .input(NItems.SOUL_BERRIES)
                .input(Items.SUGAR)
                .input(Items.EGG)
                .criterion("has_soul_berries", conditionsFromItem(NItems.SOUL_BERRIES))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, NItems.GLOW_SALAD)
                .input(NItems.SOUL_BERRIES)
                .input(Items.GLOW_BERRIES)
                .input(Items.BOWL)
                .criterion("has_bowl", conditionsFromItem(Items.BOWL))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, NItems.NETHER_STEW)
                .input(NItems.SOUL_BERRIES)
                .input(NItems.FAAR)
                .input(NItems.NEAR)
                .input(Items.BOWL)
                .input(Items.NETHER_WART)
                .criterion("has_nether_fruit", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(NItemTags.NETHER_FRUITS).build()))
                .offerTo(exporter);

        FabricRecipeProvider.offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, NItems.CINDER_GRAIN, RecipeCategory.BUILDING_BLOCKS, NBlocks.CINDER_BALE);

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, NItems.CINDER_SANGAK)
                .input('G', NItems.CINDER_GRAIN)
                .pattern("GGG")
                .criterion("has_cinder_grain", conditionsFromItem(NItems.CINDER_GRAIN))
                .offerTo(exporter);

        COLOR_MELTING_MAP.forEach((fruit, dye) -> {
                FabricRecipeProvider.offerSmelting(exporter,
                List.of(fruit),
                RecipeCategory.DECORATIONS,
                dye,
                0.15f,
                200,
                "");
        });

    }
}

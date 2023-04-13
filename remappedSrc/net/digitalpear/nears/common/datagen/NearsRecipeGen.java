package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NearsRecipeGen  extends FabricRecipeProvider {
    public static Map<Item, Item> COLOR_MELTING_MAP = new HashMap<>();
    public NearsRecipeGen(FabricDataOutput output) {
        super(output);

    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC,NItems.SOUL_BERRY_PIPS)
                .input(NItems.SOUL_BERRIES)
                .criterion("has_soul_berries", conditionsFromItem(NItems.SOUL_BERRIES))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC,NItems.FAAR_SEEDS)
                .input(NItems.FAAR)
                .criterion("has_faar", conditionsFromItem(NItems.FAAR))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, NBlocks.FAAR_BUNDLE)
                .input('F', NItems.FAAR)
                .pattern("FFF")
                .pattern("FFF")
                .pattern("FFF")
                .criterion("has_faar", conditionsFromItem(NItems.FAAR))
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

package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.init.NItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
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

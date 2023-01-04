package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.init.NItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class NearsRecipeGen  extends FabricRecipeProvider {
    public NearsRecipeGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC,NItems.SOUL_BERRY_PIPS)
                .input(NItems.SOUL_BERRIES)
                .criterion("has_soul_berries", conditionsFromItem(NItems.SOUL_BERRIES))
                .offerTo(exporter);

    }
}

package net.digitalpear.nears.common.datagen.provider;

import net.digitalpear.nears.common.datagen.NearsRecipeGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class NearsRecipeProvider extends FabricRecipeProvider {

    public NearsRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new NearsRecipeGenerator(registries, output);
    }

    @Override
    public String getName() {
        return "recipe";
    }
}

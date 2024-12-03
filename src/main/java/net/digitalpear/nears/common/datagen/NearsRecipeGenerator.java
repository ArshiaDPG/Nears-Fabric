package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NItemTags;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;

import java.util.HashMap;
import java.util.Map;

public class NearsRecipeGenerator extends RecipeGenerator {
    public static Map<Item, Item> COLOR_MELTING_MAP = new HashMap<>();
    RegistryEntryLookup<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

    public NearsRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        super(registries, exporter);
    }

    @Override
    public void generate() {
        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.MISC, NItems.SOUL_BERRY_PIPS)
                .input(NItems.SOUL_BERRIES)
                .criterion("has_soul_berries", conditionsFromItem(NItems.SOUL_BERRIES))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.MISC, NItems.FAAR_SEEDS)
                .input(NItems.FAAR)
                .criterion("has_faar", conditionsFromItem(NItems.FAAR))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.MISC, NItems.NEAR_SPORES)
                .input(NItems.NEAR)
                .criterion("has_near", conditionsFromItem(NItems.NEAR))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.DECORATIONS, NBlocks.FAAR_BUNDLE)
                .input('F', NItems.FAAR)
                .pattern("FFF")
                .pattern("FFF")
                .pattern("FFF")
                .criterion("has_faar", conditionsFromItem(NItems.FAAR))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.FOOD, NItems.SOULLESS_PASTRY)
                .input(NItems.SOUL_BERRIES)
                .input(Items.SUGAR)
                .input(Items.EGG)
                .criterion("has_soul_berries", conditionsFromItem(NItems.SOUL_BERRIES))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.FOOD, NItems.GLOW_SALAD)
                .input(NItems.SOUL_BERRIES)
                .input(Items.GLOW_BERRIES)
                .input(Items.BOWL)
                .criterion("has_bowl", conditionsFromItem(Items.BOWL))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.FOOD, NItems.NETHER_STEW)
                .input(NItems.SOUL_BERRIES)
                .input(NItems.FAAR)
                .input(NItems.NEAR)
                .input(Items.BOWL)
                .input(Items.NETHER_WART)
                .criterion("has_nether_fruit", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(itemLookup, NItemTags.NETHER_FRUITS).build()))
                .offerTo(exporter);

        offerReversibleCompactingRecipes(RecipeCategory.MISC, NItems.CINDER_GRAIN, RecipeCategory.BUILDING_BLOCKS, NBlocks.CINDER_BALE);

        ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.FOOD, NItems.CINDER_SANGAK)
                .input('G', NItems.CINDER_GRAIN)
                .pattern("GGG")
                .criterion("has_cinder_grain", conditionsFromItem(NItems.CINDER_GRAIN))
                .offerTo(exporter);

        COLOR_MELTING_MAP.forEach((fruit, dye) -> {
            CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(fruit),
                    RecipeCategory.DECORATIONS,
                    dye,
                    0.15f,
                    200).criterion(hasItem(fruit), conditionsFromItem(fruit)).offerTo(exporter, keyOf(getItemPath(dye) + "_from_smelting_" + getItemPath(fruit)));

            CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(fruit),
                    RecipeCategory.DECORATIONS,
                    dye,
                    0.15f,
                    100).criterion(hasItem(fruit), conditionsFromItem(fruit)).offerTo(exporter, keyOf(getItemPath(dye) + "_from_smoking_" + getItemPath(fruit)));

            CookingRecipeJsonBuilder.createCampfireCooking(Ingredient.ofItems(fruit),
                    RecipeCategory.DECORATIONS,
                    dye,
                    0.15f,
                    600).criterion(hasItem(fruit), conditionsFromItem(fruit)).offerTo(exporter, keyOf(getItemPath(dye) + "_from_campfire_cooking"));

        });

        makeVanillaWheatRecipes(exporter);

        ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NBlocks.NEAR_TWIG_BLOCK, 1)
                .input('#', NItems.NEAR_TWIG)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .criterion("has_log", conditionsFromItem(NItems.NEAR_TWIG)).offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, Blocks.CRIMSON_PLANKS, 2)
                .input(NBlocks.NEAR_TWIG_BLOCK).group("planks")
                .criterion(hasItem(NBlocks.NEAR_TWIG_BLOCK), conditionsFromItem(NBlocks.NEAR_TWIG_BLOCK))
                .offerTo(exporter);
    }

    public RegistryKey<Recipe<?>> keyOf(String name){
        return RegistryKey.of(RegistryKeys.RECIPE, Nears.id(name));
    }
    public void makeVanillaWheatRecipes(RecipeExporter exporter){
        ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.REDSTONE, Blocks.TARGET).input('H', NBlocks.CINDER_BALE).input('R', Items.REDSTONE).pattern(" R ").pattern("RHR").pattern(" R ").criterion("has_redstone", conditionsFromItem(Items.REDSTONE)).criterion("has_cinder_bale", conditionsFromItem(NBlocks.CINDER_BALE)).offerTo(exporter, fromBale(Blocks.TARGET));
        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, Blocks.PACKED_MUD, 1).input(Blocks.MUD).input(NItems.CINDER_GRAIN).criterion("has_mud", conditionsFromItem(Blocks.MUD)).offerTo(exporter, fromGrain(Items.PACKED_MUD));
        ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.FOOD, Blocks.CAKE).input('A', Items.MILK_BUCKET).input('B', Items.SUGAR).input('C', NItems.CINDER_GRAIN).input('E', Items.EGG).pattern("AAA").pattern("BEB").pattern("CCC").criterion("has_egg", conditionsFromItem(Items.EGG)).offerTo(exporter, fromGrain(Items.CAKE));
        ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.FOOD, Items.COOKIE, 8).input('#', NItems.CINDER_GRAIN).input('X', Items.COCOA_BEANS).pattern("#X#").criterion("has_cocoa", conditionsFromItem(Items.COCOA_BEANS)).offerTo(exporter, fromGrain(Items.COOKIE));
    }

    public String fromGrain(ItemConvertible itemConvertible){
        return Registries.ITEM.getId(itemConvertible.asItem()).getPath() + "_from_cinder_grain";
    }
    public String fromBale(ItemConvertible itemConvertible){
        return Registries.ITEM.getId(itemConvertible.asItem()).getPath() + "_from_cinder_bale";
    }
}

package net.digitalpear.nears.common.datagen;

import net.digitalpear.nears.Nears;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.digitalpear.nears.init.data.tags.NItemTags;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class NearsRecipeGenerator extends RecipeProvider {
    public static Map<Item, Item> COLOR_MELTING_MAP = new HashMap<>();
    HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

    public NearsRecipeGenerator(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes() {
        ShapelessRecipeBuilder
	        .shapeless(itemLookup, RecipeCategory.MISC, NItems.SOUL_BERRY_PIPS)
	        .requires(NItems.SOUL_BERRIES)
	        .unlockedBy("has_soul_berries", has(NItems.SOUL_BERRIES))
	        .save(output);
	    
	    ShapelessRecipeBuilder
		    .shapeless(itemLookup, RecipeCategory.MISC, NItems.FAAR_SEEDS)
		    .requires(NItems.FAAR)
		    .unlockedBy("has_faar", has(NItems.FAAR))
		    .save(output);
	    
	    ShapelessRecipeBuilder
		    .shapeless(itemLookup, RecipeCategory.MISC, NItems.NEAR_SPORES)
		    .requires(NItems.NEAR)
		    .unlockedBy("has_near", has(NItems.NEAR))
		    .save(output);
	    
	    ShapedRecipeBuilder
		    .shaped(itemLookup, RecipeCategory.DECORATIONS, NBlocks.FAAR_BUNDLE)
		    .define('F', NItems.FAAR)
	        .pattern("FFF")
	        .pattern("FFF")
	        .pattern("FFF")
	        .unlockedBy("has_faar", has(NItems.FAAR))
	        .save(output);
	    
	    ShapelessRecipeBuilder
		    .shapeless(itemLookup, RecipeCategory.FOOD, NItems.SOULLESS_PASTRY)
		    .requires(NItems.SOUL_BERRIES)
		    .requires(Items.SUGAR)
		    .requires(Items.EGG)
		    .unlockedBy("has_soul_berries", has(NItems.SOUL_BERRIES))
		    .save(output);
	    
	    ShapelessRecipeBuilder
		    .shapeless(itemLookup, RecipeCategory.FOOD, NItems.GLOW_SALAD)
		    .requires(NItems.SOUL_BERRIES)
		    .requires(Items.GLOW_BERRIES)
		    .requires(Items.BOWL)
		    .unlockedBy("has_bowl", has(Items.BOWL))
		    .save(output);
	    
	    ShapelessRecipeBuilder
		    .shapeless(itemLookup, RecipeCategory.FOOD, NItems.NETHER_STEW)
	        .requires(NItems.SOUL_BERRIES)
	        .requires(NItems.FAAR)
	        .requires(NItems.NEAR)
	        .requires(Items.BOWL)
	        .requires(Items.NETHER_WART)
	        .unlockedBy(
				"has_nether_fruit",
		        InventoryChangeTrigger.TriggerInstance.hasItems(
					ItemPredicate.Builder
						.item()
						.of(itemLookup, NItemTags.NETHER_FRUITS)
						.build()
		        )
	        )
	        .save(output);
        
        nineBlockStorageRecipes(RecipeCategory.MISC, NItems.CINDER_GRAIN, RecipeCategory.BUILDING_BLOCKS, NBlocks.CINDER_BALE);

        ShapedRecipeBuilder
	        .shaped(itemLookup, RecipeCategory.FOOD, NItems.CINDER_SANGAK)
            .define('G', NItems.CINDER_GRAIN)
            .pattern("GGG")
            .unlockedBy("has_cinder_grain", has(NItems.CINDER_GRAIN))
            .save(output);

        COLOR_MELTING_MAP.forEach((fruit, dye) -> {
            SimpleCookingRecipeBuilder
                .smelting(
                    Ingredient.of(fruit),
                    RecipeCategory.DECORATIONS,
                    CookingBookCategory.BLOCKS, //TODO: ???
                    dye,
                    0.15f,
                    200
                )
                .unlockedBy(getHasName(fruit), has(fruit))
                .save(output, keyOf(getItemName(dye) + "_from_smelting_" + getItemName(fruit)));

            SimpleCookingRecipeBuilder
                .smoking(
                    Ingredient.of(fruit),
                    RecipeCategory.DECORATIONS,
                    dye,
                    0.15f,
                    100
                )
                .unlockedBy(getHasName(fruit), has(fruit))
                .save(output, keyOf(getItemName(dye) + "_from_smoking_" + getItemName(fruit)));

            SimpleCookingRecipeBuilder
                .campfireCooking(
                    Ingredient.of(fruit),
                    RecipeCategory.DECORATIONS,
                    dye,
                    0.15f,
                    600
                )
                .unlockedBy(getHasName(fruit), has(fruit))
                .save(output, keyOf(getItemName(dye) + "_from_campfire_cooking"));

        });

        makeVanillaWheatRecipes(output);

        ShapedRecipeBuilder
            .shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, NBlocks.NEAR_TWIG_BLOCK, 1)
            .define('#', NItems.NEAR_TWIG)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy("has_log", has(NItems.NEAR_TWIG)).save(output);

        ShapelessRecipeBuilder
            .shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, Blocks.CRIMSON_PLANKS, 2)
            .requires(NBlocks.NEAR_TWIG_BLOCK).group("planks")
            .unlockedBy(getHasName(NBlocks.NEAR_TWIG_BLOCK), has(NBlocks.NEAR_TWIG_BLOCK))
            .save(output);
    }

    public ResourceKey<Recipe<?>> keyOf(String name){
        return ResourceKey.create(Registries.RECIPE, Nears.id(name));
    }
    public void makeVanillaWheatRecipes(RecipeOutput output){
        ShapedRecipeBuilder
            .shaped(itemLookup, RecipeCategory.REDSTONE, Blocks.TARGET)
            .define('H', NBlocks.CINDER_BALE)
	        .define('R', Items.REDSTONE)
	        .pattern(" R ")
	        .pattern("RHR")
	        .pattern(" R ")
	        .unlockedBy("has_redstone", has(Items.REDSTONE))
	        .unlockedBy("has_cinder_bale", has(NBlocks.CINDER_BALE))
	        .save(output, fromBale(Blocks.TARGET));
		
        ShapelessRecipeBuilder
	        .shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, Blocks.PACKED_MUD, 1)
	        .requires(Blocks.MUD)
	        .requires(NItems.CINDER_GRAIN)
	        .unlockedBy("has_mud", has(Blocks.MUD))
	        .save(output, fromGrain(Items.PACKED_MUD));
		
        ShapedRecipeBuilder
	        .shaped(itemLookup, RecipeCategory.FOOD, Blocks.CAKE)
	        .define('A', Items.MILK_BUCKET)
	        .define('B', Items.SUGAR)
	        .define('C', NItems.CINDER_GRAIN)
	        .define('E', Items.EGG)
	        .pattern("AAA")
	        .pattern("BEB")
	        .pattern("CCC")
	        .unlockedBy("has_egg", has(Items.EGG))
	        .save(output, fromGrain(Items.CAKE));
		
        ShapedRecipeBuilder
	        .shaped(itemLookup, RecipeCategory.FOOD, Items.COOKIE, 8)
	        .define('#', NItems.CINDER_GRAIN)
	        .define('X', Items.COCOA_BEANS)
	        .pattern("#X#")
	        .unlockedBy("has_cocoa", has(Items.COCOA_BEANS))
	        .save(output, fromGrain(Items.COOKIE));
    }

    public String fromGrain(ItemLike itemConvertible){
        return BuiltInRegistries.ITEM.getKey(itemConvertible.asItem()).getPath() + "_from_cinder_grain";
    }
    public String fromBale(ItemLike itemConvertible){
        return BuiltInRegistries.ITEM.getKey(itemConvertible.asItem()).getPath() + "_from_cinder_bale";
    }
}

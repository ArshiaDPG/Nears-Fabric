package net.digitalpear.nears.init.data;


import net.minecraft.world.food.FoodProperties;

public class NFoodProperties {

    public static final FoodProperties NEAR = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build();
    public static final FoodProperties FAAR = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3F).build();
    public static final FoodProperties SOUL_BERRIES = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2F).build();

    public static final FoodProperties NETHER_STEW = new FoodProperties.Builder().nutrition(10).saturationModifier(0.4F).build();
    public static final FoodProperties CINDER_SANGAK = new FoodProperties.Builder().nutrition(5).saturationModifier(0.3F).build();
    public static final FoodProperties SOULLESS_PASTRY = new FoodProperties.Builder().nutrition(8).saturationModifier(0.3F).build();
    public static final FoodProperties GLOW_SALAD = new FoodProperties.Builder().nutrition(5).saturationModifier(0.7F).build();
}

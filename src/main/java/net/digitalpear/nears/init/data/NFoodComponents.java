package net.digitalpear.nears.init.data;

import net.minecraft.item.FoodComponent;

public class NFoodComponents {



    public static final FoodComponent NEAR = new FoodComponent.Builder().hunger(4).saturationModifier(0.3F).build();
    public static final FoodComponent FAAR = new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build();
    public static final FoodComponent SOUL_BERRIES = new FoodComponent.Builder().hunger(2).saturationModifier(0.2F).build();

    public static final FoodComponent NETHER_STEW = new FoodComponent.Builder().hunger(10).saturationModifier(0.4F).build();
    public static final FoodComponent CINDER_SANGAK = new FoodComponent.Builder().hunger(5).saturationModifier(0.3F).build();
    public static final FoodComponent SOULLESS_PASTRY = new FoodComponent.Builder().hunger(8).saturationModifier(0.3F).build();
    public static final FoodComponent GLOW_SALAD = new FoodComponent.Builder().hunger(5).saturationModifier(0.7F).build();
}

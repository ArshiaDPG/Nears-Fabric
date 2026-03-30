package net.digitalpear.nears.init.data;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.RemoveStatusEffectsConsumeEffect;

public class NConsumables {
    public static final Consumable NETHER_STEW = Consumables.defaultDrink().sound(SoundEvents.GENERIC_EAT).onConsume(new RemoveStatusEffectsConsumeEffect(
        HolderSet.direct(
            BuiltInRegistries.MOB_EFFECT
                .listElements()
                .filter(statusEffectReference -> statusEffectReference.value().getCategory() == MobEffectCategory.HARMFUL)
                .toList()
        )
    )).build();
}

package net.digitalpear.nears.init.data;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.consume.RemoveEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.sound.SoundEvents;

public class NConsumableComponents {
    public static final ConsumableComponent NETHER_STEW = ConsumableComponents.drink().sound(SoundEvents.ENTITY_GENERIC_EAT).consumeEffect(new RemoveEffectsConsumeEffect(
            RegistryEntryList.of(Registries.STATUS_EFFECT.streamEntries().filter(statusEffectReference -> statusEffectReference.value().getCategory() == StatusEffectCategory.HARMFUL).toList())
    )).build();
}

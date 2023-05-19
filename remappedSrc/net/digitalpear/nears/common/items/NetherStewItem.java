package net.digitalpear.nears.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class NetherStewItem extends Item {
    public NetherStewItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        for (StatusEffect statusEffect : user.getActiveStatusEffects().keySet()) {
            if (isHarmful(statusEffect) && world.getRandom().nextFloat() > 0.8) {
                user.removeStatusEffect(statusEffect);
                break;
            }
        }
        if (stack.isEmpty()) {
            return new ItemStack(Items.BOWL);
        } else {
            if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
                ItemStack itemStack = new ItemStack(Items.BOWL);
                PlayerEntity playerEntity = (PlayerEntity)user;
                if (!playerEntity.getInventory().insertStack(itemStack)) {
                    playerEntity.dropItem(itemStack, false);
                }
            }
            return super.finishUsing(stack, world, user);
        }
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public SoundEvent getDrinkSound() {
        return getEatSound();
    }
    public static boolean isHarmful(StatusEffect effect){
        return effect.getCategory() == StatusEffectCategory.HARMFUL;
    }
}

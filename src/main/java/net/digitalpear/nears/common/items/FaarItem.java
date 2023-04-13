package net.digitalpear.nears.common.items;

import net.digitalpear.nears.init.NItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class FaarItem extends Item {
    public FaarItem(Settings settings) {
        super(settings);
    }


    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.random.nextFloat() > 0.6) {
            user.dropItem(NItems.FAAR_SEEDS);
        }
        return super.finishUsing(stack, world, user);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

}

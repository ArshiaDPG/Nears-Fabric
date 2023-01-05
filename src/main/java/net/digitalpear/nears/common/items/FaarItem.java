package net.digitalpear.nears.common.items;

import net.digitalpear.nears.init.NItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class FaarItem extends Item {
    public FaarItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (world.random.nextFloat() > 0.6) {
            if (stack.isEmpty()) {
                return new ItemStack(NItems.FAAR_SEEDS);
            } else {
                if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
                    ItemStack itemStack = new ItemStack(NItems.FAAR_SEEDS);
                    PlayerEntity playerEntity = (PlayerEntity) user;
                    if (!playerEntity.getInventory().insertStack(itemStack)) {
                        playerEntity.dropItem(itemStack, false);
                    }
                }

                return stack;
            }
        }
        return stack;
    }
}

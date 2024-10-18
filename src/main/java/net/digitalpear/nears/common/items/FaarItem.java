package net.digitalpear.nears.common.items;

import net.digitalpear.nears.init.NItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class FaarItem extends Item {
    public FaarItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.random.nextFloat() > 0.6 && world instanceof ServerWorld) {
            user.dropItem((ServerWorld) world, NItems.FAAR_SEEDS);
        }
        return super.finishUsing(stack, world, user);
    }

}

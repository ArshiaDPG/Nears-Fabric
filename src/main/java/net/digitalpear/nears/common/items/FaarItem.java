package net.digitalpear.nears.common.items;

import net.digitalpear.nears.init.NItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FaarItem extends Item {
    public FaarItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (level.getRandom().nextFloat() > 0.6 && level instanceof ServerLevel) {
            //TODO: don't know if this is right either
            user.drop(new ItemStack(NItems.FAAR_SEEDS), true, true);
        }
        return super.finishUsingItem(stack, level, user);
    }
}

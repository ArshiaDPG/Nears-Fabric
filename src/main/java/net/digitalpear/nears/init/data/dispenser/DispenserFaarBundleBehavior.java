package net.digitalpear.nears.init.data.dispenser;

import net.digitalpear.nears.common.blocks.FaarBundleBlock;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class DispenserFaarBundleBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
        BlockPos blockPos = pointer.getPos().offset(direction);
        World world = pointer.getWorld();
        Random random = world.getRandom();
        if (world.getBlockState(blockPos).isAir()){
            stack.split(1);
            world.setBlockState(blockPos, NBlocks.FAAR_BUNDLE.getDefaultState(), 3);
            this.setSuccess(true);
        }
        else if (!world.getBlockState(blockPos).isLiquid()){
            int faarNumber = random.nextBetween(3, 7);
            Position position = DispenserBlock.getOutputLocation(pointer);
            stack.split(1);
            ItemStack output = new ItemStack(NItems.FAAR, faarNumber);
            world.playSound(null, blockPos, FaarBundleBlock.getPopSound(), SoundCategory.BLOCKS, 1F, 2F * random.nextFloat());
            spawnItem(pointer.getWorld(), output, 6, direction, position);
            this.setSuccess(true);
        }

        return stack;
    }
}

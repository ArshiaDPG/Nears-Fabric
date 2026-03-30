package net.digitalpear.nears.init.data.dispenser;

import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.NItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class DispenserFaarBundleBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource source, ItemStack dispensed) {
        Direction direction = source.state().getValue(DispenserBlock.FACING);
        BlockPos blockPos = source.pos().relative(direction);
        Level level = source.level();
        RandomSource random = level.getRandom();
        
        if (level.getBlockState(blockPos).isAir() || level.getBlockState(blockPos).liquid()){
            dispensed.split(1);
            level.setBlock(blockPos, NBlocks.FAAR_BUNDLE.defaultBlockState(), 3);
            this.setSuccess(true);
        }
        else if (!level.getBlockState(blockPos).isFaceSturdy(level, blockPos, direction.getOpposite())){
            int faarNumber = random.nextIntBetweenInclusive(3, 7);
            Position position = DispenserBlock.getDispensePosition(source);
            dispensed.split(1);
            ItemStack output = new ItemStack(NItems.FAAR, faarNumber);
            spawnItem(level, output, 6, direction, position);
            this.setSuccess(true);
        }
        
        return dispensed;
    }
}

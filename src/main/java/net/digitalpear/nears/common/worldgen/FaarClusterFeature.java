package net.digitalpear.nears.common.worldgen;

import com.mojang.serialization.Codec;
import net.digitalpear.nears.init.NBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class FaarClusterFeature extends Feature<DefaultFeatureConfig> {
    public FaarClusterFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        int spreadVertical = 6;
        int spreadHorizontal = 12;
        int chance = 5;

        BlockPos initialPos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();

        Iterable<BlockPos> positions = BlockPos.iterate(initialPos.add(-spreadHorizontal, -spreadVertical, -spreadHorizontal), initialPos.add(spreadHorizontal, spreadVertical, spreadHorizontal));
        int i = 0;
        for (BlockPos pos : positions){
            if (world.getBlockState(pos).isAir() && world.getBlockState(pos.up()).isOf(Blocks.WARPED_WART_BLOCK) && random.nextInt(chance) == 0){
                world.setBlockState(pos, NBlocks.FAAR_BUNDLE.getDefaultState(), 2);
                i++;
            }
        }

        return i > 0;
    }

}

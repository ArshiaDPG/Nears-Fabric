package net.digitalpear.nears.common.worldgen;

import com.mojang.serialization.Codec;
import net.digitalpear.nears.common.blocks.NearHangBlock;
import net.digitalpear.nears.common.blocks.NearHangStemBlock;
import net.digitalpear.nears.init.NBlocks;
import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NetherConfiguredFeatures;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class NearHangFeature extends Feature<DefaultFeatureConfig> {
    public NearHangFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos origin = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        boolean generated = false;
        int radius = 3;
        for (BlockPos pos : BlockPos.iterate(origin.add(-radius, -radius, -radius), origin.add(radius, radius, radius))) {
            if (isSupported(world, pos) && (random.nextFloat() > 0.93)){
                generateNearHang(world, pos, random);
                if (!generated){
                    world.getRegistryManager().getOptional(RegistryKeys.CONFIGURED_FEATURE).flatMap((registry) ->
                            registry.getEntry(NetherConfiguredFeatures.WEEPING_VINES)).ifPresent((reference) ->
                            reference.value().generate(world, context.getGenerator(), random, origin.up()));
                }
                generated = true;
            }
        }

        return generated;
    }

    public static void generateNearHang(StructureWorldAccess world, BlockPos pos, Random random){
        int length = random.nextBetween(6, 8);
        world.setBlockState(pos, Blocks.NETHER_WART_BLOCK.getDefaultState(), 3);
        for (int i = 1; i < length; i++){
            if (world.getBlockState(pos.down(i + 1)).isAir() && (pos.down().getY() > world.getBottomY())){
                world.setBlockState(pos.down(i), NBlocks.NEAR_HANG_STEM.getDefaultState()
                        .with(NearHangStemBlock.AGE, random.nextInt(3))
                        .with(NearHangStemBlock.SUPPORTED, world.getBlockState(pos.down(i)).isOf(NBlocks.NEAR_HANG_STEM)), 3);
            }
            else{
                world.setBlockState(pos.down(i), NBlocks.NEAR_HANG.getDefaultState().with(NearHangBlock.MATURED, true).with(NearHangBlock.AGE, 5), 3);
                return;
            }
        }
        world.setBlockState(pos.down(length), NBlocks.NEAR_HANG.getDefaultState().with(NearHangBlock.MATURED, true).with(NearHangBlock.AGE, 5), 3);
    }
    public static boolean isSupported(StructureWorldAccess world, BlockPos pos){
        for (BlockPos pos1 : BlockPos.iterate(pos.down(), pos.down(5))) {
            if (!world.getBlockState(pos1).isAir()) {
                return false;
            }
        }
        return  isBlockStable(world, pos) && isBlockStable(world, pos.up());
    }

    public static boolean isBlockStable(StructureWorldAccess world, BlockPos pos){
        return (world.getBlockState(pos.up()).isIn(BlockTags.BASE_STONE_NETHER) || world.getBlockState(pos.up()).isIn(NBlockTags.NEAR_HANG_PLANTABLE_ON));
    }
}

package net.digitalpear.nears.common.blocks;

import net.digitalpear.nears.init.NItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


public class FaarBundleBlock extends FallingBlock {

    public FaarBundleBlock(Settings settings) {
        super(settings);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isNotSupported(world, pos)){
            super.scheduledTick(state, world, pos, random);
        }
    }

    public boolean isNotSupported(World world, BlockPos pos){
        return !world.getBlockState(pos.up()).isOf(Blocks.WARPED_WART_BLOCK);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (isNotSupported(world, pos)) {
            super.onBlockAdded(state, world, pos, oldState, notify);
        }
    }

    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else {
            this.bounceEntity(entity);
        }
    }

    private void bounceEntity(Entity entity) {
        World world = entity.getWorld();
        BlockPos pos = entity.getBlockPos().down();
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0D) {
            double d = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setVelocity(vec3d.x, -vec3d.y * 0.6600000262260437D * d, vec3d.z);
        }
        if (vec3d.y < -0.8D){
            onBreak(world, pos, this.getDefaultState(), null);
            combust(entity);
        }
    }

    public void combust(Entity entity) {
        World world = entity.getWorld();
        SoundEvent popSound = Blocks.PUMPKIN.getSoundGroup(Blocks.PUMPKIN.getDefaultState()).getBreakSound();
        world.playSoundFromEntity(null, entity, popSound, SoundCategory.BLOCKS, 1.0F, 1.0F);
        int i = 1 + world.random.nextBetween(4, 6);
        for(int j = 0; j < i; ++j) {
            ItemEntity itemEntity = entity.dropItem(NItems.FAAR, 1);
            if (itemEntity != null) {
                itemEntity.setVelocity(itemEntity.getVelocity().add(
                        (world.random.nextFloat() - world.random.nextFloat()) * 0.1F,
                        world.random.nextFloat() * 0.05F,
                        (world.random.nextFloat() - world.random.nextFloat()) * 0.1F));
            }
        }
    }
}

package net.digitalpear.nears.common.blocks;

import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
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
        return !world.getBlockState(pos.up()).isIn(NBlockTags.FAAR_GROWTH_PLANTABLE_ON);
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
        World world = entity.method_48926();
        BlockPos pos = entity.getSteppingPos();
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0D) {
            double d = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setVelocity(vec3d.x, -vec3d.y * 0.6600000262260437D * d, vec3d.z);
        }
        if (vec3d.y < -0.8D){
            world.breakBlock(pos, true);
        }
    }


}

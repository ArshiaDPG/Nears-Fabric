package net.digitalpear.nears.common.blocks;

import net.digitalpear.nears.init.data.tags.NBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FaarBundleBlock extends ColoredFallingBlock {
    public FaarBundleBlock(ColorRGBA color, Properties settings) {
        super(color, settings);
    }
    
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isNotSupported(level, pos)){
            super.tick(state, level, pos, random);
        }
    }

    public boolean isNotSupported(Level world, BlockPos pos){
        return !world.getBlockState(pos.above()).is(NBlockTags.FAAR_GROWTH_PLANTABLE_ON);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (isNotSupported(world, pos)) {
            super.onPlace(state, world, pos, oldState, notify);
        }
    }
    
    @Override
    public void updateEntityMovementAfterFallOn(BlockGetter level, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityMovementAfterFallOn(level, entity);
        } else {
            this.bounceEntity(entity);
        }
    }

    @Override
    public float getJumpFactor() {
        return 1.0F;
    }

    private void bounceEntity(Entity entity) {
        Level level = entity.level();
        BlockPos pos = entity.getOnPos();
        Vec3 vec3d = entity.getDeltaMovement();
        if (vec3d.y < 0.0D) {
            double d = (entity instanceof LivingEntity) ? 1.0D : 0.8D;
            entity.setDeltaMovement(vec3d.x, -vec3d.y * 0.6600000262260437D * d, vec3d.z);
        }
        if (vec3d.y < -0.8D){
            level.destroyBlock(pos, true);
        }
    }


}

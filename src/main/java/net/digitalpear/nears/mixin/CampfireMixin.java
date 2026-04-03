package net.digitalpear.nears.mixin;


import net.digitalpear.nears.init.NBlocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfireMixin {

    @Inject(method = "isSmokeSource", at = @At("RETURN"), cancellable = true)
    private void injectMethod(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(NBlocks.CINDER_BALE)){
            cir.setReturnValue(true);
        }
    }
}

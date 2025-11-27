package org.btwr.tough_environment.mixin.block;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.btwr.tough_environment.util.BlockBreakSpeedManager;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void setSpeedModifier(BlockState block, CallbackInfoReturnable<Float> cir, @Local float f) {
        float modifiedSpeed = BlockBreakSpeedManager.getInstance().getModifiedSpeed((PlayerEntity) (Object)this, block, f);
        cir.setReturnValue(modifiedSpeed);
    }

}

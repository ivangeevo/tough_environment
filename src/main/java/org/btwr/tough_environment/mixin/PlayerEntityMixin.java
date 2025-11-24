package org.btwr.tough_environment.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.btwr.tough_environment.util.BlockBreakSpeedManager;
import org.btwr.tough_environment.util.PlayerEffectsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin
{
    @Unique private PlayerEntity playerEntity = (PlayerEntity)(Object)this;
    @Unique private PlayerEffectsManager effectsManager = PlayerEffectsManager.getInstance();

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    public void applyMovementEffectsOnTick(BlockState block, CallbackInfoReturnable<Float> cir, @Local float f) {
       cir.setReturnValue(BlockBreakSpeedManager.getInstance().getModifiedSpeed(playerEntity, block, f));
    }
}
package org.tough_environment.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tough_environment.util.PlayerBreakingTracker;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {

    @Final @Shadow protected ServerPlayerEntity player;

    @Shadow private BlockPos miningPos;

    @Inject(method = "update", at = @At("HEAD"))
    private void captureBlockBreaking(CallbackInfo ci) {
        if (miningPos != null) {
            World world = player.getServerWorld();
            BlockState state = world.getBlockState(miningPos);
            PlayerBreakingTracker.set(player, miningPos, state);
        } else {
            PlayerBreakingTracker.clear(player);
        }
    }

}
package org.tough_environment.mixin.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tough_environment.util.OGPistonPackingUtil;

@Mixin(PistonBlockEntity.class)
public class PistonBlockEntityMixin {

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeBlockEntity(Lnet/minecraft/util/math/BlockPos;)V"))
    private static void onTick(World world, BlockPos pos, BlockState state, PistonBlockEntity blockEntity, CallbackInfo ci) {
        if (!world.isClient) {
            Direction movementDirection = blockEntity.getMovementDirection();
            boolean isExtending = blockEntity.isExtending();
            BlockPos targetPos;
            if (isExtending) {
                targetPos = pos.offset(movementDirection);
            } else {
                targetPos = pos.offset(movementDirection.getOpposite());

            }
            OGPistonPackingUtil.attemptToPackItems(world, targetPos, movementDirection);
            //PistonPackingUtil.getInstance().attemptToPackItems(world, targetPos, movementDirection);

        }
    }



}

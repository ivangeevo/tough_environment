package org.tough_environment.block.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public interface DirectionalDroppingBlock extends StateConvertableBlock {

    default Direction getBlockHitSide() {

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        ClientPlayerEntity player = minecraftClient.player;

        // Ensure the player and world are not null
        if (player != null && minecraftClient.world != null) {
            // Get the player's crosshair position
            BlockHitResult hitResult = (BlockHitResult) minecraftClient.crosshairTarget;

            // Check if the crosshair is pointing at a block
            assert hitResult != null;
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = hitResult.getBlockPos();
                BlockState blockState = minecraftClient.world.getBlockState(blockPos);

                // Get the side from the block state
                return hitResult.getSide();
            }
        }

        return null; // Return null if the player or world is null, or if the crosshair is not pointing at a block
    }

    default boolean shouldDirectionalDrop(BlockState state, ItemStack tool) {
        return false;
    }

}

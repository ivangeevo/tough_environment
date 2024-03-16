package org.tough_environment.block.interfaces;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface DirectionalDroppingBlock  {

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

                // Get the side from the block state
                return hitResult.getSide();
            }
        }

        return null; // Return null if the player or world is null, or if the crosshair is not pointing at a block
    }

    default void ejectStackTowardsPlayer(World world, BlockPos pos, ItemStack stack) {

        Direction facing = getBlockHitSide();
        if (facing != null) {
            Vec3d playerPos = world.getPlayerByUuid(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(),
                    -1.0, false).getUuid()).getPos();
            double xSpeed = playerPos.x - (pos.getX() + 0.5);
            double ySpeed = playerPos.y - (pos.getY() + 0.5);
            double zSpeed = playerPos.z - (pos.getZ() + 0.5);
            double speed = 0.05;
            ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5,
                    pos.getY() + 0.5, pos.getZ() + 0.5, stack);
            itemEntity.setVelocity(xSpeed * speed, ySpeed * speed, zSpeed * speed);
            world.spawnEntity(itemEntity);
        }
    }



    default boolean shouldDirectionalDrop(BlockState state, ItemStack tool) {
        return false;
    }

}

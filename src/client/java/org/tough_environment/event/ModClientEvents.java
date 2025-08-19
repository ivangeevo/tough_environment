package org.tough_environment.event;

import net.fabricmc.fabric.api.event.client.player.ClientPlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.tough_environment.block.BlockBreakHandler;
import org.tough_environment.block.blocks.StoneConvertingBlock;

public class ModClientEvents {

    public static void register() {
        // Player Block Events
        ClientPlayerBlockBreakEvents.AFTER.register(ModClientEvents::onAfterBlockBreak);
    }

    // Directly update client world block state to prevent flicker
    private static void onAfterBlockBreak(ClientWorld world, ClientPlayerEntity player, BlockPos pos, BlockState state) {
        ItemStack tool = player.getMainHandStack();

        BlockState newVanillaStoneState = BlockBreakHandler.getInstance().getNextStateForVanillaStone(state, tool);
        updateStoneState(world, pos, newVanillaStoneState);

        if (state.getBlock() instanceof StoneConvertingBlock) {
            BlockState newModdedStoneState = BlockBreakHandler.getInstance().getNextStateForModdedStone(state, tool);
            updateStoneState(world, pos, newModdedStoneState);
        }
    }

    private static void updateStoneState(ClientWorld world, BlockPos pos, BlockState state) {
        if (state != null) {
            world.setBlockState(pos, state);
        }
    }

}

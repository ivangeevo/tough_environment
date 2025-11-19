package org.tough_environment.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class PlayerBreakingTracker {

    public record BreakingBlock(BlockPos pos, BlockState state) { }

    private static final Map<ServerPlayerEntity, BreakingBlock> TRACKER = new HashMap<>();

    public static void set(ServerPlayerEntity player, BlockPos pos, BlockState state) {
        TRACKER.put(player, new BreakingBlock(pos, state));
    }

    public static BreakingBlock get(ServerPlayerEntity player) {
        return TRACKER.get(player);
    }

    public static void clear(ServerPlayerEntity player) {
        TRACKER.remove(player);
    }
}

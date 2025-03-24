package org.tough_environment.util;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

public class WorldGenerationEventListener {

    // Hook into chunk load to detect when world generation is happening
    public static void onChunkLoad(ServerWorld world, Chunk chunk) {
        // If chunk is in the generation phase, set it to true
        if (chunk.getStatus() == ChunkStatus.EMPTY) {
            WorldGenerationState.onWorldGenerationStart();
        } else {
            WorldGenerationState.onWorldGenerationEnd();
        }
    }

    // Hook into chunk unload (optional if needed)
    public static void onChunkUnload(ServerWorld world, Chunk chunk) {
        WorldGenerationState.onWorldGenerationEnd();
    }
}

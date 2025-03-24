package org.tough_environment.data;

import dev.corgitaco.dataanchor.data.registry.TrackedDataKey;
import dev.corgitaco.dataanchor.data.type.chunk.ChunkTrackedData;
import dev.corgitaco.dataanchor.data.type.chunk.ProtoChunkTrackedData;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import org.tough_environment.util.BlockStateReplacer;
import org.tough_environment.util.BlockReplacerService;
import org.tough_environment.util.WorldGenerationState;

public class BlockSwapProtoChunkData extends ProtoChunkTrackedData {

    public BlockSwapProtoChunkData(TrackedDataKey<? extends ChunkTrackedData> trackedDataKey, ProtoChunk protoChunk) {
        super(trackedDataKey, protoChunk);

        // Check the world generation status before applying block replacements
        boolean isWorldGeneration = WorldGenerationState.isWorldGeneration();  // Assuming this method exists

        // Apply block replacement logic to all chunk sections
        for (ChunkSection section : protoChunk.getSectionArray()) {
            if (section instanceof BlockStateReplacer replacer) {
                if (isWorldGeneration) {
                    // Skip block replacement if we're in world generation phase
                    continue;
                }

                // Set the block replacement map after world generation is complete
                Int2IntMap map = BlockReplacerService.getBlockReplacementMap();
                replacer.setReplaceMap(map);
            }
        }
    }
}

package org.tough_environment.data;

import dev.corgitaco.dataanchor.data.registry.TrackedDataKey;
import dev.corgitaco.dataanchor.data.type.chunk.ChunkBlockStateInterceptor;
import dev.corgitaco.dataanchor.data.type.chunk.LevelChunkTrackedData;
import dev.corgitaco.dataanchor.data.type.chunk.ServerLevelChunkTrackedData;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlockSwapWorldChunkData extends ServerLevelChunkTrackedData implements ChunkBlockStateInterceptor {

    private final Set<String> placeHolders = new HashSet<>();

    Map<BlockState, BlockState> blockStateMap = Util.make(new Reference2ReferenceOpenHashMap<>(), map -> map.put(Blocks.STONE.getDefaultState(), Blocks.WATER.getDefaultState()));


    public BlockSwapWorldChunkData(TrackedDataKey<? extends LevelChunkTrackedData> trackedDataKey, WorldChunk worldChunk) {
        super(trackedDataKey, worldChunk);
    }


    @Override
    public NbtCompound save() {
        return super.save();
    }

    @Override
    public void load(NbtCompound compoundTag) {
    }

    @Override
    public BlockState getNewState(BlockPos blockPos, BlockState blockState, BlockState blockState1, boolean b) {
        return blockStateMap.getOrDefault(blockState, blockState1);
    }

    public Set<String> getPlaceHolders() {
        return placeHolders;
    }
}
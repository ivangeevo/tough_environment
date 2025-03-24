package org.tough_environment.util;


import net.minecraft.block.BlockState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.function.Supplier;

public interface BlockSwapPredicate {


    boolean test(World world, Chunk chunk, int localX, int localY, int localZ, BlockState lastState);

    default RegistryEntry<Biome> getBiome(World level, Chunk chunk, int localX, int localY, int localZ, Supplier<BlockPos.Mutable> mutableBlockPosSupplier, boolean useFiddle) {
        if (!useFiddle) {
            return chunk.getBiomeForNoiseGen(localX, localY, localZ);
        } else {
            ChunkPos pos = chunk.getPos();
            BlockPos.Mutable mutableBlockPos = mutableBlockPosSupplier.get();
            mutableBlockPos.set(pos.getOffsetX(localX), localY, pos.getOffsetZ(localZ));
            return level.getBiome(mutableBlockPos);
        }
    }
}
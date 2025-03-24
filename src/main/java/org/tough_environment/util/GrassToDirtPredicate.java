package org.tough_environment.util;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class GrassToDirtPredicate implements BlockSwapPredicate {
    @Override
    public boolean test(World world, Chunk chunk, int localX, int localY, int localZ, BlockState lastState) {
        return lastState.isOf(Blocks.GRASS_BLOCK); // Only swap grass blocks
    }
}

package org.tough_environment.util;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class BlockSwapHelper {

    /**
     * Creates and returns a swap map with the specified block swaps.
     * @param blockPairs varargs pairs of block states to swap (original -> replacement)
     * @return A populated Int2IntMap with block swaps.
     */
    public static Int2IntMap createSwapMap(BlockSwapPair... blockPairs) {
        Int2IntMap swapMap = new Int2IntOpenHashMap(blockPairs.length);

        for (BlockSwapPair pair : blockPairs) {
            swapMap.put(pair.getOriginalId(), pair.getSwappedId());
        }

        return swapMap;
    }

    /**
     * Helper class to store pairs of blocks to swap.
     */
    public static class BlockSwapPair {
        private final int originalId;
        private final int swappedId;

        public BlockSwapPair(Block originalBlock, Block swappedBlock) {
            this.originalId = Block.STATE_IDS.getRawIdOrThrow(originalBlock.getDefaultState());
            this.swappedId = Block.STATE_IDS.getRawIdOrThrow(swappedBlock.getDefaultState());
        }

        public int getOriginalId() {
            return originalId;
        }

        public int getSwappedId() {
            return swappedId;
        }
    }
}

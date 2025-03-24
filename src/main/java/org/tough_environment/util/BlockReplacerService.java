package org.tough_environment.util;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.tough_environment.block.ModBlocks;

public class BlockReplacerService
{

    /**
     * This method will create and return the replacement map for specific blocks.
     *
     * @return Int2IntMap containing the block replacement rules.
     */
    public static Int2IntMap getBlockReplacementMap() {
        Int2IntMap map = new Int2IntOpenHashMap();

        addPairEntry(map, Blocks.CLAY, ModBlocks.CLAY_ORE);

        return map;
    }

    private static void addPairEntry(Int2IntMap map, Block target, Block replaced) {
        map.put(Block.STATE_IDS.getRawIdOrThrow(target.getDefaultState()), Block.STATE_IDS.getRawIdOrThrow(replaced.getDefaultState()));
    }
}

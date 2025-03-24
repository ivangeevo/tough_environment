package org.tough_environment.util;

import it.unimi.dsi.fastutil.ints.Int2IntMap;

public interface BlockStateReplacer
{
    
    /**
     * Sets the block state swap map for this chunk section.
     *
     * @param replaceMap A mapping of original block state IDs to swapped block state IDs.
     */
    void setReplaceMap(Int2IntMap replaceMap);
}

package org.tough_environment.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.FallingBlock;

public class LooseAggregateBlock extends FallingBlock
{
    public static final MapCodec<LooseAggregateBlock> CODEC = LooseAggregateBlock.createCodec(LooseAggregateBlock::new);

    public LooseAggregateBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return CODEC;
    }
}
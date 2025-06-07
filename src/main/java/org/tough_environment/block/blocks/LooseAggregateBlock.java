package org.tough_environment.block.blocks;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;

public class LooseAggregateBlock extends FallingBlock
{
    public static final MapCodec<LooseAggregateBlock> CODEC = LooseAggregateBlock.createCodec(LooseAggregateBlock::new);

    // Block parameters and constants & Super settings //
    public LooseAggregateBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return CODEC;
    }


    // ---------------------------------- //
}

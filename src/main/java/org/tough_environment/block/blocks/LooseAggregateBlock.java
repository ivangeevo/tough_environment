package org.tough_environment.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.tag.BTWRConventionalTags;

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

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }



    // Block specific logic //
    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state,
                           @Nullable BlockEntity blockEntity, ItemStack tool)
    {


        // handles the case where the LooseBlock is a DIRT_LOOSE and mined with a hoe
        if (tool.isIn(BTWRConventionalTags.Items.MODERN_HOES) || tool.isIn(BTWRConventionalTags.Items.ADVANCED_HOES))
        {

            if (state.isOf(ModBlocks.DIRT_LOOSE))
            {
                world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
            }

        }

        super.afterBreak(world, player, pos, state, blockEntity, tool);


    }




    // ---------------------------------- //
}

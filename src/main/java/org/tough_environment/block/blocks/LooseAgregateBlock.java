package org.tough_environment.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

public class LooseAgregateBlock extends FallingBlock
{
    public static final MapCodec<LooseAgregateBlock> CODEC = LooseAgregateBlock.createCodec(LooseAgregateBlock::new);

    // Block parameters and constants & Super settings //
    public LooseAgregateBlock(Settings settings)
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

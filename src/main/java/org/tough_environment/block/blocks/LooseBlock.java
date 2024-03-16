package org.tough_environment.block.blocks;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.tag.ModTags;

import java.util.HashMap;
import java.util.Map;

public class LooseBlock extends MortarReceiverBlock
{

    // Block parameters and constants & Super settings //
    public LooseBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()));
    }



    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        if (!world.isClient && player.getStackInHand(hand).isIn(ModTags.Items.MORTARING_ITEMS))
        {

            // Mortar the block
            this.applyMortar(state, world, pos, player);

            // Reduce item stack size
            ItemStack handStack = player.getStackInHand(hand);
            handStack.decrement(1);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }


    // Block specific logic //
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state,
                           @Nullable BlockEntity blockEntity, ItemStack tool)
    {


        // handles the case where the LooseBlock is a DIRT_LOOSE and mined with a hoe
        if (tool.isIn(ModTags.Items.MODERN_HOES))
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

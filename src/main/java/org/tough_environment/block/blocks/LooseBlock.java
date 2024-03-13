package org.tough_environment.block.blocks;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.block.interfaces.Mortarable;
import org.tough_environment.tag.ModTags;

import java.util.HashMap;
import java.util.Map;

public class LooseBlock extends FallingBlock implements Mortarable, LandingBlock
{
    private static final Map<BlockPos, Integer> delayedFalls = new HashMap<>();


    // Block parameters and constants & Super settings //
    public LooseBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(HAS_MORTAR);
    }

    @Override
    public void applyMortar(BlockState state, World world, BlockPos pos, PlayerEntity player)
    {

        Block newBlock = getReplacementBlock(state.getBlock());
        if (newBlock != null)
        {
            BlockState newState = newBlock.getDefaultState();
            world.setBlockState(pos, newState);
        }

        world.playSound(null,pos, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.BLOCKS);

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        if (!world.isClient
                && player.getStackInHand(hand).isIn(ModTags.Items.MORTARING_ITEMS))
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

    private Block getReplacementBlock(Block originalBlock)
    {

        if (originalBlock == ModBlocks.COBBLESTONE_LOOSE)
        { return Blocks.COBBLESTONE; }

        if (originalBlock == ModBlocks.COBBLED_DEEPSLATE_LOOSE)
        { return Blocks.COBBLED_DEEPSLATE; }

        if (originalBlock == ModBlocks.GRANITE_LOOSE)
        { return Blocks.GRANITE; }

        if (originalBlock == ModBlocks.ANDESITE_LOOSE)
        { return Blocks.ANDESITE; }

        return null;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (canFallThrough(world.getBlockState(pos.down())) || shouldFallDelayed(world, pos)) {
            super.scheduledTick(state, world, pos, random);
        }
    }

    private boolean shouldFallDelayed(World world, BlockPos pos) {
        if (delayedFalls.containsKey(pos)) {
            int remainingTicks = delayedFalls.get(pos);
            if (remainingTicks > 0) {
                delayedFalls.put(pos, remainingTicks - 1);
                return true;
            } else {
                delayedFalls.remove(pos);
                return false;  // Fall instantly if the delay is over
            }
        }

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos offsetPos = pos.offset(direction);
            if (world.getBlockState(offsetPos).isIn(ModTags.Blocks.MORTARED_BLOCKS)) {
                return true;
            }
        }
        return false;
    }



    // Custom block update logic
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (delayedFalls.containsKey(pos)) {
            delayedFalls.put(pos, delayedFalls.get(pos) - 1);
            if (delayedFalls.get(pos) <= 0) {
                world.breakBlock(pos, true);
                delayedFalls.remove(pos);
            }
        }
    }

    static {
        // Register the custom tick handler
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for (BlockPos pos : delayedFalls.keySet()) {
                delayedFalls.put(pos, delayedFalls.get(pos) - 1);
                if (delayedFalls.get(pos) <= 0) {
                    world.breakBlock(pos, true);
                    delayedFalls.remove(pos);
                }
            }
        });
    }


    // Block specific logic //
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state,
                           @Nullable BlockEntity blockEntity, ItemStack tool)
    {


        // handles the case where the LooseBlock is a DIRT_LOOSE and mined with a hoe
        if ((tool.isOf(Items.IRON_HOE)) || (tool.isOf(Items.DIAMOND_HOE)))
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

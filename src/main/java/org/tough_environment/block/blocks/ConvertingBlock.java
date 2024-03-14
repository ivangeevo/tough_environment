package org.tough_environment.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.block.interfaces.StateConvertableBlock;
import org.tough_environment.state.property.ModProperties;
import org.tough_environment.tag.ModTags;

public class ConvertingBlock extends Block implements StateConvertableBlock
{
    public static final IntProperty BREAK_LEVEL = ModProperties.BREAK_LEVEL;

    public ConvertingBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(BREAK_LEVEL, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BREAK_LEVEL);
    }


    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state,
                           @Nullable BlockEntity blockEntity, ItemStack stack)
    {

        super.afterBreak(world, player, pos, state, blockEntity, stack);
        this.convertBlock(world, pos, state, stack);

    }


    // combiner method for emitting block based events
    public void emitBlockEvents(World world, BlockPos pos, BlockState state)
    {
        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(state));
        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
    }




    public void convertBlock(World world, BlockPos pos, BlockState state, ItemStack stack)
    {

        boolean isModernPickaxe = stack.isIn(ModTags.Items.MODERN_PICKAXES);
        boolean isPrimitivePickaxe = stack.isIn(ModTags.Items.PRIMITIVE_PICKAXES);
        boolean isModernChisel = stack.isIn(ModTags.Items.MODERN_CHISELS);

        int breakLevel = state.get(BREAK_LEVEL);

        if ((isModernPickaxe || isPrimitivePickaxe) && breakLevel >= 2)
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            this.emitBlockEvents(world, pos, state);
            return;
        }

        if (isPrimitivePickaxe && breakLevel < 1)
        {
            world.setBlockState(pos, state.with(BREAK_LEVEL, 5));
            this.emitBlockEvents(world, pos, state);
            return;

        }

        if (isModernChisel)
        {
            this.handleModernChiselBreak(world, pos, state);
            this.emitBlockEvents(world, pos, state);
            return;

        }

        if (breakLevel >= 9 && !(state.getBlock() instanceof BrokenBlock))
        {

                if (state.isIn(ModTags.Blocks.CONVERTED_STONE_BLOCKS))
            {
                world.setBlockState(pos, ModBlocks.STONE_BROKEN.getDefaultState());
            }

            this.emitBlockEvents(world, pos, state);
            return;
        }

        world.setBlockState(pos, state.with(BREAK_LEVEL, breakLevel + 1));
        this.emitBlockEvents(world, pos, state);
    }




    private void handleModernChiselBreak(World world, BlockPos pos, BlockState state)
    {
        int breakLevel = state.get(BREAK_LEVEL);

        // Calculate the new break level
        int newBreakLevel = Math.min(breakLevel + 2, 9);

        // If break level is greater or equal to 7, set to broken state;
        // otherwise, update the block state
        if (breakLevel >= 7)
        {
            world.setBlockState(pos, ModBlocks.STONE_BROKEN.getDefaultState());
        }
        else
        {
            world.setBlockState(pos, state.with(BREAK_LEVEL, newBreakLevel));
        }
    }



    private void playSpecialBreakFX(ServerWorld world, BlockPos pos)
    {}







}

package org.tough_environment.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
        this.convertOnBreak(world, pos, state, stack);
        super.afterBreak(world, player, pos, state, blockEntity, stack);

    }


    // combiner method for emitting block based events
    public void emitBlockEvents(World world, BlockPos pos, BlockState state)
    {
        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(state));
        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
    }




    public void convertOnBreak(World world, BlockPos pos, BlockState state, ItemStack stack)
    {
        int breakLevel = state.get(BREAK_LEVEL);

        world.setBlockState(pos, state.with(BREAK_LEVEL, breakLevel + 1));
        this.emitBlockEvents(world, pos, state);
    }







    private void playSpecialBreakFX(ServerWorld world, BlockPos pos)
    {}







}

package org.tough_environment.block.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Unique;
import org.tough_environment.block.ModBlocks;

public interface StateConvertableBlock {

    @Unique
    default void setAdjacentDirtBlocksOnBreak(World world, BlockPos pos) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        // Check the four cardinal directions
        for (Direction direction : Direction.Type.HORIZONTAL) {
            mutablePos.set(pos).move(direction);
            BlockState neighborState = world.getBlockState(mutablePos);

            // Check if the neighbor is dirt and not already loose dirt
            if (neighborState.getBlock() == Blocks.DIRT && neighborState.getBlock() != ModBlocks.DIRT_LOOSE) {
                world.setBlockState(mutablePos, ModBlocks.DIRT_LOOSE.getDefaultState());
            }
        }
    }

    default void setConvertableStates(World world, BlockPos pos, BlockState state, ItemStack tool) {}



}

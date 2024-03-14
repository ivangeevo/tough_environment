package org.tough_environment.block.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BrokenBlock extends ConvertingBlock
{


    public BrokenBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public void convertBlock(World world, BlockPos pos, BlockState state, ItemStack stack) {

        if (state.getBlock() instanceof BrokenBlock && stack.isIn(ItemTags.PICKAXES))
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            this.emitBlockEvents(world, pos, state);
            return;
        }

        super.convertBlock(world, pos, state, stack);
    }
}

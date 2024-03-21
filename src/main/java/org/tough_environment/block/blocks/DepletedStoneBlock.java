package org.tough_environment.block.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DepletedStoneBlock extends ConvertingBlock
{


    public DepletedStoneBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public void convert(World world, BlockPos pos, BlockState state, ItemStack stack) {

        if (state.getBlock() instanceof DepletedStoneBlock && stack.isIn(ItemTags.PICKAXES))
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            //this.emitBlockEvents(world, pos, state);
            return;
        }

        super.convert(world, pos, state, stack);
    }
}

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

        // if broken with a pickaxe, break fully (set to air)
        if (stack.isIn(ItemTags.PICKAXES) && !world.isClient)
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }
        // else increment break level
        super.convert(world, pos, state, stack);
    }
}

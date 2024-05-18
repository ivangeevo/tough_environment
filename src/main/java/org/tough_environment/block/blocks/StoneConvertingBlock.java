package org.tough_environment.block.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

public class StoneConvertingBlock extends ConvertingBlock
{

    public StoneConvertingBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public void convert(World world, BlockPos pos, BlockState state, ItemStack stack)
    {

        int breakLevel = state.get(BREAK_LEVEL);
        boolean isModernChisel = stack.isIn(BTWRConventionalTags.Items.MODERN_CHISELS);
        boolean isPrimitivePickaxe = stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES);
        boolean isModernPickaxe = stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES);
        boolean isAdvancedPickaxe = stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES);



        if ( (isAdvancedPickaxe || isModernPickaxe) || ( isPrimitivePickaxe && breakLevel >= 5 ))
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }

        if (isPrimitivePickaxe)
        {
            world.setBlockState(pos, state.with(BREAK_LEVEL, 5));
            return;
        }

        if (isModernChisel)
        {
            this.handleModernChiselBreak(world, pos, state);
            return;
        }

        if (breakLevel >= 8 && !(state.getBlock() instanceof DepletedStoneBlock))
        {

            if (state.isIn(ModTags.Blocks.CONVERTED_STONE_BLOCKS))
            {
                world.setBlockState(pos, ModBlocks.STONE_BROKEN.getDefaultState());
            }

            return;
        }

        super.convert(world, pos, state, stack);

    }


    private void handleModernChiselBreak(World world, BlockPos pos, BlockState state)
    {
        int breakLevel = state.get(BREAK_LEVEL);

        // Calculate the new break level
        int newBreakLevel = Math.min(breakLevel + 2, 8);

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

}

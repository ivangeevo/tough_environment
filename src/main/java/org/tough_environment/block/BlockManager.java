package org.tough_environment.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.tough_environment.block.blocks.ConvertingBlock;
import org.tough_environment.item.items.ChiselItem;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

import java.util.HashMap;
import java.util.Map;

import static org.tough_environment.block.blocks.ConvertingBlock.BREAK_LEVEL;

/** A class that is used for managing the behaviour of mixin'd in logic for blocks in the mod.
 *  Different vanilla blocks are affected in more advanced ways with BTWR, so we inject additional logic
 *  that would usually require a dedicated class.
 */
public class BlockManager
{

    private static final BlockManager instance = new BlockManager();

    // Private constructor to prevent instantiation
    private BlockManager() {}
    public static BlockManager getInstance()
    {
        return instance;
    }


    // 0.005f hunger whenever a block is placed
    public void handleOnPlaced(LivingEntity placer)
    {
        if (placer instanceof PlayerEntity)
        {
            HungerManager hungerManager = ((PlayerEntity) placer).getHungerManager();
            hungerManager.addExhaustion(0.005f);
        }
    }

    public void handleAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {

        setConvertibleState(world, pos, state, tool);

        if (!(tool.getItem() instanceof AxeItem) && state.getHardness(world, pos) <= 0)
        {
            player.addExhaustion(0.2f);
        }

    }

    public void playDingSoundOnBreak(World world, BlockPos pos, BlockState state, ItemStack stack, PlayerEntity player)
    {
        if ( shouldDing(state, stack) && !player.isCreative())
        {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_LAND,
                    SoundCategory.BLOCKS,0.5F,world.random.nextFloat() * 0.25F + 1.75F);
        }
    }

    private boolean shouldDing(BlockState state, ItemStack stack)
    {
        // TODO: Add chisel ding sound when breaking and also ding sound for primitive pickaxes breaking stone.
        if (state.getBlock() instanceof ConvertingBlock && stack.getItem() != null && !state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS))
        {
            int breakLevel = state.get(BREAK_LEVEL);

            if ( (breakLevel == 0 || breakLevel == 2 || breakLevel == 4 || breakLevel == 6 || breakLevel == 8)
                    && !isFullyBreakingTool(stack) )
            {
                return true;
            }
        }

        if ( (state.getBlock() instanceof ExperienceDroppingBlock && !isFullyBreakingTool(stack)) )
        {
            return true;
        }

        // Default condition
        return false;
    }





    private void setConvertibleState(World world, BlockPos pos, BlockState state, ItemStack tool)
    {
        if (!world.isClient)
        {

            if ( isDirtLooseningBlock(state) ) {
                setStateForDirt(world, pos, state, tool);
            }

            if ( state.isIn(BTWRConventionalTags.Blocks.ORES) )
            {
                setStateForOre(world, pos, state, tool);
            }

            Map<Block, BlockState> blockMap = new HashMap<>();

            blockMap.put(Blocks.STONE, ModBlocks.STONE_CONVERTING.getDefaultState());
            blockMap.put(Blocks.GRANITE, ModBlocks.GRANITE_CONVERTING.getDefaultState());
            blockMap.put(Blocks.ANDESITE, ModBlocks.ANDESITE_CONVERTING.getDefaultState());
            blockMap.put(Blocks.DIORITE, ModBlocks.DIORITE_CONVERTING.getDefaultState());
            blockMap.put(Blocks.CALCITE, ModBlocks.CALCITE_CONVERTING.getDefaultState());
            blockMap.put(Blocks.TUFF, ModBlocks.TUFF_CONVERTING.getDefaultState());
            blockMap.put(Blocks.BLACKSTONE, ModBlocks.BLACKSTONE_CONVERTING.getDefaultState());
            blockMap.put(Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_CONVERTING.getDefaultState());
            blockMap.put(Blocks.BASALT, ModBlocks.BASALT_CONVERTING.getDefaultState());
            blockMap.put(Blocks.END_STONE, ModBlocks.END_STONE_CONVERTING.getDefaultState());


            for (Map.Entry<Block, BlockState> entry : blockMap.entrySet())
            {
                Block originalBlock = entry.getKey();
                BlockState convertedState = entry.getValue();

                if (state.isOf(originalBlock))
                {
                    setStateForStone(world, pos, tool, convertedState);
                    break;
                }

            }

        }
    }

    private void setStateForStone(World world, BlockPos pos, ItemStack tool, BlockState state)
    {

        if (tool.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                || (tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES) && !state.isIn(ModTags.Blocks.STONE_STRATA3)) )
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }

        if (tool.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES) && state.get(BREAK_LEVEL) < 5)
        {
            world.setBlockState(pos, state.with(BREAK_LEVEL, 5));
            return;
        }

        if (tool.isIn(BTWRConventionalTags.Items.MODERN_CHISELS))
        {
            world.setBlockState(pos, state.with(BREAK_LEVEL, 3));
            return;
        }

        world.setBlockState(pos, state.with(BREAK_LEVEL, 0));
    }

    private void setStateForOre(World world, BlockPos pos, BlockState state, ItemStack tool) {

        if (!shouldConvertOre(state, tool)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }

        if (tool.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES)
                || tool.getItem() instanceof ChiselItem)
        {
            if (state.isIn(ModTags.Blocks.STONE_ORES))
            {
                world.setBlockState(pos, ModBlocks.STONE_CONVERTING.getDefaultState().with(BREAK_LEVEL, 5));
                return;
            }

            if (state.isIn(ModTags.Blocks.MANTLE_ORES))
            {
                // TODO: add mantle converting block here
                world.setBlockState(pos, ModBlocks.STONE_CONVERTING.getDefaultState().with(BREAK_LEVEL, 5));
                return;
            }

            if (state.isIn(ModTags.Blocks.DEEPSLATE_ORES))
            {
                world.setBlockState(pos, ModBlocks.DEEPSLATE_CONVERTING.getDefaultState().with(BREAK_LEVEL, 5));
            }

        }
        else if (tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES))
        {

            if (state.isIn(ModTags.Blocks.DEEPSLATE_ORES))
            {
                world.setBlockState(pos, ModBlocks.DEEPSLATE_CONVERTING.getDefaultState().with(BREAK_LEVEL, 5));
            }

        }


    }

    private boolean shouldConvertOre(BlockState state, ItemStack stack)
    {

        if ( stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES) )
        {
            return false;
        }

        return !stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES) || state.isIn(ModTags.Blocks.DEEPSLATE_ORES);
    }


    private void setStateForDirt(World world, BlockPos pos, BlockState state, ItemStack tool)
    {

        boolean isAboveDirtAndTwoAboveGrass = state.isOf(Blocks.DIRT)
                && (world.getBlockState(pos.up()).isOf(Blocks.DIRT)
                || world.getBlockState(pos.up(1)).isOf(Blocks.GRASS_BLOCK));

        boolean isDirtAbove = world.getBlockState(pos.up()).isOf(Blocks.DIRT);
        boolean isGrassAbove = world.getBlockState(pos.up()).isOf(Blocks.GRASS_BLOCK);

        boolean isFullyBreakingShovel = tool.isIn(BTWRConventionalTags.Items.MODERN_SHOVELS)
                || tool.isIn(BTWRConventionalTags.Items.ADVANCED_SHOVELS);

        boolean isModernOrAdvancedHoe = tool.isIn(BTWRConventionalTags.Items.MODERN_HOES)
                || tool.isIn(BTWRConventionalTags.Items.ADVANCED_HOES);

        if (isModernOrAdvancedHoe)
        {
            world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
            return;
        }

        if ( !isFullyBreakingShovel )
        {

            if (isAboveDirtAndTwoAboveGrass)
            {
                world.setBlockState(pos.up(1), ModBlocks.DIRT_LOOSE.getDefaultState());
                return;
            }

            if (isDirtAbove || isGrassAbove)
            {
                world.setBlockState(pos.up(), ModBlocks.DIRT_LOOSE.getDefaultState());
                return;
            }

            setAdjacentDirtBlocksOnBreak(world, pos);

        }
        // TODO: Fix hoes not turning the block to farmland
        else
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }

    }

    private void setAdjacentDirtBlocksOnBreak(World world, BlockPos pos)
    {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        // Check the four cardinal directions
        for (Direction direction : Direction.Type.HORIZONTAL)
        {
            mutablePos.set(pos).move(direction);
            BlockState neighborState = world.getBlockState(mutablePos);

            // Check if the neighbor is dirt and not already loose dirt
            if (neighborState.getBlock() == Blocks.DIRT && neighborState.getBlock() != ModBlocks.DIRT_LOOSE)
            {
                world.setBlockState(mutablePos, ModBlocks.DIRT_LOOSE.getDefaultState());
            }
        }
    }


    private boolean isFullyBreakingTool(ItemStack stack)
    {
        return stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.ADVANCED_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.ADVANCED_AXES)

                || stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_AXES);
    }

    private boolean isDirtLooseningBlock(BlockState state)
    {
        return state.isOf(Blocks.DIRT)
                || state.isOf(Blocks.COARSE_DIRT)
                || state.isOf(Blocks.DIRT_PATH)
                || state.isOf(Blocks.GRASS_BLOCK)
                || state.isOf(Blocks.MYCELIUM);
    }



}

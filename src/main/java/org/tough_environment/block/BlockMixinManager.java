package org.tough_environment.block;

import btwr.btwrsl.tag.BTWRConventionalTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.tough_environment.block.blocks.ConvertingBlock;
import org.tough_environment.tag.ModTags;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.block.Block.*;
import static org.tough_environment.block.blocks.ConvertingBlock.BREAK_LEVEL;

/** Manages mixin'd logic for vanilla blocks affected by BTWR mechanics. */
public class BlockMixinManager
{

    private static final BlockMixinManager instance = new BlockMixinManager();

    private BlockMixinManager() {}

    public static BlockMixinManager getInstance() {
        return instance;
    }

    // Exhaustion when placing blocks
    public void handleOnPlaced(LivingEntity placer) {
        if (placer instanceof PlayerEntity player) {
            player.getHungerManager().addExhaustion(0.005f);
        }
    }

    public void handleAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, ItemStack tool) {
        setConvertibleState(world, pos, state, tool);

        if (!(tool.getItem() instanceof AxeItem) && state.getHardness(world, pos) <= 0) {
            player.addExhaustion(0.1f);
        }

        if (shouldPlayDing(state, tool) && !player.isCreative()) {
            world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.5F, 1.75F + world.random.nextFloat() * 0.25F);
        }
    }

    private boolean shouldPlayDing(BlockState state, ItemStack tool) {
        if (state.getBlock() instanceof ConvertingBlock && !state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS)) {
            int breakLevel = state.get(BREAK_LEVEL);
            return (breakLevel % 2 == 0) && !isFullyBreakingTool(tool);
        }

        if (state.getBlock() instanceof ExperienceDroppingBlock) {
            return !isFullyBreakingTool(tool);
        }

        return isStrata1StoneBlock(state) && (isChisel(tool) || tool.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES));
    }

    private void setConvertibleState(World world, BlockPos pos, BlockState state, ItemStack tool) {
        if (world.isClient) return;

        if (state.isIn(ModTags.Blocks.VANILLA_DIRT_BLOCKS) || state.isOf(Blocks.GRASS_BLOCK)) {
            setStateForDirt(world, pos, tool);
        }

        if (state.isIn(ModTags.Blocks.SIMPLE_DIRT_BLOCKS) && tool.isIn(ItemTags.HOES)) {
            setToFarmland(state, world, pos);
        }

        if (state.isIn(BTWRConventionalTags.Blocks.ORES)) {
            setStateForOre(world, pos, state, tool);
        }

        convertStoneState(world, pos, state, tool);
    }

    private void convertStoneState(World world, BlockPos pos, BlockState state, ItemStack tool) {
        Map<Block, Block> blockMap = new HashMap<>();

        blockMap.put(Blocks.STONE, ModBlocks.STONE_CONVERTING);
        blockMap.put(Blocks.GRANITE, ModBlocks.GRANITE_CONVERTING);
        blockMap.put(Blocks.ANDESITE, ModBlocks.ANDESITE_CONVERTING);
        blockMap.put(Blocks.DIORITE, ModBlocks.DIORITE_CONVERTING);
        blockMap.put(Blocks.CALCITE, ModBlocks.CALCITE_CONVERTING);
        blockMap.put(Blocks.TUFF, ModBlocks.TUFF_CONVERTING);
        blockMap.put(Blocks.BLACKSTONE, ModBlocks.BLACKSTONE_CONVERTING);
        blockMap.put(Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_CONVERTING);
        blockMap.put(Blocks.BASALT, ModBlocks.BASALT_CONVERTING);
        blockMap.put(Blocks.END_STONE, ModBlocks.END_STONE_CONVERTING);


        for (Map.Entry<Block, Block> entry : blockMap.entrySet())
        {
            Block originalBlock = entry.getKey();
            Block convertedBlock = entry.getValue();

            if (state.isOf(originalBlock))
            {
                setStateForStone(world, pos, tool, convertedBlock);
                break;
            }

        }
    }

    private void setStateForStone(World world, BlockPos pos, ItemStack tool, Block block)
    {

        BlockState state = block.getDefaultState();

        if (tool.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                || (tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES) && !state.isIn(ModTags.Blocks.STONE_STRATA3)) )
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }

        if (tool.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES) && state.get(BREAK_LEVEL) < 5)
        {
            setState(world, pos, state.with(BREAK_LEVEL, 5));
            return;
        }

        if (tool.isIn(BTWRConventionalTags.Items.MODERN_CHISELS) || tool.isIn(BTWRConventionalTags.Items.ADVANCED_CHISELS))
        {
            setState(world, pos, state.with(BREAK_LEVEL, 3));
            return;
        }

        setState(world, pos, state.with(BREAK_LEVEL, 0));
    }

    private void setStateForDirt(World world, BlockPos pos, ItemStack tool) {
        if (isFullyBreakingTool(tool)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        } else {
            setAdjacentStateToLooseDirt(world, pos);
        }
    }

    private void setStateForOre(World world, BlockPos pos, BlockState state, ItemStack tool) {
        if (!shouldConvertOre(state, tool)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }

        setState(world, pos, ModBlocks.STONE_CONVERTING.getDefaultState().with(BREAK_LEVEL, 5), tool);
    }

    private boolean shouldConvertOre(BlockState state, ItemStack tool) {
        return !(tool.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES) ||
                tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES) && !state.isIn(ModTags.Blocks.DEEPSLATE_ORES));
    }

    private void setState(World world, BlockPos pos, BlockState newState, ItemStack tool) {
        BlockState oldState = world.getBlockState(pos);
        BlockState updatedState = pushEntitiesUpBeforeBlockChange(oldState, newState, world, pos);
        world.setBlockState(pos, updatedState,0,0);
    }

    private void setState(World world, BlockPos pos, BlockState newState) {
        BlockState oldState = world.getBlockState(pos);
        BlockState updatedState = pushEntitiesUpBeforeBlockChange(oldState, newState, world, pos);
        world.setBlockState(pos, updatedState,0,0);
    }

    private void setAdjacentStateToLooseDirt(World world, BlockPos pos) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        // Check the four cardinal directions
        for (Direction direction : Direction.Type.HORIZONTAL) {
            mutablePos.set(pos).move(direction);
            BlockState neighborState = world.getBlockState(mutablePos);
            boolean isLooseningNeighbour = neighborState.isIn(ModTags.Blocks.LOOSEN_ON_IMPROPER_BREAK);

            // Check if the neighbor is dirt and not already loose dirt
            if (neighborState.getBlock() == Blocks.DIRT && neighborState.getBlock() != ModBlocks.DIRT_LOOSE) {
                world.setBlockState(mutablePos, ModBlocks.DIRT_LOOSE.getDefaultState(),0,0);
            }
        }

        // Check the top and bottom directions
        for (Direction direction : Direction.Type.VERTICAL) {
            mutablePos.set(pos).move(direction);
            BlockState neighborState = world.getBlockState(mutablePos);
            boolean isLooseningNeighbour = neighborState.isIn(ModTags.Blocks.LOOSEN_ON_IMPROPER_BREAK);

            // Check if the neighbor is dirt and not already loose dirt
            if (isLooseningNeighbour && neighborState.getBlock() != ModBlocks.DIRT_LOOSE) {
                setState(world, mutablePos, ModBlocks.DIRT_LOOSE.getDefaultState());
            }
        }

    }

    private void setToFarmland(BlockState state, World world, BlockPos pos) {
        setState(world, pos, Blocks.FARMLAND.getDefaultState());
        setState(world, pos, Blocks.FARMLAND.getDefaultState(), new ItemStack(state.getBlock()));
    }

    private boolean isFullyBreakingTool(ItemStack tool) {
        return tool.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES) ||
                tool.isIn(BTWRConventionalTags.Items.ADVANCED_SHOVELS) ||
                tool.isIn(BTWRConventionalTags.Items.ADVANCED_AXES) ||
                tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES) ||
                tool.isIn(BTWRConventionalTags.Items.MODERN_SHOVELS) ||
                tool.isIn(BTWRConventionalTags.Items.MODERN_AXES);
    }

    private boolean isChisel(ItemStack tool) {
        return tool.isIn(BTWRConventionalTags.Items.MODERN_CHISELS) || tool.isIn(BTWRConventionalTags.Items.ADVANCED_CHISELS);
    }

    private boolean isStrata1StoneBlock(BlockState state) {
        return state.isOf(Blocks.STONE) || state.isOf(Blocks.GRANITE) || state.isOf(Blocks.ANDESITE) || state.isOf(Blocks.DIORITE);
    }
}

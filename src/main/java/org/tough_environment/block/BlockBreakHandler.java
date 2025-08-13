package org.tough_environment.block;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.tough_environment.block.blocks.StoneConvertingBlock;
import org.tough_environment.tag.ModTags;

import java.util.HashMap;
import java.util.Map;

import static btwr.btwr_sl.tag.BTWRConventionalTags.Blocks.*;
import static btwr.btwr_sl.tag.BTWRConventionalTags.Items.*;
import static org.tough_environment.block.blocks.ConvertingBlock.BREAK_LEVEL;

/** Manages logic for vanilla blocks affected by BTWR breaking mechanics. */
public class BlockBreakHandler {

    private static final BlockBreakHandler instance = new BlockBreakHandler();

    private BlockBreakHandler() {}

    public static BlockBreakHandler getInstance() {
        return instance;
    }

    public boolean setConvertibleState(World world, BlockPos pos, BlockState state, ItemStack tool) {
        BlockState converted = getConvertedState(state, tool);
        if (converted == null) return false;
        return world.setBlockState(pos, converted);
    }

    public boolean setStateForDirt(World world, BlockPos pos, BlockState state, ItemStack tool) {
        if (tool.isIn(SHOVELS_HARVEST_FULL_BLOCK)) {
            return world.setBlockState(pos, Blocks.AIR.getDefaultState());
        } else {
            boolean isUpsideDown = state.isIn(LOOSEN_ON_IMPROPER_BREAK_SLABS) && state.get(Properties.SLAB_TYPE) == SlabType.TOP;
            if (state.getBlock() == ModBlocks.DIRT_LOOSE) return false;
            onDirtDugWithImproperTool(world, pos);
            onDirtSlabDugWithImproperTool(world, pos, isUpsideDown);
            return true;
        }
    }

    /**
     * Notifies neighbors of dirt blocks that they should be loosened
     */
    protected void onDirtDugWithImproperTool(World world, BlockPos pos) {
        for (Direction facing : Direction.values()) {
            notifyNeighborDirtDugWithImproperTool(world, pos, facing);
        }
    }

    protected void onDirtSlabDugWithImproperTool(World world, BlockPos pos, boolean isUpsideDown) {
        for (Direction facing : Direction.values()) {
            if (!(isUpsideDown && facing == Direction.DOWN) && !(!isUpsideDown && facing == Direction.UP)) {
                notifyNeighborDirtDugWithImproperTool(world, pos, facing);
            }
        }
    }

    protected void notifyNeighborDirtDugWithImproperTool(World world, BlockPos pos, Direction facing) {
        BlockPos neighborPos = pos.offset(facing);

        BlockState state = world.getBlockState(neighborPos);

        if (state != null) {
            onNeighborDirtDugWithImproperTool(world, neighborPos, facing.getOpposite());
        }
    }

    protected void onNeighborDirtDugWithImproperTool(World world, BlockPos neighborPos, Direction facing) {
        BlockState neighborState = world.getBlockState(neighborPos);

        if (!neighborState.isIn(LOOSEN_ON_IMPROPER_BREAK) && !neighborState.isIn(LOOSEN_ON_IMPROPER_BREAK_SLABS)) {
            return;
        }

        if (neighborState.isOf(Blocks.GRASS_BLOCK) || neighborState.isOf(Blocks.MYCELIUM)) {
            if (facing == Direction.DOWN) {
                setToLooseDirt(world, neighborPos);
            }
        } else {
            setToLooseDirt(world, neighborPos);
        }
    }

    private void setToLooseDirt(World world, BlockPos neighborPos) {
        world.setBlockState(neighborPos, ModBlocks.DIRT_LOOSE.getDefaultState());
    }

    // Returns the converted state or null if no conversion
    public BlockState getConvertedState(BlockState state, ItemStack tool) {
        if (state.isIn(ModTags.Blocks.STONE_ORES)) {
            return getConvertedOreState(state, tool, ModBlocks.STONE_CONVERTING);
        }

        if (state.isIn(ModTags.Blocks.DEEPSLATE_ORES)) {
            return getConvertedOreState(state, tool, ModBlocks.DEEPSLATE_CONVERTING);
        }

        return getConvertedStoneState(state, tool);
    }

    public boolean shouldPlayCrackingSound(BlockState state, ItemStack tool) {
        boolean isFullyBreakingPickaxe = tool.isIn(PICKAXES_HARVEST_FULL_BLOCK);
        if (state.getBlock() instanceof StoneConvertingBlock && !state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS)) {
            int breakLevel = state.get(BREAK_LEVEL);

            return ((breakLevel % 2 == 0) && !isFullyBreakingPickaxe) || ((breakLevel == 3) && isChisel(tool));
        }

        if (state.getBlock() instanceof ExperienceDroppingBlock) {
            return !isFullyBreakingPickaxe;
        }

        return isStrata1StoneBlock(state) && (isChisel(tool) || tool.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES));
    }

    // --- Conversion helper methods returning BlockState or null ---

    private BlockState getConvertedOreState(BlockState state, ItemStack tool, Block convertingBlock) {
        if (!shouldConvertOre(state, tool)) {
            return Blocks.AIR.getDefaultState();
        }
        return convertingBlock.getDefaultState().with(BREAK_LEVEL, 5);
    }

    private BlockState getConvertedStoneState(BlockState state, ItemStack tool) {
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

        for (Map.Entry<Block, Block> entry : blockMap.entrySet()) {
            Block originalBlock = entry.getKey();
            Block convertedBlock = entry.getValue();

            if (state.isOf(originalBlock)) {
                return getStoneConvertedState(tool, convertedBlock);
            }
        }

        return null;
    }

    private BlockState getStoneConvertedState(ItemStack tool, Block convertedBlock) {
        BlockState state = convertedBlock.getDefaultState();

        if (tool.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                || (tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES) && !state.isIn(ModTags.Blocks.STONE_STRATA3))) {
            return Blocks.AIR.getDefaultState();
        }

        if (tool.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES) && state.get(BREAK_LEVEL) < 5) {
            return state.with(BREAK_LEVEL, 5);
        }

        if (tool.isIn(BTWRConventionalTags.Items.MODERN_CHISELS) || tool.isIn(BTWRConventionalTags.Items.ADVANCED_CHISELS)) {
            return state.with(BREAK_LEVEL, 3);
        }

        return state.with(BREAK_LEVEL, 0);
    }

    // --- Other helpers ---

    private boolean shouldConvertOre(BlockState state, ItemStack tool) {
        return !(tool.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                || (tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES) && !state.isIn(ModTags.Blocks.DEEPSLATE_ORES)));
    }


    private boolean isChisel(ItemStack tool) {
        return tool.isIn(BTWRConventionalTags.Items.MODERN_CHISELS) || tool.isIn(BTWRConventionalTags.Items.ADVANCED_CHISELS);
    }

    private boolean isStrata1StoneBlock(BlockState state) {
        return state.isOf(Blocks.STONE) || state.isOf(Blocks.GRANITE) || state.isOf(Blocks.ANDESITE) || state.isOf(Blocks.DIORITE);
    }

    private boolean isValidAxeItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || isBWTAxe(stack);
    }

    private boolean isBWTAxe(ItemStack stack) {
        // Special case added originally for BWT's BattleAxe because it's a mining tool and it should be in this tag
        return (stack.getItem() instanceof MiningToolItem && stack.isIn(BTWRConventionalTags.Items.AXES_MAKE_PLANKS));
    }
}

package org.tough_environment.block;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.blocks.StoneConvertingBlock;
import org.tough_environment.tag.ModTags;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.block.Block.pushEntitiesUpBeforeBlockChange;
import static org.tough_environment.block.blocks.ConvertingBlock.BREAK_LEVEL;

public class BlockBreakHandler {

    public static void registerBreakEvent() {
        // BEFORE event for block state changes; return false to cancel breaking if needed
        PlayerBlockBreakEvents.BEFORE.register(BlockBreakHandler::onBeforeBlockBreak);
        // AFTER event for exhaustion and sound effects
        PlayerBlockBreakEvents.AFTER.register(BlockBreakHandler::onAfterBlockBreak);
    }

    private static boolean onBeforeBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (world.isClient) return true;

        ItemStack tool = player.getMainHandStack();
        BlockState newState = setConvertibleState(world, pos, state, tool);

        if (newState != null) {
            // Block state changed, update world and cancel the break event (return false)
            setState(world, pos, newState);
            return false;
        }

        // No change, allow breaking
        return true;
    }

    // AFTER event: handles exhaustion and sound effects
    private static void onAfterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        ItemStack tool = player.getMainHandStack();

        if (!isValidAxeItem(tool) && state.getHardness(world, pos) <= 0) {
            player.addExhaustion(0.1f);
        }

        if (shouldPlayDing(state, tool) && !player.isCreative()) {
            world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.5F, 1.75F + world.random.nextFloat() * 0.25F);
        }
    }

    private static boolean shouldPlayDing(BlockState state, ItemStack tool) {
        if (state.getBlock() instanceof StoneConvertingBlock && !state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS)) {
            int breakLevel = state.get(BREAK_LEVEL);

            return ((breakLevel % 2 == 0) && !isFullyBreakingTool(tool)) || ((breakLevel == 3) && isChisel(tool));
        }

        if (state.getBlock() instanceof ExperienceDroppingBlock) {
            return !isFullyBreakingTool(tool);
        }

        return isStrata1StoneBlock(state) && (isChisel(tool) || tool.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES));
    }

    /**
     * Returns updated BlockState if changed, null otherwise
     */
    private static BlockState setConvertibleState(World world, BlockPos pos, BlockState state, ItemStack tool) {
        if (state.isIn(ModTags.Blocks.VANILLA_DIRT_BLOCKS) || state.isOf(Blocks.GRASS_BLOCK)) {
            return setStateForDirt(world, pos, tool);
        }

        if (state.isIn(ModTags.Blocks.SIMPLE_DIRT_BLOCKS) && tool.isIn(ItemTags.HOES)) {
            return Blocks.FARMLAND.getDefaultState();
        }

        if (state.isIn(ModTags.Blocks.STONE_ORES)) {
            return setStateForOre(world, pos, state, tool, ModBlocks.STONE_CONVERTING);
        }

        if (state.isIn(ModTags.Blocks.DEEPSLATE_ORES)) {
            return setStateForOre(world, pos, state, tool, ModBlocks.DEEPSLATE_CONVERTING);
        }

        return convertStoneState(world, pos, state, tool);
    }

    private static BlockState setStateForDirt(World world, BlockPos pos, ItemStack tool) {
        if (isFullyBreakingTool(tool)) {
            return Blocks.AIR.getDefaultState();
        } else {
            setAdjacentStateToLooseDirt(world, pos);
            return null;
        }
    }

    private static BlockState setStateForOre(World world, BlockPos pos, BlockState state, ItemStack tool, Block blockToSet) {
        if (!shouldConvertOre(state, tool)) {
            return Blocks.AIR.getDefaultState();
        }

        return blockToSet.getDefaultState().with(BREAK_LEVEL, 5);
    }

    private static BlockState convertStoneState(World world, BlockPos pos, BlockState state, ItemStack tool) {
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
            if (state.isOf(entry.getKey())) {
                return setStateForStone(tool, entry.getValue());
            }
        }

        return null;
    }

    private static BlockState setStateForStone(ItemStack tool, Block block) {
        BlockState newState = block.getDefaultState();

        if (tool.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                || (tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES) && !newState.isIn(ModTags.Blocks.STONE_STRATA3))) {
            return Blocks.AIR.getDefaultState();
        }

        if (tool.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES) && newState.get(BREAK_LEVEL) < 5) {
            return newState.with(BREAK_LEVEL, 5);
        }

        if (tool.isIn(BTWRConventionalTags.Items.MODERN_CHISELS) || tool.isIn(BTWRConventionalTags.Items.ADVANCED_CHISELS)) {
            return newState.with(BREAK_LEVEL, 3);
        }

        return newState.with(BREAK_LEVEL, 0);
    }

    private static boolean shouldConvertOre(BlockState state, ItemStack tool) {
        return !(tool.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES) ||
                (tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES) && !state.isIn(ModTags.Blocks.DEEPSLATE_ORES)));
    }

    /**
     * Sets block state with pushing entities and returns the updated state for chaining.
     */
    private static BlockState setState(World world, BlockPos pos, BlockState newState) {
        BlockState oldState = world.getBlockState(pos);
        BlockState updatedState = pushEntitiesUpBeforeBlockChange(oldState, newState, world, pos);
        world.setBlockState(pos, updatedState);
        return updatedState;
    }

    private static void setAdjacentStateToLooseDirt(World world, BlockPos pos) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        // Check four horizontal directions
        for (Direction direction : Direction.Type.HORIZONTAL) {
            mutablePos.set(pos).move(direction);
            BlockState neighborState = world.getBlockState(mutablePos);

            if (neighborState.getBlock() == Blocks.DIRT && neighborState.getBlock() != ModBlocks.DIRT_LOOSE) {
                world.setBlockState(mutablePos, ModBlocks.DIRT_LOOSE.getDefaultState());
            }
        }

        // Check vertical directions top and bottom
        for (Direction direction : Direction.Type.VERTICAL) {
            mutablePos.set(pos).move(direction);
            BlockState neighborState = world.getBlockState(mutablePos);

            if (neighborState.isIn(ModTags.Blocks.LOOSEN_ON_IMPROPER_BREAK) && neighborState.getBlock() != ModBlocks.DIRT_LOOSE) {
                setState(world, mutablePos, ModBlocks.DIRT_LOOSE.getDefaultState());
            }
        }
    }

    private static void setToFarmland(World world, BlockPos pos) {
        setState(world, pos, Blocks.FARMLAND.getDefaultState());
    }

    private static boolean isFullyBreakingTool(ItemStack tool) {
        return tool.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES) ||
                tool.isIn(BTWRConventionalTags.Items.ADVANCED_SHOVELS) ||
                tool.isIn(BTWRConventionalTags.Items.ADVANCED_AXES) ||
                tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES) ||
                tool.isIn(BTWRConventionalTags.Items.MODERN_SHOVELS) ||
                tool.isIn(BTWRConventionalTags.Items.MODERN_AXES);
    }

    private static boolean isChisel(ItemStack tool) {
        return tool.isIn(BTWRConventionalTags.Items.MODERN_CHISELS) || tool.isIn(BTWRConventionalTags.Items.ADVANCED_CHISELS);
    }

    private static boolean isStrata1StoneBlock(BlockState state) {
        return state.isOf(Blocks.STONE) || state.isOf(Blocks.GRANITE) || state.isOf(Blocks.ANDESITE) || state.isOf(Blocks.DIORITE);
    }

    private static boolean isValidAxeItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || isBWTAxe(stack);
    }

    private static boolean isBWTAxe(ItemStack stack) {
        // Special case added originally for BWT's BattleAxe because it's a mining tool and it should be in this tag
        return (stack.getItem() instanceof MiningToolItem && stack.isIn(BTWRConventionalTags.Items.AXES_MAKE_PLANKS));
    }
}

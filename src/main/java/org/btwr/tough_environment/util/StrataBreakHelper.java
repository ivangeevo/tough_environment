package org.btwr.tough_environment.util;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import org.btwr.shared_library.tag.BTWRConventionalTags;
import org.btwr.tough_environment.tag.ModTags;

public interface StrataBreakHelper {

    default boolean isProblemToBreak(BlockState state, ItemStack stack) {
        if (isStrataStone3(state) && !isAdvancedPickaxe(stack)) {
            return true;
        }

        return isStrataStone2(state) && !isAdvancedPickaxe(stack) && !isModernPickaxe(stack);
    }
    default boolean isUnfeasibleToBreak(BlockState state, ItemStack stack) {
        return cantBreakStrata3(state, stack) || cantBreakStrata2(state, stack);
    }
    default boolean cantBreakStrata2(BlockState state, ItemStack stack) {
        return isStrataStone2(state) & (isPrimitivePickaxe(stack) || stack.isOf(Items.GOLDEN_PICKAXE));
    }
    default boolean cantBreakStrata3(BlockState state, ItemStack stack) {
        return isStrataStone3(state) && !isAdvancedPickaxe(stack);
    }

    // Tool checks
    default boolean isPrimitivePickaxe(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES);
    }
    default boolean isModernPickaxe(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES);
    }
    default boolean isAdvancedPickaxe(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES);
    }
    default boolean isPickaxeTool(ItemStack stack) {
        return stack.isIn(ItemTags.PICKAXES);
    }
    default boolean isAxeTool(ItemStack stack) {
        return stack.isIn(ItemTags.AXES);
    }
    default boolean isShovelTool(ItemStack stack) {
        return stack.isIn(ItemTags.SHOVELS);
    }
    default boolean isHoeTool(ItemStack stack) {
        return stack.isIn(ItemTags.HOES);
    }
    default boolean isChiselTool(ItemStack stack) {
        return stack.isIn(ModTags.Items.CHISELS);
    }

    // Strata checks
    default boolean isStrataStone1(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.STONE_STRATA1);
    }
    default boolean isStrataStone2(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.STONE_STRATA2);
    }
    default boolean isStrataStone3(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.STONE_STRATA3);
    }
    default boolean isOreStrata1(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.ORE_STRATA1);
    }
    default boolean isOreStrata2(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.ORE_STRATA2);
    }
    default boolean isOreStrata3(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.ORE_STRATA3);
    }
    default boolean isOreBlock(BlockState state) {
        return state.isIn(ConventionalBlockTags.ORES);
    }

}
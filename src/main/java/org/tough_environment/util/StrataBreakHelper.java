package org.tough_environment.util;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

public interface StrataBreakHelper {

    default boolean cantBreakStrata2(BlockState state, ItemStack stack) {
        return isStrata2(state) && !isHigherThanPrimitive(stack);
    }

    default boolean cantBreakStrata3(BlockState state, ItemStack stack) {
        return isStrata3(state) && !stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES);
    }

    private boolean isHigherThanPrimitive(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES) || stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES);
    }

    private boolean isPrimitivePickaxe(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES);
    }
    private boolean isModernPickaxe(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES);
    }
    private boolean isAdvancedPickaxe(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES);
    }

    private boolean isStrata1(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.STRATA1);
    }
    private boolean isStrata2(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.STRATA2);
    }
    private boolean isStrata3(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.STRATA3);
    }

    enum StrataTier {
        STRATA3,
        STRATA2,
        STRATA1;

        StrataTier() {}

        public static StrataTier getTierFrom(BlockState state) {
            if (state.isIn(BTWRConventionalTags.Blocks.STRATA3)) return STRATA3;
            if (state.isIn(BTWRConventionalTags.Blocks.STRATA2)) return STRATA2;
            return STRATA1;
        }

        public static StrataTier getFor(int ordinal) {
            return switch (ordinal) {
                case 0 -> STRATA3;
                case 1 -> STRATA2;
                default -> STRATA1;
            };

        }
    }

    enum ToolTier {
        ADVANCED,
        MODERN,
        PRIMITIVE,
        MELEE_WEAPON,
        RANGED_WEAPON,
        HAND;

        ToolTier() {}

        public static ToolTier getFor(ItemStack stack) {
            if (stack.isEmpty()) return HAND;
            if (stack.isIn(BTWRConventionalTags.Items.ADVANCED_TOOLS)) return ADVANCED;
            if (stack.isIn(BTWRConventionalTags.Items.MODERN_TOOLS)) return MODERN;
            if (stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_TOOLS)) return PRIMITIVE;
            if (stack.isIn(ConventionalItemTags.MELEE_WEAPON_TOOLS)) return MELEE_WEAPON;
            if (stack.isIn(ConventionalItemTags.RANGED_WEAPON_TOOLS)) return RANGED_WEAPON;

            // fallback category
            return HAND;
        }

    }

    private boolean isValidToolRequiringBlock(BlockState state) {
        boolean isTough = state.isIn(BTWRConventionalTags.Blocks.WEB_BLOCKS) || (state.isIn(BTWRConventionalTags.Blocks.STUMP_BLOCKS));
        return state.isToolRequired() && isTough;
    }
}

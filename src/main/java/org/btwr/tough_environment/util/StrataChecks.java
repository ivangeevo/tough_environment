package org.btwr.tough_environment.util;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;
import org.btwr.tough_environment.tag.ModTags;

public final class StrataChecks {

    private StrataChecks() {}

    /* =========================
       Tool checks
       ========================= */

    public static boolean isPrimitivePickaxe(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES);
    }

    public static boolean isModernPickaxe(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES);
    }

    public static boolean isAdvancedPickaxe(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES);
    }

    public static boolean isPickaxe(ItemStack stack) {
        return stack.isIn(ItemTags.PICKAXES);
    }

    public static boolean isAxe(ItemStack stack) {
        return stack.isIn(ItemTags.AXES);
    }

    public static boolean isShovel(ItemStack stack) {
        return stack.isIn(ItemTags.SHOVELS);
    }

    public static boolean isHoe(ItemStack stack) {
        return stack.isIn(ItemTags.HOES);
    }

    public static boolean isChisel(ItemStack stack) {
        return stack.isIn(ModTags.Items.CHISELS);
    }

    /* =========================
       Strata checks
       ========================= */

    public static boolean isStoneStrata1(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.STONE_STRATA1);
    }

    public static boolean isStoneStrata2(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.STONE_STRATA2);
    }

    public static boolean isStoneStrata3(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.STONE_STRATA3);
    }

    public static boolean isOreStrata1(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.ORE_STRATA1);
    }

    public static boolean isOreStrata2(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.ORE_STRATA2);
    }

    public static boolean isOreStrata3(BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.ORE_STRATA3);
    }

    public static boolean isOre(BlockState state) {
        return state.isIn(ConventionalBlockTags.ORES);
    }
}

package org.btwr.tough_environment.util;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import org.btwr.shared_library.api.loot.condition.DirectionalDropConditions;

import java.util.function.Predicate;

/**
 * Registers directional drop conditions based on tool type and block strata.
 */
public class DirectionalDropManager {

    public static void register() {
        // Strata 3 without an advanced pickaxe
        addCondition(
                stack -> !StrataChecks.isAdvancedPickaxe(stack),
                state -> StrataChecks.isStoneStrata3(state) || StrataChecks.isOreStrata3(state)
        );

        // Strata 1 without advanced or modern pickaxes
        addCondition(
                stack -> !StrataChecks.isAdvancedPickaxe(stack) || !StrataChecks.isModernPickaxe(stack),
                state -> StrataChecks.isStoneStrata1(state) || StrataChecks.isOreStrata1(state)
        );
    }

    private static void addCondition(Predicate<ItemStack> stackCheck, Predicate<BlockState> stateCheck) {
        DirectionalDropConditions.register(stackCheck, stateCheck);
    }
}
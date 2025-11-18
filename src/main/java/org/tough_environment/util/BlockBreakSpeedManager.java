package org.tough_environment.util;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Unique;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.config.TESettings;
import org.tough_environment.tag.ModTags;

public class BlockBreakSpeedManager implements StrataBreakHelper {

    private static final BlockBreakSpeedManager INSTANCE = new BlockBreakSpeedManager();
    private BlockBreakSpeedManager() {}
    public static BlockBreakSpeedManager getInstance() {
        return INSTANCE;
    }

    private final TESettings configChecker = ToughEnvironmentMod.getInstance().settings;

    public float getModifiedSpeed(PlayerEntity player, BlockState state, float currentSpeed) {
        ItemStack stack = player.getMainHandStack();

        // Only apply this logic if the item is a MiningToolItem
        if (stack.getItem() instanceof MiningToolItem) {
            ToolComponent toolComponent = stack.get(DataComponentTypes.TOOL);
            assert toolComponent != null;
            float originalSpeed = toolComponent.getSpeed(state);

            // Apply custom logic
            if (isUnfeasibleToBreak(state, stack) && configChecker.isStratificationToughnessEnabled()) {
                return originalSpeed / 8000f;
            } else if (isProblemToBreak(state, stack)) {
                return originalSpeed / 80f;
            } else if (isHoeWithCustomSpeed(stack)) {
                return originalSpeed / getHoeSpeedModifier(stack);
            } else if (state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS) && stack.isSuitableFor(state)) {
                return originalSpeed * 12f;
            } else {
                return originalSpeed;
            }
        }

        return currentSpeed;
    }

    // Separate method to determine if the stack is a hoe with custom speed
    private boolean isHoeWithCustomSpeed(ItemStack stack) {
        return stack.isIn(ItemTags.HOES) && configChecker.isHardcorePlayerMiningSpeedEnabled();
    }

    // Method to determine the speed modifier for specific hoe items
    private float getHoeSpeedModifier(ItemStack stack) {
        // Add logic for different hoe items
        if (stack.isOf(Items.WOODEN_HOE)) {
            return 12f;
        } else if (stack.isOf(Items.STONE_HOE)) {
            return 10f;
        } else if (stack.isOf(Items.IRON_HOE)) {
            return 7.5f;
        } else if (stack.isOf(Items.DIAMOND_HOE)) {
            return 2f;
        } else if (stack.isOf(Items.NETHERITE_HOE)) {
            return 1.5f;
        } else {
            return 1.0f;
        }
    }

    @Unique
    private boolean isProblemToBreak(BlockState state, ItemStack stack) {
        boolean isStrata3 = state.isIn(ModTags.Blocks.STONE_STRATA3);
        boolean isStrata2 = state.isIn(ModTags.Blocks.STONE_STRATA2);
        boolean isStrata1 = state.isIn(ModTags.Blocks.STONE_STRATA1);

        boolean isModernPickaxe = stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES);
        boolean isAdvancedPickaxe = stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES);

        if (isStrata3 && !isAdvancedPickaxe) {
            return true;
        }

        return isStrata2 && !isAdvancedPickaxe && !isModernPickaxe;
    }

    //
    private boolean isUnfeasibleToBreak(BlockState state, ItemStack stack) {
        return cantBreakStrata3(state, stack) || cantBreakStrata2(state, stack);
    }




}

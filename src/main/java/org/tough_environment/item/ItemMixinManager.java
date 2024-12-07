package org.tough_environment.item;

import btwr.btwrsl.tag.BTWRConventionalTags;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.config.TESettings;
import org.tough_environment.tag.ModTags;

public class ItemMixinManager
{
    @Unique private static final TESettings configChecker = ToughEnvironmentMod.getInstance().settings;

    private static final ItemMixinManager instance = new ItemMixinManager();

    private ItemMixinManager() {}

    public static ItemMixinManager getInstance()
    {
        return instance;
    }


    public void handleGetMiningSpeed(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir)
    {
        ToolComponent toolComponent = stack.get(DataComponentTypes.TOOL);

        // Only apply this logic if the item is a MiningToolItem
        if (stack.getItem() instanceof MiningToolItem)
        {
            assert toolComponent != null;
            float originalSpeed = toolComponent.getSpeed(state);

            // Apply custom logic
            if (isUnfeasibleToBreak(state, stack) && configChecker.isStratificationToughnessEnabled()) {
                cir.setReturnValue(originalSpeed / 8000f);
            } else if (isProblemToBreak(state, stack)) {
                cir.setReturnValue(originalSpeed / 80f);
            } else if ((!isValidToolRequiringBlock(state) && isPrimitiveTool(stack)) && configChecker.isHardcorePlayerMiningSpeedEnabled()) {
                cir.setReturnValue(originalSpeed / 5f);
            } else if (stack.isIn(ItemTags.HOES) && configChecker.isHardcorePlayerMiningSpeedEnabled()) {
                cir.setReturnValue(originalSpeed / 4f);
            } else if (state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS) && stack.isSuitableFor(state)) {
                cir.setReturnValue(originalSpeed * 12f);
            } else {
                cir.setReturnValue(originalSpeed);
            }
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

    @Unique
    private boolean isUnfeasibleToBreak(BlockState state, ItemStack stack)
    {
        if (state.isIn(ModTags.Blocks.STONE_CONVERTING_STRATA3) && !stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES))
        {
            return true;
        }

        return state.isIn(ModTags.Blocks.STONE_CONVERTING_STRATA2) && !stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES);
    }

    @Unique
    private boolean isPrimitiveTool(ItemStack stack)
    {
        return stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_AXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_HOES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_CHISELS);
    }

    private boolean isValidToolRequiringBlock(BlockState state) {
        boolean isTough = state.isIn(BTWRConventionalTags.Blocks.WEB_BLOCKS) || (state.isIn(BTWRConventionalTags.Blocks.STUMP_BLOCKS));
        return state.isToolRequired() && isTough;
    }

}

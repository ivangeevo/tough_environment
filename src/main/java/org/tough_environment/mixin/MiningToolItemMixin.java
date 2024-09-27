package org.tough_environment.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin extends ToolItem
{
    public MiningToolItemMixin(ToolMaterial material, Settings settings)
    {
        super(material, settings);
    }

    @Override
    public float getMiningSpeed(ItemStack stack, BlockState state) {
        // pretty much unbreakable
        if ( isUnfeasibleToBreak(state, stack) )
        {
            return super.getMiningSpeed(stack, state) / 8000f;
        }
        // inappropriate for the block, but can still break it slowly.
        // handles stratified stone cases and other similar breaks.
        else if ( isProblemToBreak(state, stack) )
        {
            return super.getMiningSpeed(stack, state) / 80f;
        }
        // primitive chisels are 6x slower for their materials.
        else if ( isPrimitiveTool(stack)  )
        {
            if (!ToughEnvironmentMod.getInstance().settings.isHardcorePlayerMiningSpeedEnabled())
            {
                return super.getMiningSpeed(stack, state);
            }

            return super.getMiningSpeed(stack, state) / 6f;
        }

        // if the block is broken type, and the stack is suitable for it.
        if ( state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS) && stack.isSuitableFor(state) )
        {
            return super.getMiningSpeed(stack, state) * 12;
        }

        return super.getMiningSpeed(stack, state);
    }

    /**
    @Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
    private void injectedGetMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir)
    {

        // pretty much unbreakable
        if ( isUnfeasibleToBreak(state, stack) )
        {
            cir.setReturnValue(this.miningSpeed / 8000f);
        }
        // inappropriate for the block, but can still break it slowly.
        // handles stratified stone cases and other similar breaks.
        else if ( isProblemToBreak(state, stack) )
        {
            cir.setReturnValue(this.miningSpeed / 80f);
        }
        // primitive tools are 6x slower for their materials.
        else if ( isPrimitiveTool(stack) )
        {
            cir.setReturnValue(this.miningSpeed / 6f);
        }

        // if the block is broken type, and the stack is suitable for it.
        if ( state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS) && stack.isSuitableFor(state) )
        {
            cir.setReturnValue(this.miningSpeed * 12);
        }

    }
     **/

    @Unique
    private boolean isProblemToBreak(BlockState state, ItemStack stack)
    {
        boolean isStrata3 = state.isIn(ModTags.Blocks.STONE_STRATA3);
        boolean isStrata2 = state.isIn(ModTags.Blocks.STONE_STRATA2);
        boolean isStrata1 = state.isIn(ModTags.Blocks.STONE_STRATA1);


        boolean isModernPickaxe = stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES);
        boolean isAdvancedPickaxe = stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES);
        // for pickaxes
        // making strata stones to be tougher to break
        if ( isStrata3 && !isAdvancedPickaxe )
        {
            return true;
        }

        return isStrata2 && !isAdvancedPickaxe && !isModernPickaxe;
    }

    @Unique
    private boolean isUnfeasibleToBreak(BlockState state, ItemStack stack)
    {
        // used to prevent turning blocks into air with wrong tool
        // or disallow further breaking after getting ore drops from said block


        // for pickaxes
        if ( state.isIn(ModTags.Blocks.STONE_CONVERTING_STRATA3) && ( !stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES) ) )
        {
            return true;
        }
        else return state.isIn(ModTags.Blocks.STONE_CONVERTING_STRATA2) && !stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES);
    }


    @Unique
    private boolean isPrimitiveTool(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_AXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_HOES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_CHISELS);
    }

}

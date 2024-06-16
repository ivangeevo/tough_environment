package org.tough_environment.mixin;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;
import org.tough_environment.item.items.ChiselItem;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin extends ToolItem
{
    @Shadow @Final private TagKey<Block> effectiveBlocks;

    @Shadow @Final protected float miningSpeed;

    public MiningToolItemMixin(ToolMaterial material, Settings settings)
    {
        super(material, settings);
    }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
    private void injectedGetMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir)
    {

        // pretty much unbreakable
        if ( isUnviableToBreak(state, stack) )
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

    @Unique
    private boolean isStrata3Converting(BlockState state)
    {
        return state.isOf(ModBlocks.DEEPSLATE_CONVERTING)
                || state.isOf(ModBlocks.BLACKSTONE_CONVERTING)
                || state.isOf(ModBlocks.END_STONE_CONVERTING);
    }



    @Unique
    private boolean isStrata2Converting(BlockState state)
    {
        return state.isOf(ModBlocks.BASALT_CONVERTING);
    }

    @Unique
    private boolean isProblemToBreak(BlockState state, ItemStack stack)
    {
        boolean isStrata3 = state.isIn(ModTags.Blocks.STONE_STRATA3);
        boolean isStrata2 = state.isIn(ModTags.Blocks.STONE_STRATA2);
        boolean isStrata1 = state.isIn(ModTags.Blocks.STONE_STRATA1);

        // for pickaxes
        // making strata stones to be tougher to break
        if ( isStrata3 && ( !stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES) ) )
        {
            return true;
        }
        else if (isStrata2 && !stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES))
        {
            return true;
        }

        // for chisels
        // used to disallow breaking after getting ore drops from said block
        if ( stack.getItem() instanceof ChiselItem )
        {

            if ( ( (ChiselItem) stack.getItem() ).getType() == ChiselItem.ChiselType.IRON )
            {
                return isStrata3Converting(state);
            }
            else if (( (ChiselItem) stack.getItem() ).getType() == ChiselItem.ChiselType.STONE)
            {
                return isStrata2Converting(state);

            }

        }

        return false;
    }

    @Unique
    private boolean isUnviableToBreak(BlockState state, ItemStack stack)
    {
        // conditions for trying to break converted stones
        // used to disallow breaking after getting ore drops from said block


        // for pickaxes
        if ( isStrata3Converting(state) && ( !stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES) ) )
        {
            return true;
        }
        else if (isStrata2Converting(state) && !stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES))
        {
            return true;
        }

        // for chisels
        if ( stack.getItem() instanceof ChiselItem )
        {

            if ( ( (ChiselItem) stack.getItem() ).getType() == ChiselItem.ChiselType.IRON )
            {
                return isStrata3Converting(state);
            }
            else if (( (ChiselItem) stack.getItem() ).getType() == ChiselItem.ChiselType.STONE)
            {
                return isStrata2Converting(state);

            }

        }

        return false;
    }

    @Unique
    private boolean isPrimitiveTool(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_AXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_HOES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_CHISELS);
    }

    @Unique
    private boolean isModernTool(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_AXES)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_HOES)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_CHISELS);
    }

    @Unique
    private boolean isAdvancedTool(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.ADVANCED_AXES)
                || stack.isIn(BTWRConventionalTags.Items.ADVANCED_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.ADVANCED_HOES);
    }
}

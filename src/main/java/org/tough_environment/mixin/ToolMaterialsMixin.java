package org.tough_environment.mixin;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.ToughEnvironmentMod;

@Mixin(ToolMaterials.class)
public abstract class ToolMaterialsMixin
{
    @Shadow public abstract TagKey<Block> getInverseTag();

    @Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
    private void customMiningSpeedMultiplier(CallbackInfoReturnable<Float> cir)
    {

        if (this.getInverseTag() == BlockTags.INCORRECT_FOR_WOODEN_TOOL)
        {
            cir.setReturnValue(1.1f);
        }
        else if (this.getInverseTag() == BlockTags.INCORRECT_FOR_STONE_TOOL)
        {
            cir.setReturnValue(4f);
        }
        else if (this.getInverseTag() == BlockTags.INCORRECT_FOR_IRON_TOOL)
        {
            cir.setReturnValue(6f);
        }
        else if (this.getInverseTag() == BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
        {
            cir.setReturnValue(8.0f);
        }
        else if (this.getInverseTag() == BlockTags.INCORRECT_FOR_NETHERITE_TOOL)
        {
            cir.setReturnValue(9.0f);
        }

    }


    // Modify the durability values for all tool materials.
    @Inject(method = "getDurability", at = @At("HEAD"), cancellable = true)
    private void customMaterialDurability(CallbackInfoReturnable<Integer> cir)
    {
        /**
         *  Mixing in this (and maybe all?) enum/s is not preferable because
         *  configs can't be used as they are loaded too early.
         * **/
        // TODO: Figure out a way to configure(with Cloth API) the durability values of tools.

        //if (ToughEnvironmentMod.getInstance().settings.isHardcoreMaterialDurabilityEnabled()) {
            applyCustomDurability(cir);
        //}




    }

    @Unique
    private void applyCustomDurability(CallbackInfoReturnable<Integer> cir)
    {

        if (this.getInverseTag() == BlockTags.INCORRECT_FOR_WOODEN_TOOL)
        {
            cir.setReturnValue(10);
        }
        else if (this.getInverseTag() == BlockTags.INCORRECT_FOR_STONE_TOOL)
        {
            cir.setReturnValue(50);
        }
        else if (this.getInverseTag() == BlockTags.INCORRECT_FOR_IRON_TOOL)
        {
            cir.setReturnValue(500);
        }
        else if (this.getInverseTag() == BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
        {
            cir.setReturnValue(1800);
        }
        else if (this.getInverseTag() == BlockTags.INCORRECT_FOR_NETHERITE_TOOL)
        {
            cir.setReturnValue(2560);
        }

    }

}

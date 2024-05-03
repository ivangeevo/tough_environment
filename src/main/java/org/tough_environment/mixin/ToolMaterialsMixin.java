package org.tough_environment.mixin;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterials;
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
    @Shadow @Final private int miningLevel;

    @Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
    private void customMiningSpeedMultiplier(CallbackInfoReturnable<Float> cir)
    {

        if (this.miningLevel == MiningLevels.WOOD)
        {
            cir.setReturnValue(1.1f);
        }
        else if (this.miningLevel == MiningLevels.STONE)
        {
            cir.setReturnValue(4f);
        }
        else if (this.miningLevel == MiningLevels.IRON)
        {
            cir.setReturnValue(6f);
        }
        else if (this.miningLevel == MiningLevels.DIAMOND)
        {
            cir.setReturnValue(6.5f);
        }
        else if (this.miningLevel == MiningLevels.NETHERITE)
        {
            cir.setReturnValue(7.5f);
        }

    }


    // Modify the durability values for all tool materials.
    @Inject(method = "getDurability", at = @At("HEAD"), cancellable = true)
    private void customMaterialDurability(CallbackInfoReturnable<Integer> cir)
    {
        /**
         *  Mixing in this(and maybe all?) enum/s is not preferable because
         *  configs can't be used as they are loaded too early.
         *  **/
        // TODO: Figure out a way to configure(with Cloth API) the durability values of tools.

        //if (ToughEnvironmentMod.getInstance().settings.isHardcoreMaterialDurabilityEnabled()) {
            applyCustomDurability(cir);
        //}
    }

    @Unique
    private void applyCustomDurability(CallbackInfoReturnable<Integer> cir)
    {
        switch (this.miningLevel)
        {
            case MiningLevels.WOOD:
                cir.setReturnValue(10);
                break;
            case MiningLevels.STONE:
                cir.setReturnValue(50);
                break;
            case MiningLevels.IRON:
                cir.setReturnValue(500);
                break;
            case MiningLevels.DIAMOND:
                cir.setReturnValue(1800);
                break;
            case MiningLevels.NETHERITE:
                cir.setReturnValue(2560);
                break;
        }
    }

}

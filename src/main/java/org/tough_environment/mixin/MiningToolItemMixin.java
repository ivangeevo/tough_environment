package org.tough_environment.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.config.SettingsGUI;
import org.tough_environment.config.TESettings;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin
{
    @Shadow @Final private TagKey<Block> effectiveBlocks;
    @Shadow @Final protected float miningSpeed;

    @Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
    private void injectedCustomSpeed(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {


        if (SettingsGUI.getConfigValue(TESettings.HARDCORE_MATERIAL_SPEED_KEY)) {
            ToolItem miningItem = (ToolItem) stack.getItem();
            ToolMaterial toolMaterial = miningItem.getMaterial();

            if (stack.isSuitableFor(state)) {
                if (toolMaterial.equals(ToolMaterials.WOOD)) {
                    cir.setReturnValue(0.8f);
                } else if (toolMaterial.equals(ToolMaterials.STONE)) {
                    cir.setReturnValue(1.1f);
                } else if (toolMaterial.equals(ToolMaterials.IRON)) {
                    cir.setReturnValue(6.0f);
                } else if (toolMaterial.equals(ToolMaterials.GOLD)) {
                    cir.setReturnValue(8.0f);
                } else if (toolMaterial.equals(ToolMaterials.DIAMOND)) {
                    cir.setReturnValue(8.0f);
                } else if (toolMaterial.equals(ToolMaterials.NETHERITE)) {
                    cir.setReturnValue(9.0f);
                }

            }
        }

        //cir.setReturnValue(state.isIn(this.effectiveBlocks) ? this.miningSpeed : 1.0f);

    }



}

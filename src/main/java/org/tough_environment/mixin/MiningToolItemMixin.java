package org.tough_environment.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.tag.ModTags;

import javax.tools.Tool;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin
{

    @Shadow @Final protected float miningSpeed;

    @Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
    private void injectedCustomSpeed(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir)
    {
                if (ToughEnvironmentMod.getInstance().settings.isHardcoreMaterialSpeedEnabled())
                {
                    ToolItem tool = (ToolItem) stack.getItem();
                    ToolMaterial toolMaterial = tool.getMaterial();
                    float returnValue = 1.0f;

                    if (toolMaterial.equals(ToolMaterials.WOOD))
                    {
                        returnValue = 0.8f;
                    }
                    else if (toolMaterial.equals(ToolMaterials.STONE))
                    {
                        returnValue = 1.1f;
                    }
                    else if (toolMaterial.equals(ToolMaterials.IRON))
                    {
                        returnValue = 5.5f;
                    }
                    else if (toolMaterial.equals(ToolMaterials.GOLD))
                    {
                        returnValue = 7.8f;
                    }
                    else if (toolMaterial.equals(ToolMaterials.DIAMOND))
                    {
                        returnValue = 8f;

                    }
                    else if (toolMaterial.equals(ToolMaterials.NETHERITE))
                    {
                        returnValue = 9f;

                    }

                    cir.setReturnValue(returnValue);

                }

        if (state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS))
        {
            cir.setReturnValue(this.miningSpeed + 5f);
        }

    }


}

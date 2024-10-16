package org.tough_environment.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.tag.ModTags;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin
{

    // TODO: This has been added as a temporary "solution".
    //  It makes some items which usually merge (to reduce lag) to not merge.
    //  Later on will be replaced with the custom dropping logic for incorrect item breaks.
    @Inject(method = "canMerge(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private static void injectedCustomMergeLogic(ItemStack stack1, ItemStack stack2, CallbackInfoReturnable<Boolean> cir)
    {
        if (stack1.isIn(ModTags.Items.SHOULD_NOT_MERGE) || stack2.isIn(ModTags.Items.SHOULD_NOT_MERGE))
        {
            cir.setReturnValue(false);
        }
    }
}

package org.tough_environment.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.item.ItemMixinManager;

@Mixin(Item.class)
public abstract class ItemMixin
{
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void injectedUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir)
    {
        ItemMixinManager.getInstance().handleUseOnBlock(context, cir);
    }

    @Inject(method = "getMiningSpeed(Lnet/minecraft/item/ItemStack;Lnet/minecraft/block/BlockState;)F", at = @At("HEAD"), cancellable = true)
    public void modifyMiningSpeed(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir)
    {
        ItemMixinManager.getInstance().handleGetMiningSpeed(stack, state, cir);
    }

}




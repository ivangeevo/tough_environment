package org.tough_environment.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin
{
    @Inject(method = "isSuitableFor", at = @At("HEAD"))
    private void injectedIsSuitableFor(BlockState state, CallbackInfoReturnable<Float> cir)
    {

    }
}



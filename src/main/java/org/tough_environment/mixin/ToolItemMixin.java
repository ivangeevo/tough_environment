package org.tough_environment.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ToolItem.class)
public abstract class ToolItemMixin extends Item
{

    public ToolItemMixin(Settings settings) {
        super(settings);
    }


}

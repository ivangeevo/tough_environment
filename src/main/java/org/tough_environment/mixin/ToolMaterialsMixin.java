package org.tough_environment.mixin;

import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ToolMaterials.class)
public abstract class ToolMaterialsMixin
{

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ToolMaterials;<init>(Ljava/lang/String;ILnet/minecraft/registry/tag/TagKey;IFFILjava/util/function/Supplier;)V"))
    private static void modifyToolMaterialDurability(Args args)
    {
        String name = args.get(0);

        switch (name)
        {
            case "WOOD":
                args.set(3, 10); // Setting durability to 10
                break;
            case "STONE":
                args.set(3, 50); // Setting durability to 50
                break;
            case "IRON":
                args.set(3, 500); // Setting durability to 500
                break;
            case "DIAMOND":
                args.set(3, 1800); // Setting durability to 1800
                break;
            case "GOLD":
                args.set(3, 45); // Setting durability to 45
                break;
            case "NETHERITE":
                args.set(3, 2560); // Setting durability to 2560
                break;
            default:
                // Do nothing for unknown tool materials
                break;
        }
    }
}

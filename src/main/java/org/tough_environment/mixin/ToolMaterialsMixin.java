package org.tough_environment.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ToolMaterials.class)
public abstract class ToolMaterialsMixin {

    // Modify the return value of getDurability() method for specific tool materials
    @ModifyReturnValue(method = "getDurability", at = @At("RETURN"))
    private int modifyToolDurability(int original) {
        // Directly use the enum ToolMaterials for comparison
        ToolMaterials self = (ToolMaterials) (Object) this;

        return switch (self) {
            case WOOD -> 10;  // Custom durability for wood tools
            case STONE -> 50;  // Custom durability for stone tools
            case IRON -> 500;  // Custom durability for iron tools
            case DIAMOND -> 1800;  // Custom durability for diamond tools
            case GOLD -> 45;  // Custom durability for gold tools
            case NETHERITE -> 2560;  // Custom durability for netherite tools
            default -> original;  // Default behavior for other materials
        };
    }
}

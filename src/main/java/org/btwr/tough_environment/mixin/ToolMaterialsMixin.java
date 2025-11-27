package org.btwr.tough_environment.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ToolMaterials.class)
public abstract class ToolMaterialsMixin {

    // Modify the return value of the durability method for specific tool materials
    @ModifyReturnValue(method = "getDurability", at = @At("RETURN"))
    private int modifyToolDurability(int original) {
        // Directly use the enum ToolMaterials for comparison
        ToolMaterials self = (ToolMaterials) (Object) this;

        return switch (self) {
            case WOOD -> 10;  // wood tools
            case STONE -> 50;  // Custom durability for stone tools
            case IRON -> 500;  // Custom durability for iron tools
            case DIAMOND -> 1561;  // Custom durability for diamond tools
            case GOLD -> 32;  // Custom durability for gold tools
            case NETHERITE -> 2250;  // Custom durability for netherite tools
            default -> original;  // Default behavior for other materials
        };
    }

    @ModifyReturnValue(method = "getMiningSpeedMultiplier", at = @At("RETURN"))
    private float modifyPrimitiveToolSpeed(float original) {
        ToolMaterials self = (ToolMaterials) (Object) this;
        return switch (self) {
            case WOOD, STONE -> 1.01f;
            default -> original;
        };
    }

    @ModifyReturnValue(method = "getMiningSpeedMultiplier", at = @At("RETURN"))
    private float modifyNetheriteToolSpeed(float original) {
        ToolMaterials self = (ToolMaterials) (Object) this;
        return self == ToolMaterials.NETHERITE ? 12f : original;
    }

}
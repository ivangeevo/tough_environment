package org.tough_environment.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin extends ToolItem
{
    @Shadow @Final private TagKey<Block> effectiveBlocks;

    @Shadow @Final protected float miningSpeed;

    public MiningToolItemMixin(ToolMaterial material, Settings settings)
    { super(material, settings); }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
    private void injectedGetMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir)
    {
        if ( state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS) && stack.isSuitableFor(state) )
        {
            cir.setReturnValue(this.miningSpeed * 12);
        }

    }



    @Unique
    private boolean isPrimitiveTool(ItemStack stack)
    {
        return stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_AXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_HOES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_CHISELS);
    }
}

package org.tough_environment.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.tag.ModTags;


@Mixin(HoeItem.class)
public abstract class HoeItemMixin extends MiningToolItem
{

    public HoeItemMixin(ToolMaterial material, Settings settings) {
        super(material, BlockTags.HOE_MINEABLE, settings);
    }

    // Removes right-clicking for hoes.
    // Instead, farmland is set in the afterBreak method for the specific blocks that a hoe can left-click break,
    // like dirt, coarse dirt, loose dirt, etc...
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void injectedUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);

        if ( state.isOf(Blocks.GRASS_BLOCK) || state.isIn(ModTags.Blocks.VANILLA_DIRT_BLOCKS))
        {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}

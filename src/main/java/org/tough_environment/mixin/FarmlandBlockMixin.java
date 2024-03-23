package org.tough_environment.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.block.ModBlocks;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin extends Block  {
    public FarmlandBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
    private void injectedCustomBlock(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if (!this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            cir.setReturnValue(Blocks.DIRT.getDefaultState());
        }
            cir.setReturnValue(super.getPlacementState(ctx));
        cir.cancel();
    }

    @Inject(method = "setToDirt", at = @At("HEAD"), cancellable = true)
    private static void injectedCustomSetToDirtBlock(Entity entity, BlockState state, World world, BlockPos pos, CallbackInfo ci) {
        world.setBlockState(pos, FarmlandBlock.pushEntitiesUpBeforeBlockChange(state, ModBlocks.DIRT_LOOSE.getDefaultState(), world, pos));
        ci.cancel();

    }

}

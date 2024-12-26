package org.tough_environment.mixin.block;

import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.tag.ModTags;
import org.tough_environment.util.MakeAsFallingBlock;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlock$AbstractBlockStateMixin implements LandingBlock
{

    @Shadow public abstract Block getBlock();

    // remove the outline in order to make it unbreakable by hand
    @Inject(method = "getOutlineShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"),  cancellable = true)
    private void removeOutline(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (this.getBlock().getDefaultState().isIn(BlockTags.FIRE)) {
            cir.setReturnValue(VoxelShapes.empty());
        }
    }

}

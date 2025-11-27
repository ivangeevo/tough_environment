package org.btwr.tough_environment.mixin.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.btwr.tough_environment.tag.ModTags;
import org.btwr.tough_environment.util.MakeAsFallingBlock;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin implements LandingBlock {

    // BAD - Causes freezes with chunk loading when fabric's chunk gen events are used. We should find an alternative way to register custom falling blocks
    //@Inject(method = "onBlockAdded", at = @At("RETURN"))
    private void onOnBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci)
    {
        MakeAsFallingBlock.getInstance().onOnBlockAdded(state, world, pos, oldState, notify);
    }

    //@Inject(method = "scheduledTick", at = @At("HEAD"))
    private void onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        MakeAsFallingBlock.getInstance().onScheduledTick(state, world, pos, random);
    }

    //@Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"), cancellable = true)
    private void onGetStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir)
    {
        if (state.isIn(ModTags.Blocks.TURNED_TO_FALLING_BLOCKS) /**&& MakeAsFallingBlock.canFallInCurrentDimension((World) world, state)**/)
        {
            world.scheduleBlockTick(pos, state.getBlock(), 2);
            cir.setReturnValue(state);
        }
    }

}
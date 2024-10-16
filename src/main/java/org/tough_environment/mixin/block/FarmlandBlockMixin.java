package org.tough_environment.mixin.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tough_environment.block.ModBlocks;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin extends Block  {
    public FarmlandBlockMixin(Settings settings) {
        super(settings);
    }

    // set to dirt for landed upon to be DIRT_LOOSE instead of the regular DIRT Block
    @Inject(method = "setToDirt", at = @At("HEAD"), cancellable = true)
    private static void injectedCustomSetToDirtBlock(Entity entity, BlockState state, World world, BlockPos pos, CallbackInfo ci) {
        BlockState blockState = pushEntitiesUpBeforeBlockChange(state, ModBlocks.DIRT_LOOSE.getDefaultState(), world, pos);
        world.setBlockState(pos, blockState);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(entity, blockState));
        ci.cancel();

    }

}

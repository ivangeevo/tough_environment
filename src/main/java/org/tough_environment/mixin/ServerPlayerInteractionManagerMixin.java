package org.tough_environment.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.event.BlockBreakEvent;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin
{

    //@Inject(method = "tryBreakBlock", at = @At("HEAD"), cancellable = true)
    private void onBlockBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        ServerPlayerInteractionManager interactionManager = (ServerPlayerInteractionManager) (Object) this;
        ServerPlayerEntity player = ((ServerPlayerInteractionManagerAccessor) interactionManager).getPlayer();
        World world = player.getWorld();
        BlockState state = world.getBlockState(pos);

        ActionResult result = BlockBreakEvent.EVENT.invoker().onBlockBreak(world, player, pos, state);

        if (result != ActionResult.FAIL) {
            cir.setReturnValue(result == ActionResult.PASS || interactionManager.tryBreakBlock(pos));
        } else {
            cir.setReturnValue(false);
        }
    }
}

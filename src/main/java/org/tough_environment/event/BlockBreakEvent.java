package org.tough_environment.event;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Callback for when a block is broken by a player.
 * This is hooked into the block break process.
 *
 * <p>Upon return:
 * <ul><li>SUCCESS cancels further processing.
 * <li>PASS falls back to further processing.
 * <li>FAIL cancels further processing and prevents the block from being broken.</ul>
 */
public interface BlockBreakEvent {
    Event<BlockBreakEvent> EVENT = EventFactory.createArrayBacked(BlockBreakEvent.class,
            (listeners) -> (world, player, pos, state) -> {
                for (BlockBreakEvent event : listeners) {
                    ActionResult result = event.onBlockBreak(world, player, pos, state);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult onBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state);
}

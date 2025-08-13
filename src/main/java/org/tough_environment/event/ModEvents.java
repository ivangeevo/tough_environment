package org.tough_environment.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.BlockBreakHandler;

public class ModEvents {

    public static void register() {
        // Player Block Events
        PlayerBlockBreakEvents.AFTER.register(ModEvents::onAfterBlockBreak);
    }

    private static void onAfterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (world.isClient) return;

        ItemStack tool = player.getMainHandStack();

        if (BlockBreakHandler.getInstance().shouldPlayCrackingSound(state, tool) && !player.isCreative()) {
            world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS,
                    0.5F, 1.75F + world.random.nextFloat() * 0.25F
            );
        }

        BlockBreakHandler.getInstance().setStateForDirt(world, pos, state, tool);
        BlockBreakHandler.getInstance().setConvertibleState(world, pos, state, tool);
    }

}

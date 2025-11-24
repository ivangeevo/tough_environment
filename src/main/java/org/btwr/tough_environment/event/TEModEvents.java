package org.btwr.tough_environment.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.btwr.tough_environment.block.BlockBreakHandler;

public class TEModEvents {

    public static void register() {
        // Player Block Events
        PlayerBlockBreakEvents.AFTER.register(TEModEvents::afterBlockBreak);
        // Item Component Events
        //DefaultItemComponentEvents.MODIFY.register(ModEvents::modifyToolComponents);
        // Loot Table Events
        //LootTableEvents.REPLACE...
    }

    private static void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (!world.isClient()) {
            BlockBreakHandler.getInstance().setStateForDirt(world, pos, state, player);
            BlockBreakHandler.getInstance().setStateForStone(world, pos, state, player);
            BlockBreakHandler.getInstance().setStateForConvertedStone(world, pos, state, player);

            if (BlockBreakHandler.getInstance().shouldPlayCrackingSound(state, player.getMainHandStack()) && !player.isCreative()) {
                world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS,
                        0.5F, 1.75F + world.random.nextFloat() * 0.25F
                );
            }
        }
    }
}
package org.tough_environment.event;

import btwr.btwr_sl.tag.BTWRConventionalTags;
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
import org.tough_environment.block.blocks.StoneConvertingBlock;

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

        if (state.isIn(BTWRConventionalTags.Blocks.LOOSEN_ON_IMPROPER_BREAK) || state.isIn(BTWRConventionalTags.Blocks.LOOSEN_ON_IMPROPER_BREAK_SLABS)) {
            BlockBreakHandler.getInstance().setStateForDirt(world, pos, state, player);
        }

        BlockBreakHandler.getInstance().setStateForStone(world, pos, state, player);

        if (state.getBlock() instanceof StoneConvertingBlock) {
            BlockBreakHandler.getInstance().setStateForConvertedStone(world, pos, state, player);
        }
    }

}

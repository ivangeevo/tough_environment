package org.btwr.tough_environment.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import org.btwr.tough_environment.config.TEModConfig;

public class BlockBreakSpeedManager implements StrataBreakHelper {
    private static final BlockBreakSpeedManager INSTANCE = new BlockBreakSpeedManager();

    public static BlockBreakSpeedManager getInstance() {
        return INSTANCE;
    }

    private BlockBreakSpeedManager() {}

    public float getModifiedSpeed(PlayerEntity player, BlockState state, float currentSpeed) {
        ItemStack stack = player.getMainHandStack();
        float newSpeed;
        newSpeed = currentSpeed;

        if (isProblemToBreak(state, stack) && TEModConfig.Settings.stratificationToughness.get()) {
            newSpeed /= 80F;
        }

        // make it practically impossible(very long)
        if (isUnfeasibleToBreak(state, stack) && TEModConfig.Settings.strataBasedBlockBreakingRestrictions.get()) {
            newSpeed /= 8000F;
        }

        return newSpeed;
    }

}
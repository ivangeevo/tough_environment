package org.tough_environment.util;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.config.TESettings;

public class BlockBreakSpeedManager {

    private static final BlockBreakSpeedManager INSTANCE = new BlockBreakSpeedManager();
    private BlockBreakSpeedManager() {}
    public static BlockBreakSpeedManager getInstance() {
        return INSTANCE;
    }

    private final TESettings configChecker = ToughEnvironmentMod.getInstance().settings;

    public float getModifiedSpeed(PlayerEntity player, BlockState state, float currentSpeed) {
        float speed = 1.0F;
        ItemStack stack = player.getMainHandStack();
        boolean isTool = stack.getItem() instanceof ToolItem;
        boolean isWeapon = stack.isIn(ItemTags.WEAPON_ENCHANTABLE) || stack.getItem() instanceof MaceItem;

        if (isTool || isWeapon) return speed;
        // conditions for restricting breaking blocks without the correct item
        if (configChecker.isHardcorePlayerMiningSpeedEnabled()) {
            speed = currentSpeed / 6F;

            // conditions for restricting breaking blocks without the correct item
            if (configChecker.isBlockBreakingRestrictionsEnabled()) {
                if (state.isToolRequired() & !player.getMainHandStack().isSuitableFor(state)) {
                    speed = currentSpeed / 8000F;
                }
            }
        }


        return speed;
    }

    private boolean isValidToolRequiringBlock(BlockState state) {
        boolean isTough = state.isIn(BTWRConventionalTags.Blocks.WEB_BLOCKS) || (state.isIn(BTWRConventionalTags.Blocks.STUMP_BLOCKS));
        return state.isToolRequired() && isTough;
    }


}

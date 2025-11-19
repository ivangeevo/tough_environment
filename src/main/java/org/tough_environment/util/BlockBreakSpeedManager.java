package org.tough_environment.util;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.config.TESettings;
import org.tough_environment.tag.ModTags;

public class BlockBreakSpeedManager implements StrataBreakHelper {

    private static final BlockBreakSpeedManager INSTANCE = new BlockBreakSpeedManager();
    private BlockBreakSpeedManager() {}
    public static BlockBreakSpeedManager getInstance() {
        return INSTANCE;
    }

    private final TESettings configChecker = ToughEnvironmentMod.getInstance().settings;

    public float getModifiedSpeed(PlayerEntity player, BlockState state, float currentSpeed) {
        ItemStack stack = player.getMainHandStack();

        // Only apply this logic if the item is a MiningToolItem
        if (stack.getItem() instanceof MiningToolItem) {
            // Apply custom logic
            if (isUnfeasibleToBreak(state, stack) && configChecker.isStratificationToughnessEnabled()) {
                return currentSpeed / 8000f;
            } else if (isProblemToBreak(state, stack)) {
                return currentSpeed / 80f;
            }  else if (state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS) && stack.isSuitableFor(state)) {
                return currentSpeed * 12f;
            } else {
                return currentSpeed;
            }
        }

        return currentSpeed;
    }

}

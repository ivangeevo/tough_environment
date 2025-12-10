package org.btwr.tough_environment.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import org.btwr.shared_library.tag.BTWRConventionalTags;
import org.btwr.tough_environment.ToughEnvironmentMod;
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

        if ((isProblemToBreak(state, stack) || !stack.isSuitableFor(state)) && TEModConfig.stratificationToughness.get()) {
            newSpeed /= 80F;
        }

        // make it practically impossible(very long)
        if (isUnfeasibleToBreak(state, stack) && TEModConfig.strataBasedBlockBreakingRestrictions.get()) {
            newSpeed /= 8000F;
        }

        return newSpeed;
    }

    public enum SpeedState {
        HARDCORE(0.16f),
        NORMAL(1.0f);

        private final EntityAttributeModifier genericModifier;

        SpeedState(float modifier) {
            this.genericModifier = new EntityAttributeModifier(
                    Identifier.of(ToughEnvironmentMod.MOD_ID, "generic_modifier"),
                    modifier - 1.0,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
        }

        public EntityAttributeModifier getModifier() {
            return genericModifier;
        }

        public static SpeedState getState(int severity) {
            return severity == 1 ? HARDCORE : NORMAL;
        }

        public static SpeedState getStateFrom(PlayerEntity player) {
            int severity = 0;

            ItemStack stack = player.getMainHandStack();

            if (TEModConfig.hcPlayerMiningSpeed.get()) {
                severity = 1;
                if (stack.getItem() instanceof MiningToolItem) {
                    //if (!stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_TOOLS))
                        severity = 0;
                }
            }

            return getState(severity);
        }
    }

}
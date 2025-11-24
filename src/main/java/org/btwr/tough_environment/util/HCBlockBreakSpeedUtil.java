package org.btwr.tough_environment.util;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import org.btwr.tough_environment.ToughEnvironmentMod;
import org.btwr.tough_environment.config.TEModConfig;

public class HCBlockBreakSpeedUtil {

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

            if (TEModConfig.Settings.hcPlayerMiningSpeed.get()) {
                severity = 1;
                if (stack.getItem() instanceof MiningToolItem) {
                    severity = 0;
                }
            }

            return getState(severity);
        }
    }
}
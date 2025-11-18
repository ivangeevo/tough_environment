package org.tough_environment.util;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;

public class BlockBreakSpeedUtil {

    public enum GenericState {
        HARDCORE(0.16f),
        NORMAL(1.0f);

        private final EntityAttributeModifier genericModifier;

        GenericState(float modifier) {
            this.genericModifier = new EntityAttributeModifier(
                    Identifier.of(ToughEnvironmentMod.MOD_ID, "generic_modifier"),
                    modifier - 1.0,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
        }

        public EntityAttributeModifier getModifier() {
            return genericModifier;
        }

        public static GenericState getState(int severity) {
            return severity == 1 ? HARDCORE : NORMAL;
        }

        public static GenericState getStateFromPlayer(PlayerEntity player) {
            ItemStack stack = player.getMainHandStack();

            int severity = 0;

            if (ToughEnvironmentMod.getInstance().settings.isHardcorePlayerMiningSpeedEnabled()) {
                severity = 1;
            }

            return getState(severity);
        }
    }
}

package org.tough_environment.util;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.item.MiningToolItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;

public class BreakSpeedStatusEffectUtils {

    public enum GenericState {
        UNFEASIBLE(0.000125f),
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
            return switch (severity) {
                case 1 -> HARDCORE;
                case 2 -> UNFEASIBLE;
	            default -> NORMAL;
            };
        }

        public static GenericState getStateFromPlayer(PlayerEntity player) {
            ItemStack stack = player.getMainHandStack();
            boolean isMiningTool = stack.getItem() instanceof MiningToolItem;
            boolean isWeapon = stack.isIn(ItemTags.WEAPON_ENCHANTABLE) || stack.getItem() instanceof MaceItem;

            int severity = 0;
            /**
            // conditions for restricting breaking blocks without the correct item
            if (ToughEnvironmentMod.getInstance().settings.isHardcorePlayerMiningSpeedEnabled()) {
                severity = 1;
            }

            // Not tool or weapon - generic usage of item in hand or hand
            if (!isMiningTool && !isWeapon) {
                // conditions for restricting breaking blocks without the correct item
                if (ToughEnvironmentMod.getInstance().settings.isBlockBreakingRestrictionsEnabled()) {
                    severity = 2;
                }
            }
             **/
            return getState(severity);
        }
    }

}

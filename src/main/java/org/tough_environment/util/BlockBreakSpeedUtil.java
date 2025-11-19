package org.tough_environment.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.tag.ModTags;

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
            int severity = 0;

            ItemStack stack = player.getMainHandStack();

            PlayerBreakingTracker.BreakingBlock breaking = PlayerBreakingTracker.get((ServerPlayerEntity) player);
            if (breaking != null) {
                BlockState state = breaking.state();
                if (ToughEnvironmentMod.getSettings().isHardcorePlayerMiningSpeedEnabled()) {
                    severity = 1;
                    if (stack.getItem() instanceof MiningToolItem) {
                        severity = 0;
                    }
                }
            }

            return getState(severity);
        }
    }

    private static boolean isVanillaMiningToolEfficient(ItemStack stack, BlockState state) {
        return isPickaxeEfficient(stack, state) || isAxeEfficient(stack, state) || isShovelEfficient(stack, state) || isHoeEfficient(stack, state);
    }

    static boolean isPickaxeEfficient(ItemStack stack, BlockState state) {
        return stack.isIn(ItemTags.PICKAXES) && state.isIn(BlockTags.PICKAXE_MINEABLE);
    }
    static boolean isAxeEfficient(ItemStack stack, BlockState state) {
        return stack.isIn(ItemTags.AXES) && state.isIn(BlockTags.AXE_MINEABLE);
    }
    static boolean isShovelEfficient(ItemStack stack, BlockState state) {
        return stack.isIn(ItemTags.SHOVELS) && state.isIn(BlockTags.SHOVEL_MINEABLE);
    }
    static boolean isHoeEfficient(ItemStack stack, BlockState state) {
        return stack.isIn(ItemTags.HOES) && state.isIn(BlockTags.HOE_MINEABLE);
    }
}

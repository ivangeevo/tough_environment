package org.btwr.tough_environment.util;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.btwr.tough_environment.ToughEnvironmentMod;
import org.btwr.tough_environment.config.TEModConfig;


public class PlayerEffectsManager {

    private static final PlayerEffectsManager INSTANCE = new PlayerEffectsManager();

    private PlayerEffectsManager() {}

    public static PlayerEffectsManager getInstance() {
        return INSTANCE;
    }

    private BlockBreakSpeedManager.SpeedState currentSpeedState = BlockBreakSpeedManager.SpeedState.NORMAL;

    // gets called in PlayerEntity only
    public void onTick(PlayerEntity player) {
    }

    // gets called in ServerPlayerEntity only
    public void onServerTick(PlayerEntity player) {
        this.updateAttributes(player);
    }

    // Determines if debuffs should affect player
    public boolean shouldBeAffected(PlayerEntity player) {
        return (!player.isCreative() && !player.isSpectator() && !player.isDead());
    }

    private void updateAttributes(PlayerEntity player) {
        EntityAttributeInstance blockBreakSpeedAttribute = player.getAttributeInstance(EntityAttributes.PLAYER_BLOCK_BREAK_SPEED);

        // Get the player's current block break speed state
        BlockBreakSpeedManager.SpeedState newSpeedState = BlockBreakSpeedManager.SpeedState.getStateFrom(player);

        if (blockBreakSpeedAttribute != null) {
            // Update GenericState modifier
            if (newSpeedState != currentSpeedState) {
                blockBreakSpeedAttribute.removeModifier(currentSpeedState.getModifier());
                blockBreakSpeedAttribute.addPersistentModifier(newSpeedState.getModifier());
            }

            // Revert if player shouldn't be affected at this time
            if (!TEModConfig.hcPlayerMiningSpeed.get()) {
                blockBreakSpeedAttribute.removeModifier(currentSpeedState.getModifier());
            } else {
                if (!blockBreakSpeedAttribute.hasModifier(currentSpeedState.getModifier().id()))
                    blockBreakSpeedAttribute.addPersistentModifier(newSpeedState.getModifier());
            }

            /**
            // Revert if player shouldn't be affected at this time
            if (!stratificationToughness.get()) {
                blockBreakSpeedAttribute.removeModifier(currentSpeedState.getModifier());
            } else {
                if (!blockBreakSpeedAttribute.hasModifier(currentSpeedState.getModifier().id()))
                    blockBreakSpeedAttribute.addPersistentModifier(newSpeedState.getModifier());
            }
             **/
        }

        currentSpeedState = newSpeedState;
    }

}
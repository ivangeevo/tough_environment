package org.tough_environment.util;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerEffectsManager {

    private static final PlayerEffectsManager INSTANCE = new PlayerEffectsManager();

    private PlayerEffectsManager() {}

    public static PlayerEffectsManager getInstance() {
        return INSTANCE;
    }

    private BlockBreakSpeedUtil.GenericState currentGenericState = BlockBreakSpeedUtil.GenericState.NORMAL;

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
        BlockBreakSpeedUtil.GenericState newGenericState = BlockBreakSpeedUtil.GenericState.getStateFromPlayer(player);

        if (blockBreakSpeedAttribute != null) {
            // Update GenericState modifier
            if (newGenericState != currentGenericState) {
                blockBreakSpeedAttribute.removeModifier(currentGenericState.getModifier());
                blockBreakSpeedAttribute.addPersistentModifier(newGenericState.getModifier());
            }
        }

        currentGenericState = newGenericState;
    }

}

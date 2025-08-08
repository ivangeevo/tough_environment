package org.tough_environment.loot;

import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.loot.conditions.DestroyedByExplosionCondition;

public class ModLootConditionTypes {

    public static LootConditionType DESTROYED_BY_EXPLOSION;

    public static void register() {

        DESTROYED_BY_EXPLOSION = Registry.register(
                Registries.LOOT_CONDITION_TYPE,
                Identifier.of(ToughEnvironmentMod.MOD_ID, "destroyed_by_explosion"),
                new LootConditionType(DestroyedByExplosionCondition.CODEC)
        );

    }
}

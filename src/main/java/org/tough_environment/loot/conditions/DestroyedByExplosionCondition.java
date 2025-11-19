package org.tough_environment.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import org.tough_environment.loot.ModLootConditionTypes;

import java.util.Set;

public class DestroyedByExplosionCondition implements LootCondition {

    public static final DestroyedByExplosionCondition INSTANCE = new DestroyedByExplosionCondition();
    public static final MapCodec<DestroyedByExplosionCondition> CODEC = MapCodec.unit(INSTANCE);

    private DestroyedByExplosionCondition() {}

    @Override
    public LootConditionType getType() {
        return ModLootConditionTypes.DESTROYED_BY_EXPLOSION;
    }

    @Override
    public Set<LootContextParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootContextParameters.EXPLOSION_RADIUS);
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.hasParameter(LootContextParameters.EXPLOSION_RADIUS);
    }

    public static LootCondition.Builder builder() {
        return () -> INSTANCE;
    }

}
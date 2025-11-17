package org.tough_environment.mixin;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ComponentMap.Builder.class)
public interface ComponentMap$BuilderAccessor {
    @Invoker("put") <T> void put(ComponentType<T> type, @Nullable Object value);
}

package org.btwr.tough_environment.item.component;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.btwr.tough_environment.ToughEnvironmentMod;
import org.btwr.tough_environment.item.component.components.TieredToolComponent;

public class ModComponentTypes {

    public static final ComponentType<TieredToolComponent> TIERED_TOOL = ComponentType.<TieredToolComponent>builder()
            .codec(TieredToolComponent.CODEC)
            .build();

    // Register method, to be called in the mod initialization
    public static void register() {
        registerDataComponent(TIERED_TOOL, "tiered_tool");
    }

    private static void registerDataComponent(ComponentType<?> componentType, String stringName) {
        register(Registries.DATA_COMPONENT_TYPE, componentType, stringName);
    }

    private static void register(
            Registry<ComponentType<?>> registryType,
            ComponentType<?> componentType,
            String stringName)
    {
        Registry.register(registryType, ToughEnvironmentMod.MOD_ID + ":" + stringName, componentType);
    }
}
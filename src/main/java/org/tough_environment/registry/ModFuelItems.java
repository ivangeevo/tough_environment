package org.tough_environment.registry;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.tough_environment.item.ModItems;

public class ModFuelItems
{

    public static void register()
    {
        FuelRegistry.INSTANCE.add(ModItems.DUST_COAL, 800);
        FuelRegistry.INSTANCE.add(ModItems.CHISEL_WOOD, 100);
    }
}

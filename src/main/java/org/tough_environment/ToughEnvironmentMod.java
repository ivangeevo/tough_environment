package org.tough_environment;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItemGroup;
import org.tough_environment.item.ModItems;

public class ToughEnvironmentMod implements ModInitializer
{
    public  static final String MOD_ID = "tough_environment";
    public  static final Logger LOGGER = LoggerFactory.getLogger("tough_environment");

    @Override
    public void onInitialize()
    {
        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModItemGroup.registerItemGroups();
    }

}

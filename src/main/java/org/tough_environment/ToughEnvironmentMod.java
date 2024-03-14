package org.tough_environment;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItemGroup;
import org.tough_environment.item.ModItems;
import org.tough_environment.util.BlockReplacementMapInitializer;

import java.util.Map;

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


        // Initialize or load the block replacement map
        Map<String, String> blockReplacementMap = BlockReplacementMapInitializer.loadBlockReplacementMap();
        if (blockReplacementMap == null) {
            BlockReplacementMapInitializer.initializeBlockReplacementMap();
            BlockReplacementMapInitializer.loadBlockReplacementMap();
        }
    }

}

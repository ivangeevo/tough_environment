package org.tough_environment;

import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.world.event.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.config.TESettings;
import org.tough_environment.event.BlockBreakEvent;
import org.tough_environment.item.ModItemGroup;
import org.tough_environment.item.ModItems;
import org.tough_environment.registry.ModFuelItems;
import org.tough_environment.util.BlockMortarMapper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ToughEnvironmentMod implements ModInitializer
{
    public  static final String MOD_ID = "tough_environment";
    public  static final Logger LOGGER = LoggerFactory.getLogger("tough_environment");

    public TESettings settings;
    private static ToughEnvironmentMod instance;

    public static ToughEnvironmentMod getInstance() {
        return instance;
    }

    @Override
    public void onInitialize()
    {
        LOGGER.info("Initializing Tough Environment.");
        loadSettings();
        instance = this;

        // Initialize or load the block replacement map
        Map<String, String> blockReplacementMap = BlockMortarMapper.loadMap();
        if (blockReplacementMap == null)
        {
            BlockMortarMapper.initMap();
            BlockMortarMapper.loadMap();
        }

        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModItemGroup.registerItemGroups();
        ModFuelItems.register();

        /**
        BlockBreakEvent.EVENT.register((world, player, pos, state) ->
        {
            System.out.println("Block broken at position: " + pos);
            ItemStack tool = player.getMainHandStack();

            if (tool.isIn(ItemTags.HOES) && state.isOf(Blocks.GRASS_BLOCK))
            {
                if (!player.isCreative())
                {
                    world.setBlockState(pos, ModBlocks.DIRT_LOOSE.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, ModBlocks.DIRT_LOOSE.getDefaultState()));
                }
                else
                {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }

            // Return ActionResult.PASS to allow normal processing, or ActionResult.FAIL to cancel
            return ActionResult.PASS;
        });
        **/



    }

    // Do not remove this comment or the project will NOT compile!
    public void loadSettings() {
        File file = new File("./config/btwr/toughEnvironmentCommon.json");
        Gson gson = new Gson();
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                settings = gson.fromJson(fileReader, TESettings.class);
                fileReader.close();
            } catch (IOException e) {
                LOGGER.warn("Could not load Tough Environment settings: " + e.getLocalizedMessage());
            }
        } else {
            settings = new TESettings();
        }
    }

    public void saveSettings() {
        Gson gson = new Gson();
        File file = new File("./config/btwr/toughEnvironmentCommon.json");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(settings));
            fileWriter.close();
        } catch (IOException e) {
            LOGGER.warn("Could not save Tough Environment settings: " + e.getLocalizedMessage());
        }
    }


}

package org.tough_environment;

import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.config.TESettings;
import org.tough_environment.item.ModItemGroup;
import org.tough_environment.item.ModItems;
import org.tough_environment.registry.ModFuelItems;
import org.tough_environment.util.BlockMortarMapper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModItemGroup.registerItemGroups();
        ModFuelItems.register();

        // Initialize or load the block replacement map
        BlockMortarMapper.init();

        /** save for later attempts // trying to make a custom vanilla resourcepack with datagen.
        // Built in resource-pack
        Identifier vanillaOverrideId = Identifier.ofVanilla("vanilla_override");
        ModContainer modContainer = FabricLoader.getInstance().getModContainer("minecraft").orElseThrow();
        ResourceManagerHelper.registerBuiltinResourcePack(vanillaOverrideId, modContainer, ResourcePackActivationType.NORMAL);
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

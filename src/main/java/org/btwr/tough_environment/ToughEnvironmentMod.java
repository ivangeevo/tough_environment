package org.btwr.tough_environment;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import org.btwr.shared_library.registry.BlockReplacementRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.btwr.tough_environment.config.TEModConfig;
import org.btwr.tough_environment.event.TEModEvents;
import org.btwr.tough_environment.block.ModBlocks;
import org.btwr.tough_environment.item.ModItemGroup;
import org.btwr.tough_environment.item.ModItems;
import org.btwr.tough_environment.item.component.TEModComponentTypes;
import org.btwr.tough_environment.loot.TEModLootConditionTypes;
import org.btwr.tough_environment.util.BlockMortarMapper;

public class ToughEnvironmentMod implements ModInitializer {
    public static final String MOD_ID = "tough_environment";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static ToughEnvironmentMod instance;


    public static ToughEnvironmentMod getInstance() {
        return instance;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Tough Environment.");
        instance = this;

        TEModConfig.register();
        ModBlocks.register();
        ModItems.register();
        ModItemGroup.register();
        TEModLootConditionTypes.register();
        TEModComponentTypes.register();

        // Initialize or load the block replacement map for mortaring
        BlockMortarMapper.register();

        BlockReplacementRegistry.registerReplacement(Blocks.CLAY, ModBlocks.CLAY_ORE);

        TEModEvents.register();

        TillableBlockRegistry.register(ModBlocks.DIRT_LOOSE, HoeItem::canTillFarmland, context -> {
                    BlockState result = Blocks.FARMLAND.getDefaultState();
                    HoeItem.createTillAction(result).accept(context);
        });

        /** save for later attempts // trying to make a custom vanilla resourcepack with datagen.
        // Built in resource-pack
        Identifier vanillaOverrideId = Identifier.ofVanilla("vanilla_override");
        ModContainer modContainer = FabricLoader.getInstance().getModContainer("minecraft").orElseThrow();
        ResourceManagerHelper.registerBuiltinResourcePack(vanillaOverrideId, modContainer, ResourcePackActivationType.NORMAL);
          **/
    }

    // Do not remove this comment or the project will NOT compile!
}
package org.btwr.tough_environment;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.btwr.tough_environment.config.TEModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.btwr.tough_environment.event.TEModEvents;
import org.btwr.tough_environment.block.ModBlocks;
import org.btwr.tough_environment.item.ModItemGroup;
import org.btwr.tough_environment.item.ModItems;
import org.btwr.tough_environment.loot.TEModLootConditionTypes;
import org.btwr.tough_environment.util.BlockMortarMapper;

public class ToughEnvironmentMod implements ModInitializer {

    public static final String MOD_ID = "tough_environment";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Tough Environment");

        TEModConfig.register();
        ModBlocks.register();
        ModItems.register();
        ModItemGroup.register();
        TEModLootConditionTypes.register();
        //TEModComponentTypes.register();

        // Initialize or load the block replacement map for mortaring
        BlockMortarMapper.register();

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

    public record HarvestState(BlockPos pos, Direction face, float miningSpeed) {}


    public static void handleStartBlockHarvest(ServerPlayerEntity player,
                                               BlockPos pos,
                                               int face,
                                               float speed) {

        // This is where your BTW logic will eventually go.

        System.out.println(
                "Start harvest: " + pos +
                        " face=" + Direction.byId(face) +
                        " speed=" + speed +
                        " player=" + player.getName().getString()
        );
    }
}
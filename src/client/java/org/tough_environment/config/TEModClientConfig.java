package org.tough_environment.config;

import com.google.common.reflect.Reflection;
import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;
import me.shedaniel.clothconfig2.api.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.tough_environment.ToughEnvironmentMod;

import java.util.function.Supplier;

public class TEModClientConfig {

    public static void register() {
        Reflection.initialize(Settings.class);
    }

    public static class Settings {

        public static Supplier<Boolean> exampleSetting;

        static {
            // construct a new config builder
            String modId = ToughEnvironmentMod.MOD_ID;
            IConfigBuilder builder = ConfigBuilders.newTomlConfig(modId, modId + "_client", true);

            // Boolean checks
            exampleSetting = builder
                    .comment("exampleSetting")
                    .define("exampleSetting", true);

            // build the config
            builder.build();
        }
    }

    // Handles config screen creation with Cloth Config API
    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.tough_environment.config"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.tough_environment.category.general"));

        // Client Settings
        general.addEntry(entryBuilder.startTextDescription(Text.translatable("config.tough_environment.text.clientSettingsText"))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.translatable("config.tough_environment.text.emptyClientConfigText"))
                .build()
        );

        // Server Settings
        general.addEntry(entryBuilder.startTextDescription(Text.translatable("config.tough_environment.text.serverSettingsText"))
                .build()
        );
        general.addEntry(entryBuilder
                .startTextDescription(Text.translatable("config.tough_environment.text.serverSettingsNoAccessText")
                )
                .setDisplayRequirement(displayWhenRemoteOrLAN())
                .build()
        );
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.tough_environment.hcPlayerMiningSpeed"), TEModConfig.Settings.hcPlayerMiningSpeed.get())
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> TEModConfig.Settings.hcPlayerMiningSpeed.get())
                .setTooltip(Text.translatable("config.tough_environment.tooltip.hcPlayerMiningSpeed"))
                .setDisplayRequirement(displayWhenTrueSingleplayer())
                .build()
        );
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.tough_environment.stratificationToughness"), TEModConfig.Settings.stratificationToughness.get())
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> TEModConfig.Settings.stratificationToughness.get())
                .setTooltip(Text.translatable("config.tough_environment.tooltip.stratificationToughness"))
                .setDisplayRequirement(displayWhenTrueSingleplayer())
                .build()
        );
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.tough_environment.strataBasedBlockBreakingRestrictions"), TEModConfig.Settings.strataBasedBlockBreakingRestrictions.get())
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> TEModConfig.Settings.strataBasedBlockBreakingRestrictions.get())
                .setTooltip(Text.translatable("config.tough_environment.tooltip.strataBasedBlockBreakingRestrictions"))
                .setDisplayRequirement(displayWhenTrueSingleplayer())
                .build()
        );

        return builder.build();
    }

     /* -----------------------------------------------------------
       Detection helpers
       ----------------------------------------------------------- */

    private static boolean isNotTrueSingleplayer() {
        return !isTrueSingleplayer();
    }

    private static Requirement displayWhenTrueSingleplayer() {
        return TEModClientConfig::isTrueSingleplayer;
    }

    private static Requirement hideWhenNotTrueSingleplayer() {
        return () -> !isTrueSingleplayer();
    }

    private static Requirement displayWhenRemoteOrLAN() {
        return TEModClientConfig::isNotTrueSingleplayer;
    }

    private static boolean isWorldLoaded() {
        return MinecraftClient.getInstance().world != null;
    }

    private static boolean isTrueSingleplayer() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return false;
        if (client.getCurrentServerEntry() != null) return false;

        // Integrated server exists in SP and LAN host
        if (client.getServer() == null) return false;

        // Only SP has isRemote == false
        return !client.getServer().isRemote();
    }
}
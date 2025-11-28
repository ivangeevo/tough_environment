package org.btwr.tough_environment.config;

import me.shedaniel.clothconfig2.api.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class TEModClientConfig {

    // Handles config screen creation with Cloth Config API
    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.tough_environment.config"));
        //builder.setSavingRunnable();

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.tough_environment.category.general"));

        // Client Settings
        general.addEntry(entryBuilder
                .startTextDescription(Text.translatable("config.tough_environment.text.clientSettingsText"))
                .build()
        );
        general.addEntry(entryBuilder
                .startTextDescription(Text.translatable("config.tough_environment.text.emptyClientConfigText"))
                .build()
        );

        // Server Settings
        general.addEntry(entryBuilder
                .startTextDescription(Text.translatable("config.tough_environment.text.serverSettingsNoAccessText"))
                //.setDisplayRequirement(displayWhenRemoteOrLAN())
                .build()
        );

        return builder.build();
    }

     /* -----------------------------------------------------------
       Detection helpers
       ----------------------------------------------------------- */

    private static Requirement displayWhenLocalServer() {
        return TEModClientConfig::isTrueSingleplayer;
    }

    private static Requirement displayWhenRemoteOrLAN() {
        return TEModClientConfig::isNotTrueSingleplayer;
    }

    private static Requirement hideWhenNotTrueSingleplayer() {
        return () -> !isTrueSingleplayer();
    }

    private static boolean isWorldLoaded() {
        return MinecraftClient.getInstance().world != null;
    }

    private static boolean isNotTrueSingleplayer() {
        return !isTrueSingleplayer();
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
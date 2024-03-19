package org.tough_environment.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.tough_environment.ToughEnvironmentMod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsGUI
{
    static TESettings settingsCommon = ToughEnvironmentMod.getInstance().settings;
    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent).setTitle(Text.translatable("title.tough_environment.config"));
        builder.setSavingRunnable(() -> {
            ToughEnvironmentMod.getInstance().saveSettings();


        });

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.tough_environment.category.general"));

        /** General Category**/

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.translatable("config.tough_environment.hcMaterialSpeed"), settingsCommon.hcMaterialSpeed)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.hcMaterialSpeed=newValue)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.translatable("config.tough_environment.hcMaterialDurability"), settingsCommon.hcMaterialDurability)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.hcMaterialDurability=newValue)
                .build());


        return builder.build();
    }




    public static Text[] wrapLines(Text text){
        List<StringVisitable> lines = MinecraftClient.getInstance().textRenderer.getTextHandler()
                .wrapLines(text,Math.max(MinecraftClient.getInstance().getWindow().getScaledWidth()/2 - 43,170),
                        Style.EMPTY);
        lines.get(0).getString();
        Text[] textLines = new Text[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            textLines[i]=Text.literal(lines.get(i).getString());
        }
        return textLines;
    }



}

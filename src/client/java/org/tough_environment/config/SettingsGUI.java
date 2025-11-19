package org.tough_environment.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.tough_environment.ToughEnvironmentMod;

public class SettingsGUI
{
    static TESettings settingsCommon = ToughEnvironmentMod.getInstance().settings;
    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent).setTitle(Text.translatable("title.tough_environment.config"));
        builder.setSavingRunnable(() -> { ToughEnvironmentMod.getInstance().saveSettings(); });

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.tough_environment.category.general"));

        /** General Category **/
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.tough_environment.hcPlayerMiningSpeed"), settingsCommon.hcPlayerMiningSpeed)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.hcPlayerMiningSpeed = newValue)
                .setTooltip(Text.translatable("config.tough_environment.tooltip.hcPlayerMiningSpeed"))
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.tough_environment.stratificationToughness"), settingsCommon.stratificationToughness)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> settingsCommon.stratificationToughness = newValue)
                .setTooltip(Text.translatable("config.tough_environment.tooltip.stratificationToughness"))
                .build());

        return builder.build();
    }

}

package org.tough_environment.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class TEModClientSettingsGUI {

    public static Screen createConfigScreen(Screen parent) {

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent);
                //.setTitle(Text.translatable("title.tough_environment.client.config")

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        //ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.tough_environment.client.category.general"));
        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("General"));

        general.addEntry(
                entryBuilder
                        .startTextDescription(Text.literal("""
                                        §eNote:§r Server settings can only be changed by editing the config file directly and require a world reload.\
                                        
                                        There are currently no client config settings.\s"""
                                 )
                        )
                        .build()
        );

        /**
        general.addEntry(entryBuilder
                .startBooleanToggle(Text.translatable("config.mod_id.client.exampleClientSetting"), ModClientSettings.exampleClientSetting.get())
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> ModClientSettings.exampleClientSetting.get())
                .setTooltip(Text.translatable("config.mod_id.tooltip.exampleClientSetting")).build()
        );
         **/

        return builder.build();
    }
}
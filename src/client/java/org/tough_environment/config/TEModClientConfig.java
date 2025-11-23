package org.tough_environment.config;

import com.google.common.reflect.Reflection;
import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;
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
}
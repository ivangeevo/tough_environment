package org.tough_environment.config;

import com.google.common.reflect.Reflection;
import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;
import org.tough_environment.ToughEnvironmentMod;

import java.util.function.Supplier;

public class TEModConfig {

    public static void register() {
        Reflection.initialize(Settings.class);
    }

    public static class Settings {
        public static Supplier<Boolean> hcPlayerMiningSpeed;
        public static Supplier<Boolean> stratificationToughness;
        public static Supplier<Boolean> strataBasedBlockBreakingRestrictions;

        static {
            // construct a new config builder
            String modId = ToughEnvironmentMod.MOD_ID;
            IConfigBuilder builder = ConfigBuilders.newTomlConfig(modId, modId + "_common", true);

            // Boolean checks
            hcPlayerMiningSpeed = builder
                    .comment("Makes most blocks take longer to break overall")
                    .define("hcPlayerMiningSpeed", true);
            stratificationToughness = builder
                    .comment("Stratification toughness disallows breaking of tougher stones with lower-tier pickaxes")
                    .define("stratificationToughness", false);
            strataBasedBlockBreakingRestrictions = builder
                    .comment("Highly discourages breaking of stone type 'strata' blocks which you don't have the correct tool for.\nThis also includes other stone-like blocks like Obsidian, etc..")
                    .define("strataBasedBlockBreakingRestrictions", false);

            // build the config
            builder.build();
        }
    }
}
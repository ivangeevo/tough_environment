package org.btwr.tough_environment.config;

import com.google.common.reflect.Reflection;
import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;
import org.btwr.tough_environment.ToughEnvironmentMod;

import java.util.function.Supplier;

public class TEModConfig {

    public static void register() {
        Reflection.initialize(Settings.class);
    }

    public static class Settings {
        public static final Supplier<Boolean> hcPlayerMiningSpeed;
        public static Supplier<Boolean> stratificationToughness;
        public static final Supplier<Boolean> strataBasedBlockBreakingRestrictions;
        //public static final Supplier<Boolean> exampleClientCategoryValue;

        static {
            // construct a new config builder
            IConfigBuilder builder = ConfigBuilders.newTomlConfig(ToughEnvironmentMod.MOD_ID, ToughEnvironmentMod.MOD_ID, true);

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

            // values can be put into categories
            //builder.push("client").categoryComment("this is a comment for the 'client' category");
            // a value in the 'client' category
            //exampleClientCategoryValue = builder/**.onlyOnClient() ??? idk bro **/.comment("this value is in the 'client' category").define("clientValue", true);
            // end the 'client' category
            //builder.pop();

            // build the config
            builder.build();
        }
    }

}
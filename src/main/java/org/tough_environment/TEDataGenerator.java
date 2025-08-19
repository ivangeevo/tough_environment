package org.tough_environment;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.tough_environment.datagen.*;

public class TEDataGenerator implements DataGeneratorEntrypoint
{

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(TELootTableProvider::new);
        pack.addProvider(TEItemTagProvider::new);
        pack.addProvider(TEBlockTagProvider::new);
        pack.addProvider(TERecipeProvider::new);
        pack.addProvider(TELangGenerator::new);
        pack.addProvider(TEBlockMortarProvider::new);

        // Save for later attempts
        /**
        // Another pack for vanilla recipes
        FabricDataGenerator.Pack vanillaPack = fabricDataGenerator
                .createBuiltinResourcePack( Identifier.ofVanilla("vanilla_override"));

        // Add a provider for generating vanilla recipes
        vanillaPack.addProvider(VanillaRecipeOverrideProvider::new);

         **/
    }


}

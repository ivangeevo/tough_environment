package org.tough_environment;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Identifier;
import org.tough_environment.datagen.*;

import java.nio.file.Path;

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

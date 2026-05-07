package org.btwr.tough_environment;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.btwr.tough_environment.datagen.*;
import org.btwr.tough_environment.datagen.loot_table.TELootTableProvider;
import org.btwr.tough_environment.datagen.loot_table.VanillaOverrideLootTableProvider;
import org.btwr.tough_environment.datagen.recipe.TERecipeProvider;
import org.btwr.tough_environment.datagen.recipe.VanillaOverrideRecipeProvider;
import org.btwr.tough_environment.datagen.tag.TEBlockTagProvider;
import org.btwr.tough_environment.datagen.tag.TEItemTagProvider;

public class TEDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(TELangGenerator::new);
        pack.addProvider(TEItemTagProvider::new);
        pack.addProvider(TEBlockTagProvider::new);
        pack.addProvider(TEBlockMortarProvider::new);
        pack.addProvider(TEModelGenerator::new);

        pack.addProvider(TERecipeProvider::new);
        pack.addProvider(VanillaOverrideRecipeProvider::new);

        pack.addProvider(TELootTableProvider::new);
        pack.addProvider(VanillaOverrideLootTableProvider::new);
    }

}
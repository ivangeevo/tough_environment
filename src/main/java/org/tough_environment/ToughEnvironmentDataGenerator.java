package org.tough_environment;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.tough_environment.datagen.TEBlockTagProvider;
import org.tough_environment.datagen.TEItemTagProvider;
import org.tough_environment.datagen.TERecipeProvier;

public class ToughEnvironmentDataGenerator implements DataGeneratorEntrypoint
{


    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(TEItemTagProvider::new);
        pack.addProvider(TEBlockTagProvider::new);
        pack.addProvider(TERecipeProvier::new);

    }
}

package org.btwr.tough_environment.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.server.recipe.*;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;


public class VanillaRecipeOverrideProvider extends TEBaseRecipeProvider
{
    public VanillaRecipeOverrideProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "Tough Environment Vanilla Override Recipes";
    }

    @Override
    public void generate(RecipeExporter exporter) {
        //disableVanilla(exporter, "clay");
        disableVanilla(exporter, "bricks");
        disableVanilla(exporter, "nether_brick");
        disableVanilla(exporter, "nether_bricks");
    }
}
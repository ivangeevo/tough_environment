package org.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class VanillaRecipeOverrideProvider extends RecipeProvider
{

    public VanillaRecipeOverrideProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter)
    {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BEDROCK)
                .input(Items.BEDROCK)
                .criterion("has_bedrock", RecipeProvider.conditionsFromItem(Items.BEDROCK))
                .offerTo(exporter, Identifier.ofVanilla("repair_item"));


    }


    
}

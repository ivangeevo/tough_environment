package org.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;

import java.util.function.Consumer;

public class TERecipeProvider extends FabricRecipeProvider
{
    public TERecipeProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter)
    {

        // Shapeless Recipes
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_DIRT,ModItems.PILE_DIRT, ModItems.PILE_DIRT, ModItems.PILE_DIRT, ModItems.PILE_DIRT , "group_te", 1 );
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_SAND,ModItems.PILE_SAND, ModItems.PILE_SAND, ModItems.PILE_SAND, ModItems.PILE_SAND , "group_te", 1 );
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_RED_SAND,ModItems.PILE_RED_SAND, ModItems.PILE_RED_SAND, ModItems.PILE_RED_SAND, ModItems.PILE_RED_SAND , "group_te", 1 );
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_GRAVEL,ModItems.PILE_GRAVEL, ModItems.PILE_GRAVEL, ModItems.PILE_GRAVEL, ModItems.PILE_GRAVEL , "group_te", 1 );

        // Shaped Recipes
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CLAY_BALL).input('#', ModItems.PILE_CLAY).pattern("#").pattern("#").criterion("has_pile_clay", RecipeProvider.conditionsFromItem(ModItems.PILE_CLAY)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIRT_LOOSE).input('#', ModBlocks.SLAB_DIRT).pattern("#").pattern("#").criterion("has_slab_dirt", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_DIRT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Blocks.SAND).input('#', ModBlocks.SLAB_SAND).pattern("#").pattern("#").criterion("has_slab_sand", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_SAND)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Blocks.RED_SAND).input('#', ModBlocks.SLAB_RED_SAND).pattern("#").pattern("#").criterion("has_slab_red_sand", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_RED_SAND)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Blocks.GRAVEL).input('#', ModBlocks.SLAB_GRAVEL).pattern("#").pattern("#").criterion("has_slab_gravel", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_GRAVEL)).offerTo(exporter);

    }


    public static void offerTwoInputShapelessRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input,  ItemConvertible input2, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, outputCount)
                .input(input).input(input2)
                .group(group)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, convertBetween(output, input));
    }

    public static void offerThreeInputShapelessRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input,  ItemConvertible input2, ItemConvertible input3, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, outputCount)
                .input(input).input(input2).input(input3)
                .group(group)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, convertBetween(output, input));
    }

    public static void offerFourInputShapelessRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input,  ItemConvertible input2, ItemConvertible input3,ItemConvertible input4, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, outputCount)
                .input(input).input(input2).input(input3).input(input4)
                .group(group)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter,  convertBetween(output, input));
    }
}
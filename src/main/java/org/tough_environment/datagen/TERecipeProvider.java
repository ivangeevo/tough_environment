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

        offerShapelessRecipe(exporter, ModItems.CHISEL_WOOD, Items.STICK, "group_btwr", 1);
        offerShapelessRecipe(exporter, ModItems.CHISEL_STONE, ModItems.SMALL_STONE, "group_btwr", 1);

        offerTwoInputShapelessRecipe(exporter, Items.COAL, ModItems.DUST_COAL, ModItems.DUST_COAL, "group_btwr", 1);
        offerTwoInputShapelessRecipe(exporter, Items.RAW_IRON, ModItems.DUST_IRON, ModItems.DUST_IRON, "group_btwr", 1);



        offerFourInputShapelessRecipe(exporter, ModItems.CHISEL_IRON, Items.IRON_NUGGET, Items.IRON_NUGGET, Items.IRON_NUGGET, Items.IRON_NUGGET, "group_btwr",1);

        // We add the diamond chisel manually with a data pack.
        //offerTwoInputShapelessRecipe(exporter, ModItems.CHISEL_DIAMOND, ModItems.DIAMOND_INGOT,  ModItems.DIAMOND_INGOT, "group_btwr",1);



        /** Lesser drops crafts **/

        // Piles, Stones & Shards to Slabs
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_DIRT,ModItems.PILE_DIRT, ModItems.PILE_DIRT, ModItems.PILE_DIRT, ModItems.PILE_DIRT , "group_te", 1 );
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_SAND,ModItems.PILE_SAND, ModItems.PILE_SAND, ModItems.PILE_SAND, ModItems.PILE_SAND , "group_te", 1 );
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_RED_SAND,ModItems.PILE_RED_SAND, ModItems.PILE_RED_SAND, ModItems.PILE_RED_SAND, ModItems.PILE_RED_SAND , "group_te", 1 );
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_GRAVEL,ModItems.PILE_GRAVEL, ModItems.PILE_GRAVEL, ModItems.PILE_GRAVEL, ModItems.PILE_GRAVEL , "group_te", 1 );
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_COBBLESTONE_LOOSE,ModItems.SMALL_STONE, ModItems.SMALL_STONE, ModItems.SMALL_STONE, ModItems.SMALL_STONE , "group_te", 1 );
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE,ModItems.SMALL_STONE_2, ModItems.SMALL_STONE_2, ModItems.SMALL_STONE_2, ModItems.SMALL_STONE_2 , "group_te", 1 );
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_GRANITE_LOOSE,ModItems.SHARD_GRANITE, ModItems.SHARD_GRANITE, ModItems.SHARD_GRANITE, ModItems.SHARD_GRANITE , "group_te", 1 );
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_ANDESITE_LOOSE,ModItems.SHARD_ANDESITE, ModItems.SHARD_ANDESITE, ModItems.SHARD_ANDESITE, ModItems.SHARD_ANDESITE , "group_te", 1 );
        offerFourInputShapelessRecipe(exporter, ModBlocks.SLAB_BRICK_LOOSE,Items.BRICK, Items.BRICK, Items.BRICK, Items.BRICK , "group_te", 1 );

        /** Reversed Slab crafts  **/
        // Piles, Stones & Shards from Slabs
        offerShapelessRecipe(exporter, ModItems.PILE_DIRT, ModBlocks.SLAB_DIRT, "group_te", 3);
        offerShapelessRecipe(exporter, ModItems.PILE_SAND, ModBlocks.SLAB_SAND, "group_te", 3);
        offerShapelessRecipe(exporter, ModItems.PILE_RED_SAND, ModBlocks.SLAB_RED_SAND, "group_te", 3);
        offerShapelessRecipe(exporter, ModItems.PILE_GRAVEL, ModBlocks.SLAB_GRAVEL, "group_te", 3);

        offerShapelessRecipe(exporter, ModItems.SMALL_STONE, ModBlocks.SLAB_COBBLESTONE_LOOSE, "group_te", 4);
        // TODO: insert level 1 (mantle) here when added!
        offerShapelessRecipe(exporter, ModItems.SMALL_STONE_2, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, "group_te", 4);
        offerShapelessRecipe(exporter, ModItems.SHARD_ANDESITE, ModBlocks.SLAB_ANDESITE_LOOSE, "group_te", 4);
        offerShapelessRecipe(exporter, ModItems.SHARD_GRANITE, ModBlocks.SLAB_GRANITE_LOOSE, "group_te", 4);
        offerShapelessRecipe(exporter, Items.BRICK, ModBlocks.SLAB_BRICK_LOOSE, "group_te", 4);

        // Piles, Stones & Shards from Full Blocks
        offerShapelessRecipe(exporter, ModItems.PILE_DIRT, ModBlocks.DIRT_LOOSE, "group_te", 6);
        offerShapelessRecipe(exporter, ModItems.PILE_SAND, Blocks.SAND, "group_te", 6);
        offerShapelessRecipe(exporter, ModItems.PILE_RED_SAND, Blocks.RED_SAND, "group_te", 6);
        offerShapelessRecipe(exporter, ModItems.PILE_GRAVEL, Blocks.GRAVEL, "group_te", 6);

        offerShapelessRecipe(exporter, ModItems.SMALL_STONE, ModBlocks.COBBLESTONE_LOOSE, "group_te", 8);
        // TODO: insert level 1 (mantle) here when added!
        offerShapelessRecipe(exporter, ModItems.SMALL_STONE_2, ModBlocks.COBBLED_DEEPSLATE_LOOSE, "group_te", 8);
        offerShapelessRecipe(exporter, ModItems.SHARD_ANDESITE, ModBlocks.ANDESITE_LOOSE, "group_te", 8);
        offerShapelessRecipe(exporter, ModItems.SHARD_GRANITE, ModBlocks.GRANITE_LOOSE, "group_te", 8);

        offerShapelessRecipe(exporter, Items.BRICK, ModBlocks.BRICKS_LOOSE, "group_te", 4);

        offerShapelessRecipe(exporter, Items.CLAY_BALL, ModBlocks.SLAB_BRICK_LOOSE, "group_te", 4);




        // Shaped Recipes
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CLAY_BALL).input('#', ModItems.PILE_CLAY).pattern("#").pattern("#").criterion("has_pile_clay", RecipeProvider.conditionsFromItem(ModItems.PILE_CLAY)).offerTo(exporter);



        // Full block from LOOSE SLABS
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Blocks.SAND).input('#', ModBlocks.SLAB_SAND).pattern("#").pattern("#").criterion("has_slab_sand", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_SAND)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Blocks.RED_SAND).input('#', ModBlocks.SLAB_RED_SAND).pattern("#").pattern("#").criterion("has_slab_red_sand", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_RED_SAND)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Blocks.GRAVEL).input('#', ModBlocks.SLAB_GRAVEL).pattern("#").pattern("#").criterion("has_slab_gravel", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_GRAVEL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIRT_LOOSE).input('#', ModBlocks.SLAB_DIRT).pattern("#").pattern("#").criterion("has_slab_dirt", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_DIRT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLESTONE_LOOSE).input('#', ModBlocks.SLAB_COBBLESTONE_LOOSE).pattern("#").pattern("#").criterion("has_slab_cobblestone_loose", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_COBBLESTONE_LOOSE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLED_DEEPSLATE_LOOSE).input('#', ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE).pattern("#").pattern("#").criterion("has_slab_cobbled_deepslate_loose", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE)).offerTo(exporter);


        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRANITE_LOOSE).input('#', ModBlocks.SLAB_GRANITE_LOOSE).pattern("#").pattern("#").criterion("has_slab_granite", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_GRANITE_LOOSE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ANDESITE_LOOSE).input('#', ModBlocks.SLAB_ANDESITE_LOOSE).pattern("#").pattern("#").criterion("has_slab_andesite", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_ANDESITE_LOOSE)).offerTo(exporter);

        // Slabs from Full Loose Blocks
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLAB_DIRT,4).input('#', ModBlocks.DIRT_LOOSE).pattern("##").criterion("has_slab_dirt", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_DIRT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLAB_SAND,4).input('#', Blocks.SAND).pattern("##").criterion("has_slab_sand", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_SAND)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLAB_RED_SAND,4).input('#', Blocks.RED_SAND).pattern("##").criterion("has_slab_red_sand", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_RED_SAND)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLAB_GRAVEL,4).input('#', Blocks.GRAVEL).pattern("##").criterion("has_slab_gravel", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_GRAVEL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLAB_COBBLESTONE_LOOSE,4).input('#', ModBlocks.COBBLESTONE_LOOSE).pattern("##").criterion("has_slab_cobblestone_loose", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_COBBLESTONE_LOOSE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE,4).input('#', ModBlocks.COBBLED_DEEPSLATE_LOOSE).pattern("##").criterion("has_slab_cobbled_deepslate_loose", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLAB_GRANITE_LOOSE,4).input('#', ModBlocks.GRANITE_LOOSE).pattern("##").criterion("has_slab_granite_loose", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_GRANITE_LOOSE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLAB_ANDESITE_LOOSE,4).input('#', ModBlocks.ANDESITE_LOOSE).pattern("##").criterion("has_slab_andesite_loose", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_ANDESITE_LOOSE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLAB_BRICK_LOOSE,4).input('#', ModBlocks.BRICKS_LOOSE).pattern("##").criterion("has_slab_brick_loose", RecipeProvider.conditionsFromItem(ModBlocks.SLAB_BRICK_LOOSE)).offerTo(exporter);

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

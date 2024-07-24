package org.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TERecipeProvider extends FabricRecipeProvider
{

    public TERecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter)
    {
        this.addBlockRecipes(exporter);
        this.addLesserDropRecipes(exporter);
        this.addSlabRecipes(exporter);
        this.addMiscRecipes(exporter);

    }

    private void addMiscRecipes(RecipeExporter exporter)
    {
        /** Shapeless **/

        // Tools
        offerShapelessRecipe(exporter, ModItems.CHISEL_WOOD, Items.STICK, "group_te", 1);
        offerShapelessRecipe(exporter, ModItems.CHISEL_STONE, ModItems.SMALL_STONE, "group_te", 1);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CHISEL_IRON).input('#', Items.IRON_NUGGET).pattern("##").pattern("##").criterion("has_iron_nugget", RecipeProvider.conditionsFromItem(Items.IRON_NUGGET)).offerTo(exporter);
        // We add the diamond chisel manually with a data pack.

        // Misc
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.COAL).input(ModItems.DUST_COAL).input(ModItems.DUST_COAL).criterion("has_dust_coal", RecipeProvider.conditionsFromItem(ModItems.DUST_COAL)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.RAW_IRON).input(ModItems.DUST_IRON).input(ModItems.DUST_IRON).criterion("has_dust_iron", RecipeProvider.conditionsFromItem(ModItems.DUST_IRON)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.RAW_GOLD).input(ModItems.DUST_GOLD).input(ModItems.DUST_GOLD).criterion("has_dust_gold", RecipeProvider.conditionsFromItem(ModItems.DUST_GOLD)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.RAW_COPPER).input(ModItems.DUST_COPPER).input(ModItems.DUST_COPPER).criterion("has_dust_copper", RecipeProvider.conditionsFromItem(ModItems.DUST_COPPER)).offerTo(exporter);


        /** Shaped **/

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CLAY_BALL).input('#', ModItems.PILE_CLAY).pattern("#").pattern("#").criterion("has_pile_clay", RecipeProvider.conditionsFromItem(ModItems.PILE_CLAY)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SLAB_BRICKS_LOOSE).input('#', Items.BRICK).pattern("##").pattern("##").criterion("has_brick", RecipeProvider.conditionsFromItem(Items.BRICK)).offerTo(exporter);


    }
    private void addLesserDropRecipes(RecipeExporter exporter)
    {
        // Piles, Stones & Shards from Slabs
        offerLesserDropsFromSlab(exporter, ModItems.PILE_DIRT, ModBlocks.SLAB_DIRT);
        offerLesserDropsFromSlab(exporter, ModItems.PILE_SAND, ModBlocks.SLAB_SAND);
        offerLesserDropsFromSlab(exporter, ModItems.PILE_RED_SAND, ModBlocks.SLAB_RED_SAND);
        offerLesserDropsFromSlab(exporter, ModItems.PILE_GRAVEL, ModBlocks.SLAB_GRAVEL);
        offerLesserDropsFromSlab(exporter, ModItems.SMALL_STONE, ModBlocks.SLAB_COBBLESTONE_LOOSE);
        // TODO: insert level 1 (mantle) here when added!
        offerLesserDropsFromSlab(exporter, ModItems.SMALL_STONE_2, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE);
        offerLesserDropsFromSlab(exporter, ModItems.SHARD_ANDESITE, ModBlocks.SLAB_ANDESITE_LOOSE);
        offerLesserDropsFromSlab(exporter, ModItems.SHARD_GRANITE, ModBlocks.SLAB_GRANITE_LOOSE);
        offerLesserDropsFromSlab(exporter, ModItems.SHARD_DIORITE, ModBlocks.SLAB_DIORITE_LOOSE);
        offerLesserDropsFromSlab(exporter, Items.BRICK, ModBlocks.SLAB_BRICKS_LOOSE);

        // Piles, Stones & Shards from Full Blocks
        offerLesserDropsFromBlock(exporter, ModItems.PILE_DIRT, ModBlocks.DIRT_LOOSE);
        offerLesserDropsFromBlock(exporter, ModItems.PILE_SAND, Blocks.SAND);
        offerLesserDropsFromBlock(exporter, ModItems.PILE_RED_SAND, Blocks.RED_SAND);
        offerLesserDropsFromBlock(exporter, ModItems.PILE_GRAVEL, Blocks.GRAVEL);
        offerLesserDropsFromBlock(exporter, ModItems.SMALL_STONE, ModBlocks.COBBLESTONE_LOOSE);
        // TODO: insert level 1 (mantle) here when added!
        offerLesserDropsFromBlock(exporter, ModItems.SMALL_STONE_2, ModBlocks.COBBLED_DEEPSLATE_LOOSE);
        offerLesserDropsFromBlock(exporter, ModItems.SHARD_ANDESITE, ModBlocks.ANDESITE_LOOSE);
        offerLesserDropsFromBlock(exporter, ModItems.SHARD_GRANITE, ModBlocks.GRANITE_LOOSE);
        offerLesserDropsFromBlock(exporter, ModItems.SHARD_DIORITE, ModBlocks.DIORITE_LOOSE);


        // Piles, Stones & Shards from Stair Blocks
        offerLesserDropsFromStairs(exporter, ModItems.SMALL_STONE, ModBlocks.COBBLESTONE_LOOSE_STAIRS);
        // TODO: insert level 1 (mantle) here when added!
        offerLesserDropsFromStairs(exporter, ModItems.SMALL_STONE_2, ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS);
        offerLesserDropsFromStairs(exporter, ModItems.SHARD_ANDESITE, ModBlocks.ANDESITE_LOOSE_STAIRS);
        offerLesserDropsFromStairs(exporter, ModItems.SHARD_ANDESITE, ModBlocks.GRANITE_LOOSE_STAIRS);
        offerLesserDropsFromStairs(exporter, ModItems.SHARD_DIORITE, ModBlocks.DIORITE_LOOSE_STAIRS);
    }
    private void addBlockRecipes(RecipeExporter exporter)
    {

        // From slabs
        offerBlockFromSlabs(exporter, ModBlocks.DIRT_LOOSE, ModBlocks.SLAB_DIRT);
        offerBlockFromSlabs(exporter, Blocks.SAND, ModBlocks.SLAB_SAND);
        offerBlockFromSlabs(exporter, Blocks.RED_SAND, ModBlocks.SLAB_RED_SAND);
        offerBlockFromSlabs(exporter, Blocks.GRAVEL, ModBlocks.SLAB_GRAVEL);
        offerBlockFromSlabs(exporter, ModBlocks.COBBLESTONE_LOOSE, ModBlocks.SLAB_COBBLESTONE_LOOSE);
        offerBlockFromSlabs(exporter, ModBlocks.COBBLED_DEEPSLATE_LOOSE, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE);
        offerBlockFromSlabs(exporter, ModBlocks.GRANITE_LOOSE, ModBlocks.SLAB_GRANITE_LOOSE);
        offerBlockFromSlabs(exporter, ModBlocks.ANDESITE_LOOSE, ModBlocks.SLAB_ANDESITE_LOOSE);
        offerBlockFromSlabs(exporter, ModBlocks.DIORITE_LOOSE, ModBlocks.SLAB_DIORITE_LOOSE);
        offerBlockFromSlabs(exporter, ModBlocks.BRICKS_LOOSE, ModBlocks.SLAB_BRICKS_LOOSE);


        // From piles, small stones/shards
        offerBlockFromLesserDrops(exporter, Blocks.SAND, ModItems.PILE_SAND);
        offerBlockFromLesserDrops(exporter, Blocks.RED_SAND, ModItems.PILE_RED_SAND);
        offerBlockFromLesserDrops(exporter, Blocks.GRAVEL, ModItems.PILE_GRAVEL);
        offerBlockFromLesserDrops(exporter, ModBlocks.DIRT_LOOSE, ModItems.PILE_DIRT);
        offerBlockFromLesserDrops(exporter, ModBlocks.COBBLESTONE_LOOSE, ModItems.SMALL_STONE);
        offerBlockFromLesserDrops(exporter, ModBlocks.COBBLED_DEEPSLATE_LOOSE, ModItems.SMALL_STONE_2);
        offerBlockFromLesserDrops(exporter, ModBlocks.GRANITE_LOOSE, ModItems.SHARD_GRANITE);
        offerBlockFromLesserDrops(exporter, ModBlocks.ANDESITE_LOOSE, ModItems.SHARD_ANDESITE);
        offerBlockFromLesserDrops(exporter, ModBlocks.DIORITE_LOOSE, ModItems.SHARD_DIORITE);
        offerBlockFromLesserDrops(exporter, ModBlocks.BRICKS_LOOSE, Items.BRICK);

        // 2x2 and 3x3 stair recipes in one method.
        offerStairs(exporter, ModBlocks.COBBLESTONE_LOOSE_STAIRS, ModBlocks.COBBLESTONE_LOOSE);
        offerStairs(exporter, ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS, ModBlocks.COBBLED_DEEPSLATE_LOOSE);
        offerStairs(exporter, ModBlocks.GRANITE_LOOSE_STAIRS, ModBlocks.GRANITE_LOOSE);
        offerStairs(exporter, ModBlocks.ANDESITE_LOOSE_STAIRS, ModBlocks.ANDESITE_LOOSE);
        offerStairs(exporter, ModBlocks.DIORITE_LOOSE_STAIRS, ModBlocks.DIORITE_LOOSE);
        offerStairs(exporter, ModBlocks.GRANITE_STAIRS, ModBlocks.GRANITE_LOOSE);
        offerStairs(exporter, ModBlocks.ANDESITE_STAIRS, ModBlocks.ANDESITE_LOOSE);
        offerStairs(exporter, ModBlocks.DIORITE_STAIRS, ModBlocks.DIORITE_LOOSE);

    }
    private void addSlabRecipes(RecipeExporter exporter)
    {
        // From lesser drops/items (stones/piles/dust)
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_DIRT, ModItems.PILE_DIRT);
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_SAND, ModItems.PILE_SAND);
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_RED_SAND, ModItems.PILE_RED_SAND);
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_GRAVEL, ModItems.PILE_GRAVEL);
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_COBBLESTONE_LOOSE, ModItems.SMALL_STONE);
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, ModItems.SMALL_STONE_2);
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_GRANITE_LOOSE, ModItems.SHARD_GRANITE);
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_ANDESITE_LOOSE, ModItems.SHARD_ANDESITE);
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_DIORITE_LOOSE, ModItems.SHARD_DIORITE);

        // From full loose blocks
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_DIRT, ModBlocks.DIRT_LOOSE);
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_SAND, Blocks.SAND);
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_RED_SAND, Blocks.RED_SAND);
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_GRAVEL, Blocks.GRAVEL);
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_COBBLESTONE_LOOSE, ModBlocks.COBBLESTONE_LOOSE);
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, ModBlocks.COBBLED_DEEPSLATE_LOOSE);
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_GRANITE_LOOSE, ModBlocks.GRANITE_LOOSE);
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_ANDESITE_LOOSE, ModBlocks.ANDESITE_LOOSE);
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_DIORITE_LOOSE, ModBlocks.DIORITE_LOOSE);
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_BRICKS_LOOSE, ModBlocks.BRICKS_LOOSE);

    }

    // LESSER DROP METHODS
    private static void offerLesserDropsFromSlab(RecipeExporter exporter, ItemConvertible output, ItemConvertible input)
    {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter,  convertBetween(output, input));
    }
    private static void offerLesserDropsFromBlock(RecipeExporter exporter, ItemConvertible output, ItemConvertible input)
    {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 8)
                .input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter,  convertBetween(output, input));
    }
    public static void offerLesserDropsFromStairs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input)
    {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter,  convertBetween(output, input));
    }

    // SLAB METHODS
    private static void offerSlabsFromBlock(RecipeExporter exporter, ItemConvertible output, ItemConvertible input)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .input('#', input)
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, convertBetween(output, input));
    }
    private static void offerSlabFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('#', input)
                .pattern("##")
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter,  convertBetween(output, input));
    }

    // BLOCK METHODS
    private static void offerBlockFromSlabs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('#', input)
                .pattern("#")
                .pattern("#")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, convertBetween(output, input));

    }
    private static void offerBlockFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input)
    {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input).input(input).input(input).input(input).input(input).input(input).input(input).input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, convertBetween(output, input));
    }
    private static void offerStairs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .input('#', input)
                .pattern("# ")
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, (convertBetween(output, input) + "_compact"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 8)
                .input('#', input)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, convertBetween(output, input));

    }

    // use later
    private static void offerBiDirectionalConversionRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, int inputQuantity, int outputQuantity) {
        // From block to lesser drops
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, outputQuantity)
                .input(input, inputQuantity)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, convertBetween(output, input));

        // From lesser drops to block
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, input, inputQuantity)
                .input(output, outputQuantity)
                .group("group_te")
                .criterion(hasItem(output), conditionsFromItem(output))
                .offerTo(exporter, convertBetween(input, output));
    }

    
}

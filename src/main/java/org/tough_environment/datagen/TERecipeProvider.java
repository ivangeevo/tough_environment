package org.tough_environment.datagen;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class TERecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{

    public TERecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

    @Override
    public void generate(RecipeExporter exporter)
    {
        // for vanilla
        this.addDisabledRecipes(exporter);
        this.addVanillaBlockRecipes(exporter);

        // for mod
        this.addFullBlockRecipes(exporter);
        this.addStairBlockRecipes(exporter);
        this.addItemRecipes(exporter);
        this.addLesserDropRecipes(exporter);
        this.addSlabRecipes(exporter);
        this.addMiscRecipes(exporter);
        this.addCookingRecipes(exporter);

        /**
        PistonPackingRecipe.JsonBuilder.create().result(Blocks.GRAVEL)
                .category(CraftingRecipeCategory.MISC)
                .ingredient(Items.FLINT)
                .criterion("has_flint", conditionsFromItem(Items.FLINT))
                .offerTo(exporter);
         **/
    }

    private void addDisabledRecipes(RecipeExporter exporter) {
        //disableVanilla(exporter, "clay");
        disableVanilla(exporter, "bricks");
        disableVanilla(exporter, "nether_brick");
        disableVanilla(exporter, "nether_bricks");

    }

    // We add recipes for some existing vanilla blocks.
    // In this case, we need a new recipe for Furnace because the one with making it out of
    // 8 loose cobblestone is too hard to acquire with just stone chisel.
    private void addVanillaBlockRecipes(RecipeExporter exporter) {
        // change furnace recipe
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, Items.FURNACE)
                .input('#', ModBlocks.SLAB_COBBLESTONE_LOOSE)
                .pattern("##")
                .pattern("##")
                .criterion("has_slab_cobblestone_loose", conditionsFromItem(ModBlocks.SLAB_COBBLESTONE_LOOSE))
                .offerTo(exporter, ID.ofMC("furnace"));

        // change clay block recipes
        offerLesserDropsFromBlock(exporter, Items.CLAY_BALL,9, Blocks.CLAY, ID.ofMC("clay_ball_from_clay_block"));
        offerFullBlockFromLesserDrops(exporter, Blocks.CLAY, Items.CLAY_BALL, ID.ofMC("clay"));

    }

    private void addMiscRecipes(RecipeExporter exporter) {

        // Tools
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CHISEL_WOOD)
                .input(Items.STICK)
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, ID.ofTE("chisel_wood"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CHISEL_STONE)
                .input(ModItems.SMALL_STONE)
                .criterion("has_small_stone", conditionsFromItem(ModItems.SMALL_STONE))
                .offerTo(exporter, ID.ofTE("chisel_stone"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CHISEL_IRON)
                .input('#', Items.IRON_NUGGET)
                .pattern("##")
                .pattern("##")
                .criterion("has_iron_nugget", RecipeProvider.conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter, ID.ofTE("chisel_iron"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CHISEL_DIAMOND)
                .input(Items.DIAMOND)
                .criterion("has_diamond", RecipeProvider.conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter, ID.ofTE("chisel_diamond"));

        // Ore chunks from dust
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.COAL)
                .input(ModItems.DUST_COAL)
                .input(ModItems.DUST_COAL)
                .criterion("has_dust_coal", RecipeProvider.conditionsFromItem(ModItems.DUST_COAL))
                .offerTo(exporter, ID.ofTE("coal_from_dust_coal"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.RAW_IRON)
                .input(ModItems.DUST_IRON)
                .input(ModItems.DUST_IRON)
                .criterion("has_dust_iron", RecipeProvider.conditionsFromItem(ModItems.DUST_IRON))
                .offerTo(exporter, ID.ofTE("raw_iron_from_dust_iron"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.RAW_GOLD)
                .input(ModItems.DUST_GOLD)
                .input(ModItems.DUST_GOLD)
                .criterion("has_dust_gold", RecipeProvider.conditionsFromItem(ModItems.DUST_GOLD))
                .offerTo(exporter, ID.ofTE("raw_gold_from_dust_gold"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.RAW_COPPER)
                .input(ModItems.DUST_COPPER)
                .input(ModItems.DUST_COPPER)
                .criterion("has_dust_copper", RecipeProvider.conditionsFromItem(ModItems.DUST_COPPER))
                .offerTo(exporter, ID.ofTE("raw_copper_from_dust_copper"));

        // Misc
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CLAY_BALL)
                .input('#', ModItems.PILE_CLAY)
                .pattern("#")
                .pattern("#")
                .criterion("has_pile_clay", RecipeProvider.conditionsFromItem(ModItems.PILE_CLAY))
                .offerTo(exporter, ID.ofTE("clay_ball_from_pile_clay"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.NETHER_SLUDGE,4)
                .input('N', Items.NETHERRACK)
                .input('C', Items.CLAY_BALL)
                .pattern("NN")
                .pattern("CN")
                .criterion("has_netherrack", conditionsFromItem(Items.NETHERRACK))
                .offerTo(exporter, ID.ofTE("nether_sludge"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.NETHER_BRICK_UNFIRED)
                .input(ModItems.NETHER_SLUDGE)
                .criterion("has_nether_sludge", conditionsFromItem(ModItems.NETHER_SLUDGE))
                .offerTo(exporter, ID.ofTE("nether_brick_unfired"));

    }

    private void addLesserDropRecipes(RecipeExporter exporter) {
        // Piles, Stones & Shards from Full Blocks
        offerLesserDropsFromBlock(exporter, ModItems.PILE_DIRT,8, ModBlocks.DIRT_LOOSE, ID.ofTE("pile_dirt_from_block_dirt_loose"));
        offerLesserDropsFromBlock(exporter, ModItems.PILE_SAND,8, Blocks.SAND, ID.ofTE("pile_sand_from_block_sand"));
        offerLesserDropsFromBlock(exporter, ModItems.PILE_RED_SAND,8, Blocks.RED_SAND, ID.ofTE("pile_red_sand_from_block_red_sand"));
        offerLesserDropsFromBlock(exporter, ModItems.PILE_GRAVEL,8, Blocks.GRAVEL, ID.ofTE("pile_gravel_from_block_gravel"));
        offerLesserDropsFromBlock(exporter, ModItems.SMALL_STONE,8, ModBlocks.COBBLESTONE_LOOSE, ID.ofTE("small_stone_from_block_cobblestone_loose"));
        // TODO: insert level 1 (mantle) here when added!
        offerLesserDropsFromBlock(exporter, ModItems.SMALL_STONE_2,8, ModBlocks.COBBLED_DEEPSLATE_LOOSE, ID.ofTE("small_stone_2_from_block_cobbled_deepslate_loose"));
        offerLesserDropsFromBlock(exporter, ModItems.SHARD_ANDESITE,8, ModBlocks.ANDESITE_LOOSE, ID.ofTE("shard_andesite_from_block_andesite_loose"));
        offerLesserDropsFromBlock(exporter, ModItems.SHARD_GRANITE,8, ModBlocks.GRANITE_LOOSE, ID.ofTE("shard_granite_from_block_granite_loose"));
        offerLesserDropsFromBlock(exporter, ModItems.SHARD_DIORITE,8, ModBlocks.DIORITE_LOOSE, ID.ofTE("shard_diorite_from_block_diorite_loose"));
        offerLesserDropsFromBlock(exporter, Items.BRICK, 8, ModBlocks.BRICKS_LOOSE, ID.ofTE("brick_from_block_bricks_loose"));
        offerLesserDropsFromBlock(exporter, Items.NETHER_BRICK, 8, ModBlocks.NETHER_BRICKS_LOOSE, ID.ofTE("nether_brick_from_block_nether_bricks_loose"));

        offerLesserDropsFromBlock(exporter, ModItems.STONE_BRICK, 4, ModBlocks.STONE_BRICKS_LOOSE, ID.ofTE("stone_brick_from_block_stone_bricks_loose"));
        offerLesserDropsFromBlock(exporter, ModItems.STONE_BRICK_2, 4, ModBlocks.DEEPSLATE_BRICKS_LOOSE, ID.ofTE("stone_brick_2_from_block_deepslate_bricks_loose"));


        // Piles, Stones & Shards from Stair Blocks
        offerLesserDropsFromStairs(exporter, ModItems.SMALL_STONE, 6, ModBlocks.COBBLESTONE_LOOSE_STAIRS, ID.ofTE("small_stone_from_stairs_cobblestone_loose"));
        // TODO: insert level 1 (mantle) here when added!
        offerLesserDropsFromStairs(exporter, ModItems.SMALL_STONE_2, 6, ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS, ID.ofTE("small_stone_2_from_stairs_cobbled_deepslate_loose"));
        offerLesserDropsFromStairs(exporter, ModItems.SHARD_ANDESITE, 6, ModBlocks.ANDESITE_LOOSE_STAIRS, ID.ofTE("shard_andesite_from_stairs_andesite_loose"));
        offerLesserDropsFromStairs(exporter, ModItems.SHARD_ANDESITE, 6, ModBlocks.GRANITE_LOOSE_STAIRS, ID.ofTE("shard_andesite_from_stairs_granite_loose"));
        offerLesserDropsFromStairs(exporter, ModItems.SHARD_DIORITE, 6, ModBlocks.DIORITE_LOOSE_STAIRS, ID.ofTE("shard_diorite_from_stairs_diorite_loose"));
        offerLesserDropsFromStairs(exporter, Items.BRICK, 6, ModBlocks.BRICKS_LOOSE_STAIRS, ID.ofTE("brick_from_stairs_bricks_loose"));
        offerLesserDropsFromStairs(exporter, Items.NETHER_BRICK, 6, ModBlocks.NETHER_BRICKS_LOOSE_STAIRS, ID.ofTE("nether_brick_from_stairs_nether_bricks_loose"));

        offerLesserDropsFromStairs(exporter, ModItems.STONE_BRICK, 3, ModBlocks.STONE_BRICKS_LOOSE_STAIRS, ID.ofTE("stone_brick_from_stairs_stone_bricks_loose"));
        offerLesserDropsFromStairs(exporter, ModItems.STONE_BRICK_2, 3, ModBlocks.DEEPSLATE_BRICKS_LOOSE_STAIRS, ID.ofTE("stone_brick_2_from_stairs_deepslate_bricks_loose"));


        // Piles, Stones & Shards from Slabs
        offerLesserDropsFromSlab(exporter, ModItems.PILE_DIRT, 4, ModBlocks.SLAB_DIRT, ID.ofTE("pile_dirt_from_slab_dirt"));
        offerLesserDropsFromSlab(exporter, ModItems.PILE_SAND, 4, ModBlocks.SLAB_SAND, ID.ofTE("pile_sand_from_slab_sand"));
        offerLesserDropsFromSlab(exporter, ModItems.PILE_RED_SAND, 4, ModBlocks.SLAB_RED_SAND, ID.ofTE("pile_red_sand_from_slab_red_sand"));
        offerLesserDropsFromSlab(exporter, ModItems.PILE_GRAVEL, 4, ModBlocks.SLAB_GRAVEL, ID.ofTE("pile_gravel_from_slab_gravel"));
        offerLesserDropsFromSlab(exporter, ModItems.SMALL_STONE, 4, ModBlocks.SLAB_COBBLESTONE_LOOSE, ID.ofTE("small_stone_from_slab_cobblestone_loose"));
        // TODO: insert level 1 (mantle) here when added!
        offerLesserDropsFromSlab(exporter, ModItems.SMALL_STONE_2, 4, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, ID.ofTE("small_stone_2_from_slab_cobbled_deepslate_loose"));
        offerLesserDropsFromSlab(exporter, ModItems.SHARD_ANDESITE, 4, ModBlocks.SLAB_ANDESITE_LOOSE, ID.ofTE("shard_andesite_from_slab_andesite_loose"));
        offerLesserDropsFromSlab(exporter, ModItems.SHARD_GRANITE,4, ModBlocks.SLAB_GRANITE_LOOSE, ID.ofTE("shard_granite_from_slab_granite_loose"));
        offerLesserDropsFromSlab(exporter, ModItems.SHARD_DIORITE, 4, ModBlocks.SLAB_DIORITE_LOOSE, ID.ofTE("shard_diorite_from_slab_diorite_loose"));
        offerLesserDropsFromSlab(exporter, Items.BRICK, 4, ModBlocks.SLAB_BRICKS_LOOSE, ID.ofTE("brick_from_slab_bricks_loose"));
        offerLesserDropsFromSlab(exporter, Items.NETHER_BRICK, 4, ModBlocks.SLAB_NETHER_BRICKS_LOOSE, ID.ofTE("nether_brick_from_slab_nether_bricks_loose"));

        offerLesserDropsFromSlab(exporter, ModItems.STONE_BRICK, 2, ModBlocks.SLAB_STONE_BRICKS_LOOSE, ID.ofTE("brick_from_slab_stone_bricks_loose"));
        offerLesserDropsFromSlab(exporter, ModItems.STONE_BRICK_2, 2, ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE, ID.ofTE("brick_from_slab_deepslate_bricks_loose"));

    }


    private void addFullBlockRecipes(RecipeExporter exporter) {
        // Exclusive recipes

        // we give 2 packed earth in this recipe that uses 4 loose dirt
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIRT_PACKED, 2)
                .input('D', ModBlocks.DIRT_LOOSE)
                .pattern("DD")
                .pattern("DD")
                .criterion("has_dirt_loose", conditionsFromItem(ModBlocks.DIRT_LOOSE))
                .offerTo(exporter, ID.ofTE("dirt_packed_from_loose_dirt"));

        // and we convert each packed earth back to 2 loose dirt blocks each
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIRT_LOOSE, 2)
                .input(ModBlocks.DIRT_PACKED)
                .criterion("has_dirt_packed", conditionsFromItem(ModBlocks.DIRT_PACKED))
                .offerTo(exporter, ID.ofTE("dirt_loose_from_dirt_packed"));


        // From slabs
        offerBlockFromSlabs(exporter, ModBlocks.DIRT_LOOSE, ModBlocks.SLAB_DIRT, ID.ofTE("block_from_slab_dirt"));
        offerBlockFromSlabs(exporter, Blocks.SAND, ModBlocks.SLAB_SAND, ID.ofTE("block_from_slab_sand"));
        offerBlockFromSlabs(exporter, Blocks.RED_SAND, ModBlocks.SLAB_RED_SAND, ID.ofTE("block_from_slab_red_sand"));
        offerBlockFromSlabs(exporter, Blocks.GRAVEL, ModBlocks.SLAB_GRAVEL, ID.ofTE("block_from_slab_gravel"));
        offerBlockFromSlabs(exporter, ModBlocks.COBBLESTONE_LOOSE, ModBlocks.SLAB_COBBLESTONE_LOOSE, ID.ofTE("block_from_slab_cobblestone_loose"));
        offerBlockFromSlabs(exporter, ModBlocks.COBBLED_DEEPSLATE_LOOSE, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, ID.ofTE("block_from_slab_cobbled_deepslate_loose"));
        offerBlockFromSlabs(exporter, ModBlocks.GRANITE_LOOSE, ModBlocks.SLAB_GRANITE_LOOSE, ID.ofTE("block_from_slab_granite_loose"));
        offerBlockFromSlabs(exporter, ModBlocks.ANDESITE_LOOSE, ModBlocks.SLAB_ANDESITE_LOOSE, ID.ofTE("block_from_slab_andesite_loose"));
        offerBlockFromSlabs(exporter, ModBlocks.DIORITE_LOOSE, ModBlocks.SLAB_DIORITE_LOOSE, ID.ofTE("block_from_slab_diorite_loose"));
        offerBlockFromSlabs(exporter, ModBlocks.BRICKS_LOOSE, ModBlocks.SLAB_BRICKS_LOOSE, ID.ofTE("block_from_slab_bricks_loose"));
        offerBlockFromSlabs(exporter, ModBlocks.STONE_BRICKS_LOOSE, ModBlocks.SLAB_STONE_BRICKS_LOOSE, ID.ofTE("block_from_slab_stone_bricks_loose"));
        offerBlockFromSlabs(exporter, ModBlocks.DIRT_PACKED, ModBlocks.SLAB_DIRT_PACKED, ID.ofTE("block_from_slab_packed_dirt"));

        offerSimpleConverting(exporter, ModBlocks.DIRT_LOOSE, ModBlocks.SLAB_DIRT_PACKED, ID.ofTE("dirt_loose_from_slab_dirt_packed"));

        offerBlockFromSlabs(exporter, ModBlocks.DEEPSLATE_BRICKS_LOOSE, ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE, ID.ofTE("block_from_slab_deepslate_bricks_loose"));
        offerBlockFromSlabs(exporter, ModBlocks.NETHER_BRICKS_LOOSE, ModBlocks.SLAB_NETHER_BRICKS_LOOSE, ID.ofTE("block_from_slab_nether_bricks_loose"));

        // From piles, small stones/shards
        offerBlockFromLesserDrops(exporter, Blocks.SAND, ModItems.PILE_SAND, ID.ofTE("block_from_pile_sand"));
        offerBlockFromLesserDrops(exporter, Blocks.RED_SAND, ModItems.PILE_RED_SAND, ID.ofTE("block_from_pile_red_sand"));
        offerBlockFromLesserDrops(exporter, Blocks.GRAVEL, ModItems.PILE_GRAVEL, ID.ofTE("block_from_pile_gravel"));
        offerBlockFromLesserDrops(exporter, ModBlocks.DIRT_LOOSE, ModItems.PILE_DIRT, ID.ofTE("block_from_pile_dirt"));
        offerBlockFromLesserDrops(exporter, ModBlocks.COBBLESTONE_LOOSE, ModItems.SMALL_STONE, ID.ofTE("block_from_small_stone"));
        offerBlockFromLesserDrops(exporter, ModBlocks.COBBLED_DEEPSLATE_LOOSE, ModItems.SMALL_STONE_2, ID.ofTE("block_from_small_stone_2"));
        offerBlockFromLesserDrops(exporter, ModBlocks.GRANITE_LOOSE, ModItems.SHARD_GRANITE, ID.ofTE("block_from_shard_granite"));
        offerBlockFromLesserDrops(exporter, ModBlocks.ANDESITE_LOOSE, ModItems.SHARD_ANDESITE, ID.ofTE("block_from_shard_andesite"));
        offerBlockFromLesserDrops(exporter, ModBlocks.DIORITE_LOOSE, ModItems.SHARD_DIORITE, ID.ofTE("block_from_shard_diorite"));
        offerBlockFromLesserDrops(exporter, ModBlocks.BRICKS_LOOSE, Items.BRICK, ID.ofTE("block_from_brick"));
        offerBlockFromLesserDrops(exporter, ModBlocks.NETHER_BRICKS_LOOSE, Items.NETHER_BRICK, ID.ofTE("block_from_nether_brick"));

        offerStoneBricksBlockFromLesserDrops(exporter, ModBlocks.STONE_BRICKS_LOOSE, ModItems.STONE_BRICK, ID.ofTE("block_from_stone_brick"));
        offerStoneBricksBlockFromLesserDrops(exporter, ModBlocks.DEEPSLATE_BRICKS_LOOSE, ModItems.STONE_BRICK_2, ID.ofTE("deepslate_from_stone_brick"));

    }

    private void addStairBlockRecipes(RecipeExporter exporter) {
        // Simple stairs recipes
        offerSimpleStairs(exporter, ModBlocks.WHITE_STONE_STAIRS, ModBlocks.WHITE_STONE, ID.ofTE("white_stone_stairs"));
        offerSimpleStairs(exporter, ModBlocks.WHITE_COBBLESTONE_STAIRS, ModBlocks.WHITE_COBBLESTONE, ID.ofTE("white_cobblestone_stairs"));

        // 2x2 and 3x3 stair recipes in one method.
        offerCombinedStairs(exporter, ModBlocks.COBBLESTONE_LOOSE_STAIRS, ModBlocks.COBBLESTONE_LOOSE, ModItems.SMALL_STONE, ID.ofTE("stairs_cobblestone_loose"));
        offerCombinedStairs(exporter, ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS, ModBlocks.COBBLED_DEEPSLATE_LOOSE, ModItems.SMALL_STONE_2, ID.ofTE("stairs_cobbled_deepslate_loose"));
        offerCombinedStairs(exporter, ModBlocks.GRANITE_LOOSE_STAIRS, ModBlocks.GRANITE_LOOSE, ModItems.SHARD_GRANITE, ID.ofTE("stairs_granite_loose"));
        offerCombinedStairs(exporter, ModBlocks.ANDESITE_LOOSE_STAIRS, ModBlocks.ANDESITE_LOOSE, ModItems.SHARD_ANDESITE, ID.ofTE("stairs_andesite_loose"));
        offerCombinedStairs(exporter, ModBlocks.DIORITE_LOOSE_STAIRS, ModBlocks.DIORITE_LOOSE, ModItems.SHARD_DIORITE, ID.ofTE("stairs_diorite_loose"));
        offerCombinedStairs(exporter, ModBlocks.BRICKS_LOOSE_STAIRS, ModBlocks.BRICKS_LOOSE, Items.BRICK, ID.ofTE("stairs_bricks_loose"));
        offerCombinedStairs(exporter, ModBlocks.STONE_BRICKS_LOOSE_STAIRS, ModBlocks.STONE_BRICKS_LOOSE, ModItems.STONE_BRICK, ID.ofTE("stairs_stone_bricks_loose"));
        offerCombinedStairs(exporter, ModBlocks.DEEPSLATE_BRICKS_LOOSE_STAIRS, ModBlocks.DEEPSLATE_BRICKS_LOOSE, ModItems.STONE_BRICK_2, ID.ofTE("stairs_deepslate_bricks_loose"));
        offerCombinedStairs(exporter, ModBlocks.NETHER_BRICKS_LOOSE_STAIRS, ModBlocks.NETHER_BRICKS_LOOSE, Items.NETHER_BRICK, ID.ofTE("stairs_nether_bricks_loose"));

        offerVeryCompactStairs(exporter, ModBlocks.STONE_BRICKS_LOOSE_STAIRS, ModItems.STONE_BRICK, ID.ofTE("stairs_stone_bricks_loose"));
        offerVeryCompactStairs(exporter, ModBlocks.DEEPSLATE_BRICKS_LOOSE_STAIRS, ModItems.STONE_BRICK_2, ID.ofTE("stairs_deepslate_bricks_loose"));
    }

    private void addSlabRecipes(RecipeExporter exporter) {
        offerSimpleSlabs(exporter, ModBlocks.SLAB_WHITE_STONE, ModBlocks.WHITE_STONE, ID.ofTE("slab_white_stone"));
        offerSimpleSlabs(exporter, ModBlocks.SLAB_WHITE_COBBLESTONE, ModBlocks.WHITE_COBBLESTONE, ID.ofTE("slab_white_cobblestone"));
        offerSimpleSlabs(exporter, ModBlocks.SLAB_DIRT_PACKED, ModBlocks.SLAB_DIRT_PACKED, ID.ofTE("slab_dirt_packed"));

        // From lesser drops/items (stones/piles/dust)
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_DIRT, ModItems.PILE_DIRT, ID.ofTE("slab_dirt"));
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_SAND, ModItems.PILE_SAND, ID.ofTE("slab_sand"));
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_RED_SAND, ModItems.PILE_RED_SAND, ID.ofTE("slab_red_sand"));
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_GRAVEL, ModItems.PILE_GRAVEL, ID.ofTE("slab_gravel"));
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_COBBLESTONE_LOOSE, ModItems.SMALL_STONE, ID.ofTE("slab_cobblestone_loose"));
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, ModItems.SMALL_STONE_2, ID.ofTE("slab_cobbled_deepslate_loose"));
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_GRANITE_LOOSE, ModItems.SHARD_GRANITE, ID.ofTE("slab_granite_loose"));
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_ANDESITE_LOOSE, ModItems.SHARD_ANDESITE, ID.ofTE("slab_andesite_loose"));
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_DIORITE_LOOSE, ModItems.SHARD_DIORITE, ID.ofTE("slab_diorite_loose"));
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_BRICKS_LOOSE, Items.BRICK, ID.ofTE("slab_bricks_loose"));
        offerSlabFromLesserDrops(exporter, ModBlocks.SLAB_NETHER_BRICKS_LOOSE, Items.NETHER_BRICK, ID.ofTE("slab_nether_bricks_loose"));

        offerStoneBrickSlabFromLesserDrops(exporter, ModBlocks.SLAB_STONE_BRICKS_LOOSE, ModItems.STONE_BRICK, ID.ofTE("slab_stone_bricks_loose"));
        offerStoneBrickSlabFromLesserDrops(exporter, ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE, ModItems.STONE_BRICK_2, ID.ofTE("slab_deepslate_bricks_loose"));

        // From full loose blocks
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_DIRT, ModBlocks.DIRT_LOOSE, ID.ofTE("slab_dirt_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_SAND, Blocks.SAND, ID.ofTE("slab_sand_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_RED_SAND, Blocks.RED_SAND, ID.ofTE("slab_red_sand_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_GRAVEL, Blocks.GRAVEL, ID.ofTE("slab_gravel_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_COBBLESTONE_LOOSE, ModBlocks.COBBLESTONE_LOOSE, ID.ofTE("slab_cobblestone_loose_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, ModBlocks.COBBLED_DEEPSLATE_LOOSE, ID.ofTE("slab_cobbled_deepslate_loose_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_GRANITE_LOOSE, ModBlocks.GRANITE_LOOSE, ID.ofTE("slab_granite_loose_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_ANDESITE_LOOSE, ModBlocks.ANDESITE_LOOSE, ID.ofTE("slab_andesite_loose_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_DIORITE_LOOSE, ModBlocks.DIORITE_LOOSE, ID.ofTE("slab_diorite_loose_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_BRICKS_LOOSE, ModBlocks.BRICKS_LOOSE, ID.ofTE("slab_bricks_loose_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_NETHER_BRICKS_LOOSE, ModBlocks.NETHER_BRICKS_LOOSE, ID.ofTE("slab_nether_bricks_loose_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_DIRT_PACKED, ModBlocks.DIRT_PACKED, ID.ofTE("slab_dirt_packed_from_block"));

        offerSlabsFromBlock(exporter, ModBlocks.SLAB_STONE_BRICKS_LOOSE, ModBlocks.STONE_BRICKS_LOOSE, ID.ofTE("slab_stone_bricks_loose_from_block"));
        offerSlabsFromBlock(exporter, ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE, ModBlocks.DEEPSLATE_BRICKS_LOOSE, ID.ofTE("slab_deepslate_bricks_loose_from_block"));

    }

    private void addItemRecipes(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.COPPER_INGOT)
                .input('N', ModItems.COPPER_NUGGET)
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .criterion("has_copper_nugget", conditionsFromItem(ModItems.COPPER_NUGGET))
                .offerTo(exporter, ID.ofTE("copper_ingot_from_copper_nugget"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.NETHERITE_INGOT)
                .input('N', ModItems.NETHERITE_NUGGET)
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .criterion("has_netherite_nugget", conditionsFromItem(ModItems.NETHERITE_NUGGET))
                .offerTo(exporter, ID.ofTE("netherite_ingot_from_netherite_nugget"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.COPPER_NUGGET, 9)
                .input(Items.COPPER_INGOT)
                .criterion("has_copper_ingot", conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, ID.ofTE("copper_nugget_from_copper_ingot"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.NETHERITE_NUGGET, 9)
                .input(Items.NETHERITE_INGOT)
                .criterion("has_netherite_ingot", conditionsFromItem(Items.NETHERITE_INGOT))
                .offerTo(exporter, ID.ofTE("netherite_nugget_from_netherite_ingot"));

    }

    private void addCookingRecipes(RecipeExporter exporter) {
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(Blocks.END_STONE), RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WHITE_COBBLESTONE, 0.10f, 200).criterion("has_end_stone", conditionsFromItem(Blocks.END_STONE)).offerTo(exporter);

        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(Blocks.END_STONE), RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WHITE_COBBLESTONE, 0.15f, 100).criterion("has_end_stone", conditionsFromItem(Blocks.END_STONE)).offerTo(exporter, ID.ofTE("white_stone_from_blasting"));

        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(ModBlocks.WHITE_COBBLESTONE), RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WHITE_STONE, 0.15f, 100).criterion("has_white_cobblestone", conditionsFromItem(ModBlocks.WHITE_COBBLESTONE)).offerTo(exporter);

        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(ModItems.NETHER_BRICK_UNFIRED), RecipeCategory.MISC,
                Items.NETHER_BRICK, 0.10f, 200).criterion("has_nether_brick_unfired", conditionsFromItem(ModItems.NETHER_BRICK_UNFIRED)).offerTo(exporter, ID.ofTE("nether_brick_from_smelting"));

        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(ModItems.NETHER_BRICK_UNFIRED), RecipeCategory.MISC,
                Items.NETHER_BRICK, 0.15f, 100).criterion("has_nether_brick_unfired", conditionsFromItem(ModItems.NETHER_BRICK_UNFIRED)).offerTo(exporter, ID.ofTE("nether_brick_from_blasting"));


    }

    // LESSER DROP METHODS
    private static void offerLesserDropsFromSlab(RecipeExporter exporter, ItemConvertible output, int count, ItemConvertible input, Identifier id)
    {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    // simple recipe for converting one item to another
    private static void offerSimpleConverting(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    private static void offerLesserDropsFromBlock(RecipeExporter exporter, ItemConvertible output, int count, ItemConvertible input, Identifier id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    public static void offerLesserDropsFromStairs(RecipeExporter exporter, ItemConvertible output, int count, ItemConvertible input, Identifier id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    // SLAB METHODS
    private static void offerSlabsFromBlock(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .input('#', input)
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    private static void offerSlabFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('#', input)
                .pattern("##")
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    private static void offerStoneBrickSlabFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('#', input)
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    // BLOCK METHODS
    private static void offerBlockFromSlabs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('#', input)
                .pattern("#")
                .pattern("#")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);

    }

    private static void offerStoneBricksBlockFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('S', input)
                .pattern("SS")
                .pattern("SS")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    // an 8 input block
    private static void offerBlockFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input).input(input).input(input).input(input).input(input).input(input).input(input).input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }


    // a 9 input block
    private static void offerFullBlockFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input).input(input).input(input).input(input).input(input).input(input).input(input).input(input).input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    /** Combined stairs method used for loose blocks only **/
    private static void offerCombinedStairs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, ItemConvertible pileInput, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .input('#', input)
                .pattern("# ")
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id + "_compact");

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 8)
                .input('#', input)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);

    }

    private static void offerVeryCompactStairs(RecipeExporter exporter, ItemConvertible output, ItemConvertible pileInput, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output)
                .input('#', pileInput)
                .pattern("# ")
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(pileInput), conditionsFromItem(pileInput))
                .offerTo(exporter, id + "_very_compact");
    }

    /** Simple stairs method usually used for non-loose blocks that create their stairs the regular way only **/
    private static void offerSimpleStairs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output,6)
                .input('S', input)
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SSS")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    /** Simple slab method usually used for non-loose blocks that create their slabs the regular way only **/
    private static void offerSimpleSlabs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output,6)
                .input('S', input)
                .pattern("SSS")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    // use later
    private static void offerBiDirectionalConversionRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, int inputQuantity, int outputQuantity, Identifier id) {
        // From block to lesser drops
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, outputQuantity)
                .input(input, inputQuantity)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);

        // From lesser drops to block
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, input, inputQuantity)
                .input(output, outputQuantity)
                .group("group_te")
                .criterion(hasItem(output), conditionsFromItem(output))
                .offerTo(exporter, id);
    }

}

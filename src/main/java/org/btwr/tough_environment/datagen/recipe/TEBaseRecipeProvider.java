package org.btwr.tough_environment.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.btwr.shared_library.util.utils.RecipeUtils;

import java.util.concurrent.CompletableFuture;


public class TEBaseRecipeProvider extends FabricRecipeProvider implements RecipeUtils {

    public TEBaseRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "Tough Environment Recipes";
    }

    @Override
    public void generate(RecipeExporter exporter) {}

    // LESSER DROP METHODS
    public static void offerLesserDropsFromSlab(RecipeExporter exporter, ItemConvertible output, int count, ItemConvertible input, Identifier id)
    {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    // simple recipe for converting one item to another
    public static void offerSimpleConverting(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    public static void offerLesserDropsFromBlock(RecipeExporter exporter, ItemConvertible output, int count, ItemConvertible input, Identifier id) {
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
    public static void offerSlabsFromBlock(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .input('#', input)
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    public static void offerSlabFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('#', input)
                .pattern("##")
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    public static void offerStoneBrickSlabFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('#', input)
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    // BLOCK METHODS
    public static void offerBlockFromSlabs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('#', input)
                .pattern("#")
                .pattern("#")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);

    }

    public static void offerStoneBricksBlockFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('S', input)
                .pattern("SS")
                .pattern("SS")
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    // an 8 input block
    public static void offerBlockFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input).input(input).input(input).input(input).input(input).input(input).input(input).input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    // a 9 input block
    public static void offerFullBlockFromLesserDrops(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input).input(input).input(input).input(input).input(input).input(input).input(input).input(input).input(input)
                .group("group_te")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    /** Combined stairs method used for loose blocks only **/
    public static void offerCombinedStairs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, ItemConvertible pileInput, Identifier id) {
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

    public static void offerVeryCompactStairs(RecipeExporter exporter, ItemConvertible output, ItemConvertible pileInput, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output)
                .input('#', pileInput)
                .pattern("# ")
                .pattern("##")
                .group("group_te")
                .criterion(hasItem(pileInput), conditionsFromItem(pileInput))
                .offerTo(exporter, id + "_very_compact");
    }

    /** Simple stairs method usually used for non-loose blocks that create their stairs the regular way only **/
    public static void offerSimpleStairs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output,6)
                .input('S', input)
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SSS")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    /** Simple slab method usually used for non-loose blocks that create their slabs the regular way only **/
    public static void offerSimpleSlabs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, Identifier id) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output,6)
                .input('S', input)
                .pattern("SSS")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, id);
    }

    // use later
    public static void offerBiDirectionalConversionRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, int inputQuantity, int outputQuantity, Identifier id) {
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
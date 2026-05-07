package org.btwr.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import org.btwr.tough_environment.block.ModBlocks;
import org.btwr.tough_environment.item.ModItems;

import static net.minecraft.data.client.BlockStateModelGenerator.createSlabBlockState;

public class TEModelGenerator extends FabricModelProvider {
    public TEModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        this.registerSimpleSlab(generator, Blocks.SAND, ModBlocks.SLAB_SAND);
        this.registerSimpleSlab(generator, Blocks.RED_SAND, ModBlocks.SLAB_RED_SAND);
        this.registerSimpleSlab(generator, Blocks.DIRT, ModBlocks.SLAB_DIRT);
        this.registerSimpleSlab(generator, Blocks.GRAVEL, ModBlocks.SLAB_GRAVEL);
        this.registerSimpleSlab(generator, ModBlocks.COBBLESTONE_LOOSE, ModBlocks.SLAB_COBBLESTONE_LOOSE);
        this.registerSimpleSlab(generator, ModBlocks.COBBLED_DEEPSLATE_LOOSE, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE);
        this.registerSimpleSlab(generator, ModBlocks.GRANITE_LOOSE, ModBlocks.SLAB_GRANITE_LOOSE);
        this.registerSimpleSlab(generator, ModBlocks.ANDESITE_LOOSE, ModBlocks.SLAB_ANDESITE_LOOSE);
        this.registerSimpleSlab(generator, ModBlocks.DIORITE_LOOSE, ModBlocks.SLAB_DIORITE_LOOSE);
        this.registerSimpleSlab(generator, ModBlocks.BRICKS_LOOSE, ModBlocks.SLAB_BRICKS_LOOSE);
        this.registerSimpleSlab(generator, ModBlocks.STONE_BRICKS_LOOSE, ModBlocks.SLAB_STONE_BRICKS_LOOSE);
        this.registerSimpleSlab(generator, ModBlocks.DEEPSLATE_BRICKS_LOOSE, ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE);
        this.registerSimpleSlab(generator, ModBlocks.NETHER_BRICKS_LOOSE, ModBlocks.SLAB_NETHER_BRICKS_LOOSE);
        this.registerSimpleSlab(generator, ModBlocks.WHITE_STONE, ModBlocks.SLAB_WHITE_STONE);
        this.registerSimpleSlab(generator, ModBlocks.WHITE_COBBLESTONE, ModBlocks.SLAB_WHITE_COBBLESTONE);
        this.registerSimpleSlab(generator, ModBlocks.DIRT_PACKED, ModBlocks.SLAB_DIRT_PACKED);

        generator.registerSimpleCubeAll(ModBlocks.DIRT_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.COBBLESTONE_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.COBBLED_DEEPSLATE_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.GRANITE_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.ANDESITE_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.DIORITE_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.BRICKS_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.STONE_BRICKS_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_BRICKS_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.NETHER_BRICKS_LOOSE);

        generator.registerSimpleCubeAll(ModBlocks.LAVA_FILLED_COBBLESTONE_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.LAVA_FILLED_COBBLED_DEEPSLATE_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.LAVA_FILLED_STONE_BRICKS_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.LAVA_FILLED_DEEPSLATE_BRICKS_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.LAVA_PILLOW);

        generator.registerSimpleCubeAll(ModBlocks.CALCITE_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.TUFF_LOOSE);
        generator.registerSimpleCubeAll(ModBlocks.BLACKSTONE_LOOSE);
        generator.registerAxisRotated(ModBlocks.BASALT_LOOSE, TexturedModel.CUBE_COLUMN);

        generator.registerSimpleCubeAll(ModBlocks.END_STONE_LOOSE);

        generator.registerSimpleCubeAll(ModBlocks.STONE_CONVERTING);
        generator.registerSimpleCubeAll(ModBlocks.GRANITE_CONVERTING);
        generator.registerSimpleCubeAll(ModBlocks.DIORITE_CONVERTING);
        generator.registerSimpleCubeAll(ModBlocks.ANDESITE_CONVERTING);
        generator.registerSimpleCubeAll(ModBlocks.CALCITE_CONVERTING);
        generator.registerSimpleCubeAll(ModBlocks.TUFF_CONVERTING);
        generator.registerSimpleCubeAll(ModBlocks.BLACKSTONE_CONVERTING);
        generator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_CONVERTING);
        generator.registerSimpleCubeAll(ModBlocks.BASALT_CONVERTING);
        generator.registerSimpleCubeAll(ModBlocks.END_STONE_CONVERTING);

        generator.registerSimpleCubeAll(ModBlocks.STONE_BROKEN);
        generator.registerSimpleCubeAll(ModBlocks.GRANITE_BROKEN);
        generator.registerSimpleCubeAll(ModBlocks.DIORITE_BROKEN);
        generator.registerSimpleCubeAll(ModBlocks.ANDESITE_BROKEN);
        generator.registerSimpleCubeAll(ModBlocks.CALCITE_BROKEN);
        generator.registerSimpleCubeAll(ModBlocks.TUFF_BROKEN);
        generator.registerSimpleCubeAll(ModBlocks.BLACKSTONE_BROKEN);
        generator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_BROKEN);
        generator.registerSimpleCubeAll(ModBlocks.BASALT_BROKEN);
        generator.registerSimpleCubeAll(ModBlocks.ENDSTONE_BROKEN);

        generator.registerSimpleCubeAll(ModBlocks.CLAY_ORE);
        generator.registerSimpleCubeAll(ModBlocks.WHITE_STONE);
        generator.registerSimpleCubeAll(ModBlocks.WHITE_COBBLESTONE);
        generator.registerSimpleCubeAll(ModBlocks.DIRT_PACKED);

        this.registerStairs(generator, ModBlocks.COBBLESTONE_LOOSE_STAIRS, ModBlocks.COBBLESTONE_LOOSE);
        this.registerStairs(generator, ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS, ModBlocks.COBBLED_DEEPSLATE_LOOSE);
        this.registerStairs(generator, ModBlocks.ANDESITE_LOOSE_STAIRS, ModBlocks.ANDESITE_LOOSE);
        this.registerStairs(generator, ModBlocks.GRANITE_LOOSE_STAIRS, ModBlocks.GRANITE_LOOSE);
        this.registerStairs(generator, ModBlocks.DIORITE_LOOSE_STAIRS, ModBlocks.DIORITE_LOOSE);
        this.registerStairs(generator, ModBlocks.BRICKS_LOOSE_STAIRS, ModBlocks.BRICKS_LOOSE);
        this.registerStairs(generator, ModBlocks.STONE_BRICKS_LOOSE_STAIRS, ModBlocks.STONE_BRICKS_LOOSE);
        this.registerStairs(generator, ModBlocks.DEEPSLATE_BRICKS_LOOSE_STAIRS, ModBlocks.DEEPSLATE_BRICKS_LOOSE);
        this.registerStairs(generator, ModBlocks.NETHER_BRICKS_LOOSE_STAIRS, ModBlocks.NETHER_BRICKS_LOOSE);
        this.registerStairs(generator, ModBlocks.WHITE_STONE_STAIRS, ModBlocks.WHITE_STONE);
        this.registerStairs(generator, ModBlocks.WHITE_COBBLESTONE_STAIRS, ModBlocks.WHITE_COBBLESTONE);

        // Blocks which aren't generated below

        // Raw ore placed blocks are manually created
        // Nether brick unfired is manually created
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(ModItems.GROUP_TE, Models.GENERATED);
        generator.register(ModItems.PILE_DIRT, Models.GENERATED);
        generator.register(ModItems.PILE_GRAVEL, Models.GENERATED);
        generator.register(ModItems.PILE_SAND, Models.GENERATED);
        generator.register(ModItems.PILE_RED_SAND, Models.GENERATED);
        generator.register(ModItems.PILE_CLAY, Models.GENERATED);
        generator.register(ModItems.NETHER_SLUDGE, Models.GENERATED);
        generator.register(ModItems.SMALL_STONE, Models.GENERATED);
        generator.register(ModItems.SMALL_STONE_1, Models.GENERATED);
        generator.register(ModItems.SMALL_STONE_2, Models.GENERATED);
        generator.register(ModItems.SHARD_GRANITE, Models.GENERATED);
        generator.register(ModItems.SHARD_ANDESITE, Models.GENERATED);
        generator.register(ModItems.SHARD_DIORITE, Models.GENERATED);
        generator.register(ModItems.COPPER_NUGGET, Models.GENERATED);
        generator.register(ModItems.NETHERITE_NUGGET, Models.GENERATED);
        generator.register(ModItems.DUST_COAL, Models.GENERATED);
        generator.register(ModItems.DUST_IRON, Models.GENERATED);
        generator.register(ModItems.DUST_GOLD, Models.GENERATED);
        generator.register(ModItems.DUST_COPPER, Models.GENERATED);
        generator.register(ModItems.STONE_BRICK, Models.GENERATED);
        generator.register(ModItems.STONE_BRICK_1, Models.GENERATED);
        generator.register(ModItems.STONE_BRICK_2, Models.GENERATED);
        generator.register(ModItems.NETHER_BRICK_UNFIRED, Models.GENERATED);
        generator.register(ModItems.CHISEL_WOOD, Models.GENERATED);
        generator.register(ModItems.CHISEL_STONE, Models.GENERATED);
        generator.register(ModItems.CHISEL_IRON, Models.GENERATED);
        generator.register(ModItems.CHISEL_DIAMOND, Models.GENERATED);
    }

    private void registerSimpleSlab(BlockStateModelGenerator gen, Block mainBlock, Block slabBlock) {
        Identifier identifier = ModelIds.getBlockModelId(mainBlock);
        TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(mainBlock);
        Identifier identifier2 = Models.SLAB.upload(slabBlock, texturedModel.getTextures(), gen.modelCollector);
        Identifier identifier3 = Models.SLAB_TOP.upload(slabBlock, texturedModel.getTextures(), gen.modelCollector);
        gen.blockStateCollector.accept(createSlabBlockState(slabBlock, identifier2, identifier3, identifier));
    }

    private void registerStairs(BlockStateModelGenerator gen, Block stairsBlock, Block texSourceBlock) {
        TextureMap texMap = TextureMap.all(texSourceBlock);

        Identifier innerModel = Models.INNER_STAIRS.upload(stairsBlock, texMap, gen.modelCollector);
        Identifier straightModel = Models.STAIRS.upload(stairsBlock, texMap, gen.modelCollector);
        Identifier outerModel = Models.OUTER_STAIRS.upload(stairsBlock, texMap, gen.modelCollector);

        gen.blockStateCollector.accept(
                BlockStateModelGenerator.createStairsBlockState(stairsBlock, innerModel, straightModel, outerModel)
        );

        gen.registerParentedItemModel(stairsBlock, straightModel);
    }
}

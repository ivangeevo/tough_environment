package org.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import org.tough_environment.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class TEBlockMortarProvider extends GeneralStringPairProvider {

    public TEBlockMortarProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    protected String getMapFolder() {
        return "mortar";
    }

    @Override
    protected String getMapPath() {
        return "block_mortar_map";
    }

    @Override
    public void generatePairs(RegistryWrapper.WrapperLookup registryLookup, PairBuilder builder) {
        builder.add(ModBlocks.COBBLESTONE_LOOSE, Blocks.COBBLESTONE);
        builder.add(ModBlocks.COBBLED_DEEPSLATE_LOOSE, Blocks.COBBLED_DEEPSLATE);
        builder.add(ModBlocks.DIORITE_LOOSE, Blocks.DIORITE);
        builder.add(ModBlocks.ANDESITE_LOOSE, Blocks.ANDESITE);
        builder.add(ModBlocks.GRANITE_LOOSE, Blocks.GRANITE);
        builder.add(ModBlocks.CALCITE_LOOSE, Blocks.CALCITE);
        builder.add(ModBlocks.TUFF_LOOSE, Blocks.TUFF);
        builder.add(ModBlocks.BASALT_LOOSE, Blocks.BASALT);
        builder.add(ModBlocks.BLACKSTONE_LOOSE, Blocks.BLACKSTONE);
        builder.add(ModBlocks.END_STONE_LOOSE, Blocks.END_STONE);

        builder.add(ModBlocks.SLAB_COBBLESTONE_LOOSE, Blocks.COBBLESTONE_SLAB);
        builder.add(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, Blocks.COBBLED_DEEPSLATE_SLAB);
        builder.add(ModBlocks.SLAB_DIORITE_LOOSE, Blocks.DIORITE_SLAB);
        builder.add(ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE, Blocks.DEEPSLATE_BRICK_SLAB);
        builder.add(ModBlocks.SLAB_ANDESITE_LOOSE, Blocks.ANDESITE_SLAB);
        builder.add(ModBlocks.SLAB_GRANITE_LOOSE, Blocks.GRANITE_SLAB);

        builder.add(ModBlocks.COBBLESTONE_LOOSE_STAIRS, Blocks.COBBLESTONE_STAIRS);
        builder.add(ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS, Blocks.COBBLED_DEEPSLATE_STAIRS);
        builder.add(ModBlocks.DEEPSLATE_BRICKS_LOOSE_STAIRS, Blocks.DEEPSLATE_BRICK_STAIRS);
        builder.add(ModBlocks.DIORITE_LOOSE_STAIRS, Blocks.DIORITE_STAIRS);
        builder.add(ModBlocks.ANDESITE_LOOSE_STAIRS, Blocks.ANDESITE_STAIRS);
        builder.add(ModBlocks.GRANITE_LOOSE_STAIRS, Blocks.GRANITE_STAIRS);

        builder.add(ModBlocks.BRICKS_LOOSE, Blocks.BRICKS);
        builder.add(ModBlocks.NETHER_BRICKS_LOOSE, Blocks.NETHER_BRICKS);
        builder.add(ModBlocks.NETHER_BRICKS_LOOSE_STAIRS, Blocks.NETHER_BRICK_STAIRS);

        builder.add(ModBlocks.SLAB_STONE_BRICKS_LOOSE, Blocks.STONE_BRICK_SLAB);
        builder.add(ModBlocks.SLAB_BRICKS_LOOSE, Blocks.BRICK_SLAB);
        builder.add(ModBlocks.SLAB_NETHER_BRICKS_LOOSE, Blocks.NETHER_BRICK_SLAB);

        builder.add(ModBlocks.STONE_BRICKS_LOOSE_STAIRS, Blocks.STONE_BRICK_STAIRS);
        builder.add(ModBlocks.BRICKS_LOOSE_STAIRS, Blocks.BRICK_STAIRS);
    }


}

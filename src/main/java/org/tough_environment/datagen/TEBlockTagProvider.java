package org.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.tag.ModTags;

import java.util.concurrent.CompletableFuture;

public class TEBlockTagProvider extends FabricTagProvider.BlockTagProvider
{

    public TEBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        getOrCreateTagBuilder(ModTags.Blocks.BROKEN_STONES)
                .add(ModBlocks.STONE_BROKEN)
                .add(ModBlocks.GRANITE_BROKEN)
                .add(ModBlocks.DIORITE_BROKEN)
                .add(ModBlocks.ANDESITE_BROKEN)
                .add(ModBlocks.CALCITE_BROKEN)
                .add(ModBlocks.TUFF_BROKEN)
                .add(ModBlocks.BLACKSTONE_BROKEN)
                .add(ModBlocks.DEEPSLATE_BROKEN)
                .add(ModBlocks.BASALT_BROKEN)
                .add(ModBlocks.ENDSTONE_BROKEN);

        getOrCreateTagBuilder(ModTags.Blocks.CONVERTED_STONE_BLOCKS)
                .add(ModBlocks.STONE_CONVERTING)
                .add(ModBlocks.GRANITE_CONVERTING)
                .add(ModBlocks.DIORITE_CONVERTING)
                .add(ModBlocks.ANDESITE_CONVERTING)
                .add(ModBlocks.CALCITE_CONVERTING)
                .add(ModBlocks.TUFF_CONVERTING)
                .add(ModBlocks.BLACKSTONE_CONVERTING)
                .add(ModBlocks.DEEPSLATE_CONVERTING)
                .add(ModBlocks.BASALT_CONVERTING)
                .add(ModBlocks.ENDSTONE_CONVERTING);

        getOrCreateTagBuilder(ModTags.Blocks.MORTARABLE_BLOCKS)
                .add(Blocks.COBBLESTONE)
                .add(Blocks.COBBLED_DEEPSLATE)
                .add(Blocks.GRANITE)
                .add(Blocks.ANDESITE)
                .add(Blocks.COBBLESTONE_SLAB)
                .add(Blocks.COBBLED_DEEPSLATE_SLAB)
                .add(Blocks.ANDESITE_SLAB)
                .add(Blocks.GRANITE_SLAB);



        // Vanilla mineable tags added

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.STONE_CONVERTING)
                .add(ModBlocks.GRANITE_CONVERTING)
                .add(ModBlocks.DIORITE_CONVERTING)
                .add(ModBlocks.ANDESITE_CONVERTING)
                .add(ModBlocks.CALCITE_CONVERTING)
                .add(ModBlocks.TUFF_CONVERTING)
                .add(ModBlocks.BLACKSTONE_CONVERTING)
                .add(ModBlocks.DEEPSLATE_CONVERTING)
                .add(ModBlocks.BASALT_CONVERTING)
                .add(ModBlocks.ENDSTONE_CONVERTING)

                .add(ModBlocks.STONE_BROKEN)
                .add(ModBlocks.GRANITE_BROKEN)
                .add(ModBlocks.DIORITE_BROKEN)
                .add(ModBlocks.ANDESITE_BROKEN)
                .add(ModBlocks.CALCITE_BROKEN)
                .add(ModBlocks.TUFF_BROKEN)
                .add(ModBlocks.BLACKSTONE_BROKEN)
                .add(ModBlocks.DEEPSLATE_BROKEN)
                .add(ModBlocks.BASALT_BROKEN)
                .add(ModBlocks.ENDSTONE_BROKEN)

                .add(ModBlocks.SLAB_COBBLESTONE_LOOSE)
                .add(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE)
                .add(ModBlocks.SLAB_GRANITE_LOOSE)
                .add(ModBlocks.SLAB_ANDESITE_LOOSE)
                .add(ModBlocks.SLAB_BRICK_LOOSE)

                .add(ModBlocks.COBBLESTONE_LOOSE)
                .add(ModBlocks.COBBLED_DEEPSLATE_LOOSE)
                .add(ModBlocks.GRANITE_LOOSE)
                .add(ModBlocks.ANDESITE_LOOSE);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.SLAB_DIRT)
                .add(ModBlocks.SLAB_GRAVEL)
                .add(ModBlocks.SLAB_SAND)
                .add(ModBlocks.SLAB_RED_SAND)

                .add(ModBlocks.DIRT_LOOSE);



    }

}

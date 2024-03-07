package org.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
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



    }

}

package org.tough_environment.datagen;

import com.terraformersmc.modmenu.util.mod.Mod;
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

        addToVanillaTags(arg);
        addToModTags(arg);

    }

    private void addToVanillaTags(RegistryWrapper.WrapperLookup arg)
    {

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .forceAddTag(ModTags.Blocks.STONE_STRATA3);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)

                .addTag(ModTags.Blocks.CONVERTED_STONE_BLOCKS)
                .addTag(ModTags.Blocks.BROKEN_STONE_BLOCKS);


        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.SLAB_DIRT)
                .add(ModBlocks.SLAB_GRAVEL)
                .add(ModBlocks.SLAB_SAND)
                .add(ModBlocks.SLAB_RED_SAND)
                .add(ModBlocks.DIRT_LOOSE);
    }

    private void addToModTags(RegistryWrapper.WrapperLookup arg)
    {
        getOrCreateTagBuilder(ModTags.Blocks.ORES_ALL)
                .addTag(ModTags.Blocks.ORES_LVL1)
                .addTag(ModTags.Blocks.ORES_LVL2)
                .addTag(ModTags.Blocks.ORES_LVL3)
                .addTag(ModTags.Blocks.ORES_LVL4);

        getOrCreateTagBuilder(ModTags.Blocks.DEEPSLATE_ORES)
                .add(Blocks.DEEPSLATE_COAL_ORE)
                .add(Blocks.DEEPSLATE_IRON_ORE)
                .add(Blocks.DEEPSLATE_COPPER_ORE)
                .add(Blocks.DEEPSLATE_REDSTONE_ORE)
                .add(Blocks.DEEPSLATE_LAPIS_ORE)
                .add(Blocks.DEEPSLATE_GOLD_ORE)
                .add(Blocks.DEEPSLATE_DIAMOND_ORE)
                .add(Blocks.DEEPSLATE_EMERALD_ORE);

        getOrCreateTagBuilder(ModTags.Blocks.STONE_ORES)
                .add(Blocks.COAL_ORE)
                .add(Blocks.IRON_ORE)
                .add(Blocks.COPPER_ORE)
                .add(Blocks.REDSTONE_ORE)
                .add(Blocks.LAPIS_ORE)
                .add(Blocks.GOLD_ORE)
                .add(Blocks.DIAMOND_ORE)
                .add(Blocks.EMERALD_ORE);



        getOrCreateTagBuilder(ModTags.Blocks.ORES_LVL1)
                .forceAddTag(BlockTags.COAL_ORES)
                .forceAddTag(BlockTags.IRON_ORES)
                .forceAddTag(BlockTags.COPPER_ORES);

        getOrCreateTagBuilder(ModTags.Blocks.ORES_LVL2)
                .forceAddTag(BlockTags.REDSTONE_ORES)
                .forceAddTag(BlockTags.GOLD_ORES)
                .forceAddTag(BlockTags.LAPIS_ORES);

        getOrCreateTagBuilder(ModTags.Blocks.ORES_LVL3)
                .forceAddTag(BlockTags.DIAMOND_ORES)
                .forceAddTag(BlockTags.EMERALD_ORES);

        getOrCreateTagBuilder(ModTags.Blocks.ORES_LVL4)
                .add(Blocks.ANCIENT_DEBRIS);


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
                .add(ModBlocks.END_STONE_CONVERTING);

        getOrCreateTagBuilder(ModTags.Blocks.MORTARED_BLOCKS)
                .add(Blocks.COBBLESTONE)
                .add(Blocks.COBBLED_DEEPSLATE)
                .add(Blocks.GRANITE)
                .add(Blocks.ANDESITE)
                .add(Blocks.COBBLESTONE_SLAB)
                .add(Blocks.COBBLED_DEEPSLATE_SLAB)
                .add(Blocks.ANDESITE_SLAB)
                .add(Blocks.GRANITE_SLAB);

        getOrCreateTagBuilder(ModTags.Blocks.BROKEN_STONE_BLOCKS)
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

        getOrCreateTagBuilder(ModTags.Blocks.STONE_STRATA3)
                .add(Blocks.DEEPSLATE);
    }



}

package org.btwr.tough_environment.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import org.btwr.shared_library.tag.BTWRConventionalTags;
import org.btwr.tough_environment.block.ModBlocks;
import org.btwr.tough_environment.tag.ModTags;

import java.util.concurrent.CompletableFuture;

public class TEBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public TEBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.addToVanillaTags();
        this.addToModTags();
        this.addToConventionalTags();
    }

    private void addToConventionalTags() {
        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.MODDED_CONVERTING_BLOCKS)
                .add(ModBlocks.STONE_CONVERTING)
                .add(ModBlocks.GRANITE_CONVERTING)
                .add(ModBlocks.DIORITE_CONVERTING)
                .add(ModBlocks.ANDESITE_CONVERTING)
                .add(ModBlocks.TUFF_CONVERTING)
                .add(ModBlocks.BLACKSTONE_CONVERTING)
                .add(ModBlocks.DEEPSLATE_CONVERTING)
                .add(ModBlocks.BASALT_CONVERTING)
                .add(ModBlocks.END_STONE_CONVERTING);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.STONE)
                .addTag(ModTags.Blocks.LOOSE_STONE_BLOCKS)
                .add(ModBlocks.WHITE_STONE)
                .add(ModBlocks.WHITE_COBBLESTONE)
                .add(ModBlocks.SLAB_WHITE_STONE)
                .add(ModBlocks.SLAB_WHITE_COBBLESTONE)
                .add(ModBlocks.WHITE_STONE_STAIRS)
                .add(ModBlocks.WHITE_COBBLESTONE_STAIRS);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.LOOSEN_ON_IMPROPER_BREAK_SLABS)
                .addOptional(Identifier.of("bwt", "dirt_slab"))
                .addOptional(Identifier.of("bwt", "grass_slab"))
                .addOptional(Identifier.of("bwt", "mycelium_slab"))
                .addOptional(Identifier.of("bwt", "podzol_slab"));

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.STONE_STRATA1)
                .forceAddTag(ModTags.Blocks.STONE_CONVERTING_STRATA1);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.STONE_STRATA2)
                .forceAddTag(ModTags.Blocks.STONE_CONVERTING_STRATA2);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.STONE_STRATA3)
                .forceAddTag(ModTags.Blocks.STONE_CONVERTING_STRATA3);

    }

    private void addToVanillaTags() {
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .forceAddTag(BTWRConventionalTags.Blocks.STUMP_BLOCKS);

        getOrCreateTagBuilder(BlockTags.SCULK_REPLACEABLE)
                .add(ModBlocks.CLAY_ORE);

        getOrCreateTagBuilder(BlockTags.SCULK_REPLACEABLE_WORLD_GEN)
                .add(ModBlocks.CLAY_ORE);

        getOrCreateTagBuilder(BlockTags.ENDERMAN_HOLDABLE)
                .add(ModBlocks.CLAY_ORE);

        getOrCreateTagBuilder(BlockTags.AZALEA_ROOT_REPLACEABLE)
                .add(ModBlocks.CLAY_ORE);

        getOrCreateTagBuilder(BlockTags.BIG_DRIPLEAF_PLACEABLE)
                .add(ModBlocks.CLAY_ORE);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.CLAY_ORE);

        getOrCreateTagBuilder(BlockTags.AXOLOTLS_SPAWNABLE_ON)
                .add(ModBlocks.CLAY_ORE);

        getOrCreateTagBuilder(BlockTags.SMALL_DRIPLEAF_PLACEABLE)
                .add(ModBlocks.CLAY_ORE);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                // tags
                .forceAddTag(BTWRConventionalTags.Blocks.STONE_STRATA1)
                //.forceAddTag(BTWRConventionalTags.Blocks.STONE_STRATA2)
                .forceAddTag(BTWRConventionalTags.Blocks.STONE_STRATA3)
                .addTag(ModTags.Blocks.CONVERTED_STONE_BLOCKS)
                .addTag(ModTags.Blocks.LOOSE_STONE_BLOCKS)
                .addTag(ModTags.Blocks.LOOSE_STONE_SLABS)
                .addTag(ModTags.Blocks.LOOSE_STONE_STAIRS)
                .addTag(ModTags.Blocks.BROKEN_STONE_BLOCKS)

                // blocks
                .add(ModBlocks.WHITE_STONE)
                .add(ModBlocks.WHITE_COBBLESTONE)
                .add(ModBlocks.SLAB_WHITE_STONE)
                .add(ModBlocks.SLAB_WHITE_COBBLESTONE)
                .add(ModBlocks.WHITE_STONE_STAIRS)
                .add(ModBlocks.WHITE_COBBLESTONE_STAIRS);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .forceAddTag(BlockTags.LEAVES);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.SLAB_DIRT)
                .add(ModBlocks.SLAB_GRAVEL)
                .add(ModBlocks.SLAB_SAND)
                .add(ModBlocks.SLAB_RED_SAND)
                .add(ModBlocks.SLAB_DIRT_PACKED)
                .add(ModBlocks.DIRT_LOOSE)
                .add(ModBlocks.DIRT_PACKED);

        getOrCreateTagBuilder(BlockTags.BASE_STONE_OVERWORLD)
                .add(ModBlocks.STONE_CONVERTING)
                .add(ModBlocks.GRANITE_CONVERTING)
                .add(ModBlocks.DIORITE_CONVERTING)
                .add(ModBlocks.ANDESITE_CONVERTING)
                .add(ModBlocks.CALCITE_CONVERTING)
                .add(ModBlocks.TUFF_CONVERTING);

        getOrCreateTagBuilder(BlockTags.DIRT)
                .add(ModBlocks.DIRT_LOOSE);

        getOrCreateTagBuilder(BlockTags.STAIRS)
                .forceAddTag(ModTags.Blocks.LOOSE_STONE_STAIRS)
                .add(ModBlocks.WHITE_STONE_STAIRS)
                .add(ModBlocks.WHITE_COBBLESTONE_STAIRS);
    }

    private void addToModTags() {

        this.getOrCreateTagBuilder(ModTags.Mineable.CHISEL)
                .forceAddTag(BlockTags.PICKAXE_MINEABLE)
                .forceAddTag(BlockTags.LOGS)
                .addOptionalTag(BTWRConventionalTags.Blocks.STUMP_BLOCKS)
                .addOptionalTag(BTWRConventionalTags.Blocks.WEB_BLOCKS)
        ;

        this.getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_FOR_DIAMOND_CHISEL);

        this.getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_FOR_IRON_CHISEL)
                .forceAddTag(BlockTags.NEEDS_DIAMOND_TOOL);

        this.getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_FOR_STONE_CHISEL)
                .forceAddTag(ModTags.Blocks.INCORRECT_FOR_IRON_CHISEL);

        this.getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_FOR_WOODEN_CHISEL)
                .forceAddTag(ModTags.Blocks.INCORRECT_FOR_STONE_CHISEL);



        this.getOrCreateTagBuilder(ModTags.Blocks.STONE_ORES)
                .add(Blocks.COAL_ORE)
                .add(Blocks.IRON_ORE)
                .add(Blocks.COPPER_ORE)
                .add(Blocks.REDSTONE_ORE)
                .add(Blocks.LAPIS_ORE)
                .add(Blocks.GOLD_ORE)
                .add(Blocks.DIAMOND_ORE)
                .add(Blocks.EMERALD_ORE);

        this.getOrCreateTagBuilder(ModTags.Blocks.DEEPSLATE_ORES)
                .add(Blocks.DEEPSLATE_COAL_ORE)
                .add(Blocks.DEEPSLATE_IRON_ORE)
                .add(Blocks.DEEPSLATE_COPPER_ORE)
                .add(Blocks.DEEPSLATE_REDSTONE_ORE)
                .add(Blocks.DEEPSLATE_LAPIS_ORE)
                .add(Blocks.DEEPSLATE_GOLD_ORE)
                .add(Blocks.DEEPSLATE_DIAMOND_ORE)
                .add(Blocks.DEEPSLATE_EMERALD_ORE);

        this.getOrCreateTagBuilder(ModTags.Blocks.ORES_LVL1)
                .forceAddTag(BlockTags.COAL_ORES)
                .forceAddTag(BlockTags.IRON_ORES)
                .forceAddTag(BlockTags.COPPER_ORES);

        this.getOrCreateTagBuilder(ModTags.Blocks.ORES_LVL2)
                .forceAddTag(BlockTags.REDSTONE_ORES)
                .forceAddTag(BlockTags.GOLD_ORES)
                .forceAddTag(BlockTags.LAPIS_ORES);

        this.getOrCreateTagBuilder(ModTags.Blocks.ORES_LVL3)
                .forceAddTag(BlockTags.DIAMOND_ORES)
                .forceAddTag(BlockTags.EMERALD_ORES);

        this.getOrCreateTagBuilder(ModTags.Blocks.ORES_LVL4)
                .add(Blocks.ANCIENT_DEBRIS);

        this.getOrCreateTagBuilder(ModTags.Blocks.CONVERTED_STONE_BLOCKS)
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

        this.getOrCreateTagBuilder(ModTags.Blocks.LOOSE_STONE_BLOCKS)
                .add(ModBlocks.COBBLESTONE_LOOSE)
                .add(ModBlocks.COBBLED_DEEPSLATE_LOOSE)
                .add(ModBlocks.ANDESITE_LOOSE)
                .add(ModBlocks.GRANITE_LOOSE)
                .add(ModBlocks.DIORITE_LOOSE)
                .add(ModBlocks.BRICKS_LOOSE)
                .add(ModBlocks.STONE_BRICKS_LOOSE)
                .add(ModBlocks.DEEPSLATE_BRICKS_LOOSE)
                .add(ModBlocks.NETHER_BRICKS_LOOSE)

                .add(ModBlocks.CALCITE_LOOSE)
                .add(ModBlocks.TUFF_LOOSE)
                .add(ModBlocks.BLACKSTONE_LOOSE)
                .add(ModBlocks.BASALT_LOOSE)
                .add(ModBlocks.END_STONE_LOOSE);

        this.getOrCreateTagBuilder(ModTags.Blocks.LOOSE_STONE_SLABS)
                .add(ModBlocks.SLAB_COBBLESTONE_LOOSE)
                .add(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE)
                .add(ModBlocks.SLAB_ANDESITE_LOOSE)
                .add(ModBlocks.SLAB_GRANITE_LOOSE)
                .add(ModBlocks.SLAB_DIORITE_LOOSE)
                .add(ModBlocks.SLAB_BRICKS_LOOSE)
                .add(ModBlocks.SLAB_STONE_BRICKS_LOOSE)
                .add(ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE)
                .add(ModBlocks.SLAB_NETHER_BRICKS_LOOSE);

        this.getOrCreateTagBuilder(ModTags.Blocks.LOOSE_STONE_STAIRS)
                .add(ModBlocks.COBBLESTONE_LOOSE_STAIRS)
                .add(ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS)
                .add(ModBlocks.ANDESITE_LOOSE_STAIRS)
                .add(ModBlocks.GRANITE_LOOSE_STAIRS)
                .add(ModBlocks.DIORITE_LOOSE_STAIRS)
                .add(ModBlocks.BRICKS_LOOSE_STAIRS)
                .add(ModBlocks.STONE_BRICKS_LOOSE_STAIRS)
                .add(ModBlocks.DEEPSLATE_BRICKS_LOOSE_STAIRS)
                .add(ModBlocks.NETHER_BRICKS_LOOSE_STAIRS);

        this.getOrCreateTagBuilder(ModTags.Blocks.MORTARED_BLOCKS)
                .add(Blocks.COBBLESTONE)
                .add(Blocks.COBBLED_DEEPSLATE)
                .add(Blocks.GRANITE)
                .add(Blocks.ANDESITE)
                .add(Blocks.DIORITE)
                .add(Blocks.BRICKS)
                // the bottom block on a BWT kiln structure turns to kiln block, but looks like bricks
                .addOptional(Identifier.of("bwt", "kiln"))
                .add(Blocks.COBBLESTONE_SLAB)
                .add(Blocks.COBBLED_DEEPSLATE_SLAB)
                .add(Blocks.ANDESITE_SLAB)
                .add(Blocks.GRANITE_SLAB);

        this.getOrCreateTagBuilder(ModTags.Blocks.BROKEN_STONE_BLOCKS)
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

        this.getOrCreateTagBuilder(ModTags.Blocks.STONE_CONVERTING_STRATA1)
                .add(ModBlocks.STONE_CONVERTING)
                .add(ModBlocks.GRANITE_CONVERTING)
                .add(ModBlocks.ANDESITE_CONVERTING)
                .add(ModBlocks.DIORITE_CONVERTING)
                .add(ModBlocks.CALCITE_CONVERTING);

        this.getOrCreateTagBuilder(ModTags.Blocks.STONE_CONVERTING_STRATA2)
                .add(ModBlocks.BASALT_CONVERTING);

        this.getOrCreateTagBuilder(ModTags.Blocks.STONE_CONVERTING_STRATA3)
                .add(ModBlocks.BLACKSTONE_CONVERTING)
                .add(ModBlocks.END_STONE_CONVERTING)
                .add(ModBlocks.DEEPSLATE_CONVERTING);

        this.getOrCreateTagBuilder(ModTags.Blocks.MISC_REQUIRING_TOOL)
                .add(Blocks.SNOW);

        this.getOrCreateTagBuilder(ModTags.Blocks.VANILLA_DIRT_BLOCKS)
                .add(Blocks.DIRT)
                .add(Blocks.COARSE_DIRT)
                .add(Blocks.DIRT_PATH)
                .add(Blocks.MYCELIUM);

        this.getOrCreateTagBuilder(ModTags.Blocks.SIMPLE_DIRT_BLOCKS)
                .add(Blocks.DIRT)
                .add(ModBlocks.DIRT_LOOSE);

        //this.getOrCreateTagBuilder(BTWRConventionalTags.Blocks.CAN_FALL_IN_OVERWORLD)
        //.add(Blocks.NETHERRACK)
        ;

    }

}
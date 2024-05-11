package org.tough_environment.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.block.blocks.*;

public class ModBlocks
{

    public static final Block SLAB_SAND = registerBlock("slab_sand", new LooseAgregateSlabBlock(FabricBlockSettings.create().strength(0.5f).sounds(BlockSoundGroup.SAND).nonOpaque()));
    public static final Block SLAB_RED_SAND = registerBlock("slab_red_sand", new LooseAgregateSlabBlock(FabricBlockSettings.create().strength(0.5f).sounds(BlockSoundGroup.SAND).nonOpaque()));
    public static final Block SLAB_DIRT = registerBlock("slab_dirt", new LooseAgregateSlabBlock(FabricBlockSettings.create().strength(0.5f).sounds(BlockSoundGroup.GRASS).nonOpaque()));
    public static final Block SLAB_GRAVEL = registerBlock("slab_gravel", new LooseAgregateSlabBlock(FabricBlockSettings.create().strength(0.5f).sounds(BlockSoundGroup.SAND).nonOpaque()));
    public static final Block SLAB_COBBLESTONE_LOOSE = registerBlock("slab_cobblestone_loose", new LooseSlabBlock(FabricBlockSettings.create().strength(0.8f).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block SLAB_COBBLED_DEEPSLATE_LOOSE = registerBlock("slab_cobbled_deepslate_loose", new LooseSlabBlock(FabricBlockSettings.create().strength(1.1F).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block SLAB_GRANITE_LOOSE = registerBlock("slab_granite_loose", new LooseSlabBlock(FabricBlockSettings.create().strength(0.8f).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block SLAB_ANDESITE_LOOSE = registerBlock("slab_andesite_loose", new LooseSlabBlock(FabricBlockSettings.create().strength(0.8f).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block SLAB_DIORITE_LOOSE = registerBlock("slab_diorite_loose", new LooseSlabBlock(FabricBlockSettings.create().strength(0.8f).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block SLAB_BRICK_LOOSE = registerBlock("slab_brick_loose", new LooseSlabBlock(FabricBlockSettings.create().strength(0.5f).sounds(BlockSoundGroup.STONE).nonOpaque()));


    public static final Block DIRT_LOOSE = registerBlock("dirt_loose", new LooseAgregateBlock(FabricBlockSettings.create().strength(0.5f).sounds(BlockSoundGroup.GRAVEL)));
    public static final Block COBBLESTONE_LOOSE = registerBlock("cobblestone_loose", new LooseBlock(FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));
    public static final Block COBBLED_DEEPSLATE_LOOSE = registerBlock("cobbled_deepslate_loose", new LooseBlock(FabricBlockSettings.create().strength(1.1F).sounds(BlockSoundGroup.DEEPSLATE)));
    public static final Block GRANITE_LOOSE = registerBlock("granite_loose", new LooseBlock(FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));
    public static final Block ANDESITE_LOOSE = registerBlock("andesite_loose", new LooseBlock(FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));
    public static final Block DIORITE_LOOSE = registerBlock("diorite_loose", new LooseBlock(FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));

    public static final Block CALCITE_LOOSE = registerBlock("calcite_loose", new LooseBlock(FabricBlockSettings.create().strength(0.75f).requiresTool().sounds(BlockSoundGroup.CALCITE)));
    public static final Block TUFF_LOOSE = registerBlock("tuff_loose", new LooseBlock(FabricBlockSettings.create().strength(0.8F).requiresTool().sounds(BlockSoundGroup.TUFF)));
    public static final Block BLACKSTONE_LOOSE = registerBlock("blackstone_loose", new LooseBlock(FabricBlockSettings.create().strength(1.0F).sounds(BlockSoundGroup.STONE)));
    public static final Block BASALT_LOOSE = registerBlock("basalt_loose", new LoosePillarBlock(FabricBlockSettings.create().strength(1.0F).sounds(BlockSoundGroup.BASALT)));
    public static final Block END_STONE_LOOSE = registerBlock("end_stone_loose", new LooseBlock(FabricBlockSettings.create().strength(1.0F).sounds(BlockSoundGroup.STONE)));

    public static final Block BRICKS_LOOSE = registerBlock("bricks_loose", new LooseBlock(FabricBlockSettings.create().strength(0.7F).sounds(BlockSoundGroup.STONE).nonOpaque()));

    public static final Block STONE_CONVERTING = registerBlock("stone_converting", new StoneConvertingBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block GRANITE_CONVERTING = registerBlock("granite_converting", new StoneConvertingBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DIORITE_CONVERTING = registerBlock("diorite_converting", new StoneConvertingBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block ANDESITE_CONVERTING = registerBlock("andesite_converting", new StoneConvertingBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block CALCITE_CONVERTING = registerBlock("calcite_converting", new StoneConvertingBlock(FabricBlockSettings.create().strength(0.75f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block TUFF_CONVERTING = registerBlock("tuff_converting", new StoneConvertingBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BLACKSTONE_CONVERTING = registerBlock("blackstone_converting", new StoneConvertingBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DEEPSLATE_CONVERTING = registerBlock("deepslate_converting", new StoneConvertingBlock(FabricBlockSettings.create().strength(3.0f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BASALT_CONVERTING = registerBlock("basalt_converting", new StoneConvertingBlock(FabricBlockSettings.create().strength(1.25f, 4.2f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block END_STONE_CONVERTING = registerBlock("end_stone_converting", new StoneConvertingBlock(FabricBlockSettings.create().strength(3f, 9f).requiresTool().sounds(BlockSoundGroup.STONE)));

    public static final Block COBBLESTONE_LOOSE_STAIRS = registerBlock("cobblestone_loose_stairs", new LooseStairsBlock(ModBlocks.COBBLESTONE_LOOSE.getDefaultState(), FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));
    public static final Block COBBLED_DEEPSLATE_LOOSE_STAIRS = registerBlock("cobbled_deepslate_loose_stairs", new LooseStairsBlock(ModBlocks.COBBLED_DEEPSLATE_LOOSE.getDefaultState(), FabricBlockSettings.create().strength(1.0F).sounds(BlockSoundGroup.DEEPSLATE)));
    public static final Block ANDESITE_LOOSE_STAIRS = registerBlock("andesite_loose_stairs", new LooseStairsBlock(ModBlocks.ANDESITE_LOOSE.getDefaultState(), FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));
    public static final Block GRANITE_LOOSE_STAIRS = registerBlock("granite_loose_stairs", new LooseStairsBlock(ModBlocks.GRANITE_LOOSE.getDefaultState(), FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));
    public static final Block DIORITE_LOOSE_STAIRS = registerBlock("diorite_loose_stairs", new LooseStairsBlock(ModBlocks.DIORITE_LOOSE.getDefaultState(), FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));

    public static final Block COBBLESTONE_STAIRS = registerBlock("cobblestone_stairs", new StairsBlock(Blocks.COBBLESTONE.getDefaultState(), FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));
    public static final Block COBBLED_DEEPSLATE_STAIRS = registerBlock("cobbled_deepslate_stairs", new StairsBlock(Blocks.COBBLED_DEEPSLATE.getDefaultState(), FabricBlockSettings.create().strength(1.0F).sounds(BlockSoundGroup.DEEPSLATE)));
    public static final Block ANDESITE_STAIRS = registerBlock("andesite_stairs", new StairsBlock(Blocks.ANDESITE.getDefaultState(), FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));
    public static final Block GRANITE_STAIRS = registerBlock("granite_stairs", new StairsBlock(Blocks.GRANITE.getDefaultState(), FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));
    public static final Block DIORITE_STAIRS = registerBlock("diorite_stairs", new StairsBlock(Blocks.DIORITE.getDefaultState(), FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));


    /** Depleted(broken) blocks have exactly 10f more strength than their normal counterparts. **/

    public static final Block STONE_BROKEN = registerBlock("stone_broken", new DepletedStoneBlock(FabricBlockSettings.create().strength(11.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block GRANITE_BROKEN = registerBlock("granite_broken", new DepletedStoneBlock(FabricBlockSettings.create().strength(11.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DIORITE_BROKEN = registerBlock("diorite_broken", new DepletedStoneBlock(FabricBlockSettings.create().strength(11.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block ANDESITE_BROKEN = registerBlock("andesite_broken", new DepletedStoneBlock(FabricBlockSettings.create().strength(11.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block CALCITE_BROKEN = registerBlock("calcite_broken", new DepletedStoneBlock(FabricBlockSettings.create().strength(10.75f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block TUFF_BROKEN = registerBlock("tuff_broken", new DepletedStoneBlock(FabricBlockSettings.create().strength(11.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BLACKSTONE_BROKEN = registerBlock("blackstone_broken", new DepletedStoneBlock(FabricBlockSettings.create().strength(11.5f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DEEPSLATE_BROKEN = registerBlock("deepslate_broken", new DepletedStoneBlock(FabricBlockSettings.create().strength(13.0f, 6.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BASALT_BROKEN = registerBlock("basalt_broken", new DepletedStoneBlock(FabricBlockSettings.create().strength(11.25f, 4.2f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block ENDSTONE_BROKEN = registerBlock("endstone_broken", new DepletedStoneBlock(FabricBlockSettings.create().strength(13f, 9f).requiresTool().sounds(BlockSoundGroup.STONE)));


    private static Block registerBlock(String name, Block block)
    {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ToughEnvironmentMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block)
    {
        return Registry.register(Registries.ITEM, new Identifier(ToughEnvironmentMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks()
    {
        ToughEnvironmentMod.LOGGER.debug("Registering ModBlocks for " + ToughEnvironmentMod.MOD_ID);
    }

}

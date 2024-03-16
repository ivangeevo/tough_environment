package org.tough_environment.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
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

    public static final Block SLAB_SAND = registerBlock("slab_sand", new LooseSlabBlock(FabricBlockSettings.create().strength(3.1f).sounds(BlockSoundGroup.SAND).nonOpaque()));
    public static final Block SLAB_RED_SAND = registerBlock("slab_red_sand", new LooseSlabBlock(FabricBlockSettings.create().strength(3.2f).sounds(BlockSoundGroup.SAND).nonOpaque()));
    public static final Block SLAB_DIRT = registerBlock("slab_dirt", new LooseSlabBlock(FabricBlockSettings.create().strength(3.4f).sounds(BlockSoundGroup.GRASS).nonOpaque()));
    public static final Block SLAB_GRAVEL = registerBlock("slab_gravel", new LooseSlabBlock(FabricBlockSettings.create().strength(4.2f).sounds(BlockSoundGroup.SAND).nonOpaque()));
    public static final Block SLAB_COBBLESTONE_LOOSE = registerBlock("slab_cobblestone_loose", new LooseSlabBlock(FabricBlockSettings.create().strength(4f).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block SLAB_COBBLED_DEEPSLATE_LOOSE = registerBlock("slab_cobbled_deepslate_loose", new LooseSlabBlock(FabricBlockSettings.create().strength(4.1f).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block SLAB_BRICK_LOOSE = registerBlock("slab_brick_loose", new LooseSlabBlock(FabricBlockSettings.create().strength(4.1f).sounds(BlockSoundGroup.STONE).nonOpaque()));


    public static final Block SLAB_GRANITE_LOOSE = registerBlock("slab_granite_loose", new LooseSlabBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).sounds(BlockSoundGroup.SAND).nonOpaque()));
    public static final Block SLAB_ANDESITE_LOOSE = registerBlock("slab_andesite_loose", new LooseSlabBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).sounds(BlockSoundGroup.SAND).nonOpaque()));



    //public static final Block COBBLESTONE_LOOSE_STAIRS = Blocks.register("cobblestone_loose_stairs", new LooseStairsBlock(ModBlocks.COBBLESTONE_LOOSE.getDefaultState(), FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));


    public static final Block DIRT_LOOSE = registerBlock("dirt_loose", new LooseBlock(FabricBlockSettings.create().strength(0.5f).sounds(BlockSoundGroup.GRAVEL)));
    public static final Block COBBLESTONE_LOOSE = registerBlock("cobblestone_loose", new LooseBlock(FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));
    public static final Block COBBLED_DEEPSLATE_LOOSE = registerBlock("cobbled_deepslate_loose", new LooseBlock(FabricBlockSettings.create().strength(1.1F).sounds(BlockSoundGroup.STONE)));
    public static final Block GRANITE_LOOSE = registerBlock("granite_loose", new LooseBlock(FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));
    public static final Block ANDESITE_LOOSE = registerBlock("andesite_loose", new LooseBlock(FabricBlockSettings.create().strength(0.8F).sounds(BlockSoundGroup.STONE)));


    public static final Block STONE_CONVERTING = registerBlock("stone_converting", new ToughStoneBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block GRANITE_CONVERTING = registerBlock("granite_converting", new ToughStoneBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block DIORITE_CONVERTING = registerBlock("diorite_converting", new ToughStoneBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block ANDESITE_CONVERTING = registerBlock("andesite_converting", new ToughStoneBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block CALCITE_CONVERTING = registerBlock("calcite_converting", new ToughStoneBlock(FabricBlockSettings.create().strength(0.75f).sounds(BlockSoundGroup.STONE)));
    public static final Block TUFF_CONVERTING = registerBlock("tuff_converting", new ToughStoneBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block BLACKSTONE_CONVERTING = registerBlock("blackstone_converting", new ToughStoneBlock(FabricBlockSettings.create().strength(1.5f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block DEEPSLATE_CONVERTING = registerBlock("deepslate_converting", new ToughStoneBlock(FabricBlockSettings.create().strength(3.0f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block BASALT_CONVERTING = registerBlock("basalt_converting", new ToughStoneBlock(FabricBlockSettings.create().strength(1.25f, 4.2f).sounds(BlockSoundGroup.STONE)));
    public static final Block ENDSTONE_CONVERTING = registerBlock("endstone_converting", new ToughStoneBlock(FabricBlockSettings.create().strength(3f, 9f).sounds(BlockSoundGroup.STONE)));


    /** Broken* blocks have exactly 10f more strength than their normal counterparts. **/

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
        Item item = Registry.register(Registries.ITEM, new Identifier(ToughEnvironmentMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        return item;
    }

    public static void registerModBlocks()
    {
        ToughEnvironmentMod.LOGGER.debug("Registering ModBlocks for " + ToughEnvironmentMod.MOD_ID);
    }

}

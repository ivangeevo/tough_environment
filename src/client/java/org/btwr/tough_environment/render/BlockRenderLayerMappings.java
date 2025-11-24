package org.btwr.tough_environment.render;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import org.btwr.tough_environment.block.ModBlocks;

public class BlockRenderLayerMappings {

    public static void register() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_DIRT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_GRAVEL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_SAND, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_RED_SAND, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_DIRT_PACKED, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_COBBLESTONE_LOOSE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_GRANITE_LOOSE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_ANDESITE_LOOSE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_DIORITE_LOOSE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_BRICKS_LOOSE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_STONE_BRICKS_LOOSE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COBBLESTONE_LOOSE_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ANDESITE_LOOSE_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRANITE_LOOSE_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DIORITE_LOOSE_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STONE_BRICKS_LOOSE_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEEPSLATE_BRICKS_LOOSE_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.NETHER_BRICK_UNFIRED, RenderLayer.getCutout());

    }
}

package org.tough_environment.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import org.tough_environment.block.ModBlocks;

public class ToughEnvironmentClient implements ClientModInitializer
{


    @Override
    public void onInitializeClient()
    {

        // Modifies a block model with another one to always look different
        /**
        ModelLoadingPlugin.register(pluginContext -> {
            pluginContext.modifyModelAfterBake().register(ModelModifier.OVERRIDE_PHASE, (bakedModel, context) -> {
                Identifier id = context.resourceId();
                if (id != null && id.toString().contains("stone")) {
                    return new Strata2StoneModel(bakedModel);
                }
                return bakedModel;
            });
        });
         **/


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

package org.btwr.tough_environment.model;

import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.material.ShadeMode;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

import java.util.function.Supplier;

public class Strata3StoneModel extends ForwardingBakedModel {

    private static final Identifier DEEPSLATE_TEXTURE = Identifier.ofVanilla("block/deepslate");
    private static final Identifier HARDENED_STRATA_3_TEXTURE = Identifier.of("tough_environment", "block/stone_strata_3");

    private static final RenderMaterial STANDARD_MATERIAL = RendererAccess.INSTANCE.getRenderer().materialFinder().shadeMode(ShadeMode.VANILLA).find();

    public Strata3StoneModel(BakedModel model) {
        wrapped = model;
    }

    @Override
    public void emitBlockQuads(BlockRenderView world, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        if (!(state.getBlock() == Blocks.DEEPSLATE)) return;

        if (RendererAccess.INSTANCE.getRenderer() == null || world == null || pos == null) {
            super.emitBlockQuads(world, state, pos, randomSupplier, context);
            return;
        }

        for (int i = 0; i <= ModelHelper.NULL_FACE_ID; i++) {
            Direction face = ModelHelper.faceFromIndex(i);


            for (BakedQuad quad : this.getQuads(state, face, randomSupplier.get())) {
                QuadEmitter emitter = context.getEmitter();
                emitter.fromVanilla(quad, STANDARD_MATERIAL, face);

                var atlas = MinecraftClient.getInstance().getSpriteAtlas(Identifier.of("textures/atlas/blocks.png"));

                Sprite original = quad.getSprite();
                if (original.getContents().getId().equals(DEEPSLATE_TEXTURE)) {
                    emitter.spriteBake(atlas.apply(HARDENED_STRATA_3_TEXTURE), MutableQuadView.BAKE_LOCK_UV);
                }

                emitter.emit();
            }
        }
    }
}

package org.tough_environment.client;

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

public class Strata2StoneModel extends ForwardingBakedModel {

    public static final Identifier STONE_TEXTURE = Identifier.ofVanilla("block/stone");
    private static final Identifier STONE_STRATA_2_TEXTURE = Identifier.of("tough_environment", "block/stone_strata_2");

    private static final RenderMaterial STANDARD_MATERIAL = RendererAccess.INSTANCE.getRenderer().materialFinder().shadeMode(ShadeMode.VANILLA).find();

    public Strata2StoneModel(BakedModel model) {
        wrapped = model;
    }

    @Override
    public void emitBlockQuads(BlockRenderView world, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        if (state.getBlock() == Blocks.STONE) {
            QuadEmitter emitter = context.getEmitter();

            var atlas = MinecraftClient.getInstance().getSpriteAtlas(Identifier.of("textures/atlas/blocks.png"));
            boolean useStrata = pos.getY() <= 32;

            for (int i = 0; i <= ModelHelper.NULL_FACE_ID; i++) {
                final Direction cullFace = ModelHelper.faceFromIndex(i);

                for (BakedQuad q : this.getQuads(state, cullFace, randomSupplier.get())) {
                    emitter.fromVanilla(q, STANDARD_MATERIAL, cullFace);
                    if (useStrata && q.getSprite().getContents().getId().equals(Identifier.ofVanilla("block/stone"))) {
                        emitter.spriteBake(atlas.apply(STONE_STRATA_2_TEXTURE), MutableQuadView.BAKE_LOCK_UV);
                    }
                    emitter.emit();
                }
            }
        } else {
            super.emitBlockQuads(world, state, pos, randomSupplier, context);
        }
    }
}

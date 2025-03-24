package org.tough_environment.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.tough_environment.block.ModBlocks;

@Mixin(ProtoChunk.class)
public abstract class ProtoChunkMixin {

    @Redirect(method = "setBlockState", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/chunk/ChunkSection;setBlockState(IIILnet/minecraft/block/BlockState;)Lnet/minecraft/block/BlockState;")
    )
    private BlockState replaceBlockState(ChunkSection chunkSection, int x, int y, int z, BlockState state) {
        if (state.isOf(Blocks.CLAY)) {
            state = ModBlocks.CLAY_ORE.getDefaultState();
        }
        return chunkSection.setBlockState(x, y, z, state);
    }
}


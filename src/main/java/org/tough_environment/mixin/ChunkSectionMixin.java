package org.tough_environment.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.tough_environment.util.BlockStateReplacer;

@Mixin(ChunkSection.class)
public abstract class ChunkSectionMixin implements BlockStateReplacer {

    @Unique
    @Nullable
    private Int2IntMap replaceMap;

    @Unique
    private boolean isWorldGeneration;

    /**
     * Sets the world generation flag. You can modify this logic based on how your world generation system works.
     * Set this flag to true during world generation and false afterward.
     */
    public void setWorldGeneration(boolean isWorldGeneration) {
        this.isWorldGeneration = isWorldGeneration;
    }

    @WrapMethod(method = "setBlockState(IIILnet/minecraft/block/BlockState;Z)Lnet/minecraft/block/BlockState;")
    private BlockState onSetBlockState(int x, int y, int z, BlockState originalState, boolean isMoving, Operation<BlockState> originalMethod) {
        // If the world is still in generation phase, don't perform replacements
        if (isWorldGeneration || replaceMap == null) {
            return originalMethod.call(x, y, z, originalState, isMoving);
        }

        int originalId = Block.STATE_IDS.getRawIdOrThrow(originalState);
        int swappedId = replaceMap.getOrDefault(originalId, -1);

        if (swappedId == -1) {
            return originalMethod.call(x, y, z, originalState, isMoving);
        }

        BlockState swappedState = Block.STATE_IDS.getOrThrow(swappedId);
        return originalMethod.call(x, y, z, swappedState, isMoving);
    }

    @Override
    public void setReplaceMap(Int2IntMap replaceMap) {
        this.replaceMap = replaceMap;
    }
}

package org.tough_environment.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.tag.ModTags;

public class MakeAsFallingBlock {
    private static final MakeAsFallingBlock instance = new MakeAsFallingBlock();

    private MakeAsFallingBlock() {}

    public static MakeAsFallingBlock getInstance() {
        return instance;
    }

    public void onOnBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        Block block = world.getBlockState(pos).getBlock();

        if (state.isIn(ModTags.Blocks.TURNED_TO_FALLING_BLOCKS) && canFallInCurrentDimension(world, state)) {
            world.scheduleBlockTick(pos, block, this.getFallDelay());
        }
    }

    public void onRandomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.isIn(ModTags.Blocks.TURNED_TO_FALLING_BLOCKS) && canFallInCurrentDimension(world, state)) {
            if (random.nextInt(16) == 0 && FallingBlock.canFallThrough(world.getBlockState(pos.down()))) {
                ParticleUtil.spawnParticle(world, pos, random, new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, state));
            }
        }
    }

    public void onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.isIn(ModTags.Blocks.TURNED_TO_FALLING_BLOCKS) && canFallInCurrentDimension(world, state)) {
            if (!FallingBlock.canFallThrough(world.getBlockState(pos.down())) || pos.getY() < world.getBottomY()) {
                return;
            }

            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
            this.configureFallingBlockEntity(fallingBlockEntity);
        }
    }

    public void onGetStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if (state.isIn(ModTags.Blocks.TURNED_TO_FALLING_BLOCKS) && MakeAsFallingBlock.canFallInCurrentDimension((World) world, state))
        {
            world.scheduleBlockTick(pos, state.getBlock(), 2);
            cir.setReturnValue(state);
        }
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity) {}

    public int getFallDelay() {
        return 2;
    }

    /**
     * Determines if the block can fall in the current dimension based on tags.
     *
     * @param world the current world
     * @param state the block state
     * @return true if the block can fall in the current dimension, false otherwise
     */
    public static boolean canFallInCurrentDimension(World world, BlockState state) {
        RegistryEntry<DimensionType> dimensionType = world.getDimensionEntry();
        if (dimensionType.matchesId(DimensionTypes.OVERWORLD_ID)) {
            return state.isIn(ModTags.Blocks.CAN_FALL_IN_OVERWORLD);
        } else if (dimensionType.matchesId(DimensionTypes.THE_NETHER_ID)) {
            return state.isIn(ModTags.Blocks.CAN_FALL_IN_THE_NETHER);
        } else if (dimensionType.matchesId(DimensionTypes.THE_END_ID)) {
            return state.isIn(ModTags.Blocks.CAN_FALL_IN_THE_END);
        }
        return false;
    }

}

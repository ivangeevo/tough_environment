package org.tough_environment.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.tough_environment.tag.ModTags;

public class MakeAsFallingBlock {
    private static final MakeAsFallingBlock instance = new MakeAsFallingBlock();

    private MakeAsFallingBlock() {}

    public static MakeAsFallingBlock getInstance() {
        return instance;
    }

    public void onOnBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        Block block = world.getBlockState(pos).getBlock();

        if (state.isIn(ModTags.Blocks.TURNED_TO_FALLING_BLOCKS)) {
            world.scheduleBlockTick(pos, block, this.getFallDelay());
        }
    }

    public void onRandomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.isIn(ModTags.Blocks.TURNED_TO_FALLING_BLOCKS)) {
            if (random.nextInt(16) == 0 && FallingBlock.canFallThrough(world.getBlockState(pos.down()))) {
                ParticleUtil.spawnParticle(world, pos, random, new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, state));
            }
        }
    }

    public void onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.isIn(ModTags.Blocks.TURNED_TO_FALLING_BLOCKS)) {
            if (!FallingBlock.canFallThrough(world.getBlockState(pos.down())) || pos.getY() < world.getBottomY()) {
                return;
            }

            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
            this.configureFallingBlockEntity(fallingBlockEntity);
        }
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity) {}

    public int getFallDelay() {
        return 2;
    }

}

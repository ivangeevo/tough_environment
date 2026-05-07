package org.btwr.tough_environment.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.btwr.tough_environment.tag.ModTags;

public class LavaLoggedBlock extends FallingBlock {

    public static final MapCodec<LavaLoggedBlock> CODEC = Block.createCodec(LavaLoggedBlock::new);

    public LavaLoggedBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.hasRain(pos.up())) {
            world.playSound(
                    null,
                    pos,
                    SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS,
                    0.5f,
                    2.6F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.8F
            );
            if (state.isIn(ModTags.Blocks.LAVA_LOGGED_STONE)) {
                world.setBlockState(pos, Blocks.STONE.getDefaultState());
                return;
            }
            if (state.isIn(ModTags.Blocks.LAVA_LOGGED_DEEPSLATE)) {
                world.setBlockState(pos, Blocks.DEEPSLATE.getDefaultState());
            }
        }
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            // Water over lava-filled block -> cool into stone/deepslate
            if (hasWaterAbove(world, pos)) {
                if (state.isIn(ModTags.Blocks.LAVA_LOGGED_STONE)) {
                    world.setBlockState(pos, Blocks.STONE.getDefaultState());
                }
                if (state.isIn(ModTags.Blocks.LAVA_LOGGED_DEEPSLATE)) {
                    world.setBlockState(pos, Blocks.DEEPSLATE.getDefaultState());
                }
                world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);
            }
        }
    }

    private boolean hasWaterAbove(World world, BlockPos pos) {
        FluidState fluidAbove = world.getFluidState(pos.offset(Direction.UP));
        return fluidAbove.isOf(Fluids.WATER) || fluidAbove.isOf(Fluids.FLOWING_WATER);
    }
}

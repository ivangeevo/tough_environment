package org.tough_environment.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class UnfiredNetherBrickBlock extends Block  {

    public static final float BRICK_HEIGHT = (4F / 16F );
    public static final float BRICK_WIDTH = (6F / 16F );
    public static final float BRICK_HALF_WIDTH = (BRICK_WIDTH / 2F );
    public static final float BRICK_LENGTH = (12F / 16F );
    public static final float BRICK_HALF_LENGTH = (BRICK_LENGTH / 2F );
    protected static final VoxelShape SHAPE_Z_AXIS = VoxelShapes.cuboid((0.5F - BRICK_HALF_LENGTH), 0D, (0.5F - BRICK_HALF_WIDTH), (0.5F + BRICK_HALF_LENGTH), BRICK_HEIGHT, (0.5F + BRICK_HALF_WIDTH));
    protected static final VoxelShape SHAPE_X_AXIS = VoxelShapes.cuboid((0.5F - BRICK_HALF_WIDTH), 0D, (0.5F - BRICK_HALF_LENGTH), (0.5F + BRICK_HALF_WIDTH), BRICK_HEIGHT, (0.5F + BRICK_HALF_LENGTH));

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public static final MapCodec<UnfiredNetherBrickBlock> CODEC = Block.createCodec(UnfiredNetherBrickBlock::new);

    @Override
    protected MapCodec<? extends UnfiredNetherBrickBlock> getCodec() {
        return CODEC;
    }

    public UnfiredNetherBrickBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(FACING).getAxis() == Direction.Axis.Z ? SHAPE_Z_AXIS : SHAPE_X_AXIS;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            world.playSound(null, pos, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.BLOCKS, ( 0.5F + 1.0F ) / 2.0F, 0.1F * 0.8F );
            world.addBlockBreakParticles(pos, state);
            Block.dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }

        world.playSound(null, pos, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.BLOCKS, ( 0.5F + 1.0F ) / 2.0F, 0.1F * 0.8F );
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify)
    {
        if (!world.getBlockState(pos.down()).isSolidBlock(world, pos.down())) {
            //dropBlockAsItem(world, pos);
            Block.dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }

        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    @Override public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolidBlock(world, pos.down());
    }

    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}
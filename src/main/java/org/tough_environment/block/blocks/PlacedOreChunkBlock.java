package org.tough_environment.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;

import java.util.Objects;

public class PlacedOreChunkBlock extends Block
{

    // Define constants for ore dimensions
    private static final double ORE_WIDTH_MIN = 6.0 / 16.0;
    private static final double ORE_WIDTH_MAX = 10.0 / 16.0;
    private static final double ORE_HEIGHT_MIN = 0.5 / 16.0;
    private static final double ORE_HEIGHT_MAX = 3.5 / 16.0;
    private static final double ORE_LENGTH_MIN = 6.0 / 16.0;
    private static final double ORE_LENGTH_MAX = 10.0 / 16.0;

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public PlacedOreChunkBlock(Settings settings)
    {
        super(settings);
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add( FACING );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        return VoxelShapes.cuboid(ORE_WIDTH_MIN, ORE_HEIGHT_MIN, ORE_LENGTH_MIN, ORE_WIDTH_MAX, ORE_HEIGHT_MAX, ORE_LENGTH_MAX);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify)
    {
        if (!world.getBlockState(pos.down()).isSolidBlock(world, pos.down())) {
            dropBlockAsItem(world, pos);
            world.removeBlock(pos, false);
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }


    @Override public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    //TODO: make not placeable on top of itself, and only placeable from top side of a block.
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
    {
        BlockState belowBlockState = world.getBlockState(pos.down());
        // Ensure the block is only placeable on top of a solid block and not on top of itself
        return belowBlockState.isSolidBlock(world, pos.down()) && !(belowBlockState.getBlock() instanceof PlacedOreChunkBlock);
    }
    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    private void dropBlockAsItem(World world, BlockPos pos)
    {
        BlockState stateAtPos = world.getBlockState(pos);
        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), Objects.requireNonNull(getDropItemForBlock(stateAtPos)));
    }

    private ItemStack getDropItemForBlock(BlockState state)
    {

        if (state.isOf(ModBlocks.RAW_COPPER_PLACED))
        {
            return Items.RAW_COPPER.getDefaultStack();
        }

        if (state.isOf(ModBlocks.RAW_IRON_PLACED))
        {
            return Items.RAW_IRON.getDefaultStack();
        }

        if (state.isOf(ModBlocks.RAW_GOLD_PLACED))
        {
            return Items.RAW_GOLD.getDefaultStack();
        }

        return null;
    }

}

package org.tough_environment.block.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.block.interfaces.Mortarable;
import org.tough_environment.state.property.ModProperties;
import org.tough_environment.tag.ModTags;

import java.util.function.Predicate;

public class LooseSlabBlock extends FallingBlock implements Mortarable, LandingBlock, Waterloggable
{

    // Block parameters and constants & Super settings //
    public static final EnumProperty<SlabType> TYPE;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty HAS_MORTAR = ModProperties.HAS_MORTAR;

    protected static final VoxelShape BOTTOM_SHAPE;
    protected static final VoxelShape TOP_SHAPE;






    public LooseSlabBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(TYPE, SlabType.BOTTOM)
                .with(WATERLOGGED, false).with(HAS_MORTAR, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE, WATERLOGGED, HAS_MORTAR);
    }

    @Override
    public boolean hasMortarNow(BlockState state)
    {
        return state.get(HAS_MORTAR);
    }

    @Override
    public boolean isMortarable(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        return state.getBlock() instanceof LooseSlabBlock;
    }

    @Override
    public void mortarBlock(BlockState state, World world, BlockPos pos, PlayerEntity player) {


    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && player.getStackInHand(hand).getItem().getDefaultStack().isIn(ModTags.Items.MORTARING_ITEMS)  && isMortarable(state, world, pos, player)) {
            // Mortar the block
            mortarBlock(state, world, pos, player);

            // Optionally, reduce item stack size or perform other actions

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;    }

    public boolean hasSidedTransparency(BlockState state) {
        return state.get(TYPE) != SlabType.DOUBLE;
    }


    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        SlabType slabType = state.get(TYPE);

        if (slabType == SlabType.DOUBLE)
        {
            return VoxelShapes.fullCube();
        }
        else
        {
            return BOTTOM_SHAPE;
        }

    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        if (blockState.isOf(this))
        {
            return blockState.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, false);
        }
        else
        {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            BlockState blockState2 = this.getDefaultState().with(TYPE, SlabType.BOTTOM)
                    .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            Direction direction = ctx.getSide();
            return direction != Direction.DOWN && (direction == Direction.UP
                    || !(ctx.getHitPos().y - (double)blockPos.getY() > 0.5)) ?
                    blockState2 : blockState2.with(TYPE, SlabType.TOP);
        }
    }



    public boolean canReplace(BlockState state, ItemPlacementContext context)
    {
        ItemStack itemStack = context.getStack();
        SlabType slabType = state.get(TYPE);
        if (slabType != SlabType.DOUBLE && itemStack.isOf(this.asItem()))
        {
            if (context.canReplaceExisting())
            {
                boolean bl = context.getHitPos().y - (double)context.getBlockPos().getY() > 0.5;
                Direction direction = context.getSide();
                if (slabType == SlabType.BOTTOM)
                {
                    return direction == Direction.UP || bl && direction.getAxis().isHorizontal();
                }
                else
                {
                    return direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
                }
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState)
    {
        return state.get(TYPE) != SlabType.DOUBLE && Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
    }

    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid)
    {
        return state.get(TYPE) != SlabType.DOUBLE && Waterloggable.super.canFillWithFluid(world, pos, state, fluid);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos)
    {
        if (state.get(WATERLOGGED))
        {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type)
    {
        return switch (type)
        {
            case LAND -> false;
            case WATER -> world.getFluidState(pos).isIn(FluidTags.WATER);
            case AIR -> false;
            default -> false;
        };
    }



    static
    {
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        BOTTOM_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
        TOP_SHAPE = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);
    }




    // ---------------------------------- //
}
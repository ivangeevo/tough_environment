package org.tough_environment.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.tag.ModTags;

import java.util.Map;

public class LooseSlabBlock extends MortarReceiverBlock implements Waterloggable
{

    // Block parameters and constants & Super settings //
    public static final EnumProperty<SlabType> TYPE;
    public static final BooleanProperty WATERLOGGED;

    protected static final VoxelShape BOTTOM_SHAPE;
    protected static final VoxelShape TOP_SHAPE;



    public LooseSlabBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(TYPE, SlabType.BOTTOM)
                .with(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return null;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClient && entity instanceof PlayerEntity) {
            BlockPos downPos = pos.down();
            BlockState downState = world.getBlockState(downPos);
            if (downState.isOf(this) && downState.get(TYPE) == SlabType.BOTTOM) {
                world.setBlockState(downPos, downState.with(TYPE, SlabType.DOUBLE));
            }
        }
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit)
    {
        if (!world.isClient && player.getStackInHand(player.getActiveHand()).isIn(ModTags.Items.MORTARING_ITEMS))
        {
            // Mortar the block
            this.applyMortar(state.with(WATERLOGGED, state.get(WATERLOGGED)), world, pos, player);

            // Optionally, reduce item stack size or perform other actions
            ItemStack handStack = player.getStackInHand(player.getActiveHand());
            handStack.decrement(1);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }


    // Overriding the original method, because slabs need some extra logic, depending on the slab type placed.
    @Override
    public void applyMortar(BlockState state, World world, BlockPos pos, PlayerEntity player)
    {
        SlabType slabType = state.get(TYPE);
        BlockState newBlock = getReplacementBlockState(state.getBlock(), slabType, this.getDefaultState());

        if (newBlock != null)
        {
            BlockState newState = newBlock;

            // Check if TYPE property exists
            if (newState.getProperties().contains(TYPE))
            {
                newState = newState.with(TYPE, slabType);
            }

            world.setBlockState(pos, newState);
        }

        world.playSound(null,pos, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.BLOCKS);

    }


    private BlockState getReplacementBlockState(Block originalBlock, SlabType slabType, BlockState originalState) {
        // Mapping of slab blocks to their corresponding mortared Minecraft variant (top or bottom)
        Map<Block, Block> slabToSlabMortaredBlock = Map.of(
                ModBlocks.SLAB_COBBLESTONE_LOOSE, Blocks.COBBLESTONE_SLAB,
                ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, Blocks.COBBLED_DEEPSLATE_SLAB,
                ModBlocks.SLAB_ANDESITE_LOOSE, Blocks.ANDESITE_SLAB,
                ModBlocks.SLAB_GRANITE_LOOSE, Blocks.GRANITE_SLAB,
                ModBlocks.SLAB_DIORITE_LOOSE, Blocks.DIORITE_SLAB,
                ModBlocks.SLAB_BRICKS_LOOSE, Blocks.BRICK_SLAB
        );

        // Mapping of slab blocks to their corresponding full counterpart blocks for DOUBLE slabs
        Map<Block, Block> slabToFullMortaredBlock = Map.of(
                ModBlocks.SLAB_COBBLESTONE_LOOSE, Blocks.COBBLESTONE,
                ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, Blocks.COBBLED_DEEPSLATE,
                ModBlocks.SLAB_ANDESITE_LOOSE, Blocks.ANDESITE,
                ModBlocks.SLAB_GRANITE_LOOSE, Blocks.GRANITE,
                ModBlocks.SLAB_DIORITE_LOOSE, Blocks.DIORITE,
                ModBlocks.SLAB_BRICKS_LOOSE, Blocks.BRICKS
        );

        // Handle TOP slabs: Get the mortared variant and set SlabType.TOP if needed
        if (slabType == SlabType.TOP && slabToSlabMortaredBlock.containsKey(originalBlock)) {
            Block mortaredBlock = slabToSlabMortaredBlock.get(originalBlock);
            BlockState mortaredState = mortaredBlock.getDefaultState();

            // Ensure the replacement state has the SlabType.TOP property set
            if (mortaredState.contains(SlabBlock.TYPE)) {
                mortaredState = mortaredState.with(SlabBlock.TYPE, SlabType.TOP);
            }

            return mortaredState;
        }

        // Handle BOTTOM slabs: Get the mortared variant (for SlabType.BOTTOM)
        if (slabType == SlabType.BOTTOM && slabToSlabMortaredBlock.containsKey(originalBlock)) {
            Block mortaredBlock = slabToSlabMortaredBlock.get(originalBlock);
            BlockState mortaredState = mortaredBlock.getDefaultState();

            // Ensure the replacement state has the SlabType.BOTTOM property set
            if (mortaredState.contains(SlabBlock.TYPE)) {
                mortaredState = mortaredState.with(SlabBlock.TYPE, SlabType.BOTTOM);
            }

            return mortaredState;
        }

        // Handle DOUBLE slabs by mapping to the full block
        if (slabType == SlabType.DOUBLE && slabToFullMortaredBlock.containsKey(originalBlock)) {
            return slabToFullMortaredBlock.get(originalBlock).getDefaultState(); // Return the full block
        }

        // Return null if no match is found
        return null;
    }


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
        else if (slabType == SlabType.TOP)
        {
            return TOP_SHAPE;

        }

        return BOTTOM_SHAPE;

    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        Direction direction = ctx.getSide();

        if (blockState.isOf(this)) {
            // If the block is the same as the LooseSlabBlock, set it to double slab
            return this.getDefaultState().with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, false);
        }
        else
        {
            // Otherwise, handle placement based on the direction and hit position
            boolean isTopHalf = ctx.getHitPos().y - blockPos.getY() > 0.5;

            if (direction == Direction.DOWN || isTopHalf) {
                // If placing on the bottom part or top half, place as a bottom slab
                return this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
            else
            {
                // If placing on the top part, place as a bottom slab instead
                return this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            }
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

    public boolean canFillWithFluid(PlayerEntity player, BlockView world, BlockPos pos, BlockState state, Fluid fluid)
    {
        return state.get(TYPE) != SlabType.DOUBLE && Waterloggable.super.canFillWithFluid(player, world, pos, state, fluid);
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
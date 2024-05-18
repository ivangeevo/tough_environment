package org.tough_environment.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LandingBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.tough_environment.tag.ModTags;

public class LoosePillarBlock extends LooseBlock
{
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;


    // Block parameters and constants & Super settings //
    public LoosePillarBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        if (!world.isClient && player.getStackInHand(hand).isIn(ModTags.Items.MORTARING_ITEMS))
        {
            BlockState newState = world.getBlockState(pos).with(AXIS, state.get(AXIS));

            // Mortar the block
            this.applyMortar(newState, world, pos, player);

            // Reduce item stack size
            ItemStack handStack = player.getStackInHand(hand);
            handStack.decrement(1);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return LoosePillarBlock.changeRotation(state, rotation);
    }

    public static BlockState changeRotation(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.get(AXIS)) {
                case X -> (BlockState) state.with(AXIS, Direction.Axis.Z);
                case Z -> (BlockState) state.with(AXIS, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(AXIS, ctx.getSide().getAxis());
    }

    // ---------------------------------- //
}

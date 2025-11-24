package org.btwr.tough_environment.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LandingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;
import org.btwr.tough_environment.util.BlockMortarMapper;

public class LoosePillarBlock extends MortarReceiverBlock implements LandingBlock
{
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;

    public LoosePillarBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
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

    @Override
    protected BlockState getMortaredState(BlockState state) {
        BlockState newState = BlockMortarMapper.getReplacement(state);

        assert newState != null;
        return newState.with(AXIS, state.get(AXIS));
    }
}
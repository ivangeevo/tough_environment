package org.tough_environment.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import org.tough_environment.state.property.ModProperties;

public class ConvertingBlock extends Block
{

    public static final IntProperty BREAK_LEVEL = ModProperties.BREAK_LEVEL;

    public ConvertingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(BREAK_LEVEL, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BREAK_LEVEL);
    }

}

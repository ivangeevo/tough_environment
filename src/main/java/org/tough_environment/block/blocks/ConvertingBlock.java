package org.tough_environment.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.BlockManager;
import org.tough_environment.state.property.ModProperties;
import org.tough_environment.tag.BTWRConventionalTags;

public class ConvertingBlock extends Block
{
    public static final IntProperty BREAK_LEVEL = ModProperties.BREAK_LEVEL;


    public ConvertingBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(BREAK_LEVEL, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BREAK_LEVEL);
    }


    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state,
                           @Nullable BlockEntity blockEntity, ItemStack stack)
    {
        this.convert(world, pos, state, stack);
        super.afterBreak(world, player, pos, state, blockEntity, stack);

    }

    public void convert(World world, BlockPos pos, BlockState state, ItemStack stack)
    {
        if (!world.isClient)
        {
            world.setBlockState(pos, state.with(BREAK_LEVEL, state.get(BREAK_LEVEL) + 1));
        }
    }





}

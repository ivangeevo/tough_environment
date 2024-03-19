package org.tough_environment.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.block.interfaces.StateConvertableBlock;
import org.tough_environment.state.property.ModProperties;
import org.tough_environment.tag.ModTags;

public class ConvertingBlock extends Block implements StateConvertableBlock
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
        this.convertOnBreak(world, pos, state, stack);
        super.afterBreak(world, player, pos, state, blockEntity, stack);

    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {

        int breakLevel = state.get(BREAK_LEVEL);

        if (breakLevel == 0 || breakLevel == 2 || breakLevel == 4 || breakLevel == 6 || breakLevel == 8)
        {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_LAND,
                    SoundCategory.BLOCKS,0.5F,world.random.nextFloat() * 0.25F + 1.75F);
        }

        super.onBreak(world, pos, state, player);
    }

    public void convertOnBreak(World world, BlockPos pos, BlockState state, ItemStack stack)
    {
        world.setBlockState(pos, state.with(BREAK_LEVEL, state.get(BREAK_LEVEL) + 1));
    }


}

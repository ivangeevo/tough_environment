package org.tough_environment.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.TickPriority;
import org.tough_environment.tag.ModTags;
import org.tough_environment.util.BlockReplacementMapInitializer;

import java.util.Map;

public class MortarReceiverBlock extends FallingBlock
{

    private static final int FALL_DELAY_TICKS = 40;

    public MortarReceiverBlock(Settings settings) {
        super(settings);
    }

    public void applyMortar(BlockState state, World world, BlockPos pos, PlayerEntity player)
    {

        Block newBlock = getReplacementBlock(state.getBlock());

        if (newBlock != null)
        {
            BlockState newState = newBlock.getDefaultState();
            world.setBlockState(pos, newState);
        }

        world.playSound(null,pos, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.BLOCKS);

    }


    private Block getReplacementBlock(Block looseBlock)
    {

        // Load the block replacement map
        Map<String, String> blockReplacementMap = BlockReplacementMapInitializer.loadBlockReplacementMap();

        // Get the identifier of the loose block
        Identifier looseBlockId = Registries.BLOCK.getId(looseBlock);


        // Check if the map contains the loose block key
        String replacementKey = looseBlockId.toString();
        if (blockReplacementMap.containsKey(replacementKey)) {
            String replacementValue = blockReplacementMap.get(replacementKey);
            return Registries.BLOCK.get(new Identifier(replacementValue));
        }

        return null;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {

        if (!world.isClient())
        {
            boolean mortarable = false;
            // Iterate over all neighboring positions
            for (Direction direction : Direction.values())
            {
                BlockPos neighborPos = pos.offset(direction);
                BlockState neighborState = world.getBlockState(neighborPos);

                // Check if the adjacent block is part of the specified tag
                if (neighborState.isIn(ModTags.Blocks.MORTARED_BLOCKS))
                {
                    mortarable = true;
                    break;
                }
            }

            // Create an OrderedTick for the block
            OrderedTick<Block> orderedTick = new OrderedTick<>(state.getBlock(), pos, 0, TickPriority.NORMAL, 0);

            // If the block is not part of the mortarable blocks tag, schedule an immediate tick
            if (!mortarable)
            {
                world.getBlockTickScheduler().scheduleTick(orderedTick);
            }
            else
            {
                // If the block is part of the mortarable blocks tag, schedule the tick for 40 ticks later
                orderedTick = new OrderedTick<>(state.getBlock(), pos, world.getTime() + FALL_DELAY_TICKS,
                        TickPriority.NORMAL, 0);
                world.getBlockTickScheduler().scheduleTick(orderedTick);
            }
        }
    }





}

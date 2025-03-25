package org.tough_environment.block.blocks;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.TickPriority;
import org.tough_environment.tag.ModTags;
import org.tough_environment.util.BlockMortarMapper;

import java.util.Map;

public class MortarReceiverBlock extends FallingBlock
{
    private static final int TACKY_FALLING_BLOCK_TICK_RATE = 40;
    public static final MapCodec<MortarReceiverBlock> CODEC = MortarReceiverBlock.createCodec(MortarReceiverBlock::new);

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return CODEC;
    }

    public MortarReceiverBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify)
    {
        boolean hasMortaredNeighbor = Direction.stream()
                .map(pos::offset)
                .map(world::getBlockState)
                .anyMatch(neighborState -> neighborState.isIn(ModTags.Blocks.MORTARED_BLOCKS));

        if (hasMortaredNeighbor) {
            // Create an OrderedTick for the block
            OrderedTick<Block> orderedTick = new OrderedTick<>(state.getBlock(), pos,
                    world.getTime() + TACKY_FALLING_BLOCK_TICK_RATE, TickPriority.NORMAL, 0);
            world.getBlockTickScheduler().scheduleTick(orderedTick);
        } else {
            // Schedule the normal fall tick (default for falling blocks)
            super.onBlockAdded(state, world, pos, oldState, notify);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            // Check both hands to avoid shield interference
            ItemStack mainHandStack = player.getStackInHand(Hand.MAIN_HAND);
            ItemStack offHandStack = player.getStackInHand(Hand.OFF_HAND);

            if (mainHandStack.isIn(ModTags.Items.MORTARING_ITEMS)) {
                return applyMortarWithHand(state, world, pos, player, Hand.MAIN_HAND);
            }
            else if (offHandStack.isIn(ModTags.Items.MORTARING_ITEMS)) {
                return applyMortarWithHand(state, world, pos, player, Hand.OFF_HAND);
            }
        }

        return ActionResult.PASS;
    }

    // Helper method to apply the mortar logic for a specific hand
    private ActionResult applyMortarWithHand(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand) {
        this.applyMortar(state, world, pos, player);

        // Reduce item stack size if not in creative mode
        if (!player.isCreative()) {
            player.getStackInHand(hand).decrement(1);
        }

        return ActionResult.SUCCESS;
    }

    public void applyMortar(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        Block newBlock = this.getReplacementBlock(state.getBlock());
        if (newBlock != null) {
            world.setBlockState(pos, newBlock.getStateWithProperties(state), Block.NOTIFY_ALL);
        }

        world.playSound(null,pos, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.BLOCKS);
    }

    //TODO: Fix the replacement logic to work better. Right now it doesn't make sense and I have no clue where blocks map is actually saved
    private Block getReplacementBlock(Block looseBlock) {
        // Load the block replacement map
        Map<Block, Block> blockReplacementMap = BlockMortarMapper.loadMap();

        // Check if the map contains the loose block
        if (blockReplacementMap.containsKey(looseBlock)) {
            return blockReplacementMap.get(looseBlock);
        }

        // Return null or a default block if no replacement is found
        return null; // Or Blocks.AIR for an explicit default
    }









}

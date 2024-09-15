package org.tough_environment.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
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
    private static final int SLOW_FALL_DELAY_TICKS = 40;
    public static final MapCodec<MortarReceiverBlock> CODEC = MortarReceiverBlock.createCodec(MortarReceiverBlock::new);

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return CODEC;
    }

    public MortarReceiverBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit)
    {
        if (!world.isClient && player.getStackInHand(player.getActiveHand()).isIn(ModTags.Items.MORTARING_ITEMS))
        {

            // Mortar the block
            this.applyMortar(state, world, pos, player);

            // Reduce item stack size
            ItemStack handStack = player.getStackInHand(player.getActiveHand());
            handStack.decrement(1);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }


    public void applyMortar(BlockState state, World world, BlockPos pos, PlayerEntity player)
    {

        Block newBlock = getReplacementBlock(state.getBlock());

        if (newBlock != null)
        {
            world.setBlockState(pos, newBlock.getStateWithProperties(state));
        }

        world.playSound(null,pos, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.BLOCKS);

    }


    private Block getReplacementBlock(Block looseBlock)
    {

        // Load the block replacement map
        Map<String, String> blockReplacementMap = BlockMortarMapper.loadMap();

        // Get the identifier of the loose block
        Identifier looseBlockId = Registries.BLOCK.getId(looseBlock);

        // Check if the map contains the loose block key
        String replacementKey = looseBlockId.toString();
        if (blockReplacementMap != null && blockReplacementMap.containsKey(replacementKey))
        {
            String replacementValue = blockReplacementMap.get(replacementKey);
            return Registries.BLOCK.get(Identifier.of(replacementValue));
        }

        return null;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify)
    {
        if (world.isClient()) return;

        boolean hasMortaredNeighbor = Direction.stream()
                .map(pos::offset)
                .map(world::getBlockState)
                .anyMatch(neighborState -> neighborState.isIn(ModTags.Blocks.MORTARED_BLOCKS));

        if (hasMortaredNeighbor)
        {
            // Create an OrderedTick for the block
            OrderedTick<Block> orderedTick = new OrderedTick<>(state.getBlock(), pos,
                    world.getTime() + SLOW_FALL_DELAY_TICKS, TickPriority.NORMAL, 0);
            world.getBlockTickScheduler().scheduleTick(orderedTick);
        }
        else
        {
            // Schedule the normal fall tick (default for falling blocks)
            super.onBlockAdded(state, world, pos, oldState, notify);
        }
    }






}

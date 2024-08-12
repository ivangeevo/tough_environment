package org.tough_environment.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.block.blocks.PlacedOreChunkBlock;

import java.util.Objects;

@Mixin(Item.class)
public abstract class ItemMixin
{
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void injectedUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir)
    {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        makePlaceableAsBlock(Items.RAW_COPPER, ModBlocks.RAW_COPPER_PLACED, world, pos, context, cir);
        makePlaceableAsBlock(Items.RAW_IRON, ModBlocks.RAW_IRON_PLACED, world, pos, context, cir);
        makePlaceableAsBlock(Items.RAW_GOLD, ModBlocks.RAW_GOLD_PLACED, world, pos, context, cir);

    }

    @Unique
    private void makePlaceableAsBlock(Item item, Block block, World world, BlockPos pos, ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir )
    {
        ItemStack heldStack = context.getStack();

        if (heldStack.isOf(item))
        {
            // Ensure the block is placed on the server side
            if (!world.isClient)
            {
                BlockPos placePos = pos.up(); // Position to place the new block

                // Check the block below the target position
                BlockState belowBlockState = world.getBlockState(pos);
                if (!belowBlockState.isSolidBlock(world, pos) || belowBlockState.getBlock() instanceof PlacedOreChunkBlock)
                {
                    // Prevent placing the block on non-solid blocks or on top of itself
                    cir.setReturnValue(ActionResult.FAIL);
                    return;
                }

                // Create an ItemPlacementContext for the new block position
                ItemPlacementContext placementContext = new ItemPlacementContext(Objects.requireNonNull(context.getPlayer()), context.getHand(), heldStack, context.getHitResult());

                // Get the block state using the placement context
                BlockState placedBlockState = block.getPlacementState(placementContext);

                // Check if the target position is air or a replaceable block
                if ((world.isAir(placePos) || world.getBlockState(placePos).canReplace(placementContext)) && placedBlockState != null)
                {
                    // Replace the block at the target position with the placed block
                    world.setBlockState(placePos, placedBlockState);
                    heldStack.decrement(1);

                    // Trigger block placement events
                    world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_PLACE, placePos);

                    // Indicate the interaction was successful
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }
}

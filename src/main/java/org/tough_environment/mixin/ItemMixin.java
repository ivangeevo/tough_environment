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
                if (belowBlockState.getBlock() instanceof PlacedOreChunkBlock)
                {
                    // Prevent placing the block on top of itself
                    cir.setReturnValue(ActionResult.FAIL);
                    return;
                }

                // TODO: Possibly not good idea to access widen getHitResult(the last parameter) try something else if compatability issues arise.

                // Create an ItemPlacementContext for the new block position
                ItemPlacementContext placementContext = new ItemPlacementContext(Objects.requireNonNull(context.getPlayer()), context.getHand(), heldStack, context.getHitResult());

                // Get the block state using the placement context
                BlockState placedBlockState = block.getPlacementState(placementContext);

                if (world.isAir(placePos) && placedBlockState != null) // Check if the position is air before placing the block
                {
                    world.setBlockState(placePos, placedBlockState);
                    heldStack.decrement(1);

                    // Indicate the interaction was successful
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }

}

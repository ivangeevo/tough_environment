package org.tough_environment.item;

import btwr.core.block.BTWR_Blocks;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.block.blocks.PlacedOreChunkBlock;
import org.tough_environment.config.TESettings;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

import java.util.Objects;

public class ItemMixinManager
{
    @Unique private static final TESettings configChecker = ToughEnvironmentMod.getInstance().settings;

    private static final ItemMixinManager instance = new ItemMixinManager();

    private ItemMixinManager() {}

    public static ItemMixinManager getInstance()
    {
        return instance;
    }

    public void handleUseOnBlock(ItemUsageContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        makePlaceableAsBlock(Items.RAW_COPPER, ModBlocks.RAW_COPPER_PLACED, world, pos, context);
        makePlaceableAsBlock(Items.RAW_IRON, ModBlocks.RAW_IRON_PLACED, world, pos, context);
        makePlaceableAsBlock(Items.RAW_GOLD, ModBlocks.RAW_GOLD_PLACED, world, pos, context);

    }

    public void handleGetMiningSpeed(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir)
    {
        ToolComponent toolComponent = stack.get(DataComponentTypes.TOOL);

        // Only apply this logic if the item is a MiningToolItem
        if (stack.getItem() instanceof MiningToolItem)
        {
            assert toolComponent != null;
            float originalSpeed = toolComponent.getSpeed(state);

            // Apply custom logic
            if (isUnfeasibleToBreak(state, stack) && configChecker.isStratificationToughnessEnabled()) {
                cir.setReturnValue(originalSpeed / 8000f);
            } else if (isProblemToBreak(state, stack)) {
                cir.setReturnValue(originalSpeed / 80f);
            } else if (isPrimitiveTool(stack) && configChecker.isHardcorePlayerMiningSpeedEnabled()) {
                cir.setReturnValue(originalSpeed / 6f);
            } else if (stack.isIn(ItemTags.HOES) && configChecker.isHardcorePlayerMiningSpeedEnabled()) {
                cir.setReturnValue(originalSpeed / 4f);
            } else if (state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS) && stack.isSuitableFor(state)) {
                cir.setReturnValue(originalSpeed * 12f);
            } else {
                cir.setReturnValue(originalSpeed);
            }
        }
    }

    @Unique
    private boolean isProblemToBreak(BlockState state, ItemStack stack) {
        boolean isStrata3 = state.isIn(ModTags.Blocks.STONE_STRATA3);
        boolean isStrata2 = state.isIn(ModTags.Blocks.STONE_STRATA2);
        boolean isStrata1 = state.isIn(ModTags.Blocks.STONE_STRATA1);

        boolean isModernPickaxe = stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES);
        boolean isAdvancedPickaxe = stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES);

        if (isStrata3 && !isAdvancedPickaxe) {
            return true;
        }

        return isStrata2 && !isAdvancedPickaxe && !isModernPickaxe;
    }

    @Unique
    private boolean isUnfeasibleToBreak(BlockState state, ItemStack stack)
    {
        if (state.isIn(ModTags.Blocks.STONE_CONVERTING_STRATA3) && !stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES))
        {
            return true;
        }

        return state.isIn(ModTags.Blocks.STONE_CONVERTING_STRATA2) && !stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES);
    }

    @Unique
    private boolean isPrimitiveTool(ItemStack stack)
    {
        return stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_AXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_HOES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_CHISELS);
    }

    public void makePlaceableAsBlock(Item item, Block block, World world, BlockPos pos, ItemUsageContext context)
    {
        ItemStack heldStack = context.getStack();

        if (heldStack.isOf(item))
        {
            // Ensure the block is placed on the server side
            if (!world.isClient)
            {
                BlockPos placePos = pos.up(); // Position to place the new block

                // Get the Blockstate of the block to place the brick on
                BlockState blockBelowState = world.getBlockState(pos);

                // Check if the block below can support a block on top of it
                if (!blockBelowState.isSolidBlock(world, pos))
                {
                    return;
                }

                // Create an ItemPlacementContext for the new block position
                ItemPlacementContext placementContext = new ItemPlacementContext(Objects.requireNonNull(context.getPlayer()), context.getHand(), heldStack, context.getHitResult());

                // Get the block state using the placement context
                BlockState placementState = block.getPlacementState(placementContext);

                // Check if the target position is air or a replaceable block
                if ((world.isAir(placePos) || world.getBlockState(placePos).canReplace(placementContext)) && placementState != null)
                {
                    // Replace the block at the target position with the brick block
                    world.setBlockState(placePos, placementState);
                    heldStack.decrement(1);

                    // Trigger block placement events
                    //world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_PLACE, placePos);
                }
            }
        }
    }

}

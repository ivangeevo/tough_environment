package org.tough_environment.block.blocks;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.tag.ModTags;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.block.PillarBlock.AXIS;

public class LooseBlock extends MortarReceiverBlock implements LandingBlock
{

    // Block parameters and constants & Super settings //
    public LooseBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && player.getStackInHand(player.getActiveHand()).isIn(ModTags.Items.MORTARING_ITEMS))
        {

            // Mortar the block
            this.applyMortar(state, world, pos, player);

            // Reduce item stack size
            ItemStack handStack = player.getStackInHand(player.getActiveHand());
            handStack.decrement(1);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;    }




    // ---------------------------------- //
}

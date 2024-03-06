package org.tough_environment.block.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.LandingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.block.ModBlocks;

import java.util.ArrayList;
import java.util.List;

public class LooseBlock extends FallingBlock implements LandingBlock {


    // Block parameters and constants & Super settings //
    public LooseBlock(Settings settings) {
        super(settings);
    }

    // Block specific logic //
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
            player.addExhaustion(0.2F);

        BlockState blockAboveState = world.getBlockState(pos.up());

        boolean isDirtAbove = blockAboveState.isOf(Blocks.DIRT);
        boolean isGrassAbove = blockAboveState.isOf(Blocks.GRASS_BLOCK);

        if (player.getMainHandStack().isSuitableFor(this.getDefaultState())) {
            if (isDirtAbove || isGrassAbove) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.setBlockState(pos.up(), this.getDefaultState());
            }
        }

        if ((tool.isOf(Items.IRON_HOE)) || (tool.isOf(Items.DIAMOND_HOE))) {

            if (state.isOf(ModBlocks.DIRT_LOOSE)) {
                world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
            }

        } else {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());

        }



    }




    // ---------------------------------- //
}

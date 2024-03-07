package org.tough_environment.block.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Mortarable {

    boolean hasMortarNow(BlockState state);

    boolean isMortarable(BlockState state, World world, BlockPos pos, PlayerEntity player);

    void mortarBlock(BlockState state, World world, BlockPos pos, PlayerEntity player);

}

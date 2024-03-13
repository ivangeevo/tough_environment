package org.tough_environment.mixin.interfaces;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockAdded
{

    boolean hasNeighborWithMortarInContact(World world, BlockPos pos);



}


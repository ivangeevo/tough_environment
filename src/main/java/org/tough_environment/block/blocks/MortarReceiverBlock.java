package org.tough_environment.block.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MortarReceiverBlock extends FallingBlock
{


    public MortarReceiverBlock(Settings settings) {
        super(settings);
    }


    @Override
    public void onBlockDestroyedWithImproperTool(World world, EntityPlayer player, int i, int j, int k, int iMetadata)
    {
        dropBlockAsItem( world, i, j, k, iMetadata, 0 );
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify)
    {

        if ( hasNeighborWithMortarInContact(world, i, j, k) )
        {
            world.playAuxSFX( BTWEffectManager.LOOSE_BLOCK_STUCK_TO_MORTAR_EFFECT_ID, i, j, k, 0 );

            world.scheduleBlockUpdate(i, j, k, blockID, TACKY_FALLING_BLOCK_TICK_RATE);
        }
        else
        {
            scheduleCheckForFall(world, i, j, k);
        }    }

    @Override
    public void onBlockAdded( World world, int i, int j, int k )
    {

    }
}

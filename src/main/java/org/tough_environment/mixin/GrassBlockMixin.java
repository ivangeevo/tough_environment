package org.tough_environment.mixin;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.tag.ModTags;

@Mixin(GrassBlock.class)
public abstract class GrassBlockMixin extends SpreadableBlock
        implements Fertilizable {
    protected GrassBlockMixin(Settings settings) {
        super(settings);
    }



    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {

        if (stack.isIn(ItemTags.HOES))
        {
            world.setBlockState(pos, ModBlocks.DIRT_LOOSE.getDefaultState());
        }

        super.afterBreak(world, player, pos, state, blockEntity, stack);

    }
}



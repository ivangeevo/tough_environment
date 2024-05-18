package org.tough_environment.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tough_environment.block.BlockManager;
import org.tough_environment.block.interfaces.DirectionalDroppingBlock;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements DirectionalDroppingBlock
{

    public BlockMixin(Settings settings) {
        super(settings);
    }

    // 0.005f hunger whenever a block is placed
    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void injectedOnPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci)
    {
        BlockManager.handleOnPlaced(placer);
    }

    // Injecting our own logic and cancelling, because we got custom stack logic.
    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void onAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci)
    {
        BlockManager.handleAfterBreak(world, player, pos, state, tool);
        BlockManager.playSoundOnBreak(world, pos, state ,tool, player);

    }
}

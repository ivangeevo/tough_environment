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

    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void injectedOnPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci)
    {
        BlockManager.getInstance().handleOnPlaced(placer);
    }

    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void onAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci)
    {
        BlockManager.getInstance().handleAfterBreak(world, player, pos, state, tool);
        BlockManager.getInstance().playDingSoundOnBreak(world, pos, state ,tool, player);

    }
}

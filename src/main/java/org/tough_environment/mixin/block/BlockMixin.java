package org.tough_environment.mixin.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.block.BlockMixinManager;
import org.tough_environment.block.interfaces.DirectionalDroppingBlock;
import org.tough_environment.util.MakeAsFallingBlock;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements DirectionalDroppingBlock {
    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomDisplayTick", at = @At("HEAD"))
    private void onRandomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        MakeAsFallingBlock.getInstance().onRandomDisplayTick(state, world, pos, random);
    }

    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void onAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci)
    {
        BlockMixinManager.getInstance().handleAfterBreak(world, player, pos, state, tool);
    }


}

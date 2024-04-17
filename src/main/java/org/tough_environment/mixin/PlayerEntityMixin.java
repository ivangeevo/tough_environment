package org.tough_environment.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.tag.ModTags;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    @Shadow @Final private PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world)
    {
        super(entityType, world);
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At("HEAD"), cancellable = true)
    private void customBlockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> cir) {

        if (!ToughEnvironmentMod.getInstance().settings.isHardcorePlayerMiningSpeedEnabled())
        {
            return;
        }

        float f;
        float defaultSpeed = this.inventory.getBlockBreakingSpeed(state);
        ItemStack stack = this.getMainHandStack();

        if (state.isIn(BlockTags.NEEDS_DIAMOND_TOOL) || state.isIn(BlockTags.NEEDS_IRON_TOOL) || state.isIn(BlockTags.NEEDS_STONE_TOOL))
        {
            if (!stack.isSuitableFor(state))
            {
                f = defaultSpeed / 8000;
                cir.setReturnValue(f);
            }
        }
        else if (state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS))
        {
            if (!stack.isSuitableFor(state))
            {
                f = defaultSpeed / 80;
                cir.setReturnValue(f);
            }
        }

        if (isPrimitiveTool(stack) || !(stack.getItem() instanceof MiningToolItem))
        {
            f = defaultSpeed / 6;
            cir.setReturnValue(f);
        }
        else
        {
            cir.setReturnValue(defaultSpeed / 2);
        }

    }

    @Unique
    private boolean isPrimitiveTool(ItemStack stack)
    {
        return stack.isIn(ModTags.Items.PRIMITIVE_PICKAXES)
                || stack.isIn(ModTags.Items.PRIMITIVE_AXES)
                || stack.isIn(ModTags.Items.PRIMITIVE_SHOVELS)
                || stack.isIn(ModTags.Items.PRIMITIVE_HOES)
                || stack.isIn(ModTags.Items.PRIMITIVE_CHISELS);
    }

}

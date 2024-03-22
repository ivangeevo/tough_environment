package org.tough_environment.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Arm;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.tag.ModTags;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    @Shadow @Final private PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At("HEAD"), cancellable = true)
    private void customBlockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> cir) {

        if (ToughEnvironmentMod.getInstance().settings.isHardcoreHandBreakingSpeedEnabled()) {

            float f;

            float defaultSpeed = this.inventory.getBlockBreakingSpeed(state);
            ItemStack stack = this.getMainHandStack();

            if (state.isIn(ModTags.Blocks.STONE_STRATA3) || state.isIn(ModTags.Blocks.STONE_STRATA2)) {

                if (!stack.isSuitableFor(state)) {
                    f = defaultSpeed / 8000;
                } else {
                    f = defaultSpeed / 4;
                }


            } else if (state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS)) {

                if (!stack.isSuitableFor(state)) {
                    f = defaultSpeed / 80;

                } else {
                    f = defaultSpeed / 4;

                }

            } else {
                f = defaultSpeed / 4;

            }


            if (f > 1.0f) {
                int i = EnchantmentHelper.getEfficiency(this);
                ItemStack itemStack = this.getMainHandStack();
                if (i > 0 && !itemStack.isEmpty()) {
                    f += (float) (i * i + 1);
                }
            }
            if (StatusEffectUtil.hasHaste(this)) {
                f *= 1.0f + (float) (StatusEffectUtil.getHasteAmplifier(this) + 1) * 0.2f;
            }
            if (this.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
                f *= (switch (this.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier()) {
                    case 0 -> 0.3f;
                    case 1 -> 0.09f;
                    case 2 -> 0.0027f;
                    default -> 8.1E-4f;
                });
            }
            if (this.isSubmergedIn(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this)) {
                f /= 5.0f;
            }
            if (!this.isOnGround()) {
                f /= 5.0f;
            }

            cir.setReturnValue(f);
        }

    }


}

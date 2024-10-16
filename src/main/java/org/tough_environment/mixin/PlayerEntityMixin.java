package org.tough_environment.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.tag.ModTags;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    @Shadow
    @Final
    PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world)
    {
        super(entityType, world);
    }

    /**
    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"))
    private void custom(BlockState block, CallbackInfoReturnable<Float> cir)
    {

    }
     **/

    // TODO: Find a better way to mix in the custom logic added by the inject below.
    //  It might be better to do so at the RETURN, try it.
    @Inject(method = "getBlockBreakingSpeed", at = @At("HEAD"), cancellable = true)
    private void customBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> cir)
    {

        if (!ToughEnvironmentMod.getInstance().settings.isHardcorePlayerMiningSpeedEnabled())
        {
            return;
        }

        float f = this.inventory.getBlockBreakingSpeed(state);

        // Tough Environment: Added
        // conditions for restricting breaking blocks without the correct item
        if ( !this.getMainHandStack().isSuitableFor(state) )
        {
            // if the block is requiring a tool, that means its a tough block.
            if ( state.isToolRequired() )
            {
                // and if it's not a special case block (like snow) which can still be broken by hand (but requires a tool)
                // then make it super tough to break
                // this additional check could probably be evaded by getting better conditions.
                if (!state.isIn(ModTags.Blocks.MISC_REQUIRING_TOOL))
                {
                    f /= 8000F;
                }
            }

            // 6x times slower speed for all other blocks
            f /= 6F;
        }
        // Tough Environment: End Mod


        if (f > 1.0F) {
            f += (float)this.getAttributeValue(EntityAttributes.PLAYER_MINING_EFFICIENCY);
        }

        if (StatusEffectUtil.hasHaste(this)) {
            f *= 1.0F + (float)(StatusEffectUtil.getHasteAmplifier(this) + 1) * 0.2F;
        }

        if (this.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
            float g = switch (Objects.requireNonNull(this.getStatusEffect(StatusEffects.MINING_FATIGUE)).getAmplifier()) {
                case 0 -> 0.3F;
                case 1 -> 0.09F;
                case 2 -> 0.0027F;
                default -> 8.1E-4F;
            };

            f *= g;
        }

        f *= (float)this.getAttributeValue(EntityAttributes.PLAYER_BLOCK_BREAK_SPEED);
        if (this.isSubmergedIn(FluidTags.WATER)) {
            f *= (float) Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.PLAYER_SUBMERGED_MINING_SPEED)).getValue();
        }

        if (!this.isOnGround()) {
            f /= 5.0F;
        }

        cir.setReturnValue(f);
    }

}

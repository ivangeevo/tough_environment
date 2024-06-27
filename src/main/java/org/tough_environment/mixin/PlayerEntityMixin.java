package org.tough_environment.mixin;

import com.terraformersmc.modmenu.util.mod.Mod;
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
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    @Shadow
    @Final
    private PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world)
    {
        super(entityType, world);
    }
    @Inject(method = "getBlockBreakingSpeed", at = @At("HEAD"), cancellable = true)
    private void customBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> cir)
    {
        /**
        if (!ToughEnvironmentMod.getInstance().settings.isHardcorePlayerMiningSpeedEnabled())
        {
            return;
        }
         **/

        float f = this.inventory.getBlockBreakingSpeed(state);

        // TODO: Fix blocks like snow unable to be broken by hand.
        //  using workaround tag for now && a replaceable check.


        // Tough Environment: Added
        // condition for breaking blocks without the correct item
        if ( !this.getMainHandStack().isSuitableFor(state) )
        {
            // if the block is requiring a tool, make it nearly unbreakable
            if ( state.isToolRequired()
                    && ( !state.isIn(ModTags.Blocks.MISC_REQUIRING_TOOL) || state.getBlock().getDefaultState().isReplaceable() ) )
            {
                f /= 8000F;
            }

            // 6x times slower speed for all other blocks
            f /= 6F;
        }
        // Tough Environment: End Mod

        if ( f > 1.0F )
        {
            int i = EnchantmentHelper.getEfficiency(this);
            ItemStack itemStack = this.getMainHandStack();

            if ( i > 0 && !itemStack.isEmpty() )
            {
                f += (float)(i * i + 1);
            }
        }

        if ( StatusEffectUtil.hasHaste(this) )
        {
            f *= 1.0F + (float)( StatusEffectUtil.getHasteAmplifier(this) + 1 ) * 0.2F;
        }

        if ( this.hasStatusEffect(StatusEffects.MINING_FATIGUE) )
        {
            float g = switch ( Objects.requireNonNull(this.getStatusEffect(StatusEffects.MINING_FATIGUE)).getAmplifier())
            {
                case 0 -> 0.3F;
                case 1 -> 0.09F;
                case 2 -> 0.0027F;
                default -> 8.1E-4F;
            };

            f *= g;
        }

        if ( this.isSubmergedIn(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this) )
        {
            f /= 5.0F;
        }

        if ( !this.isOnGround() )
        {
            f /= 5.0F;
        }

        cir.setReturnValue(f);
    }


    // TODO : FIX EFFICIENCY, HASTE & MINING FATIGUE NOT APPLIED FROM THE ORIGINAL LOGIC
    //@Inject(method = "getBlockBreakingSpeed", at = @At("HEAD"), cancellable = true)
    private void customBlockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> cir) {

        if (!ToughEnvironmentMod.getInstance().settings.isHardcorePlayerMiningSpeedEnabled())
        {
            return;
        }

        float f;
        float defaultSpeed = this.inventory.getBlockBreakingSpeed(state);
        ItemStack stack = this.getMainHandStack();

        // super slow to mine by hand blocks, possible but not viable.
        if ( isProblemToBreak(state, stack))
        {
            f = defaultSpeed / 8000;
            cir.setReturnValue(f);
        }
        // mineable by hand, but still very slow
        else if ( state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS) && !stack.isSuitableFor(state) )
        {
            f = defaultSpeed / 80;
            cir.setReturnValue(f);
        }
        // mined the wrong one
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

    // This method handles cases where the tool is very, very slow to break the block. Almost unbreakable.
    @Unique
    private boolean isProblemToBreak(BlockState state, ItemStack stack) {
        boolean isStrata3 = state.isIn(ModTags.Blocks.STONE_STRATA3);
        boolean isStrata2 = state.isIn(ModTags.Blocks.STONE_STRATA2);
        boolean isStrata1 = state.isIn(ModTags.Blocks.STONE_STRATA1);

        if (stack.isSuitableFor(state))
        {
            if (isStrata3 && !isAdvancedTool(stack)) {
                return true;
            }

            if (isStrata2 && !isModernTool(stack)) {
                return true;
            }
        }

        return !(stack.getItem() instanceof MiningToolItem);

    }

    @Unique
    private boolean isPrimitiveTool(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_AXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_HOES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_CHISELS);
    }

    @Unique
    private boolean isModernTool(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_AXES)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_HOES)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_CHISELS);
    }

    @Unique
    private boolean isAdvancedTool(ItemStack stack) {
        return stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.ADVANCED_AXES)
                || stack.isIn(BTWRConventionalTags.Items.ADVANCED_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.ADVANCED_HOES);
    }
}

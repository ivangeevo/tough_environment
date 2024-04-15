package org.tough_environment.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends MiningToolItem
{

    public AxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state)
    {
        if (state.isIn(BlockTags.LOGS))
        {
            if (stack.isOf(Items.WOODEN_AXE) || stack.isOf(Items.STONE_AXE))
            {
                return super.getMiningSpeedMultiplier(stack, state) * 2;
            }

            return super.getMiningSpeedMultiplier(stack, state) * 5;
        }

        return super.getMiningSpeedMultiplier(stack, state);
    }
}

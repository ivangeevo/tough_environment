package org.tough_environment.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.tough_environment.tag.ModTags;

@Mixin(PickaxeItem.class)
public abstract class PickaxeItemMixin extends MiningToolItem {


    public PickaxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
       if (state.isIn(ModTags.Blocks.BROKEN_STONES)) {
           return this.miningSpeed * 5f;
       }


        return super.getMiningSpeedMultiplier(stack, state);
    }
}

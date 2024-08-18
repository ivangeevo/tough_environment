package org.tough_environment.item;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

/**
 * Provides the default {@link ToolMaterial}s used by vanilla tools.
 */
public enum ModToolMaterials implements ToolMaterial
{
    WOOD_CHISEL(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 2, 1.2F, 0.0F, 15, () -> Ingredient.fromTag(ItemTags.PLANKS)),
    STONE_CHISEL(BlockTags.INCORRECT_FOR_STONE_TOOL, 8, 2.0F, 1.0F, 5, () -> Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)),
    IRON_CHISEL(BlockTags.INCORRECT_FOR_IRON_TOOL, 238, 3.0F, 2.0F, 14, () -> Ingredient.ofItems(Items.IRON_INGOT)),
    DIAMOND_CHISEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 500, 4.0F, 3.0F, 10, () -> Ingredient.ofItems(Items.DIAMOND)),
    NETHERITE_CHISEL(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 720, 4.5F, 4.0F, 15, () -> Ingredient.ofItems(Items.NETHERITE_INGOT));

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(
            final TagKey<Block> inverseTag,
            final int itemDurability,
            final float miningSpeed,
            final float attackDamage,
            final int enchantability,
            final Supplier<Ingredient> repairIngredient
    ) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}

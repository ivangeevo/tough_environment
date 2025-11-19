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
import org.tough_environment.tag.ModTags;

/**
 * Provides custom {@link ToolMaterial}s depending on tier for modification of vanilla tools.
 */
public enum TieredToolMaterials implements ToolMaterial {
	PRIMITIVE_WOOD(ModTags.Blocks.INCORRECT_FOR_PRIMITIVE_WOOD, 10, 2.0F, 0.0F, 15,
			() -> Ingredient.fromTag(ItemTags.PLANKS)
	),
	PRIMITIVE_STONE(ModTags.Blocks.INCORRECT_FOR_PRIMITIVE_STONE, 50, 4.0F, 1.0F, 5,
			() -> Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)
	),
	MODERN_GOLD(ModTags.Blocks.INCORRECT_FOR_MODERN_GOLD, 45, 12F, 0.0F, 22,
			() -> Ingredient.ofItems(Items.GOLD_INGOT)
	),
	MODERN_IRON(ModTags.Blocks.INCORRECT_FOR_MODERN_IRON, 500, 6.0F,2.0F, 14,
			() -> Ingredient.ofItems(Items.IRON_INGOT)
	),
	MODERN_DIAMOND(ModTags.Blocks.INCORRECT_FOR_MODERN_DIAMOND, 1800, 8.0F, 3.0F, 10,
			() -> Ingredient.ofItems(Items.DIAMOND)
	),
	ADVANCED_NETHERITE(ModTags.Blocks.INCORRECT_FOR_ADVANCED_NETHERITE, 2560, 9.0F, 4.0F, 15,
			() -> Ingredient.ofItems(Items.NETHERITE_INGOT)
	);

	private final TagKey<Block> inverseTag;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Supplier<Ingredient> repairIngredient;

	TieredToolMaterials(
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
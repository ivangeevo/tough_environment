package org.tough_environment.item;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.block.Block;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.btwr.shared_library.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

import java.util.List;

public enum ChiselToolMaterials implements ToolMaterial
{
    WOOD(ModTags.Blocks.INCORRECT_FOR_WOODEN_CHISEL, 2, 1.2F, 0.0F, 1,
            () -> Ingredient.ofItems(Items.STICK)
    ),
    STONE(ModTags.Blocks.INCORRECT_FOR_STONE_CHISEL, 8, 2.0F, 1.0F, 5,
            () -> Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)
    ),
    IRON(ModTags.Blocks.INCORRECT_FOR_IRON_CHISEL, 238, 3.0F, 2.0F, 21,
            () -> Ingredient.ofItems(Items.IRON_NUGGET)
    ),
    DIAMOND(ModTags.Blocks.INCORRECT_FOR_DIAMOND_CHISEL, 500, 4.0F, 3.0F, 14,
            () -> Ingredient.ofItems(Items.DIAMOND)
    );

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ChiselToolMaterials(
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

    /**
     * Create a ToolComponent dynamically for this material, setting up block-specific mining speeds.
     */
    @Override
    public ToolComponent createComponent(TagKey<Block> tag) {
        float stumpSpeed = (this == DIAMOND) ? 55.0F : 2.0F;  // Custom speed for STUMP_BLOCKS based on material
        return new ToolComponent(
                List.of(
                        ToolComponent.Rule.ofNeverDropping(this.getInverseTag()),
                        ToolComponent.Rule.ofAlwaysDropping(tag, this.getMiningSpeedMultiplier()),
                        ToolComponent.Rule.of(BTWRConventionalTags.Blocks.STUMP_BLOCKS, 55F),
                        ToolComponent.Rule.of(BTWRConventionalTags.Blocks.WEB_BLOCKS, 25F)
                ), 1.0F, 1);
    }
}
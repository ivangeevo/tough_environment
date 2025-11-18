package org.tough_environment.item.component;

import net.minecraft.block.Block;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.registry.tag.TagKey;
import org.tough_environment.item.TieredToolMaterials;
import org.tough_environment.tag.ModTags;

import java.util.List;

/** By defining a separate tool component for each tool we can modify their values individually **/
public class ModToolComponents {

    // Primitive
    public static final ToolComponent PRIMITIVE_WOODEN_PICKAXE = createToolComponent(TieredToolMaterials.PRIMITIVE_WOOD, ModTags.Mineable.WOODEN_PRIMITIVE, 1.0F);
    public static final ToolComponent PRIMITIVE_STONE_PICKAXE = createToolComponent(TieredToolMaterials.PRIMITIVE_STONE, ModTags.Mineable.STONE_PRIMITIVE, 1.2F);
    public static final ToolComponent PRIMITIVE_WOODEN_AXE = createToolComponent(TieredToolMaterials.PRIMITIVE_WOOD, ModTags.Mineable.WOODEN_PRIMITIVE, 1.0F);
    public static final ToolComponent PRIMITIVE_STONE_AXE = createToolComponent(TieredToolMaterials.PRIMITIVE_STONE, ModTags.Mineable.STONE_PRIMITIVE, 1.2F);
    public static final ToolComponent PRIMITIVE_WOODEN_SHOVEL = createToolComponent(TieredToolMaterials.PRIMITIVE_WOOD, ModTags.Mineable.WOODEN_PRIMITIVE, 1.0F);
    public static final ToolComponent PRIMITIVE_STONE_SHOVEL = createToolComponent(TieredToolMaterials.PRIMITIVE_STONE, ModTags.Mineable.STONE_PRIMITIVE, 1.2F);
    public static final ToolComponent PRIMITIVE_WOODEN_HOE = createToolComponent(TieredToolMaterials.PRIMITIVE_WOOD, ModTags.Mineable.WOODEN_PRIMITIVE, 1.0F);
    public static final ToolComponent PRIMITIVE_STONE_HOE = createToolComponent(TieredToolMaterials.PRIMITIVE_STONE, ModTags.Mineable.STONE_PRIMITIVE, 1.2F);

    // Modern
    public static final ToolComponent MODERN_GOLDEN_PICKAXE = createToolComponent(TieredToolMaterials.MODERN_GOLD, ModTags.Mineable.GOLD_MODERN, 2.5F);
    public static final ToolComponent MODERN_IRON_PICKAXE = createToolComponent(TieredToolMaterials.MODERN_IRON, ModTags.Mineable.IRON_MODERN, 1.6F);
    public static final ToolComponent MODERN_DIAMOND_PICKAXE = createToolComponent(TieredToolMaterials.MODERN_DIAMOND, ModTags.Mineable.DIAMOND_MODERN, 1.9F);
    public static final ToolComponent MODERN_GOLDEN_AXE = createToolComponent(TieredToolMaterials.MODERN_GOLD, ModTags.Mineable.GOLD_MODERN, 2.5F);
    public static final ToolComponent MODERN_IRON_AXE = createToolComponent(TieredToolMaterials.MODERN_IRON, ModTags.Mineable.IRON_MODERN, 1.6F);
    public static final ToolComponent MODERN_DIAMOND_AXE = createToolComponent(TieredToolMaterials.MODERN_DIAMOND, ModTags.Mineable.DIAMOND_MODERN, 1.9F);
    public static final ToolComponent MODERN_GOLDEN_SHOVEL = createToolComponent(TieredToolMaterials.MODERN_GOLD, ModTags.Mineable.GOLD_MODERN, 2.5F);
    public static final ToolComponent MODERN_IRON_SHOVEL = createToolComponent(TieredToolMaterials.MODERN_IRON, ModTags.Mineable.IRON_MODERN, 1.6F);
    public static final ToolComponent MODERN_DIAMOND_SHOVEL = createToolComponent(TieredToolMaterials.MODERN_DIAMOND, ModTags.Mineable.DIAMOND_MODERN, 1.9F);
    public static final ToolComponent MODERN_GOLDEN_HOE = createToolComponent(TieredToolMaterials.MODERN_GOLD, ModTags.Mineable.GOLD_MODERN, 2.5F);
    public static final ToolComponent MODERN_IRON_HOE = createToolComponent(TieredToolMaterials.MODERN_IRON, ModTags.Mineable.IRON_MODERN, 1.6F);
    public static final ToolComponent MODERN_DIAMOND_HOE = createToolComponent(TieredToolMaterials.MODERN_DIAMOND, ModTags.Mineable.DIAMOND_MODERN, 1.9F);

    // Advanced
    public static final ToolComponent ADVANCED_NETHERITE_PICKAXE = createToolComponent(TieredToolMaterials.ADVANCED_NETHERITE, ModTags.Mineable.NETHERITE_ADVANCED, 2.2F);
    public static final ToolComponent ADVANCED_NETHERITE_AXE = createToolComponent(TieredToolMaterials.ADVANCED_NETHERITE, ModTags.Mineable.NETHERITE_ADVANCED, 1.9F);
    public static final ToolComponent ADVANCED_NETHERITE_SHOVEL = createToolComponent(TieredToolMaterials.ADVANCED_NETHERITE, ModTags.Mineable.NETHERITE_ADVANCED, 2.1F);
    public static final ToolComponent ADVANCED_NETHERITE_HOE = createToolComponent(TieredToolMaterials.ADVANCED_NETHERITE, ModTags.Mineable.NETHERITE_ADVANCED, 1.8F);

    /** Helper method to build a ToolComponent for a given tier and mining tag */
    private static ToolComponent createToolComponent(TieredToolMaterials tier, TagKey<Block> alwaysDropTag, float speed) {
        return new ToolComponent(withRules(tier, alwaysDropTag), speed, 1);
    }

    private static List<ToolComponent.Rule> withRules(TieredToolMaterials tier, TagKey<Block> alwaysDropTag) {
        return List.of(
                ToolComponent.Rule.ofNeverDropping(tier.getInverseTag()),
                ToolComponent.Rule.ofAlwaysDropping(alwaysDropTag, tier.getMiningSpeedMultiplier())
        );
    }
}


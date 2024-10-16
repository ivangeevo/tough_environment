package org.tough_environment.item.items;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.tough_environment.item.ModItems;
import org.tough_environment.item.ChiselToolMaterials;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

import java.util.List;

public class ChiselItem extends MiningToolItem
{
    public ChiselItem(ChiselToolMaterials toolMaterial, Settings settings)
    {
        super(toolMaterial, ModTags.Mineable.CHISEL, settings.component(DataComponentTypes.TOOL, createToolComponent()));
    }


    // Create attribute modifiers for the tool
    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float baseAttackDamage, float attackSpeed)
    {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .build();
    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(
                        ToolComponent.Rule.ofAlwaysDropping(BTWRConventionalTags.Blocks.STUMP_BLOCKS, 15.0F),
                        ToolComponent.Rule.of(BlockTags.SWORD_EFFICIENT, 1.5F)
                ),
                1.0F, 2
        );
    }

    /**
    // Create tool component dynamically based on the chiselType
    public static List<ToolComponent.Rule> chiselConditionsList()
    {
        List<ToolComponent.Rule> rules = new ArrayList<>();
        //ChiselItem.Type chiselType = this.getType();

        // Adjust speed for mining stump blocks
        if (chiselType == Type.DIAMOND)
        {
            rules.add(ToolComponent.Rule.of(BTWRConventionalTags.Blocks.STUMP_BLOCKS, 60f));
        }
        else
        {
            rules.add(ToolComponent.Rule.of(BTWRConventionalTags.Blocks.STUMP_BLOCKS,2f));
        }

        // Adjust speed for stone chisels
        if (chiselType == Type.IRON || chiselType == Type.STONE)
        {
            rules.add(ToolComponent.Rule.of(ModTags.Blocks.STONE_CONVERTING_STRATA3, 1 / 80f));
            rules.add(ToolComponent.Rule.of(ModTags.Blocks.STONE_CONVERTING_STRATA2, 1 / 80f));
        }

        // Make primitive chisels slower when mining higher tier ores
        if (chiselType == Type.STONE || chiselType == Type.WOOD)
        {
            rules.add(ToolComponent.Rule.of(ModTags.Blocks.ORES_LVL2, 1 / 8000f));
            rules.add(ToolComponent.Rule.of(ModTags.Blocks.ORES_LVL3, 1 / 8000f));

        }

        return rules;
    }
     **/


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner)
    {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f)
        {
            if (state.isIn(BTWRConventionalTags.Blocks.STUMP_BLOCKS))
            {
                stack.damage(5, miner, EquipmentSlot.MAINHAND);
            }
            else
            {
                stack.damage(1, miner, EquipmentSlot.MAINHAND);
            }
        }
        return true;
    }

    @Override
    public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player)
    {
        BlockPos thisPos = player.getBlockPos();
        SoundEvent craftingSound;

        // Different crafting sounds based on the chisel type
        if ( stack.isOf(ModItems.CHISEL_WOOD) )
        {
            craftingSound = SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR;
        }
        else if ( stack.isOf(ModItems.CHISEL_STONE) )
        {
            craftingSound = SoundEvents.BLOCK_ANVIL_LAND;
        }
        else
        {
            craftingSound = SoundEvents.BLOCK_ANVIL_USE;
        }

        world.playSound(player, thisPos, craftingSound, SoundCategory.BLOCKS, 0.2f, 1.2f);
        player.tick();
    }
}

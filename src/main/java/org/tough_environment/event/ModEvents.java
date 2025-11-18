package org.tough_environment.event;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.block.BlockBreakHandler;
import org.tough_environment.block.blocks.StoneConvertingBlock;
import org.tough_environment.item.component.ModToolComponents;

public class ModEvents {


    //public static RegistryEntry<EntityAttribute> HARDCORE_BLOCK_BREAKING_SPEED = registerVanilla("player.block_break_speed", 0.16F, 0.0F, 1024.0F);;

    public static RegistryEntry<EntityAttribute> register(String id, double fallback, double min, double max) {
        return register(id, createClampedAttribute(id, fallback, min, max));
    }

    public static RegistryEntry<EntityAttribute> registerVanilla(String id, double fallback, double min, double max) {
        return register(id, createOfVanilla(id, fallback, min, max));
    }

    public static RegistryEntry<EntityAttribute> register(String id, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, Identifier.of(ToughEnvironmentMod.MOD_ID, id), attribute);
    }

    public static ClampedEntityAttribute createClampedAttribute(String attributeName, double fallback, double min, double max) {
        return new ClampedEntityAttribute("attribute." + ToughEnvironmentMod.MOD_ID + "." + attributeName, fallback, min, max);
    }

    private static ClampedEntityAttribute createOfVanilla(String attributeName, double fallback, double min, double max) {
        return new ClampedEntityAttribute("attribute." + "minecraft" + "." + attributeName, fallback, min, max);
    }

    public static void register() {
        // Player Block Events
        PlayerBlockBreakEvents.AFTER.register(ModEvents::onAfterBlockBreak);
        // Item Component Events
        //DefaultItemComponentEvents.MODIFY.register(ModEvents::modifyToolComponents);

    }
    
    private static void modifyToolComponents(DefaultItemComponentEvents.ModifyContext context) {
        modifyPrimitiveTools(context);
        modifyModernTools(context);
        modifyAdvancedTools(context);
    }

    private static void modifyPrimitiveTools(DefaultItemComponentEvents.ModifyContext context) {
        context.modify(Items.WOODEN_PICKAXE, builder -> modifyToolEntry(builder, ModToolComponents.PRIMITIVE_WOODEN_PICKAXE));
        context.modify(Items.STONE_PICKAXE, builder -> modifyToolEntry(builder, ModToolComponents.PRIMITIVE_STONE_PICKAXE));
        context.modify(Items.WOODEN_AXE, builder -> modifyToolEntry(builder, ModToolComponents.PRIMITIVE_WOODEN_AXE));
        context.modify(Items.STONE_AXE, builder -> modifyToolEntry(builder, ModToolComponents.PRIMITIVE_STONE_AXE));
        context.modify(Items.WOODEN_SHOVEL, builder -> modifyToolEntry(builder, ModToolComponents.PRIMITIVE_WOODEN_SHOVEL));
        context.modify(Items.STONE_SHOVEL, builder -> modifyToolEntry(builder, ModToolComponents.PRIMITIVE_STONE_SHOVEL));
        context.modify(Items.WOODEN_HOE, builder -> modifyToolEntry(builder, ModToolComponents.PRIMITIVE_WOODEN_HOE));
        context.modify(Items.STONE_HOE, builder -> modifyToolEntry(builder, ModToolComponents.PRIMITIVE_STONE_HOE));
    }
    
    private static void modifyModernTools(DefaultItemComponentEvents.ModifyContext context) {
        context.modify(Items.GOLDEN_PICKAXE, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_GOLDEN_PICKAXE));
        context.modify(Items.IRON_PICKAXE, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_IRON_PICKAXE));
        context.modify(Items.DIAMOND_PICKAXE, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_DIAMOND_PICKAXE));

        context.modify(Items.GOLDEN_AXE, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_GOLDEN_AXE));
        context.modify(Items.IRON_AXE, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_IRON_AXE));
        context.modify(Items.DIAMOND_AXE, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_DIAMOND_AXE));

        context.modify(Items.GOLDEN_SHOVEL, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_GOLDEN_SHOVEL));
        context.modify(Items.IRON_SHOVEL, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_IRON_SHOVEL));
        context.modify(Items.IRON_SHOVEL, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_DIAMOND_SHOVEL));

        context.modify(Items.GOLDEN_HOE, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_GOLDEN_HOE));
        context.modify(Items.IRON_HOE, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_IRON_HOE));
        context.modify(Items.IRON_HOE, builder -> modifyToolEntry(builder, ModToolComponents.MODERN_DIAMOND_HOE));
    }
    
    private static void modifyAdvancedTools(DefaultItemComponentEvents.ModifyContext context) {
        context.modify(Items.NETHERITE_PICKAXE, builder -> modifyToolEntry(builder, ModToolComponents.ADVANCED_NETHERITE_PICKAXE));
        context.modify(Items.NETHERITE_AXE, builder -> modifyToolEntry(builder, ModToolComponents.ADVANCED_NETHERITE_AXE));
        context.modify(Items.NETHERITE_SHOVEL, builder -> modifyToolEntry(builder, ModToolComponents.ADVANCED_NETHERITE_SHOVEL));
        context.modify(Items.NETHERITE_HOE, builder -> modifyToolEntry(builder, ModToolComponents.ADVANCED_NETHERITE_HOE));
    }

    private static void onAfterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (world.isClient) return;

        ItemStack tool = player.getMainHandStack();

        if (BlockBreakHandler.getInstance().shouldPlayCrackingSound(state, tool) && !player.isCreative()) {
            world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS,
                    0.5F, 1.75F + world.random.nextFloat() * 0.25F
            );
        }

        if (state.isIn(BTWRConventionalTags.Blocks.LOOSEN_ON_IMPROPER_BREAK) || state.isIn(BTWRConventionalTags.Blocks.LOOSEN_ON_IMPROPER_BREAK_SLABS)) {
            BlockBreakHandler.getInstance().setStateForDirt(world, pos, state, player);
        }

        BlockBreakHandler.getInstance().setStateForStone(world, pos, state, player);

        if (state.getBlock() instanceof StoneConvertingBlock) {
            BlockBreakHandler.getInstance().setStateForConvertedStone(world, pos, state, player);
        }
    }

    private static void modifyToolEntry(ComponentMap.Builder builder, ToolComponent toolComponent) {
        builder.getOrCreate(DataComponentTypes.TOOL, () -> toolComponent);
    }

}

package org.tough_environment.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.item.items.ChiselItem;


public class ModItems
{

    // GROUPS
    // Main TE Group (Tough environment) for testing. Might split items later on...
    public static final Item GROUP_TE = register( "group_te", new Item(new Item.Settings()));


    public static final Item PILE_DIRT = register( "pile_dirt", new Item(new Item.Settings()));
    public static final Item PILE_GRAVEL = register( "pile_gravel", new Item(new Item.Settings()));
    public static final Item PILE_SAND = register( "pile_sand", new Item(new Item.Settings()));
    public static final Item PILE_RED_SAND = register( "pile_red_sand", new Item(new Item.Settings()));
    public static final Item PILE_CLAY = register( "pile_clay", new Item(new Item.Settings()));


    public static final Item SMALL_STONE = register( "small_stone", new Item(new Item.Settings()));
    public static final Item SMALL_STONE_1 = register( "small_stone_1", new Item(new Item.Settings()));
    public static final Item SMALL_STONE_2 = register( "small_stone_2", new Item(new Item.Settings()));

    public static final Item SHARD_GRANITE = register( "shard_granite", new Item(new Item.Settings()));
    public static final Item SHARD_ANDESITE = register( "shard_andesite", new Item(new Item.Settings()));
    public static final Item SHARD_DIORITE = register( "shard_diorite", new Item(new Item.Settings()));

    public static final Item DUST_COAL = register( "dust_coal", new Item (new Item.Settings()));
    public static final Item DUST_IRON = register( "dust_iron", new Item (new Item.Settings()));
    public static final Item DUST_GOLD = register( "dust_gold", new Item (new Item.Settings()));
    public static final Item DUST_COPPER = register( "dust_copper", new Item (new Item.Settings()));



    // Tool Items
    public static final Item CHISEL_WOOD = register( "chisel_wood",
            new ChiselItem(ChiselToolMaterials.WOOD, new Item.Settings()
                    .attributeModifiers(ChiselItem.createAttributeModifiers(ChiselToolMaterials.WOOD, -1f, -0.4f))
            ));
    public static final Item CHISEL_STONE = register( "chisel_stone",
            new ChiselItem(ChiselToolMaterials.STONE, new Item.Settings()
                    .attributeModifiers(ChiselItem.createAttributeModifiers(ChiselToolMaterials.STONE, -2f, -2.2f))
            ));

    public static final Item CHISEL_IRON = register( "chisel_iron",
            new ChiselItem(ChiselToolMaterials.IRON, new Item.Settings()
                    .attributeModifiers(ChiselItem.createAttributeModifiers(ChiselToolMaterials.IRON, -3f, -2.5f))
            ));

    public static final Item CHISEL_DIAMOND = register("chisel_diamond",
            new ChiselItem(ChiselToolMaterials.DIAMOND, new Item.Settings()
                    .attributeModifiers(ChiselItem.createAttributeModifiers(ChiselToolMaterials.DIAMOND, -4, -2.8F))
            ));


    private static Item register(String name, Item item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(ToughEnvironmentMod.MOD_ID, name), item);
    }

    public static void registerModItems()
    {
        ToughEnvironmentMod.LOGGER.info("Registering Mod Items for " + ToughEnvironmentMod.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(BTWR_Items::addItemsToIngredientItemGroup);
    }
}

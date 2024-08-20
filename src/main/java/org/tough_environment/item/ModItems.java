package org.tough_environment.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.item.items.ChiselItem;


public class ModItems
{

    // GROUPS
    // Main TE Group (Tough environment) for testing. Might split items later on...
    public static final Item GROUP_TE = registerItem( "group_te", new Item(new Item.Settings()));


    public static final Item PILE_DIRT = registerItem( "pile_dirt", new Item(new Item.Settings()));
    public static final Item PILE_GRAVEL = registerItem( "pile_gravel", new Item(new Item.Settings()));
    public static final Item PILE_SAND = registerItem( "pile_sand", new Item(new Item.Settings()));
    public static final Item PILE_RED_SAND = registerItem( "pile_red_sand", new Item(new Item.Settings()));
    public static final Item PILE_CLAY = registerItem( "pile_clay", new Item(new Item.Settings()));


    public static final Item SMALL_STONE = registerItem( "small_stone", new Item(new Item.Settings()));
    public static final Item SMALL_STONE_1 = registerItem( "small_stone_1", new Item(new Item.Settings()));
    public static final Item SMALL_STONE_2 = registerItem( "small_stone_2", new Item(new Item.Settings()));

    public static final Item SHARD_GRANITE = registerItem( "shard_granite", new Item(new Item.Settings()));
    public static final Item SHARD_ANDESITE = registerItem( "shard_andesite", new Item(new Item.Settings()));
    public static final Item SHARD_DIORITE = registerItem( "shard_diorite", new Item(new Item.Settings()));

    public static final Item DUST_COAL = registerItem( "dust_coal", new Item (new Item.Settings()));
    public static final Item DUST_IRON = registerItem( "dust_iron", new Item (new Item.Settings()));
    public static final Item DUST_GOLD = registerItem( "dust_gold", new Item (new Item.Settings()));
    public static final Item DUST_COPPER = registerItem( "dust_copper", new Item (new Item.Settings()));



    // Tool Items
    public static final Item CHISEL_WOOD = registerItem( "chisel_wood",
            new ChiselItem(ModToolMaterials.WOOD_CHISEL, ChiselItem.Type.WOOD,
                    new Item.Settings().attributeModifiers(ChiselItem.createAttributeModifiers())
                            .component(DataComponentTypes.TOOL, ChiselItem.createToolComponent(-0.02f))
            ));
    public static final Item CHISEL_STONE = registerItem( "chisel_stone",
            new ChiselItem(ModToolMaterials.STONE_CHISEL, ChiselItem.Type.STONE,
                    new Item.Settings().attributeModifiers(ChiselItem.createAttributeModifiers())
                            .component(DataComponentTypes.TOOL, ChiselItem.createToolComponent(-0.05f))
            ));
    public static final Item CHISEL_IRON = registerItem( "chisel_iron",
            new ChiselItem(ModToolMaterials.IRON_CHISEL, ChiselItem.Type.IRON,
                    new Item.Settings().attributeModifiers(ChiselItem.createAttributeModifiers())
                            .component(DataComponentTypes.TOOL, ChiselItem.createToolComponent(-0.10f))
            ));
    public static final Item CHISEL_DIAMOND = registerItem( "chisel_diamond",
            new ChiselItem(ModToolMaterials.DIAMOND_CHISEL, ChiselItem.Type.DIAMOND,
                    new Item.Settings().attributeModifiers(ChiselItem.createAttributeModifiers())
                            .component(DataComponentTypes.TOOL, ChiselItem.createToolComponent(-0.12f))
            ));


    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(ToughEnvironmentMod.MOD_ID, name), item);
    }

    public static void registerModItems()
    {
        ToughEnvironmentMod.LOGGER.info("Registering Mod Items for " + ToughEnvironmentMod.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(BTWR_Items::addItemsToIngredientItemGroup);
    }
}

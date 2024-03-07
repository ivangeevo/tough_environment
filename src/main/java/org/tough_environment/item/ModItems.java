package org.tough_environment.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.item.items.ChiselItem;

public class ModItems
{

    // GROUPS
    public static final Item GROUP_TE = registerItem( "group_te", new Item(new FabricItemSettings()));


    public static final Item PILE_DIRT = registerItem( "pile_dirt", new Item(new FabricItemSettings()));
    public static final Item PILE_GRAVEL = registerItem( "pile_gravel", new Item(new FabricItemSettings()));
    public static final Item PILE_SAND = registerItem( "pile_sand", new Item(new FabricItemSettings()));

    public static final Item PILE_RED_SAND = registerItem( "pile_red_sand", new Item(new FabricItemSettings()));

    public static final Item PILE_CLAY = registerItem( "pile_clay", new Item(new FabricItemSettings()));


    // ToolItems //

    public static final Item CHISEL_WOOD = registerItem( "chisel_wood",
            new ChiselItem( 0.02f, 0.15f, ToolMaterials.WOOD, ChiselItem.ChiselType.WOOD,
                    new FabricItemSettings().maxDamage(2)));
    public static final Item CHISEL_STONE = registerItem( "chisel_stone",
            new ChiselItem(0.05f,-1f, ToolMaterials.STONE, ChiselItem.ChiselType.STONE,
                    new FabricItemSettings().maxDamage(10)));
    public static final Item CHISEL_IRON = registerItem( "chisel_iron",
            new ChiselItem(0.10f,0.2f, ToolMaterials.IRON, ChiselItem.ChiselType.IRON,
                    new FabricItemSettings().maxDamage(50)));

    public static final Item CHISEL_DIAMOND = registerItem( "chisel_diamond",
            new ChiselItem(0.12f,0.2f, ToolMaterials.DIAMOND, ChiselItem.ChiselType.DIAMOND,
                    new FabricItemSettings().maxDamage(550)));


    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries)
    {

        entries.add(CHISEL_WOOD);
        //Add more if needed
    }



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

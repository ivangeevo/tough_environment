package org.tough_environment.item;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
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
    public static final Item GROUP_TE = registerItem( "group_te", new Item(new FabricItemSettings()));


    public static final Item PILE_DIRT = registerItem( "pile_dirt", new Item(new FabricItemSettings()));
    public static final Item PILE_GRAVEL = registerItem( "pile_gravel", new Item(new FabricItemSettings()));
    public static final Item PILE_SAND = registerItem( "pile_sand", new Item(new FabricItemSettings()));
    public static final Item PILE_RED_SAND = registerItem( "pile_red_sand", new Item(new FabricItemSettings()));
    public static final Item PILE_CLAY = registerItem( "pile_clay", new Item(new FabricItemSettings()));


    public static final Item SMALL_STONE = registerItem( "small_stone", new Item(new FabricItemSettings()));
    public static final Item SMALL_STONE_1 = registerItem( "small_stone_1", new Item(new FabricItemSettings()));
    public static final Item SMALL_STONE_2 = registerItem( "small_stone_2", new Item(new FabricItemSettings()));

    public static final Item SHARD_GRANITE = registerItem( "shard_granite", new Item(new FabricItemSettings()));
    public static final Item SHARD_ANDESITE = registerItem( "shard_andesite", new Item(new FabricItemSettings()));
    public static final Item SHARD_DIORITE = registerItem( "shard_diorite", new Item(new FabricItemSettings()));

    public static final Item DUST_COAL = registerItem( "dust_coal", new Item (new FabricItemSettings()));

    public static final Item DUST_IRON = registerItem( "dust_iron", new Item (new FabricItemSettings()));



    // Tool Items
    public static final Item CHISEL_WOOD = registerItem( "chisel_wood",
            new ChiselItem( -0.02f, 0.15f, ToolMaterials.WOOD, ChiselItem.ChiselType.WOOD,
                    new FabricItemSettings().maxDamage(2)));
    public static final Item CHISEL_STONE = registerItem( "chisel_stone",
            new ChiselItem(-0.05f,-1f, ToolMaterials.STONE, ChiselItem.ChiselType.STONE,
                    new FabricItemSettings().maxDamage(10)));
    public static final Item CHISEL_IRON = registerItem( "chisel_iron",
            new ChiselItem(-0.10f,0.2f, ToolMaterials.IRON, ChiselItem.ChiselType.IRON,
                    new FabricItemSettings().maxDamage(238)));
    public static final Item CHISEL_DIAMOND = registerItem( "chisel_diamond",
            new ChiselItem(-0.12f,0.2f, ToolMaterials.DIAMOND, ChiselItem.ChiselType.DIAMOND,
                    new FabricItemSettings().maxDamage(500)));


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

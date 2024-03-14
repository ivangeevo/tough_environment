package org.tough_environment.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;

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

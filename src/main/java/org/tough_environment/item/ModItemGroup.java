package org.tough_environment.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.block.ModBlocks;

public class ModItemGroup
{
    public static void registerItemGroups()
    {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(ToughEnvironmentMod.MOD_ID, "group_te"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemgroup.group_te"))
                        .icon(() -> new ItemStack(ModItems.GROUP_TE))
                        .entries((displayContext, entries) -> {

                            /** Items **/

                            // Piles & Dust
                            entries.add(ModItems.PILE_CLAY);
                            entries.add(ModItems.PILE_DIRT);
                            entries.add(ModItems.PILE_GRAVEL);
                            entries.add(ModItems.PILE_SAND);
                            entries.add(ModItems.PILE_RED_SAND);

                            entries.add(ModItems.DUST_COAL);
                            entries.add(ModItems.DUST_IRON);
                            entries.add(ModItems.DUST_GOLD);
                            entries.add(ModItems.DUST_COPPER);


                            // Stones, Shards, Crystals & Ore Chunks
                            entries.add(ModItems.SMALL_STONE);
                            entries.add(ModItems.SMALL_STONE_1);
                            entries.add(ModItems.SMALL_STONE_2);
                            entries.add(ModItems.SHARD_GRANITE);
                            entries.add(ModItems.SHARD_ANDESITE);
                            entries.add(ModItems.SHARD_DIORITE);

                            // Tools
                            entries.add(ModItems.CHISEL_WOOD);
                            entries.add(ModItems.CHISEL_STONE);
                            entries.add(ModItems.CHISEL_IRON);
                            entries.add(ModItems.CHISEL_DIAMOND);

                            // Nuggets
                            entries.add(ModItems.COPPER_NUGGET);
                            entries.add(ModItems.NETHERITE_NUGGET);

                            // Bricks
                            entries.add(ModItems.STONE_BRICK);
                            //entries.add(ModItems.STONE_BRICK_1); add later when 2nd strata is available in any mod( mine or primetoxinz's)
                            entries.add(ModItems.STONE_BRICK_2);

                            entries.add(ModItems.NETHER_BRICK_UNFIRED);

                            entries.add(ModItems.NETHER_SLUDGE);




                            /** Blocks **/

                            // Slab blocks
                            entries.add(ModBlocks.SLAB_DIRT);
                            entries.add(ModBlocks.SLAB_SAND);
                            entries.add(ModBlocks.SLAB_RED_SAND);
                            entries.add(ModBlocks.SLAB_GRAVEL);
                            entries.add(ModBlocks.SLAB_COBBLESTONE_LOOSE);
                            entries.add(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE);
                            entries.add(ModBlocks.SLAB_GRANITE_LOOSE);
                            entries.add(ModBlocks.SLAB_ANDESITE_LOOSE);
                            entries.add(ModBlocks.SLAB_DIORITE_LOOSE);
                            entries.add(ModBlocks.SLAB_BRICKS_LOOSE);
                            entries.add(ModBlocks.SLAB_STONE_BRICKS_LOOSE);
                            entries.add(ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE);
                            entries.add(ModBlocks.SLAB_NETHER_BRICKS_LOOSE);

                            entries.add(ModBlocks.SLAB_WHITE_STONE);
                            entries.add(ModBlocks.SLAB_WHITE_COBBLESTONE);

                            entries.add(ModBlocks.SLAB_DIRT_PACKED);


                            // Full blocks
                            entries.add(ModBlocks.DIRT_LOOSE);
                            entries.add(ModBlocks.COBBLESTONE_LOOSE);
                            entries.add(ModBlocks.COBBLED_DEEPSLATE_LOOSE);
                            entries.add(ModBlocks.GRANITE_LOOSE);
                            entries.add(ModBlocks.ANDESITE_LOOSE);
                            entries.add(ModBlocks.DIORITE_LOOSE);
                            entries.add(ModBlocks.CALCITE_LOOSE);
                            entries.add(ModBlocks.TUFF_LOOSE);
                            entries.add(ModBlocks.BLACKSTONE_LOOSE);
                            entries.add(ModBlocks.BASALT_LOOSE);
                            entries.add(ModBlocks.END_STONE_LOOSE);
                            entries.add(ModBlocks.BRICKS_LOOSE);
                            entries.add(ModBlocks.STONE_BRICKS_LOOSE);
                            entries.add(ModBlocks.DEEPSLATE_BRICKS_LOOSE);
                            entries.add(ModBlocks.NETHER_BRICKS_LOOSE);

                            entries.add(ModBlocks.CLAY_ORE);
                            entries.add(ModBlocks.WHITE_STONE);
                            entries.add(ModBlocks.WHITE_COBBLESTONE);

                            entries.add(ModBlocks.DIRT_PACKED);

                            entries.add(ModBlocks.COBBLESTONE_LOOSE_STAIRS);
                            entries.add(ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS);
                            entries.add(ModBlocks.ANDESITE_LOOSE_STAIRS);
                            entries.add(ModBlocks.GRANITE_LOOSE_STAIRS);
                            entries.add(ModBlocks.DIORITE_LOOSE_STAIRS);
                            entries.add(ModBlocks.BRICKS_LOOSE_STAIRS);
                            entries.add(ModBlocks.STONE_BRICKS_LOOSE_STAIRS);
                            entries.add(ModBlocks.DEEPSLATE_BRICKS_LOOSE_STAIRS);
                            entries.add(ModBlocks.NETHER_BRICKS_LOOSE_STAIRS);

                            entries.add(ModBlocks.WHITE_STONE_STAIRS);
                            entries.add(ModBlocks.WHITE_COBBLESTONE_STAIRS);


                        }).build());

        addToGroups();


        // log message into console
        ToughEnvironmentMod.LOGGER.info("Registering Item Groups for " + ToughEnvironmentMod.MOD_ID);
    }


    public static void addToGroups() {
        // add items to item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries ->
        {
            entries.add(ModItems.COPPER_NUGGET);
            entries.add(ModItems.NETHERITE_NUGGET);

        });
    }

}

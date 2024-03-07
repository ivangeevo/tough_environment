package org.tough_environment.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.block.ModBlocks;

public class ModItemGroup
{

    public static final ItemGroup GROUP_TE = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ToughEnvironmentMod.MOD_ID, "group_te"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.group_te"))
                    .icon(() -> new ItemStack(ModItems.GROUP_TE)).entries((displayContext, entries) -> {

                        /** Items **/
                        // Tools
                        entries.add(ModItems.CHISEL_WOOD);
                        entries.add(ModItems.CHISEL_STONE);
                        entries.add(ModItems.CHISEL_IRON);
                        entries.add(ModItems.CHISEL_DIAMOND);

                        // Piles
                        entries.add(ModItems.PILE_CLAY);
                        entries.add(ModItems.PILE_DIRT);
                        entries.add(ModItems.PILE_GRAVEL);
                        entries.add(ModItems.PILE_SAND);
                        entries.add(ModItems.PILE_RED_SAND);

                        // Shards & Crystals
                        entries.add(ModItems.SMALL_STONE);
                        entries.add(ModItems.SMALL_STONE_1);
                        entries.add(ModItems.SMALL_STONE_2);
                        entries.add(ModItems.SHARD_GRANITE);
                        entries.add(ModItems.SHARD_ANDESITE);




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
                        entries.add(ModBlocks.SLAB_BRICK_LOOSE);

                        // Full blocks
                        entries.add(ModBlocks.COBBLESTONE_LOOSE);
                        entries.add(ModBlocks.COBBLED_DEEPSLATE_LOOSE);
                        entries.add(ModBlocks.GRANITE_LOOSE);
                        entries.add(ModBlocks.ANDESITE_LOOSE);














                    }).build());

    public static void registerItemGroups()
    {
        ToughEnvironmentMod.LOGGER.info("Registering Item Groups for " + ToughEnvironmentMod.MOD_ID);

    }
}

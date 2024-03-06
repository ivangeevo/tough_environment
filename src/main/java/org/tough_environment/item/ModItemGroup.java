package org.tough_environment.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;

public class ModItemGroup
{

    public static final ItemGroup GROUP_BTWR = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ToughEnvironmentMod.MOD_ID, "group_te"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.group_te"))
                    .icon(() -> new ItemStack(ModItems.GROUP_TE)).entries((displayContext, entries) -> {

                        entries.add(ModItems.CHISEL_WOOD);
                        entries.add(ModItems.CHISEL_STONE);
                        entries.add(ModItems.CHISEL_IRON);
                        entries.add(ModItems.CHISEL_DIAMOND);

                    }).build());

    public static void registerItemGroups()
    {
        /**
        // Example of adding to existing Item Group
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(BTWR_Items.CREEPER_OYSTERS);
            entries.add(BTWR_Items.CLUB_BONE);
            entries.add(BTWR_Items.CLUB_WOOD);
            entries.add(BTWR_Items.DIAMOND_INGOT);
            entries.add(BTWR_Items.DIAMOND_SHEARS);


        });
         **/
    }
}

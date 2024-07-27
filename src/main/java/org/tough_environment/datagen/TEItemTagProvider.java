package org.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class TEItemTagProvider extends FabricTagProvider.ItemTagProvider
{
    public TEItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        RegistryEntryLookup<Item> itemLookup = arg.getWrapperOrThrow(RegistryKeys.ITEM);

        this.addToConventionalTags(itemLookup);
        this.addToModTags();
        this.addToVanillaTags();
    }


    private void addToConventionalTags(RegistryEntryLookup<Item> itemLookup) {

        getOrCreateTagBuilder(BTWRConventionalTags.Items.PRIMITIVE_CHISELS)
                .add(ModItems.CHISEL_WOOD)
                .add(ModItems.CHISEL_STONE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.MODERN_CHISELS)
                .add(ModItems.CHISEL_IRON)
                .add(ModItems.CHISEL_DIAMOND);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.PRIMITIVE_SHOVELS)
                .add(Items.WOODEN_SHOVEL)
                .add(Items.STONE_SHOVEL);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.MODERN_SHOVELS)
                .add(Items.IRON_SHOVEL)
                .add(Items.GOLDEN_SHOVEL)
                .add(Items.DIAMOND_SHOVEL)
                .add(Items.NETHERITE_SHOVEL);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ADVANCED_SHOVELS)
                .add(Items.NETHERITE_SHOVEL)
                .addOptional(new Identifier("bwt", "netherite_mattock"));


        getOrCreateTagBuilder(BTWRConventionalTags.Items.SHOVELS_HARVEST_FULL_BLOCK)
                .addTag(BTWRConventionalTags.Items.MODERN_SHOVELS)
                .addTag(BTWRConventionalTags.Items.ADVANCED_SHOVELS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.FULLY_MINES_STRATA_1)
                .add(Items.IRON_PICKAXE)
                .addTag(BTWRConventionalTags.Items.FULLY_MINES_STRATA_2);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.FULLY_MINES_STRATA_2)
                .add(Items.GOLDEN_PICKAXE)
                .addTag(BTWRConventionalTags.Items.FULLY_MINES_STRATA_3);


        getOrCreateTagBuilder(BTWRConventionalTags.Items.FULLY_MINES_STRATA_3)
                .add(Items.DIAMOND_PICKAXE)
                .add(Items.NETHERITE_PICKAXE)
                .addOptional(new Identifier("bwt", "netherite_mattock"));
    }


    private void addToModTags() {
        getOrCreateTagBuilder(ModTags.Items.SMALL_STONES)
                .add(ModItems.SMALL_STONE)
                .add(ModItems.SMALL_STONE_1)
                .add(ModItems.SMALL_STONE_2);


        getOrCreateTagBuilder(ModTags.Items.MORTARING_ITEMS)
                .add(Items.CLAY_BALL)
                .add(Items.SLIME_BALL);

        getOrCreateTagBuilder(ModTags.Items.SHOULD_NOT_MERGE)
                .add(ModItems.PILE_DIRT)
                .add(ModItems.PILE_SAND)
                .add(ModItems.PILE_RED_SAND)
                .add(ModItems.PILE_GRAVEL)
                .add(ModItems.PILE_CLAY)
                .add(ModItems.SMALL_STONE)
                .add(ModItems.SMALL_STONE_1)
                .add(ModItems.SMALL_STONE_2)
                .add(ModItems.SHARD_ANDESITE)
                .add(ModItems.SHARD_GRANITE)
                .add(ModItems.SHARD_DIORITE)

                // vanilla ores
                .add(Items.COAL)
                .add(Items.RAW_IRON)
                .add(Items.RAW_COPPER)
                .add(Items.RAW_GOLD)
                .add(Items.REDSTONE)
                .add(Items.COAL)
                .add(Items.COAL)
                .add(Items.COAL)


        ;

    }

    private void addToVanillaTags() {
        getOrCreateTagBuilder(ItemTags.STONE_TOOL_MATERIALS)
                .add(ModItems.SMALL_STONE)
                .add(ModItems.SMALL_STONE_1)
                .add(ModItems.SMALL_STONE_2);

        getOrCreateTagBuilder(ItemTags.STONE_CRAFTING_MATERIALS)
                .add(ModBlocks.COBBLESTONE_LOOSE.asItem());

    }

    private Item getItemFromOtherMod(RegistryEntryLookup<Item> itemLookup, String modId, String itemPath) {
        Identifier identifier = new Identifier(modId, itemPath);
        Optional<RegistryEntry.Reference<Item>> entryOpt = itemLookup.getOptional(RegistryKey.of(RegistryKeys.ITEM, identifier));

        if (entryOpt.isPresent()) {
            return entryOpt.get().value();
        } else {
            // Use the fake item if the real one is not found
            return getFakeItem(modId, itemPath);
        }
    }


    private Item getFakeItem(String modId, String itemPath) {
        // Use Minecraft's Item class directly as a placeholder
        // This does not need to be an actual instance
        return new Item(new Item.Settings()); // Empty settings
    }


}

package org.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;
import org.tough_environment.tag.BTWRConventionalTags;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TELootTableProvider extends FabricBlockLootTableProvider
{


    public TELootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public static final LootCondition.Builder WITH_SHOVEL_FULLY_HARVESTS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.SHOVELS_HARVEST_FULL_BLOCK));


    @Override
    public void generate()
    {
        this.generateVanillaTables();

    }

    private void generateVanillaTables()
    {
    }

    public static LootTable.Builder aggregateBlockDrops(Block minedBlock, Item looseDrop, Item pileDrop) {
        // Create loot pool for shovels
        LootPool.Builder shovelPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(looseDrop))
                .conditionally(WITH_SHOVEL_FULLY_HARVESTS);

        // Create loot pool for piles when not using a shovel
        LootPool.Builder pilePool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(pileDrop)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(6.0f))))
                .conditionally(WITH_SHOVEL_FULLY_HARVESTS.invert());

        // Combine both pools into the loot table
        return LootTable.builder()
                .pool(shovelPool)
                .pool(pilePool);
    }

    // Add hoe logic for seeds
    public static LootTable.Builder addHoeLogic(Block block, Item seedItem, Map<Item, Float> hoeChances) {
        LootPool.Builder hoePool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f));

        for (Map.Entry<Item, Float> entry : hoeChances.entrySet()) {
            Item hoe = entry.getKey();
            float chance = entry.getValue();

            hoePool.with(ItemEntry.builder(seedItem)
                    .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(hoe)))
                    .conditionally(RandomChanceLootCondition.builder(chance)));
        }

        return LootTable.builder().pool(hoePool);
    }

    /**
    // Method to get the loot table
    public static LootTable.Builder getLootTable(Block block, Item looseDrop, Item pileDrop, Item seedItem, Map<Item, Float> hoeChances) {
        return aggregateBlockDrops(block, looseDrop, pileDrop)
                .pool(addHoeLogic(block, seedItem, hoeChances));
    }
     **/
}

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
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryWrapper;
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

        addDrop(Blocks.SAND, looseAggregateDrops(Blocks.SAND, Blocks.SAND, ModItems.PILE_SAND, 6));
        addDrop(Blocks.RED_SAND, looseAggregateDrops(Blocks.RED_SAND, Blocks.RED_SAND, ModItems.PILE_RED_SAND, 6));
        addDrop(Blocks.GRAVEL, looseAggregateDrops(Blocks.GRAVEL, Blocks.GRAVEL, ModItems.PILE_GRAVEL, 6));


    }

    public LootTable.Builder looseAggregateDrops(Block dropWithSilkTouch, Block looseDrop, Item pileDrop, float pileDropCount) {
        // Create an alternative entry with Silk Touch, Shovel, and default pile drop conditions
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                ItemEntry.builder(dropWithSilkTouch).conditionally(WITH_SILK_TOUCH),
                ItemEntry.builder(looseDrop).conditionally(WITH_SHOVEL_FULLY_HARVESTS),
                ItemEntry.builder(pileDrop).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(pileDropCount)))
        );

        // Create the loot pool with alternative entries
        LootPool.Builder mainPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(alternativeEntry)
                .conditionally(SurvivesExplosionLootCondition.builder());

        // Combine into the loot table
        return LootTable.builder().pool(mainPool);
    }








    // Add hoe logic for seeds
    public LootPool addHoeLogic(Item seedItem, Map<Item, Float> dropChance) {
        LootPool.Builder hoePool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f));

        for (Map.Entry<Item, Float> entry : dropChance.entrySet()) {
            Item hoe = entry.getKey();
            float chance = entry.getValue();

            hoePool.with(ItemEntry.builder(seedItem)
                    .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(hoe)))
                    .conditionally(RandomChanceLootCondition.builder(chance)));
        }

        return hoePool.build();
    }


}

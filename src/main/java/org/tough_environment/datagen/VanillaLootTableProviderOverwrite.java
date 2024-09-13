package org.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;
import org.tough_environment.tag.BTWRConventionalTags;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class VanillaLootTableProviderOverwrite extends FabricBlockLootTableProvider
{
    public static final LootCondition.Builder WITH_SHOVEL_FULLY_HARVESTS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.SHOVELS_HARVEST_FULL_BLOCK));
    public static final LootCondition.Builder WITHOUT_HOE = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.HOES)).invert();

    public VanillaLootTableProviderOverwrite(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(Blocks.SAND, dropsForLooseAggregate(Blocks.SAND, Blocks.SAND, ModItems.PILE_SAND, 6, null, null, null));
        addDrop(Blocks.RED_SAND, dropsForLooseAggregate(Blocks.RED_SAND, Blocks.RED_SAND, ModItems.PILE_RED_SAND, 6, null, null, null));
        addDrop(Blocks.GRAVEL, dropsForLooseAggregate(Blocks.GRAVEL, Blocks.GRAVEL, ModItems.PILE_GRAVEL, 6, List.of(new AdditionalDrop(Items.FLINT, 1, 0.1f)), null, null));

        addDrop(Blocks.GRASS_BLOCK, dropsForLooseAggregate(Blocks.GRASS_BLOCK, ModBlocks.DIRT_LOOSE, ModItems.PILE_DIRT, 6, null, Map.of(
                Items.IRON_HOE, 0.03f,
                Items.GOLDEN_HOE, 0.04f,
                Items.DIAMOND_HOE, 0.06f,
                Items.NETHERITE_HOE, 0.11f
        ), Items.WHEAT_SEEDS));

        addDrop(Blocks.STONE,
                dropsForStoneBlocks(Blocks.STONE, ModItems.SMALL_STONE, 3, ModBlocks.COBBLESTONE_LOOSE,
                        List.of(new StoneDropEntry(ModItems.PILE_GRAVEL, "minecraft:chisel_iron", null)), Map.of(
                                Items.IRON_PICKAXE, 0.05f,
                                Items.DIAMOND_PICKAXE, 0.1f
                        ), Items.STONE_BRICKS, true));
        // Add other stone blocks like granite, diorite, etc.

    }

    public LootTable.Builder dropsForStoneBlocks(Block block, Item smallStone, int smallStoneCount, Block looseDrop, List<StoneDropEntry> stoneDropEntries, Map<Item, Float> pickaxeDrops, Item stoneBrick, boolean dropStoneBrick) {
        LootTable.Builder lootTableBuilder = LootTable.builder();


        // Drop small stone with specified conditions
        LootPool.Builder smallStonePool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(smallStone)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(smallStoneCount)))
                        .conditionally(SurvivesExplosionLootCondition.builder()));

        lootTableBuilder.pool(smallStonePool);

        // Drop cobblestone when no specific tool is used
        LootPool.Builder looseDropPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(looseDrop));

        lootTableBuilder.pool(looseDropPool);

        // Add stone-specific drops based on tool conditions
        for (StoneDropEntry entry : stoneDropEntries) {
            LootPool.Builder stoneDropPool = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(entry.item))
                    .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(TagKey.of(RegistryKeys.ITEM, new Identifier(entry.toolTag)))))
                    .conditionally(entry.extraCondition != null ? entry.extraCondition : SurvivesExplosionLootCondition.builder());

            lootTableBuilder.pool(stoneDropPool);
        }

        // Add custom drops for specific pickaxes
        if (pickaxeDrops != null) {
            for (Map.Entry<Item, Float> entry : pickaxeDrops.entrySet()) {
                Item pickaxe = entry.getKey();
                float chance = entry.getValue();

                LootPool.Builder pickaxeDropPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(stoneBrick)
                                .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(pickaxe)))
                                .conditionally(RandomChanceLootCondition.builder(chance)));

                lootTableBuilder.pool(pickaxeDropPool);
            }
        }

        // Optionally add stone brick drops
        if (dropStoneBrick) {
            LootPool.Builder stoneBrickPool = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(stoneBrick));

            lootTableBuilder.pool(stoneBrickPool);
        }

        return lootTableBuilder;
    }

    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, Item pileDrop, float pileDropCount, List<AdditionalDrop> additionalDrops, Map<Item, Float> hoeDrops, Item hoeDroppedItem) {
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                ItemEntry.builder(dropWithSilkTouch).conditionally(WITH_SILK_TOUCH),
                ItemEntry.builder(looseDrop).conditionally(WITH_SHOVEL_FULLY_HARVESTS),
                ItemEntry.builder(pileDrop).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(pileDropCount)).conditionally(WITHOUT_HOE))
        );

        LootPool.Builder mainPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(alternativeEntry)
                .conditionally(SurvivesExplosionLootCondition.builder());

        LootTable.Builder lootTableBuilder = LootTable.builder().pool(mainPool);

        if (additionalDrops != null) {
            for (AdditionalDrop additionalDrop : additionalDrops) {
                LootPool.Builder additionalPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(additionalDrop.item)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(additionalDrop.count)))
                                .conditionally(RandomChanceLootCondition.builder(additionalDrop.chance)))
                        .conditionally(SurvivesExplosionLootCondition.builder());

                lootTableBuilder.pool(additionalPool);
            }
        }

        if (hoeDrops != null) {
            for (Map.Entry<Item, Float> entry : hoeDrops.entrySet()) {
                Item hoe = entry.getKey();
                float chance = entry.getValue();

                LootPool.Builder hoePool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(hoeDroppedItem)
                                .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(hoe)))
                                .conditionally(RandomChanceLootCondition.builder(chance)));

                lootTableBuilder.pool(hoePool);
            }
        }

        return lootTableBuilder;
    }

    public record AdditionalDrop(Item item, int count, float chance) {}
    public record StoneDropEntry(Item item, String toolTag, LootCondition.Builder extraCondition) {}

}

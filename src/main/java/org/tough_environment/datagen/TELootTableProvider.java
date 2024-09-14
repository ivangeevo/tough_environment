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
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TELootTableProvider extends FabricBlockLootTableProvider
{
    public static final LootCondition.Builder WITH_PICKAXE_FULLY_HARVESTS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.PICKAXES_HARVEST_FULL_BLOCK));
    public static final LootCondition.Builder WITH_SHOVEL_FULLY_HARVESTS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.SHOVELS_HARVEST_FULL_BLOCK));
    public static final LootCondition.Builder WITHOUT_HOE = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.HOES)).invert();

    public TELootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate()
    {
        this.forVanilla();
        this.forMod();
    }

    private void forVanilla()
    {
        addDrop(Blocks.DIRT, dropsForLooseAggregate(ModBlocks.DIRT_LOOSE, ModBlocks.DIRT_LOOSE, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6));
        addDrop(Blocks.SAND, dropsForLooseAggregate(Blocks.SAND, Blocks.SAND, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_SAND, 6));
        addDrop(Blocks.RED_SAND, dropsForLooseAggregate(Blocks.RED_SAND, Blocks.RED_SAND, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_RED_SAND, 6));
        addDrop(Blocks.GRAVEL, dropsForLooseAggregate(Blocks.GRAVEL, Blocks.GRAVEL, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_GRAVEL, 6,
                List.of(new AdditionalDrop(Items.FLINT, 1, 0.1f))));
        addDrop(Blocks.GRASS_BLOCK, dropsForLooseAggregate(Blocks.GRASS_BLOCK, ModBlocks.DIRT_LOOSE, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6,
                null, Map.of(Items.IRON_HOE, 0.03f, Items.GOLDEN_HOE, 0.04f, Items.DIAMOND_HOE, 0.06f, Items.NETHERITE_HOE, 0.11f), Items.WHEAT_SEEDS));

        /**
        addDrop(Blocks.STONE,
                dropsForStoneBlock(Blocks.STONE, ModItems.SMALL_STONE, 3, ModBlocks.COBBLESTONE_LOOSE,
                        List.of(new StoneDropEntry(ModItems.PILE_GRAVEL, ModTags.Items.CHISELS, null)),
                        Map.of(
                                Items.IRON_PICKAXE, 0.05f,
                                Items.DIAMOND_PICKAXE, 0.1f
                        ), Items.STONE_BRICKS, true));
         **/


        // TODO: Add other stone blocks like granite, diorite, etc.

    }

    private void forMod()
    {
        // Loose blocks that have pile items drops
        addDrop(ModBlocks.DIRT_LOOSE, dropsForLooseAggregate(ModBlocks.DIRT_LOOSE, ModBlocks.DIRT_LOOSE, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6));

        // Special case loose blocks with pile drops
        addDrop(ModBlocks.BRICKS_LOOSE, dropsForLooseAggregate(ModBlocks.BRICKS_LOOSE, ModBlocks.BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, Items.BRICK, 8));

        // Loose blocks without a pile item drop
        addDrop(ModBlocks.COBBLESTONE_LOOSE);
        addDrop(ModBlocks.COBBLED_DEEPSLATE_LOOSE);
        addDrop(ModBlocks.GRANITE_LOOSE);
        addDrop(ModBlocks.DIORITE_LOOSE);
        addDrop(ModBlocks.ANDESITE_LOOSE);
        addDrop(ModBlocks.CALCITE_LOOSE);
        addDrop(ModBlocks.TUFF_LOOSE);
        addDrop(ModBlocks.BLACKSTONE_LOOSE);
        addDrop(ModBlocks.BASALT_LOOSE);
        addDrop(ModBlocks.END_STONE_LOOSE);

        // Placed Ore block loot tables
        addDrop(ModBlocks.RAW_IRON_PLACED, Items.RAW_IRON);
        addDrop(ModBlocks.RAW_COPPER_PLACED, Items.RAW_COPPER);
        addDrop(ModBlocks.RAW_GOLD_PLACED, Items.RAW_GOLD);

        // TODO: Add loot tables for Broken stone blocks, converting stone blocks, slab blocks and the stair blocks.

    }

    // Overloaded method: Adds optional stone brick drops
    public LootTable.Builder dropsForStoneBlock(Block block, Item smallStone, int smallStoneCount, Block looseDrop, List<StoneDropEntry> stoneDropEntries, Map<Item, Float> pickaxeDrops, Item stoneBrick, boolean dropStoneBrick) {
        LootTable.Builder lootTableBuilder = dropsForStoneBlock(block, smallStone, smallStoneCount, looseDrop, stoneDropEntries, pickaxeDrops); // Call the previous method

        // Optionally add stone brick drops
        if (dropStoneBrick && stoneBrick != null) {
            LootPool.Builder stoneBrickPool = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(stoneBrick));

            lootTableBuilder.pool(stoneBrickPool);
        }

        return lootTableBuilder;
    }

    // Overloaded method: Adds pickaxe-specific drops
    public LootTable.Builder dropsForStoneBlock(Block block, Item smallStone, int smallStoneCount, Block looseDrop, List<StoneDropEntry> stoneDropEntries, Map<Item, Float> pickaxeDrops) {
        LootTable.Builder lootTableBuilder = dropsForStoneBlock(block, smallStone, smallStoneCount, looseDrop, stoneDropEntries); // Call the previous method

        // Add custom drops for specific pickaxes
        if (pickaxeDrops != null)
        {
            for (Map.Entry<Item, Float> entry : pickaxeDrops.entrySet())
            {
                Item pickaxe = entry.getKey();
                float chance = entry.getValue();

                LootPool.Builder pickaxeDropPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(smallStone) // Adjust based on your drop
                                .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(pickaxe)))
                                .conditionally(RandomChanceLootCondition.builder(chance)));

                lootTableBuilder.pool(pickaxeDropPool);
            }
        }

        return lootTableBuilder;
    }

    // Overloaded method: Adds stone-specific entries
    public LootTable.Builder dropsForStoneBlock(Block block, Item smallStone, int smallStoneCount, Block looseDrop, List<StoneDropEntry> stoneDropEntries) {
        LootTable.Builder lootTableBuilder = dropsForStoneBlock(block, smallStone, smallStoneCount, looseDrop); // Call the previous method

        // Add stone-specific tool drops
        for (StoneDropEntry entry : stoneDropEntries) {
            LootPool.Builder stoneDropPool = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(entry.item))
                    .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(entry.toolTag)))
                    .conditionally(entry.extraCondition != null ? entry.extraCondition : SurvivesExplosionLootCondition.builder());

            lootTableBuilder.pool(stoneDropPool);
        }

        return lootTableBuilder;
    }

    // Overloaded method: Adds loose drop block
    public LootTable.Builder dropsForStoneBlock(Block block, Item smallStone, int smallStoneCount, Block looseDrop) {
        return dropsForStoneBlock(block, smallStone, smallStoneCount) // Call the previous method
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(looseDrop)));
    }

    // Overloaded method: Adds small stone count
    public LootTable.Builder dropsForStoneBlock(Block block, Item smallStone, int smallStoneCount) {
        return dropsForStoneBlock(block, smallStone) // Call the simpler method
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(smallStone)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(smallStoneCount))))
                        .conditionally(SurvivesExplosionLootCondition.builder()));
    }

    // Handles only basic stone block drops
    public LootTable.Builder dropsForStoneBlock(Block block, Item smallStone) {
        LootTable.Builder lootTableBuilder = LootTable.builder();

        // Basic drop logic for small stone
        LootPool.Builder smallStonePool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(smallStone))
                .conditionally(SurvivesExplosionLootCondition.builder());

        lootTableBuilder.pool(smallStonePool);

        return lootTableBuilder;
    }

    // Overloaded method: Adds hoe-specific drops
    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, float pileDropCount, List<AdditionalDrop> additionalDrops, Map<Item, Float> hoeDrops, Item hoeDroppedItem) {
        LootTable.Builder lootTableBuilder = dropsForLooseAggregate(dropWithSilkTouch, looseDrop, toolCondition, pileDrop, pileDropCount, additionalDrops); // Call the previous method

        // Add hoe-specific drops
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

    // Overloaded method: Adds additional drops
    // Only gravel from flint for now (could be abstracted further to be suitable for different cases as well)
    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, float pileDropCount, List<AdditionalDrop> additionalDrops) {
        LootTable.Builder lootTableBuilder = dropsForLooseAggregate(dropWithSilkTouch, looseDrop, toolCondition, pileDrop, pileDropCount); // Call the previous method

        // Add additional drops
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

        return lootTableBuilder;
    }


    // Handles only basic loose aggregate drops
    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, float pileDropCount) {
        LootTable.Builder lootTableBuilder = LootTable.builder();

        // Define the main loot pool with conditions
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                ItemEntry.builder(dropWithSilkTouch).conditionally(WITH_SILK_TOUCH),
                ItemEntry.builder(looseDrop).conditionally(toolCondition),
                ItemEntry.builder(pileDrop)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(pileDropCount)))
                        .conditionally(WITHOUT_HOE)
        );

        LootPool.Builder mainPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(alternativeEntry)
                .conditionally(SurvivesExplosionLootCondition.builder());

        lootTableBuilder.pool(mainPool);

        return lootTableBuilder;
    }

    public record AdditionalDrop(Item item, int count, float chance) {}
    public record StoneDropEntry(Item item, TagKey<Item> toolTag, LootCondition.Builder extraCondition) {}


}

package org.tough_environment.datagen;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.property.Properties;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;
import org.tough_environment.tag.BTWRConventionalTags;

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
        // Blocks that require a tool to drop fully
        addDrop(Blocks.DIRT, dropsForLooseAggregate(ModBlocks.DIRT_LOOSE, ModBlocks.DIRT_LOOSE, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6));
        addDrop(Blocks.SAND, dropsForLooseAggregate(Blocks.SAND, Blocks.SAND, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_SAND, 6));
        addDrop(Blocks.RED_SAND, dropsForLooseAggregate(Blocks.RED_SAND, Blocks.RED_SAND, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_RED_SAND, 6));
        addDrop(Blocks.GRAVEL, dropsForLooseAggregate(Blocks.GRAVEL, Blocks.GRAVEL, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_GRAVEL, 6,
                List.of(new AdditionalDrop(Items.FLINT, 1, 0.1f))));
        addDrop(Blocks.GRASS_BLOCK, dropsForLooseAggregate(Blocks.GRASS_BLOCK, ModBlocks.DIRT_LOOSE, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6,
                null, Map.of(Items.IRON_HOE, 0.03f, Items.GOLDEN_HOE, 0.04f, Items.DIAMOND_HOE, 0.06f, Items.NETHERITE_HOE, 0.11f), Items.WHEAT_SEEDS));


        // TODO: Add stone blocks & others like granite, diorite, etc.

    }

    private void forMod()
    {

        // Loose blocks
        addDrop(ModBlocks.DIRT_LOOSE, dropsForLooseAggregate(ModBlocks.DIRT_LOOSE, ModBlocks.DIRT_LOOSE, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6));
        addDrop(ModBlocks.BRICKS_LOOSE, dropsForLooseAggregate(ModBlocks.BRICKS_LOOSE, ModBlocks.BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, Items.BRICK, 8));

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

        // Loose Slab blocks
        addDrop(ModBlocks.SLAB_DIRT, dropsForLooseSlab(ModBlocks.SLAB_DIRT, ModItems.PILE_DIRT, 3, 6, WITH_SHOVEL_FULLY_HARVESTS));
        addDrop(ModBlocks.SLAB_GRAVEL, dropsForLooseSlab(ModBlocks.SLAB_GRAVEL, ModItems.PILE_GRAVEL, 3, 6, WITH_SHOVEL_FULLY_HARVESTS));
        addDrop(ModBlocks.SLAB_SAND, dropsForLooseSlab(ModBlocks.SLAB_SAND, ModItems.PILE_SAND, 3, 6, WITH_SHOVEL_FULLY_HARVESTS));
        addDrop(ModBlocks.SLAB_RED_SAND, dropsForLooseSlab(ModBlocks.SLAB_RED_SAND, ModItems.PILE_RED_SAND, 3, 6, WITH_SHOVEL_FULLY_HARVESTS));
        addDrop(ModBlocks.SLAB_BRICKS_LOOSE, dropsForLooseSlab(ModBlocks.SLAB_ANDESITE_LOOSE, Items.BRICK, 4, 8, WITH_PICKAXE_FULLY_HARVESTS));

        addDrop(ModBlocks.SLAB_COBBLESTONE_LOOSE);
        addDrop(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE);
        addDrop(ModBlocks.SLAB_GRANITE_LOOSE);
        addDrop(ModBlocks.SLAB_DIORITE_LOOSE);
        addDrop(ModBlocks.SLAB_ANDESITE_LOOSE);

        // Loose Stair blocks
        addDrop(ModBlocks.COBBLESTONE_LOOSE_STAIRS);
        addDrop(ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS);
        addDrop(ModBlocks.GRANITE_LOOSE_STAIRS);
        addDrop(ModBlocks.ANDESITE_LOOSE_STAIRS);
        addDrop(ModBlocks.DIORITE_LOOSE_STAIRS);
        addDrop(ModBlocks.GRANITE_LOOSE_STAIRS);

        // Non-loose blocks that break into loose
        addDrop(ModBlocks.GRANITE_STAIRS, dropsForSimpleLooseBlock(ModBlocks.GRANITE_STAIRS, ModBlocks.GRANITE_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.SHARD_GRANITE, 4));
        addDrop(ModBlocks.ANDESITE_STAIRS, dropsForSimpleLooseBlock(ModBlocks.ANDESITE_STAIRS, ModBlocks.ANDESITE_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.SHARD_ANDESITE, 4));
        addDrop(ModBlocks.DIORITE_STAIRS, dropsForSimpleLooseBlock(ModBlocks.DIORITE_STAIRS, ModBlocks.DIORITE_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.SHARD_DIORITE, 4));


        // Placed Ore block loot tables
        addDrop(ModBlocks.RAW_IRON_PLACED, Items.RAW_IRON);
        addDrop(ModBlocks.RAW_COPPER_PLACED, Items.RAW_COPPER);
        addDrop(ModBlocks.RAW_GOLD_PLACED, Items.RAW_GOLD);

        // TODO: Add loot tables for Broken stone blocks, converting stone blocks, slab blocks and the stair blocks.

    }

    // Overloaded method: Adds hoe-specific drops
    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount, List<AdditionalDrop> additionalDrops, Map<Item, Float> hoeDrops, Item hoeDroppedItem) {
        LootTable.Builder builder = dropsForLooseAggregate(dropWithSilkTouch, looseDrop, toolCondition, pileDrop, pileDropCount, additionalDrops); // Call the previous method

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

                builder.pool(hoePool);
            }
        }

        return builder;
    }

    // Overloaded method: Adds additional drops
    // Only gravel from flint for now (could be abstracted further to be suitable for different cases as well)
    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount, List<AdditionalDrop> additionalDrops) {
        LootTable.Builder builder = dropsForLooseAggregate(dropWithSilkTouch, looseDrop, toolCondition, pileDrop, pileDropCount); // Call the previous method

        // Add additional drops
        if (additionalDrops != null) {
            for (AdditionalDrop additionalDrop : additionalDrops) {
                LootPool.Builder additionalPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(additionalDrop.item)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(additionalDrop.count)))
                                .conditionally(RandomChanceLootCondition.builder(additionalDrop.chance)))
                        .conditionally(SurvivesExplosionLootCondition.builder());

                builder.pool(additionalPool);
            }
        }

        return builder;
    }


    // Handles only basic loose aggregate drops
    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount) {
        // Define the main loot pool with conditions
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                ItemEntry.builder(dropWithSilkTouch).conditionally(createSilkTouchCondition()),
                ItemEntry.builder(looseDrop).conditionally(toolCondition),
                ItemEntry.builder(pileDrop)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(pileDropCount)))
                        .conditionally(dropWithSilkTouch == Blocks.GRASS_BLOCK ? WITHOUT_HOE : SurvivesExplosionLootCondition.builder())
        );

        LootPool.Builder mainPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(alternativeEntry);


        return LootTable.builder().pool(mainPool);
    }

    public LootTable.Builder dropsForLooseSlab(Block drop, Item pileDrop, int singleSlabPileDropCount, int doubleSlabPileDropCount, LootCondition.Builder toolCondition) {
        return LootTable.builder()
                .pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .with(
                                        // An alternative entry that drops piles if the tool condition is not met
                                        AlternativeEntry.builder(
                                                // Case 1: Drop slab itself if tool condition is met
                                                ItemEntry.builder(drop)
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                                .conditionally(isDoubleSlab(drop))) // Drop 2 slabs for double slab
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))
                                                                .conditionally(isDoubleSlab(drop).invert())) // Drop 1 slab for bottom/top slab
                                                        .conditionally(toolCondition),

                                                // Case 2: Drop pile items for DOUBLE slab
                                                this.applyExplosionDecay(pileDrop, ItemEntry.builder(pileDrop))
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(doubleSlabPileDropCount)))
                                                        .conditionally(isDoubleSlab(drop)),

                                                // Case 3: Drop pile items for BOTTOM and TOP slab types
                                                this.applyExplosionDecay(pileDrop, ItemEntry.builder(pileDrop))
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(singleSlabPileDropCount)))
                                        )
                                )
                );
    }

    public LootTable.Builder dropsForSimpleLooseBlock(Block silkTouchDrop, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount)
    {
        LeafEntry.Builder<?> silkTouchDropEntry = ItemEntry.builder(silkTouchDrop).conditionally(this.createSilkTouchCondition());
        LeafEntry.Builder<?> looseDropEntry = ItemEntry.builder(looseDrop).conditionally(toolCondition);
        LeafEntry.Builder<?> pileDropEntry = this.applyExplosionDecay(pileDrop, ItemEntry.builder(pileDrop))
                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(pileDropCount)));

        return LootTable.builder()
                .pool(
                        LootPool.builder()
                                .with(
                                        AlternativeEntry.builder(silkTouchDropEntry, looseDropEntry, pileDropEntry)
                                )
                );
    }

    // Entry definition for blocks that have and (might) break to piles(dust) when broken
    public AlternativeEntry.Builder alternativeLooseAndPileBreakable(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount) {
        LeafEntry.Builder<?> silkTouchDropEntry = ItemEntry.builder(dropWithSilkTouch).conditionally(this.createSilkTouchCondition());
        LeafEntry.Builder<?> looseDropEntry = ItemEntry.builder(looseDrop).conditionally(toolCondition);
        LeafEntry.Builder<?> pileDropEntry = this.applyExplosionDecay(pileDrop, ItemEntry.builder(pileDrop))
                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(pileDropCount)));

        return AlternativeEntry.builder(silkTouchDropEntry, looseDropEntry, pileDropEntry);
    }



    public record AdditionalDrop(Item item, int count, float chance) {}

    // Helper methods
    private static LootCondition.Builder isDoubleSlab(Block block)
    {
        return BlockStatePropertyLootCondition.builder(block)
                .properties(StatePredicate.Builder.create().exactMatch(Properties.SLAB_TYPE, SlabType.DOUBLE));
    }



}

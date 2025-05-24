package org.tough_environment.datagen;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ExplosionDecayLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.property.Properties;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;

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
    public void generate() {
        this.forVanilla();
        this.forMod();
    }

    private void forVanilla() {
        // Blocks that require a tool to drop fully
        addDrop(Blocks.DIRT, dropsForLooseAggregate(ModBlocks.DIRT_LOOSE, ModBlocks.DIRT_LOOSE, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6));
        addDrop(Blocks.SAND, dropsForLooseAggregate(Blocks.SAND, Blocks.SAND, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_SAND, 6));
        addDrop(Blocks.RED_SAND, dropsForLooseAggregate(Blocks.RED_SAND, Blocks.RED_SAND, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_RED_SAND, 6));
        addDrop(Blocks.GRAVEL, dropsForLooseAggregate(Blocks.GRAVEL, Blocks.GRAVEL, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_GRAVEL, 6,
                List.of(new AdditionalDrop(Items.FLINT, 1, 0.1f))));
        //addDrop(Blocks.GRASS_BLOCK, dropsForLooseAggregate(Blocks.GRASS_BLOCK, ModBlocks.DIRT_LOOSE, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6,
                //null, Map.of(Items.IRON_HOE, 0.03f, Items.GOLDEN_HOE, 0.04f, Items.DIAMOND_HOE, 0.06f, Items.NETHERITE_HOE, 0.11f), Items.WHEAT_SEEDS));
        addDrop(Blocks.GRASS_BLOCK, dropsForLooseAggregate(Blocks.GRASS_BLOCK, ModBlocks.DIRT_LOOSE, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6,
                null));

        addDrop(Blocks.CLAY, dropsForBreakingToLooseBlock(Blocks.CLAY, Blocks.CLAY, WITH_SHOVEL_FULLY_HARVESTS, Items.CLAY_BALL, 4));

        addDrop(Blocks.COBBLESTONE_SLAB, customSlabDrop(Blocks.COBBLESTONE_SLAB, ModBlocks.SLAB_COBBLESTONE_LOOSE));
        addDrop(Blocks.COBBLED_DEEPSLATE_SLAB, customSlabDrop(Blocks.COBBLED_DEEPSLATE_SLAB, ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE));
        addDrop(Blocks.ANDESITE_SLAB, customSlabDrop(Blocks.ANDESITE_SLAB, ModBlocks.SLAB_ANDESITE_LOOSE));
        addDrop(Blocks.GRANITE_SLAB, customSlabDrop(Blocks.GRANITE_SLAB, ModBlocks.SLAB_GRANITE_LOOSE));
        addDrop(Blocks.DIORITE_SLAB, customSlabDrop(Blocks.DIORITE_SLAB, ModBlocks.SLAB_DIORITE_LOOSE));


        /** Non-loose blocks that break into loose **/

        // full blocks
        addDrop(Blocks.BRICKS, dropsForBreakingToLooseBlock(Blocks.BRICKS, ModBlocks.BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, Items.BRICK, 8));
        addDrop(Blocks.STONE_BRICKS, dropsForBreakingToLooseBlock(Blocks.STONE_BRICKS, ModBlocks.STONE_BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, ModItems.STONE_BRICK, 8));
        addDrop(Blocks.DEEPSLATE_BRICKS, dropsForBreakingToLooseBlock(Blocks.DEEPSLATE_BRICKS, ModBlocks.DEEPSLATE_BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, ModItems.STONE_BRICK_2, 8));
        addDrop(Blocks.NETHER_BRICKS, dropsForBreakingToLooseBlock(Blocks.NETHER_BRICKS, ModBlocks.NETHER_BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, Items.NETHER_BRICK, 8));

        // slabs
        addDrop(Blocks.BRICK_SLAB, dropsForBreakingToLooseBlock(Blocks.BRICK_SLAB, ModBlocks.SLAB_BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, Items.BRICK, 4));
        addDrop(Blocks.STONE_BRICK_SLAB, dropsForBreakingToLooseBlock(Blocks.STONE_BRICK_SLAB, ModBlocks.SLAB_STONE_BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, ModItems.STONE_BRICK, 4));
        addDrop(Blocks.DEEPSLATE_BRICK_SLAB, dropsForBreakingToLooseBlock(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, ModItems.STONE_BRICK_2, 4));
        addDrop(Blocks.NETHER_BRICK_SLAB, dropsForBreakingToLooseBlock(Blocks.NETHER_BRICK_SLAB, ModBlocks.SLAB_NETHER_BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, Items.NETHER_BRICK, 4));

        // stairs
        addDrop(Blocks.GRANITE_STAIRS, dropsForBreakingToLooseBlock(Blocks.GRANITE_STAIRS, ModBlocks.GRANITE_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.SHARD_GRANITE, 3));
        addDrop(Blocks.ANDESITE_STAIRS, dropsForBreakingToLooseBlock(Blocks.ANDESITE_STAIRS, ModBlocks.ANDESITE_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.SHARD_ANDESITE, 3));
        addDrop(Blocks.DIORITE_STAIRS, dropsForBreakingToLooseBlock(Blocks.DIORITE_STAIRS, ModBlocks.DIORITE_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.SHARD_DIORITE, 3));
        addDrop(Blocks.BRICK_STAIRS, dropsForBreakingToLooseBlock(Blocks.BRICK_STAIRS, ModBlocks.BRICKS_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, Items.BRICK, 3));
        addDrop(Blocks.STONE_BRICK_STAIRS, dropsForBreakingToLooseBlock(Blocks.STONE_BRICK_STAIRS, ModBlocks.STONE_BRICKS_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.STONE_BRICK, 3));
        addDrop(Blocks.DEEPSLATE_BRICK_STAIRS, dropsForBreakingToLooseBlock(Blocks.DEEPSLATE_BRICK_STAIRS, ModBlocks.DEEPSLATE_BRICKS_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.STONE_BRICK_2, 3));
        addDrop(Blocks.NETHER_BRICK_STAIRS, dropsForBreakingToLooseBlock(Blocks.NETHER_BRICK_STAIRS, ModBlocks.NETHER_BRICKS_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, Items.NETHER_BRICK, 3));



        // TODO: Add stone blocks & others like granite, diorite, etc.

    }

    public LootTable.Builder customSlabDrop(Block drop, Block looseDrop) {
        return LootTable.builder()
                // Pool for silk touch
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(drop).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))
                                )).conditionally(createSilkTouchCondition()))

                // Pool for non-silk touch cases
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(AlternativeEntry.builder()
                                .alternatively(ItemEntry.builder(looseDrop)
                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)))
                                        .alternatively(this.applyExplosionDecay(drop, ItemEntry.builder(drop)
                                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                        .conditionally(BlockStatePropertyLootCondition.builder(drop)
                                                                .properties(StatePredicate.Builder.create()
                                                                        .exactMatch(SlabBlock.TYPE, SlabType.DOUBLE)))))
                                        )
                                )
                        ).conditionally(createSilkTouchCondition().invert())
                );
    }


    private void forMod() {

        // Loose blocks
        addDrop(ModBlocks.DIRT_LOOSE, dropsForLooseAggregate(ModBlocks.DIRT_LOOSE, ModBlocks.DIRT_LOOSE, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6));

        addDrop(ModBlocks.STONE_BRICKS_LOOSE, dropsForSimpleLooseBlock(ModBlocks.STONE_BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, ModItems.STONE_BRICK, 8));
        addDrop(ModBlocks.DEEPSLATE_BRICKS_LOOSE, dropsForSimpleLooseBlock(ModBlocks.DEEPSLATE_BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, ModItems.STONE_BRICK_2, 8));
        addDrop(ModBlocks.BRICKS_LOOSE, dropsForSimpleLooseBlock(ModBlocks.BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, Items.BRICK, 8));
        addDrop(ModBlocks.NETHER_BRICKS_LOOSE, dropsForSimpleLooseBlock(ModBlocks.NETHER_BRICKS_LOOSE, WITH_PICKAXE_FULLY_HARVESTS, Items.NETHER_BRICK, 8));

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

        addDrop(ModBlocks.WHITE_STONE, (Block block) -> drops(block, ModBlocks.WHITE_COBBLESTONE));
        addDrop(ModBlocks.WHITE_COBBLESTONE);

        addDrop(ModBlocks.SLAB_WHITE_STONE, (Block block) -> drops(block, ModBlocks.SLAB_WHITE_COBBLESTONE));
        addDrop(ModBlocks.SLAB_WHITE_COBBLESTONE);

        addDrop(ModBlocks.WHITE_STONE_STAIRS, (Block block) -> drops(block, ModBlocks.WHITE_COBBLESTONE_STAIRS));
        addDrop(ModBlocks.WHITE_COBBLESTONE_STAIRS);


        // Loose Slab blocks
        addDrop(ModBlocks.SLAB_DIRT, dropsForLooseSlab(ModBlocks.SLAB_DIRT, ModItems.PILE_DIRT, 3, 6, WITH_SHOVEL_FULLY_HARVESTS));
        addDrop(ModBlocks.SLAB_GRAVEL, dropsForLooseSlab(ModBlocks.SLAB_GRAVEL, ModItems.PILE_GRAVEL, 3, 6, WITH_SHOVEL_FULLY_HARVESTS));
        addDrop(ModBlocks.SLAB_SAND, dropsForLooseSlab(ModBlocks.SLAB_SAND, ModItems.PILE_SAND, 3, 6, WITH_SHOVEL_FULLY_HARVESTS));
        addDrop(ModBlocks.SLAB_RED_SAND, dropsForLooseSlab(ModBlocks.SLAB_RED_SAND, ModItems.PILE_RED_SAND, 3, 6, WITH_SHOVEL_FULLY_HARVESTS));

        addDrop(ModBlocks.SLAB_COBBLESTONE_LOOSE, slabDrops(ModBlocks.SLAB_COBBLESTONE_LOOSE));
        addDrop(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, slabDrops(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE));
        addDrop(ModBlocks.SLAB_GRANITE_LOOSE, slabDrops(ModBlocks.SLAB_GRANITE_LOOSE));
        addDrop(ModBlocks.SLAB_DIORITE_LOOSE, slabDrops(ModBlocks.SLAB_DIORITE_LOOSE));
        addDrop(ModBlocks.SLAB_ANDESITE_LOOSE, slabDrops(ModBlocks.SLAB_ANDESITE_LOOSE));

        addDrop(ModBlocks.SLAB_BRICKS_LOOSE, dropsForLooseSlab(ModBlocks.SLAB_BRICKS_LOOSE, Items.BRICK, 4, 8, WITH_PICKAXE_FULLY_HARVESTS));
        addDrop(ModBlocks.SLAB_NETHER_BRICKS_LOOSE, dropsForLooseSlab(ModBlocks.SLAB_NETHER_BRICKS_LOOSE, Items.NETHER_BRICK, 4, 8, WITH_PICKAXE_FULLY_HARVESTS));


        // Loose Stair blocks
        addDrop(ModBlocks.COBBLESTONE_LOOSE_STAIRS, dropsForSimpleLooseBlock(ModBlocks.COBBLESTONE_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.SMALL_STONE, 6));
        addDrop(ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS, dropsForSimpleLooseBlock(ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.SMALL_STONE_2, 6));
        addDrop(ModBlocks.GRANITE_LOOSE_STAIRS, dropsForSimpleLooseBlock(ModBlocks.GRANITE_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.SHARD_GRANITE, 6));
        addDrop(ModBlocks.ANDESITE_LOOSE_STAIRS, dropsForSimpleLooseBlock(ModBlocks.ANDESITE_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.SHARD_ANDESITE, 6));
        addDrop(ModBlocks.DIORITE_LOOSE_STAIRS, dropsForSimpleLooseBlock(ModBlocks.DIORITE_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, ModItems.SHARD_DIORITE, 6));

        addDrop(ModBlocks.BRICKS_LOOSE_STAIRS, dropsForSimpleLooseBlock(ModBlocks.BRICKS_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, Items.BRICK, 6));
        addDrop(ModBlocks.NETHER_BRICKS_LOOSE_STAIRS, dropsForSimpleLooseBlock(ModBlocks.NETHER_BRICKS_LOOSE_STAIRS, WITH_PICKAXE_FULLY_HARVESTS, Items.NETHER_BRICK, 6));

        // Non-loose full blocks
        addDrop(ModBlocks.DIRT_PACKED, dropsForSimpleLooseBlock(ModBlocks.DIRT_PACKED, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 12));

        // Non-loose slab blocks
        addDrop(ModBlocks.SLAB_DIRT_PACKED, dropsForSimpleLooseBlock(ModBlocks.SLAB_DIRT_PACKED, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6));

        // Exclusive placed as items blocks
        addDrop(ModBlocks.RAW_IRON_PLACED,  Items.RAW_IRON);
        addDrop(ModBlocks.RAW_COPPER_PLACED, Items.RAW_COPPER);
        addDrop(ModBlocks.RAW_GOLD_PLACED, Items.RAW_GOLD);
        addDrop(ModBlocks.NETHER_BRICK_UNFIRED, ModItems.NETHER_SLUDGE);

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

    /**
    // Handles only basic loose aggregate drops
    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount) {
        // Define the main loot pool with conditions
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                this.silkTouchDropEntry(dropWithSilkTouch),
                this.looseDropEntry(looseDrop, toolCondition),
                ItemEntry.builder(pileDrop).conditionally(WITHOUT_HOE)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(pileDropCount)))
                        .conditionally(dropWithSilkTouch == Blocks.GRASS_BLOCK ? WITHOUT_HOE : SurvivesExplosionLootCondition.builder())
        );

        return LootTable.builder().pool(
                LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(alternativeEntry)
        );
    }
     **/

    // Handles only basic loose aggregate drops
    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount) {
        // Define the main loot pool with conditions
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                this.silkTouchDropEntry(dropWithSilkTouch),
                this.looseDropEntry(looseDrop, toolCondition),
                ItemEntry.builder(pileDrop)
                        .apply(ExplosionDecayLootFunction.builder())
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(pileDropCount)))
                        .conditionally(SurvivesExplosionLootCondition.builder())
        );

        return LootTable.builder().pool(
                LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(alternativeEntry)
        );
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
                                                this.applyExplosionDecay(pileDrop,
                                                                ItemEntry.builder(pileDrop)
                                                                        .apply(SetCountLootFunction.builder(
                                                                                ConstantLootNumberProvider.create(singleSlabPileDropCount))))
                                                        .apply(SetCountLootFunction.builder(
                                                                ConstantLootNumberProvider.create(doubleSlabPileDropCount))
                                                                .conditionally(isDoubleSlab(drop)))
                                                /**
                                                // Case 3: Drop pile items for BOTTOM and TOP slab types
                                                this.applyExplosionDecay(pileDrop, ItemEntry.builder(pileDrop))
                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(singleSlabPileDropCount)))
                                                 **/
                                        )
                                )
                );
    }

    // Simple blocks that are (usually, but not necessarily) loose blocks and break to piles if not mined with the correct tool.
    public LootTable.Builder dropsForSimpleLooseBlock(Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount) {
        return dropsForLooseOrPileBlock(null, looseDrop, toolCondition, pileDrop, pileDropCount);
    }

    // For blocks that break to their loose counterpart if the tool doesn't have silkTouch
    public LootTable.Builder dropsForBreakingToLooseBlock(Block silkTouchDrop, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount) {
        return dropsForLooseOrPileBlock(silkTouchDrop, looseDrop, toolCondition, pileDrop, pileDropCount);
    }

    // Core method for handling loose or pile drops, with optional silk touch handling
    private LootTable.Builder dropsForLooseOrPileBlock(Block silkTouchDrop, Block looseDrop, LootCondition.Builder toolCondition,
                                                       Item pileDrop, int pileDropCount) {
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder();

        if (silkTouchDrop != null) {
            alternativeEntry.alternatively(silkTouchDropEntry(silkTouchDrop));
        }

        alternativeEntry.alternatively(looseDropEntry(looseDrop, toolCondition))
                .alternatively(pileDropEntry(pileDrop, pileDropCount));

        return LootTable.builder()
                .pool(LootPool.builder().with(alternativeEntry));
    }

    // temporary code for when the loot table builders for converting blocks will be added.
    // this code is just copy-pasted from above method and is not correct at all
    public LootTable.Builder dropsForConverting(Block drop, Item pileDrop, int singleSlabPileDropCount, int doubleSlabPileDropCount, LootCondition.Builder toolCondition) {
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
                                                this.applyExplosionDecay(pileDrop,
                                                                ItemEntry.builder(pileDrop)
                                                                        .apply(SetCountLootFunction.builder(
                                                                                ConstantLootNumberProvider.create(singleSlabPileDropCount))))
                                                        .apply(SetCountLootFunction.builder(
                                                                        ConstantLootNumberProvider.create(doubleSlabPileDropCount))
                                                                .conditionally(isDoubleSlab(drop)))
                                        )
                                )
                );
    }

    /** The 3 LeafEntry builders below are only used with the {@link TELootTableProvider#dropsForSimpleLooseBlock} and
     * {@link TELootTableProvider#dropsForBreakingToLooseBlock} methods.
     **/
    // Silk touch drop entry for when a block can be silk-touched
    private LeafEntry.Builder<?> silkTouchDropEntry(Block silkTouchDrop) {
        return ItemEntry.builder(silkTouchDrop).conditionally(this.createSilkTouchCondition());
    }

    // Used for blocks that are non-loose and break to loose if the tool condition is present
    private LeafEntry.Builder<?> looseDropEntry(Block looseDrop, LootCondition.Builder toolCondition) {
        return ItemEntry.builder(looseDrop).conditionally(toolCondition);
    }

    // Pile drop entry used for blocks that break to piles when no tool is used
    private LeafEntry.Builder<?> pileDropEntry(Item pileDrop, int pileDropCount) {
        return this.applyExplosionDecay(pileDrop, ItemEntry.builder(pileDrop))
                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(pileDropCount)));
    }

    public record AdditionalDrop(Item item, int count, float chance) {}

    // Helper methods
    private static LootCondition.Builder isDoubleSlab(Block block)
    {
        return BlockStatePropertyLootCondition.builder(block)
                .properties(StatePredicate.Builder.create().exactMatch(Properties.SLAB_TYPE, SlabType.DOUBLE));
    }



}

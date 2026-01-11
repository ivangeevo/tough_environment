package org.btwr.tough_environment.datagen.loot_table;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.enums.SlabType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.ExplosionDecayLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.predicate.item.ItemSubPredicateTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import org.btwr.tough_environment.item.ModItems;
import org.btwr.tough_environment.loot.conditions.DestroyedByExplosionCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.btwr.shared_library.api.tag.BTWRConventionalTags.Items.*;
import static org.btwr.shared_library.api.tag.BTWRConventionalTags.Items.ADVANCED_CHISELS;
import static org.btwr.shared_library.api.tag.BTWRConventionalTags.Items.ADVANCED_SHOVELS;
import static org.btwr.shared_library.api.tag.BTWRConventionalTags.Items.MODERN_CHISELS;
import static org.btwr.shared_library.api.tag.BTWRConventionalTags.Items.MODERN_SHOVELS;
import static org.btwr.shared_library.api.tag.BTWRConventionalTags.Items.PRIMITIVE_CHISELS;
import static org.btwr.shared_library.api.tag.BTWRConventionalTags.Items.PRIMITIVE_PICKAXES;
import static org.btwr.shared_library.api.tag.BTWRConventionalTags.Items.SHOVELS_HARVEST_FULL_BLOCK;

public abstract class BaseLootTableProvider extends FabricBlockLootTableProvider {

    public static final LootCondition.Builder WITH_ANY_PICKAXE = withMatchingToolTag(ItemTags.PICKAXES);
    public static final LootCondition.Builder WITH_PICKAXE_FULLY_HARVESTS = withMatchingToolTag(PICKAXES_HARVEST_FULL_BLOCK);
    public static final LootCondition.Builder WITH_ADVANCED_PICKAXES = withMatchingToolTag(ADVANCED_PICKAXES);
    public static final LootCondition.Builder WITH_MODERN_PICKAXES = withMatchingToolTag(MODERN_PICKAXES);
    public static final LootCondition.Builder WITH_PRIMITIVE_PICKAXES = withMatchingToolTag(PRIMITIVE_PICKAXES);

    public static final LootCondition.Builder WITH_SHOVEL_FULLY_HARVESTS =  withMatchingToolTag(SHOVELS_HARVEST_FULL_BLOCK);
    public static final LootCondition.Builder WITH_ADVANCED_SHOVELS =  withMatchingToolTag(ADVANCED_SHOVELS);
    public static final LootCondition.Builder WITH_MODERN_SHOVELS =  withMatchingToolTag(MODERN_SHOVELS);
    public static final LootCondition.Builder WITH_PRIMITIVE_SHOVELS =  withMatchingToolTag(PRIMITIVE_SHOVELS);

    public static final LootCondition.Builder WITH_ADVANCED_CHISELS =  withMatchingToolTag(ADVANCED_CHISELS);
    public static final LootCondition.Builder WITH_MODERN_CHISELS =  withMatchingToolTag(MODERN_CHISELS);
    public static final LootCondition.Builder WITH_PRIMITIVE_CHISELS =  withMatchingToolTag(PRIMITIVE_CHISELS);

    public static final LootCondition.Builder WITHOUT_HOE = withMatchingToolTag(ItemTags.HOES).invert();

    public final LootCondition.Builder WITH_SILK_TOUCH = withMatchingEnchantment(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1), registryLookup);

    private static LootCondition.Builder withMatchingToolTag(TagKey<Item> itemTag) {
        return MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(itemTag));
    }

    /**
     *
     * @param enchantment The enchantment to check for.
     * @param range The strength of the enchantment.
     * @param registryLookup The wrapper to use to look for the enchantment in the registry.
     * @return Loot condition for an item which matches the passed enchantment.
     */
    private static LootCondition.Builder withMatchingEnchantment(
            RegistryKey<Enchantment> enchantment, NumberRange.IntRange range, RegistryWrapper.WrapperLookup registryLookup
    ) {
        RegistryWrapper.Impl<Enchantment> impl = registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return MatchToolLootCondition.builder(ItemPredicate.Builder.create()
                .subPredicate(
                        ItemSubPredicateTypes.ENCHANTMENTS,
                        EnchantmentsPredicate.enchantments(List.of(
                                new EnchantmentPredicate(impl.getOrThrow(enchantment), range
                                )
                        ))
                )
        );
    }

    protected BaseLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
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

    /** For blocks like stone and deepslate **/
    public LootTable.Builder dropsForStoneLike(Block silkTouchDrop, Block looseDrop, Item pileDrop, Item partialDrop, Item brickDrop) {
        // All main drops that happen with each different tool
        LootPool.Builder alternativeEntries =  new LootPool.Builder().with(
                AlternativeEntry.builder(
                        // Silk touch drops
                        this.stoneSilkTouchDropEntry(silkTouchDrop).conditionally(WITH_ADVANCED_PICKAXES),
                        this.stoneSilkTouchDropEntry(silkTouchDrop).conditionally(WITH_MODERN_PICKAXES),
                        // Piles, bricks and small stone drops
                        this.simpleDropEntry(pileDrop,1).conditionally(WITH_PRIMITIVE_CHISELS),
                        this.simpleDropEntry(brickDrop, 1).conditionally(WITH_ADVANCED_CHISELS),
                        this.simpleDropEntry(brickDrop, 1).conditionally(WITH_MODERN_CHISELS),
                        this.simpleDropEntry(partialDrop, 3).conditionally(WITH_PRIMITIVE_PICKAXES),
                        // Loose block looseBlock
                        this.simpleDropEntry(looseDrop.asItem(), 1).conditionally(WITH_PICKAXE_FULLY_HARVESTS)
                )
        );

        // The next 2 pools are for the full harvest of a stone block for additional drops
        // 1 pile gravel
        LootPool.Builder pileEntries = new LootPool.Builder().with(
                AlternativeEntry.builder(
                        this.simpleDropEntry(ModItems.PILE_GRAVEL,1)
                                .conditionally(WITH_PICKAXE_FULLY_HARVESTS)
                                .conditionally(InvertedLootCondition.builder(this.createSilkTouchCondition()))
                )
        );

        // 1 partial looseBlock (small stone/shard)
        LootPool.Builder partialEntries = new LootPool.Builder().with(
                AlternativeEntry.builder(
                        this.simpleDropEntry(partialDrop,1)
                                .conditionally(WITH_PICKAXE_FULLY_HARVESTS)
                                .conditionally(InvertedLootCondition.builder(this.createSilkTouchCondition()))
                )
        );

        // Pool to handle loot on explosion break exclusively
        LootPool.Builder explosionEntries = new LootPool.Builder()
                .with(this.simpleDropEntry(partialDrop, 5))
                .with(this.simpleDropEntry(pileDrop, 3))
                .apply(ExplosionDecayLootFunction.builder())
                .conditionally(DestroyedByExplosionCondition.builder());

        return LootTable.builder().pool(alternativeEntries).pool(pileEntries).pool(partialEntries).pool(explosionEntries);
    }

    /** For blocks like andesite, diorite and granite **/
    public LootTable.Builder dropsForAlternativeStoneLike(Block silkTouchDrop, Block looseDrop, Item pileDrop, Item partialDrop) {
        // All main drops that happen with each different tool
        LootPool.Builder alternativeEntries =  new LootPool.Builder().with(
                AlternativeEntry.builder(
                        // Silk touch drops
                        this.stoneSilkTouchDropEntry(silkTouchDrop).conditionally(WITH_ADVANCED_PICKAXES),
                        this.stoneSilkTouchDropEntry(silkTouchDrop).conditionally(WITH_MODERN_PICKAXES),
                        // Piles and shard drops
                        this.simpleDropEntry(pileDrop,1).conditionally(WITH_PRIMITIVE_CHISELS),
                        this.simpleDropEntry(partialDrop, 3).conditionally(WITH_PRIMITIVE_PICKAXES),
                        // Loose block looseBlock
                        this.simpleDropEntry(looseDrop.asItem(), 1).conditionally(WITH_PICKAXE_FULLY_HARVESTS)
                )
        );

        // The next 2 pools are for the full harvest of a stone block for additional drops
        // 1 pile gravel
        LootPool.Builder pileEntries = new LootPool.Builder().with(
                AlternativeEntry.builder(
                        this.simpleDropEntry(ModItems.PILE_GRAVEL,1)
                                .conditionally(WITH_PICKAXE_FULLY_HARVESTS)
                                .conditionally(InvertedLootCondition.builder(this.createSilkTouchCondition()))
                )
        );

        // 1 partial looseBlock (small stone/shard)
        LootPool.Builder partialEntries = new LootPool.Builder().with(
                AlternativeEntry.builder(
                        this.simpleDropEntry(partialDrop,1)
                                .conditionally(WITH_PICKAXE_FULLY_HARVESTS)
                                .conditionally(InvertedLootCondition.builder(this.createSilkTouchCondition()))
                )
        );

        // Pool to handle loot on explosion break exclusively
        LootPool.Builder explosionEntries = new LootPool.Builder()
                .with(this.simpleDropEntry(partialDrop, 5))
                .with(this.simpleDropEntry(pileDrop, 3))
                .apply(ExplosionDecayLootFunction.builder())
                .conditionally(DestroyedByExplosionCondition.builder());

        return LootTable.builder().pool(alternativeEntries).pool(pileEntries).pool(partialEntries).pool(explosionEntries);
    }

    private LootCondition.Builder belowY32Condition() {
        return LocationCheckLootCondition.builder(
                LocationPredicate.Builder.createY(NumberRange.DoubleRange.atMost(32))
        );
    }

    // Handles only basic loose aggregate drops
    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount) {
        // Define the main loot pool with conditions
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                this.silkTouchDropEntry(dropWithSilkTouch),
                this.withToolConditionEntry(looseDrop, toolCondition),
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

    /** General method for handling drops for slabs **/
    public LootTable.Builder dropsForSlab(Block block, Block looseBlock, Item pileDrop, int pileCount) {
        LootPool.Builder alternativeEntries = LootPool.builder()
                .with(AlternativeEntry.builder(
                                this.explosionDropEntry(pileDrop, pileCount * 2).conditionally(isDoubleSlab(block)),
                                this.explosionDropEntry(pileDrop, pileCount),
                                this.simpleDropEntry(looseBlock.asItem(), 2).conditionally(isDoubleSlab(block)),
                                this.simpleDropEntry(looseBlock.asItem(), 1)
                        )
                );

        return LootTable.builder()
                .pool(alternativeEntries);
    }

    public LootTable.Builder dropsForAggregateSlab(Block drop, Item pileDrop, int pileCount, LootCondition.Builder toolCondition) {
        LootPool.Builder alternativesPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0F))
                .with(AlternativeEntry.builder(
                                // Case 1: Drop slab itself if tool condition is met
                                ItemEntry.builder(drop)
                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F))
                                                .conditionally(isDoubleSlab(drop))
                                        )
                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))
                                                .conditionally(isDoubleSlab(drop).invert())
                                        )
                                        .conditionally(toolCondition),

                                // Case 2: Drop pile items for DOUBLE slab
                                this.applyExplosionDecay(pileDrop,
                                                ItemEntry.builder(pileDrop)
                                                        .apply(SetCountLootFunction.builder(
                                                                ConstantLootNumberProvider.create(pileCount))))
                                        .apply(SetCountLootFunction.builder(
                                                        ConstantLootNumberProvider.create(pileCount * 2))
                                                .conditionally(isDoubleSlab(drop)))
                        )
                );


        return LootTable.builder().pool(alternativesPool);
    }

    // Simple blocks that are (usually, but not necessarily) loose blocks and break to piles if not mined with the correct tool.
    public LootTable.Builder dropsForSimpleLooseBlock(Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileCount) {
        return dropsForLooseOrPileBlock(null, looseDrop, toolCondition, pileDrop, pileCount);
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

        alternativeEntry.alternatively(withToolConditionEntry(looseDrop, toolCondition))
                .alternatively(pileDropEntry(pileDrop, pileDropCount));

        return LootTable.builder()
                .pool(LootPool.builder().with(alternativeEntry));
    }

    public LootTable.Builder lapisOreDrops(Item withSilkTouch, Item oreChunkDrop, Item smallStoneDrop, LootCondition.Builder pileDropToolCondition) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

        // Define the ore loot pool entries with conditions
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                this.silkTouchDropEntry(withSilkTouch),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 9.0F)))
                        .conditionally(WITH_ANY_PICKAXE),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 9.0F)))
                        .conditionally(WITH_ADVANCED_CHISELS),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 9.0F)))
                        .conditionally(WITH_MODERN_CHISELS),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 9.0F)))
                        .conditionally(WITH_PRIMITIVE_CHISELS)
        );

        return LootTable.builder()
                .pool(LootPool.builder().with(alternativeEntry))
                .pool(this.smallStonesPool(smallStoneDrop, pileDropToolCondition));
    }

    public LootTable.Builder redstoneOreDrops(Item withSilkTouch, Item oreChunkDrop, Item smallStoneDrop, LootCondition.Builder pileDropToolCondition) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

        // Define the ore loot pool entries with conditions
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                this.silkTouchDropEntry(withSilkTouch),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 5.0F)))
                        .conditionally(WITH_ANY_PICKAXE),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 5.0F)))
                        .conditionally(WITH_ADVANCED_CHISELS),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 5.0F)))
                        .conditionally(WITH_MODERN_CHISELS),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 5.0F)))
                        .conditionally(WITH_PRIMITIVE_CHISELS)
        );

        return LootTable.builder()
                .pool(LootPool.builder().with(alternativeEntry))
                .pool(this.smallStonesPool(smallStoneDrop, pileDropToolCondition));
    }

    // Handles only basic drops for gem like ores (Diamond/emerald)
    public LootTable.Builder dropsForGem(Item withSilkTouch, Item oreChunkDrop, Item smallStoneDrop, LootCondition.Builder pileDropToolCondition) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

        // Define the ore loot pool entries with conditions
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                this.silkTouchDropEntry(withSilkTouch),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .conditionally(WITH_ANY_PICKAXE),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .conditionally(WITH_ADVANCED_CHISELS),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .conditionally(WITH_MODERN_CHISELS),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .conditionally(WITH_PRIMITIVE_CHISELS)
        );

        return LootTable.builder()
                .pool(LootPool.builder().with(alternativeEntry))
                .pool(this.smallStonesPool(smallStoneDrop, pileDropToolCondition));
    }

    // Handles only basic drops for ore.
    public LootTable.Builder dropsForOre(Item withSilkTouch, Item oreChunkDrop, Item smallStoneDrop, Item pileDrop, LootCondition.Builder pileDropToolCondition) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

        // Define the ore loot pool entries with conditions
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                this.silkTouchDropEntry(withSilkTouch),
                this.simpleDropEntry(oreChunkDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .conditionally(WITH_ANY_PICKAXE),
                this.simpleDropEntry(pileDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .conditionally(WITH_ADVANCED_CHISELS),
                this.simpleDropEntry(pileDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .conditionally(WITH_MODERN_CHISELS),
                this.simpleDropEntry(pileDrop)
                        .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                        .apply(ExplosionDecayLootFunction.builder())
                        .conditionally(WITH_PRIMITIVE_CHISELS)
        );

        return LootTable.builder()
                .pool(LootPool.builder().with(alternativeEntry))
                .pool(this.smallStonesPool(smallStoneDrop, pileDropToolCondition));
    }

    /** Drop a 6 small "stone" items when a non-silk touch mining tool that matches the tool condition breaks the ore **/
    private LootPool.Builder smallStonesPool(Item smallStoneDrop, LootCondition.Builder pileDropToolCondition) {
        return LootPool.builder()
                .with(ItemEntry.builder(smallStoneDrop)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(6.0f)))
                        .conditionally(pileDropToolCondition)
                ).conditionally(WITH_SILK_TOUCH.invert());
    }

    // Silk touch looseBlock entry for when a block can be silk-touched
    LeafEntry.Builder<?> silkTouchDropEntry(Block silkTouchDrop) {
        return ItemEntry.builder(silkTouchDrop).conditionally(this.createSilkTouchCondition());
    }

    // Silk touch looseBlock entry for when a block can be silk-touched
    LeafEntry.Builder<?> silkTouchDropEntry(Item silkTouchDrop) {
        return ItemEntry.builder(silkTouchDrop).conditionally(this.createSilkTouchCondition());
    }

    // Normally used for blocks that are non-loose and break to loose if the tool condition is present
    LeafEntry.Builder<?> withToolConditionEntry(Block withoutSilkTouch, LootCondition.Builder toolCondition) {
        return ItemEntry.builder(withoutSilkTouch).conditionally(toolCondition);
    }

    // Normally used for blocks that are non-loose and break to loose if the tool condition is present
    LeafEntry.Builder<?> simpleDropEntry(Item withoutSilkTouch) {
        return ItemEntry.builder(withoutSilkTouch);
    }

    // Pile looseBlock entry used for blocks that break to piles when no tool is used
    private LeafEntry.Builder<?> pileDropEntry(Item pileDrop, int pileDropCount) {
        return this.applyExplosionDecay(pileDrop, ItemEntry.builder(pileDrop))
                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(pileDropCount)));
    }

    /** The 4 LeafEntry builders below are only used with the {@link TELootTableProvider#dropsForStoneLike(Block, Block, Item, Item, Item)}
     **/
    // Silk touch looseBlock entry for when a stone type block can be silk-touched
    private LeafEntry.Builder<?> stoneSilkTouchDropEntry(Block silkTouchDrop) {
        return ItemEntry.builder(silkTouchDrop).conditionally(this.createSilkTouchCondition());
    }

    // Creates a simple item entry with a looseBlock and count
    private LeafEntry.Builder<?> simpleDropEntry(Item drop, int count) {
        return ItemEntry.builder(drop)
                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)));
    }

    // Creates an entry for handling explosion drops
    private LeafEntry.Builder<?> explosionDropEntry(Item drop, int count) {
        return ItemEntry.builder(drop)
                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)))
                .apply(ExplosionDecayLootFunction.builder())
                .conditionally(DestroyedByExplosionCondition.builder());
    }

    public record AdditionalDrop(Item item, int count, float chance) {}

    // Helper methods
    private static LootCondition.Builder isDoubleSlab(Block block) {
        return BlockStatePropertyLootCondition.builder(block)
                .properties(StatePredicate.Builder.create().exactMatch(Properties.SLAB_TYPE, SlabType.DOUBLE));
    }

}
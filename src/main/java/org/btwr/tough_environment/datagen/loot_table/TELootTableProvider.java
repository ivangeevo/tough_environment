package org.btwr.tough_environment.datagen.loot_table;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.tough_environment.block.ModBlocks;
import org.btwr.tough_environment.datagen.loot_table.util.LootConditions;
import org.btwr.tough_environment.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class TELootTableProvider extends BaseLootTableProvider
{
    
    public TELootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.forVanilla();
        this.forMod();
    }

    private void forVanilla() {
        // Blocks that require a tool to looseBlock fully
        addDrop(Blocks.DIRT, dropsForLooseAggregate(
                ModBlocks.DIRT_LOOSE, ModBlocks.DIRT_LOOSE, LootConditions.WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6)
        );

        addDrop(Blocks.SAND, dropsForLooseAggregate(
                Blocks.SAND, Blocks.SAND, LootConditions.WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_SAND, 6)
        );

        addDrop(Blocks.RED_SAND,
                dropsForLooseAggregate(
                        Blocks.RED_SAND,
                        Blocks.RED_SAND,
                        LootConditions.WITH_SHOVEL_FULLY_HARVESTS,
                        ModItems.PILE_RED_SAND, 6
                )
        );

        addDrop(Blocks.GRAVEL,
                dropsForLooseAggregate(
                        Blocks.GRAVEL,
                        Blocks.GRAVEL,
                        LootConditions.WITH_SHOVEL_FULLY_HARVESTS,
                        ModItems.PILE_GRAVEL,
                        6,
                        List.of(new AdditionalDrop(Items.FLINT, 1, 0.1f))
                )
        );

        addDrop(Blocks.GRASS_BLOCK,
                dropsForLooseAggregate(
                        Blocks.GRASS_BLOCK,
                        ModBlocks.DIRT_LOOSE,
                        LootConditions.WITH_SHOVEL_FULLY_HARVESTS,
                        ModItems.PILE_DIRT,
                        6,
                        null
                )
        );

        addDrop(Blocks.CLAY,
                dropsForBreakingToLooseBlock(
                        Blocks.CLAY,
                        Blocks.CLAY,
                        LootConditions.WITH_SHOVEL_FULLY_HARVESTS,
                        Items.CLAY_BALL,
                        4
                )
        );

        addDrop(Blocks.STONE,
                dropsForStoneLike(
                        Blocks.STONE,
                        ModBlocks.COBBLESTONE_LOOSE,
                        ModItems.PILE_GRAVEL,
                        ModItems.SMALL_STONE,
                        ModItems.STONE_BRICK
                )
        );

        addDrop(Blocks.DEEPSLATE,
                dropsForStoneLike(
                        Blocks.DEEPSLATE,
                        ModBlocks.COBBLED_DEEPSLATE_LOOSE,
                        ModItems.PILE_GRAVEL,
                        ModItems.SMALL_STONE_2,
                        ModItems.STONE_BRICK_2
                )
        );

        addDrop(Blocks.ANDESITE,
                dropsForAlternativeStoneLike(
                        Blocks.ANDESITE,
                        ModBlocks.ANDESITE_LOOSE,
                        ModItems.PILE_GRAVEL,
                        ModItems.SHARD_ANDESITE)
        );

        addDrop(Blocks.GRANITE,
                dropsForAlternativeStoneLike(
                        Blocks.GRANITE,
                        ModBlocks.GRANITE_LOOSE,
                        ModItems.PILE_GRAVEL,
                        ModItems.SHARD_GRANITE)
        );

        addDrop(Blocks.DIORITE,
                dropsForAlternativeStoneLike(
                        Blocks.DIORITE,
                        ModBlocks.DIORITE_LOOSE,
                        ModItems.PILE_GRAVEL,
                        ModItems.SHARD_DIORITE)
        );

        addDrop(Blocks.COBBLESTONE_SLAB,
                dropsForSlab(
                        Blocks.COBBLESTONE_SLAB,
                        ModBlocks.SLAB_COBBLESTONE_LOOSE,
                        ModItems.SMALL_STONE, 4
                )
        );

        addDrop(Blocks.COBBLED_DEEPSLATE_SLAB,
                dropsForSlab(
                        Blocks.COBBLED_DEEPSLATE_SLAB,
                        ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE,
                        ModItems.SMALL_STONE_2,
                        4
                )
        );

        addDrop(Blocks.ANDESITE_SLAB,
                dropsForSlab(
                        Blocks.ANDESITE_SLAB,
                        ModBlocks.SLAB_ANDESITE_LOOSE,
                        ModItems.SHARD_ANDESITE,
                        4
                )
        );

        addDrop(Blocks.GRANITE_SLAB,
                dropsForSlab(
                        Blocks.GRANITE_SLAB,
                        ModBlocks.SLAB_GRANITE_LOOSE,
                        ModItems.SHARD_GRANITE,
                        4
                )
        );

        addDrop(Blocks.DIORITE_SLAB,
                dropsForSlab(
                        Blocks.DIORITE_SLAB,
                        ModBlocks.SLAB_DIORITE_LOOSE,
                        ModItems.SHARD_DIORITE,
                        4
                )
        );


        /** Non-loose blocks that break into loose **/

        // full blocks
        addDrop(Blocks.BRICKS,
                dropsForBreakingToLooseBlock(
                        Blocks.BRICKS,
                        ModBlocks.BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        Items.BRICK,
                        8
                )
        );

        addDrop(Blocks.STONE_BRICKS,
                dropsForBreakingToLooseBlock(
                        Blocks.STONE_BRICKS,
                        ModBlocks.STONE_BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.STONE_BRICK,
                        8
                )
        );

        addDrop(Blocks.DEEPSLATE_BRICKS,
                dropsForBreakingToLooseBlock(
                        Blocks.DEEPSLATE_BRICKS,
                        ModBlocks.DEEPSLATE_BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.STONE_BRICK_2,
                        8
                )
        );

        addDrop(Blocks.NETHER_BRICKS,
                dropsForBreakingToLooseBlock(
                        Blocks.NETHER_BRICKS,
                        ModBlocks.NETHER_BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        Items.NETHER_BRICK,
                        8
                )
        );

        // slabs
        addDrop(Blocks.BRICK_SLAB,
                dropsForBreakingToLooseBlock(
                        Blocks.BRICK_SLAB,
                        ModBlocks.SLAB_BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        Items.BRICK,
                        4
                )
        );

        addDrop(Blocks.STONE_BRICK_SLAB,
                dropsForBreakingToLooseBlock(
                        Blocks.STONE_BRICK_SLAB,
                        ModBlocks.SLAB_STONE_BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.STONE_BRICK,
                        4
                )
        );

        addDrop(Blocks.DEEPSLATE_BRICK_SLAB,
                dropsForBreakingToLooseBlock(
                        Blocks.DEEPSLATE_BRICK_SLAB,
                        ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.STONE_BRICK_2,
                        4
                )
        );

        addDrop(Blocks.NETHER_BRICK_SLAB,
                dropsForBreakingToLooseBlock(
                        Blocks.NETHER_BRICK_SLAB,
                        ModBlocks.SLAB_NETHER_BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        Items.NETHER_BRICK,
                        4
                )
        );

        // stairs
        addDrop(Blocks.COBBLESTONE_STAIRS,
                dropsForBreakingToLooseBlock(
                        Blocks.COBBLESTONE_STAIRS,
                        ModBlocks.COBBLESTONE_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.SMALL_STONE,
                        3
                )
        );

        addDrop(Blocks.COBBLED_DEEPSLATE_STAIRS,
                dropsForBreakingToLooseBlock(
                        Blocks.COBBLED_DEEPSLATE_STAIRS,
                        ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.SMALL_STONE_2,
                        3
                )
        );

        addDrop(Blocks.GRANITE_STAIRS,
                dropsForBreakingToLooseBlock(
                        Blocks.GRANITE_STAIRS,
                        ModBlocks.GRANITE_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.SHARD_GRANITE,
                        3
                )
        );

        addDrop(Blocks.ANDESITE_STAIRS,
                dropsForBreakingToLooseBlock(
                        Blocks.ANDESITE_STAIRS,
                        ModBlocks.ANDESITE_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.SHARD_ANDESITE,
                        3)
        );

        addDrop(Blocks.DIORITE_STAIRS,
                dropsForBreakingToLooseBlock(
                        Blocks.DIORITE_STAIRS,
                        ModBlocks.DIORITE_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.SHARD_DIORITE,
                        3
                )
        );

        addDrop(Blocks.BRICK_STAIRS,
                dropsForBreakingToLooseBlock(
                        Blocks.BRICK_STAIRS,
                        ModBlocks.BRICKS_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        Items.BRICK,
                        3
                )
        );

        addDrop(Blocks.STONE_BRICK_STAIRS,
                dropsForBreakingToLooseBlock(
                        Blocks.STONE_BRICK_STAIRS,
                        ModBlocks.STONE_BRICKS_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.STONE_BRICK,
                        3
                )
        );

        addDrop(Blocks.DEEPSLATE_BRICK_STAIRS,
                dropsForBreakingToLooseBlock(
                        Blocks.DEEPSLATE_BRICK_STAIRS,
                        ModBlocks.DEEPSLATE_BRICKS_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.STONE_BRICK_2,
                        3
                )
        );

        addDrop(Blocks.NETHER_BRICK_STAIRS,
                dropsForBreakingToLooseBlock(
                        Blocks.NETHER_BRICK_STAIRS,
                        ModBlocks.NETHER_BRICKS_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        Items.NETHER_BRICK,
                        3
                )
        );

        // TODO: Add stone blocks & others like granite, diorite, etc.

    }


    private void forMod() {

        // Loose blocks
        addDrop(ModBlocks.DIRT_LOOSE,
                dropsForLooseAggregate(
                        ModBlocks.DIRT_LOOSE,
                        ModBlocks.DIRT_LOOSE,
                        LootConditions.WITH_SHOVEL_FULLY_HARVESTS,
                        ModItems.PILE_DIRT,
                        6
                )
        );

        addDrop(ModBlocks.STONE_BRICKS_LOOSE,
                dropsForSimpleLooseBlock(
                        ModBlocks.STONE_BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.STONE_BRICK,
                        8
                )
        );

        addDrop(ModBlocks.DEEPSLATE_BRICKS_LOOSE,
                dropsForSimpleLooseBlock(
                        ModBlocks.DEEPSLATE_BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.STONE_BRICK_2,
                        8
                )
        );

        addDrop(ModBlocks.BRICKS_LOOSE,
                dropsForSimpleLooseBlock(
                        ModBlocks.BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        Items.BRICK,
                        8
                )
        );

        addDrop(ModBlocks.NETHER_BRICKS_LOOSE,
                dropsForSimpleLooseBlock(
                        ModBlocks.NETHER_BRICKS_LOOSE,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        Items.NETHER_BRICK,
                        8
                )
        );

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
        addDrop(ModBlocks.SLAB_DIRT,
                dropsForAggregateSlab(
                        ModBlocks.SLAB_DIRT,
                        ModItems.PILE_DIRT,
                        3,
                        LootConditions.WITH_SHOVEL_FULLY_HARVESTS
                )
        );

        addDrop(ModBlocks.SLAB_GRAVEL,
                dropsForAggregateSlab(
                        ModBlocks.SLAB_GRAVEL,
                        ModItems.PILE_GRAVEL,
                        3,
                        LootConditions.WITH_SHOVEL_FULLY_HARVESTS
                )
        );

        addDrop(ModBlocks.SLAB_SAND,
                dropsForAggregateSlab(
                        ModBlocks.SLAB_SAND,
                        ModItems.PILE_SAND,
                        3,
                        LootConditions.WITH_SHOVEL_FULLY_HARVESTS
                )
        );

        addDrop(ModBlocks.SLAB_RED_SAND,
                dropsForAggregateSlab(
                        ModBlocks.SLAB_RED_SAND,
                        ModItems.PILE_RED_SAND,
                        3,
                        LootConditions.WITH_SHOVEL_FULLY_HARVESTS
                )
        );

        addDrop(ModBlocks.SLAB_COBBLESTONE_LOOSE, slabDrops(ModBlocks.SLAB_COBBLESTONE_LOOSE));
        addDrop(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, slabDrops(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE));
        addDrop(ModBlocks.SLAB_GRANITE_LOOSE, slabDrops(ModBlocks.SLAB_GRANITE_LOOSE));
        addDrop(ModBlocks.SLAB_DIORITE_LOOSE, slabDrops(ModBlocks.SLAB_DIORITE_LOOSE));
        addDrop(ModBlocks.SLAB_ANDESITE_LOOSE, slabDrops(ModBlocks.SLAB_ANDESITE_LOOSE));

        addDrop(ModBlocks.SLAB_BRICKS_LOOSE,
                dropsForAggregateSlab(
                        ModBlocks.SLAB_BRICKS_LOOSE,
                        Items.BRICK,
                        4,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS
                )
        );

        addDrop(ModBlocks.SLAB_NETHER_BRICKS_LOOSE,
                dropsForAggregateSlab(
                        ModBlocks.SLAB_NETHER_BRICKS_LOOSE,
                        Items.NETHER_BRICK,
                        4,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS
                )
        );


        // Loose Stair blocks
        addDrop(ModBlocks.COBBLESTONE_LOOSE_STAIRS,
                dropsForSimpleLooseBlock(
                        ModBlocks.COBBLESTONE_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.SMALL_STONE,
                        6
                )
        );

        addDrop(ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS,
                dropsForSimpleLooseBlock(
                        ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.SMALL_STONE_2,
                        6
                )
        );

        addDrop(ModBlocks.GRANITE_LOOSE_STAIRS,
                dropsForSimpleLooseBlock(
                        ModBlocks.GRANITE_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.SHARD_GRANITE,
                        6
                )
        );

        addDrop(ModBlocks.ANDESITE_LOOSE_STAIRS,
                dropsForSimpleLooseBlock(
                        ModBlocks.ANDESITE_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.SHARD_ANDESITE,
                        6
                )
        );

        addDrop(ModBlocks.DIORITE_LOOSE_STAIRS,
                dropsForSimpleLooseBlock(
                        ModBlocks.DIORITE_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        ModItems.SHARD_DIORITE,
                        6
                )
        );

        addDrop(ModBlocks.BRICKS_LOOSE_STAIRS,
                dropsForSimpleLooseBlock(
                        ModBlocks.BRICKS_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        Items.BRICK,
                        6
                )
        );

        addDrop(ModBlocks.NETHER_BRICKS_LOOSE_STAIRS,
                dropsForSimpleLooseBlock(
                        ModBlocks.NETHER_BRICKS_LOOSE_STAIRS,
                        LootConditions.WITH_PICKAXE_FULLY_HARVESTS,
                        Items.NETHER_BRICK,
                        6
                )
        );

        // Non-loose full blocks
        addDrop(ModBlocks.DIRT_PACKED,
                dropsForSimpleLooseBlock(
                        ModBlocks.DIRT_PACKED,
                        LootConditions.WITH_SHOVEL_FULLY_HARVESTS,
                        ModItems.PILE_DIRT,
                        12
                )
        );

        // Non-loose slab blocks
        addDrop(ModBlocks.SLAB_DIRT_PACKED,
                dropsForSimpleLooseBlock(
                        ModBlocks.SLAB_DIRT_PACKED,
                        LootConditions.WITH_SHOVEL_FULLY_HARVESTS,
                        ModItems.PILE_DIRT,
                        6
                )
        );

        // Exclusive placed as items blocks
        addDrop(ModBlocks.RAW_IRON_PLACED,  Items.RAW_IRON);
        addDrop(ModBlocks.RAW_COPPER_PLACED, Items.RAW_COPPER);
        addDrop(ModBlocks.RAW_GOLD_PLACED, Items.RAW_GOLD);
        addDrop(ModBlocks.NETHER_BRICK_UNFIRED, ModItems.NETHER_SLUDGE);

        // TODO: Add loot tables for Broken stone blocks, converting stone blocks, slab blocks and the stair blocks.

    }

}
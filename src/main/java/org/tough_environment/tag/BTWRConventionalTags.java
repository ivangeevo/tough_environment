package org.tough_environment.tag;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BTWRConventionalTags
{
    public static class Blocks
    {
        // Vanilla Blocks that can convert on break
        public static final TagKey<Block> VANILLA_CONVERTING_BLOCKS = createTag("vanilla_converting_blocks");

        // Modded Blocks that can convert on break
        public static final TagKey<Block> MODDED_CONVERTING_BLOCKS = createTag("modded_converting_blocks");

        // Stump blocks grouped; normally used for Sturdy Tree's Stump blocks
        public static final TagKey<Block> STUMP_BLOCKS = createTag("stump_blocks");

        public static final TagKey<Block> STONE = createTag("stone");


        // existing conventional tags from fabric
        public static final TagKey<Block> ORES = createTag("ores");



        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", name));
        }
    }

    public static class Items
    {
        /**
         * Custom Mod Tool ranks;
         * <p>1.<b>Primitive; 2.Modern; 3.Advanced;</b>
         * <p>The different tool ranks are used to determine if a group of tools are allowed to harvest a block
         * or do some other specific action.
         */

        public static final TagKey<Item> PRIMITIVE_CHISELS = createTag("primitive_chisels");
        public static final TagKey<Item> MODERN_CHISELS = createTag("modern_chisels");
        public static final TagKey<Item> ADVANCED_CHISELS = createTag("advanced_chisels");

        public static final TagKey<Item> PRIMITIVE_PICKAXES = createTag("primitive_pickaxes");
        public static final TagKey<Item> MODERN_PICKAXES = createTag("modern_pickaxes");
        public static final TagKey<Item> ADVANCED_PICKAXES = createTag("advanced_pickaxes");
        public static final TagKey<Item> PICKAXES_HARVEST_FULL_BLOCK = createTag("pickaxes_harvest_full_block");

        public static final TagKey<Item> FULLY_MINES_STRATA_1 = createTag("fully_mines_strata_1");
        public static final TagKey<Item> FULLY_MINES_STRATA_2 = createTag("fully_mines_strata_2");
        public static final TagKey<Item> FULLY_MINES_STRATA_3 = createTag("fully_mines_strata_3");


        public static final TagKey<Item> PRIMITIVE_AXES = createTag("primitive_axes");
        public static final TagKey<Item> MODERN_AXES = createTag("modern_axes");
        public static final TagKey<Item> ADVANCED_AXES = createTag("advanced_axes");

        public static final TagKey<Item> AXES_MAKE_PLANKS = createTag("axes_make_planks");

        public static final TagKey<Item> PRIMITIVE_SHOVELS = createTag("primitive_shovels");
        public static final TagKey<Item> MODERN_SHOVELS = createTag("modern_shovels");
        public static final TagKey<Item> ADVANCED_SHOVELS = createTag("advanced_shovels");

        public static final TagKey<Item> SPIT_CAMPFIRE_ITEMS = createTag("spit_campfire_items");

        public static final TagKey<Item> SHOVELS_HARVEST_FULL_BLOCK = createTag("shovels_harvest_full_block");

        public static final TagKey<Item> PRIMITIVE_HOES = createTag("primitive_hoes");
        public static final TagKey<Item> MODERN_HOES = createTag("modern_hoes");
        public static final TagKey<Item> ADVANCED_HOES = createTag("advanced_hoes");

        /** Tag for items that should do knockback if the config for
         *  knockback restriction is turned on.
         */
        public static final TagKey<Item> DO_KNOCKBACK_ITEMS = createTag("do_knockback_items");


        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", name));
        }
    }
}

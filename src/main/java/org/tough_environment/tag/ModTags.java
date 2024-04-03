package org.tough_environment.tag;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;

public class ModTags
{

    public static class Blocks
    {

        public static final TagKey<Block> VANILLA_CONVERTING_BLOCKS = register("vanilla_converting_blocks");
        public static final TagKey<Block> CONVERTED_STONE_BLOCKS = register("converted_stone_blocks");
        public static final TagKey<Block> BROKEN_STONE_BLOCKS = register("broken_stone_blocks");
        public static final TagKey<Block> MORTARED_BLOCKS = register("mortared_blocks");

        public static final TagKey<Block> STUMP_BLOCKS = register("stump_blocks");

        public static final TagKey<Block> LOOSE_BLOCKS = register("loose_blocks");

        public static final TagKey<Block> STONE_STRATA1 = register("stone_strata1");
        public static final TagKey<Block> STONE_STRATA2 = register("stone_strata2");
        public static final TagKey<Block> STONE_STRATA3 = register("stone_strata3");

        public static final TagKey<Block> ORES_LVL1 = register("ores_lvl1");
        public static final TagKey<Block> ORES_LVL2 = register("ores_lvl2");
        public static final TagKey<Block> ORES_LVL3 = register("ores_lvl3");
        public static final TagKey<Block> ORES_LVL4 = register("ores_lvl4");



        public static final TagKey<Block> STONE_ORES = register("stone_ores");

        public static final TagKey<Block> MANTLE_ORES =  register("mantle_ores");

        public static final TagKey<Block> DEEPSLATE_ORES = register("deepslate_ores");


        public static final TagKey<Block> ORES_ALL = register("ores_all");



        private static TagKey<Block> register(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(ToughEnvironmentMod.MOD_ID, name));
        }
    }

    public static class Items
    {

        public static final TagKey<Item> PRIMITIVE_CHISELS = register("primitive_chisels");
        public static final TagKey<Item> MODERN_CHISELS = register("modern_chisels");




        /**
         * Pickaxe levels;
         *
         * 1.Primitive
         * 2.Modern
         * 3.Advanced
         *
         */
        public static final TagKey<Item> PRIMITIVE_PICKAXES = register("primitive_pickaxes");
        public static final TagKey<Item> MODERN_PICKAXES = register("modern_pickaxes");
        public static final TagKey<Item> ADVANCED_PICKAXES = register("advanced_pickaxes");



        public static final TagKey<Item> MODERN_AXES = register("modern_axes");
        public static final TagKey<Item> MODERN_SHOVELS = register("modern_shovels");
        public static final TagKey<Item> MODERN_HOES = register("modern_hoes");


        public static final TagKey<Item> SMALL_STONES = register("small_stones");

        public static final TagKey<Item> MORTARING_ITEMS = register("mortaring_items");

        /**
         * Item Tags that need to be manually added, or on the modpack level.
         **/
        // The below tags can't generate with Datagen directly.
        private static TagKey<Item> register(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(ToughEnvironmentMod.MOD_ID, name));
        }

    }

    public static class Mineable
    {

        public static final TagKey<Block> CHISEL_MINEABLE = register("mineable/chisel");

    }

    private static TagKey<Block> register(String id)
    {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(ToughEnvironmentMod.MOD_ID, id));
    }



}

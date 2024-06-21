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

        public static final TagKey<Block> CONVERTED_STONE_BLOCKS = register("converted_stone_blocks");
        public static final TagKey<Block> BROKEN_STONE_BLOCKS = register("broken_stone_blocks");

        // Blocks that are mortared, mostly used to identify which blocks loose blocks can hold on to if placed against.
        public static final TagKey<Block> MORTARED_BLOCKS = register("mortared_blocks");

        /** Workaround tag to group miscallaneous blocks like the snow layer blocks and others. **/
        public static final TagKey<Block> MISC_REQUIRING_TOOL = register("misc_requiring_tools");


        public static final TagKey<Block> LOOSE_STONE_BLOCKS = register("loose_stone_blocks");

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
        public static final TagKey<Item> SHOULD_NOT_MERGE = register("should_not_merge");
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

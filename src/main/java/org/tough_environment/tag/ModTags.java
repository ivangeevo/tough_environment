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

        public static final TagKey<Block> VANILLA_CONVERTING_BLOCKS = createTag("vanilla_converting_blocks");
        public static final TagKey<Block> CONVERTED_STONE_BLOCKS = createTag("converted_stone_blocks");
        public static final TagKey<Block> BROKEN_STONES = createTag("broken_stones");
        public static final TagKey<Block> MORTARED_BLOCKS = createTag("mortared_blocks");


        private static TagKey<Block> createTag (String name)
        {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(ToughEnvironmentMod.MOD_ID, name));
        }
    }

    public static class Items
    {

        // Manually add the chisels to the modern chisels Tagkey. Can't be added with Datagen, because they are from
        // BTWR-Core
        public static final TagKey<Item> MODERN_CHISELS = createTag("modern_chisels");
        public static final TagKey<Item> MODERN_PICKAXES = createTag("modern_pickaxes");
        public static final TagKey<Item> MODERN_AXES = createTag("modern_axes");
        public static final TagKey<Item> MODERN_SHOVELS = createTag("modern_shovels");
        public static final TagKey<Item> MODERN_HOES = createTag("modern_hoes");

        public static final TagKey<Item> PRIMITIVE_PICKAXES = createTag("primitive_pickaxes");
        public static final TagKey<Item> PRIMITIVE_MINING_TOOLS = createTag("primitive_mining_tools");

        public static final TagKey<Item> SMALL_STONES = createTag("small_stones");

        public static final TagKey<Item> MORTARING_ITEMS = createTag("mortaring_items");

        /** Item Tags that need to be manually added, or on the modpack level. **/
        // The below tags can't generate with Datagen directly.



        private static TagKey<Item> createTag (String name)
        {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(ToughEnvironmentMod.MOD_ID, name));
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

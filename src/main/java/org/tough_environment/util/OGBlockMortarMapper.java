package org.tough_environment.util;

import com.google.gson.Gson;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;
import org.tough_environment.block.ModBlocks;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OGBlockMortarMapper {

    private static final String JSON_FILE = "block_mortar_replacement_map.json";
    private static final Map<Block, Block> blockReplacementMap = new HashMap<>();

    public static void init() {
        // Load the map only once and avoid recursion
        if (blockReplacementMap.isEmpty()) {
            blockReplacementMap.putAll(loadMap());
            if (blockReplacementMap.isEmpty()) {
                populateDefaultMap();
                saveToJson();
            }
        }
    }

    /**
     * Populate the blockReplacementMap with direct references.
     */
    private static void populateDefaultMap() {
        blockReplacementMap.put(ModBlocks.COBBLESTONE_LOOSE, Blocks.COBBLESTONE);
        blockReplacementMap.put(ModBlocks.COBBLED_DEEPSLATE_LOOSE, Blocks.COBBLED_DEEPSLATE);
        blockReplacementMap.put(ModBlocks.ANDESITE_LOOSE, Blocks.ANDESITE);
        blockReplacementMap.put(ModBlocks.GRANITE_LOOSE, Blocks.GRANITE);
        blockReplacementMap.put(ModBlocks.DIORITE_LOOSE, Blocks.DIORITE);
        blockReplacementMap.put(ModBlocks.CALCITE_LOOSE, Blocks.CALCITE);
        blockReplacementMap.put(ModBlocks.TUFF_LOOSE, Blocks.TUFF);
        blockReplacementMap.put(ModBlocks.END_STONE_LOOSE, Blocks.END_STONE);
        blockReplacementMap.put(ModBlocks.BLACKSTONE_LOOSE, Blocks.BLACKSTONE);
        blockReplacementMap.put(ModBlocks.BASALT_LOOSE, Blocks.BASALT);
        blockReplacementMap.put(ModBlocks.BRICKS_LOOSE, Blocks.BRICKS);
        blockReplacementMap.put(ModBlocks.NETHER_BRICKS_LOOSE, Blocks.NETHER_BRICKS);

        blockReplacementMap.put(ModBlocks.COBBLESTONE_LOOSE_STAIRS, Blocks.COBBLESTONE_STAIRS);
        blockReplacementMap.put(ModBlocks.COBBLED_DEEPSLATE_LOOSE_STAIRS, Blocks.COBBLED_DEEPSLATE_STAIRS);
        blockReplacementMap.put(ModBlocks.ANDESITE_LOOSE_STAIRS, Blocks.ANDESITE_STAIRS);
        blockReplacementMap.put(ModBlocks.GRANITE_LOOSE_STAIRS, Blocks.GRANITE_STAIRS);
        blockReplacementMap.put(ModBlocks.DIORITE_LOOSE_STAIRS, Blocks.DIORITE_STAIRS);
        blockReplacementMap.put(ModBlocks.BRICKS_LOOSE_STAIRS, Blocks.BRICK_STAIRS);
        blockReplacementMap.put(ModBlocks.STONE_BRICKS_LOOSE_STAIRS, Blocks.STONE_BRICK_STAIRS);
        blockReplacementMap.put(ModBlocks.DEEPSLATE_BRICKS_LOOSE_STAIRS, Blocks.DEEPSLATE_BRICK_STAIRS);
        blockReplacementMap.put(ModBlocks.NETHER_BRICKS_LOOSE_STAIRS, Blocks.NETHER_BRICK_STAIRS);

        blockReplacementMap.put(ModBlocks.SLAB_COBBLESTONE_LOOSE, Blocks.COBBLESTONE_SLAB);
        blockReplacementMap.put(ModBlocks.SLAB_COBBLED_DEEPSLATE_LOOSE, Blocks.COBBLED_DEEPSLATE_SLAB);
        blockReplacementMap.put(ModBlocks.SLAB_ANDESITE_LOOSE, Blocks.ANDESITE_SLAB);
        blockReplacementMap.put(ModBlocks.SLAB_GRANITE_LOOSE, Blocks.GRANITE_SLAB);
        blockReplacementMap.put(ModBlocks.SLAB_DIORITE_LOOSE, Blocks.DIORITE_SLAB);
        // the below don't have pair, or don't have modded added alternative -> to be considered what to do with these
        //blockReplacementMap.put(ModBlocks.CALCITE_LOOSE, Blocks.CALCITE_SLAB);
        //blockReplacementMap.put(ModBlocks.SLAB_TUFF_LOOSE, Blocks.TUFF_SLAB);
        //blockReplacementMap.put(ModBlocks.END_STONE_LOOSE, Blocks.END_STONE_SLAB);
        //blockReplacementMap.put(ModBlocks.SLAB_BLACKSTONE_LOOSE, Blocks.BLACKSTONE_SLAB);
        //blockReplacementMap.put(ModBlocks.BASALT_LOOSE, Blocks.BASALT_SLAB);
        blockReplacementMap.put(ModBlocks.SLAB_STONE_BRICKS_LOOSE, Blocks.STONE_BRICK_SLAB);
        blockReplacementMap.put(ModBlocks.SLAB_DEEPSLATE_BRICKS_LOOSE, Blocks.DEEPSLATE_BRICK_SLAB);
        blockReplacementMap.put(ModBlocks.SLAB_BRICKS_LOOSE, Blocks.BRICK_SLAB);
        blockReplacementMap.put(ModBlocks.SLAB_NETHER_BRICKS_LOOSE, Blocks.NETHER_BRICK_SLAB);



    }

    private static void saveToJson() {
        Gson gson = new Gson();
        // Convert Map<Block, Block> to Map<String, String>
        Map<String, String> serializedMap = new HashMap<>();
        for (Map.Entry<Block, Block> entry : blockReplacementMap.entrySet()) {
            String key = Registries.BLOCK.getId(entry.getKey()).toString();
            String value = Registries.BLOCK.getId(entry.getValue()).toString();
            serializedMap.put(key, value);
        }
        String json = gson.toJson(serializedMap);
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            writer.write(json);
        } catch (IOException e) {
            ToughEnvironmentMod.LOGGER.error("Failed to save block mortar replacement map to JSON file.", e);
        }
    }


    public static Map<Block, Block> loadMap() {
        Map<Block, Block> loadedMap = new HashMap<>();
        try (FileReader reader = new FileReader(JSON_FILE)) {
            Gson gson = new Gson();
            // Deserialize to Map<String, String>
            Map<String, String> jsonMap = gson.fromJson(reader, HashMap.class);

            // Convert the string-based map to block-based map
            for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                Block key = Registries.BLOCK.get(Identifier.of(entry.getKey()));
                Block value = Registries.BLOCK.get(Identifier.of(entry.getValue()));
                if (key != Blocks.AIR && value != Blocks.AIR) {
                    loadedMap.put(key, value);
                }
            }
        } catch (IOException e) {
            ToughEnvironmentMod.LOGGER.error("Failed to load block mortar replacement map from JSON file.", e);
        }

        return loadedMap;
    }

    /**
     * Get the replacement for a given block.
     */
    public static Block getReplacement(Block block) {
        return blockReplacementMap.getOrDefault(block, block);
    }
}

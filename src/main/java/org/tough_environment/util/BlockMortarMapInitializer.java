package org.tough_environment.util;

import com.google.gson.Gson;
import net.minecraft.block.Blocks;
import org.tough_environment.block.ModBlocks;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlockMortarMapInitializer
{
    private static final String JSON_FILE = "block_mortar_replacement_map.json";

    public static void initMap()
    {
        Map<String, String> blockReplacementMap = new HashMap<>();
        blockReplacementMap.put("tough_environment:cobblestone_loose", "minecraft:cobblestone");
        blockReplacementMap.put("tough_environment:cobbled_deepslate_loose", "minecraft:cobbled_deepslate");
        blockReplacementMap.put("tough_environment:andesite_loose", "minecraft:andesite");
        blockReplacementMap.put("tough_environment:granite_loose", "minecraft:granite");
        blockReplacementMap.put("tough_environment:diorite_loose", "minecraft:diorite");
        blockReplacementMap.put("tough_environment:calcite_loose", "minecraft:calcite");
        blockReplacementMap.put("tough_environment:tuff_loose", "minecraft:tuff");
        blockReplacementMap.put("tough_environment:end_stone_loose", "minecraft:end_stone");
        blockReplacementMap.put("tough_environment:blackstone_loose", "minecraft:blackstone");
        blockReplacementMap.put("tough_environment:basalt_loose", "minecraft:basalt");
        blockReplacementMap.put("tough_environment:bricks_loose", "minecraft:bricks");

        blockReplacementMap.put("tough_environment:cobblestone_loose_stairs", "minecraft:cobblestone_stairs");
        blockReplacementMap.put("tough_environment:cobbled_deepslate_loose_stairs", "minecraft:cobbled_deepslate_stairs");
        blockReplacementMap.put("tough_environment:andesite_loose_stairs", "tough_environment:andesite_stairs");
        blockReplacementMap.put("tough_environment:granite_loose_stairs", "tough_environment:granite_stairs");
        blockReplacementMap.put("tough_environment:diorite_loose_stairs", "tough_environment:diorite_stairs");

        blockReplacementMap.put("tough_environment:slab_andesite_loose", "minecraft:andesite_slab");
        blockReplacementMap.put("tough_environment:slab_granite_loose", "minecraft:granite_slab");
        blockReplacementMap.put("tough_environment:slab_diorite_loose", "minecraft:diorite_slab");

        saveToJson(blockReplacementMap);
    }

    private static void saveToJson(Map<String, String> blockReplacementMap)
    {
        Gson gson = new Gson();
        String json = gson.toJson(blockReplacementMap);
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> loadMap()
    {
        try (FileReader reader = new FileReader(JSON_FILE))
        {
            Gson gson = new Gson();
            return gson.fromJson(reader, HashMap.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}

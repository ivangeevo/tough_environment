package org.tough_environment.util;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlockReplacementMapInitializer {
    private static final String JSON_FILE = "block_mortar_replacement_map.json";

    public static void initializeBlockReplacementMap() {
        Map<String, String> blockReplacementMap = new HashMap<>();
        blockReplacementMap.put("tough_environment:cobblestone_loose", "minecraft:cobblestone");
        blockReplacementMap.put("tough_environment:cobbled_deepslate_loose", "minecraft:cobbled_deepslate");
        blockReplacementMap.put("tough_environment:granite_loose", "minecraft:granite");
        blockReplacementMap.put("tough_environment:andesite_loose", "minecraft:andesite");

        saveToJson(blockReplacementMap);
    }

    private static void saveToJson(Map<String, String> blockReplacementMap) {
        Gson gson = new Gson();
        String json = gson.toJson(blockReplacementMap);
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> loadBlockReplacementMap() {
        try (FileReader reader = new FileReader(JSON_FILE)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

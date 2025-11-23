package org.tough_environment.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import org.tough_environment.ToughEnvironmentMod;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BlockMortarMapper {
    private static final Gson GSON = new Gson();
    private static final Type MAP_TYPE = new TypeToken<Map<String, String>>() {}.getType();
    private static final Map<Block, Block> blockReplacementMap = new HashMap<>();
    public static final Identifier ID = Identifier.of(ToughEnvironmentMod.MOD_ID, "mortar/block_mortar_map.json");

    public static void register() {
        // Register reload listener
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new Listener());
    }

    /**
     * Returns the mortared variant of a block state.
     * Preserves relevant properties when possible.
     */
    public static BlockState getReplacement(BlockState originalState) {
        Block replacementBlock = blockReplacementMap.get(originalState.getBlock());
        if (replacementBlock == null) return null;

        BlockState replacementState = replacementBlock.getDefaultState();

        for (Property<?> property : originalState.getProperties()) {
            if (replacementState.contains(property)) {
                // Use a helper method to safely cast property and value
                replacementState = copyProperty(replacementState, property, originalState);
            }
        }

        return replacementState;
    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState target, Property<T> property, BlockState source) {
        T value = source.get(property); // safe cast because the property is the same
        return target.with(property, value);
    }

    private static class Listener implements SimpleSynchronousResourceReloadListener {

        @Override
        public Identifier getFabricId() {
            return ID;
        }

        @Override
        public void reload(ResourceManager manager) {
            blockReplacementMap.clear();
            try {
                Resource resource = manager.getResourceOrThrow(ID);
                try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
                    Map<String, String> jsonMap = GSON.fromJson(reader, MAP_TYPE);
                    for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                        Block key = Registries.BLOCK.get(Identifier.of(entry.getKey()));
                        Block value = Registries.BLOCK.get(Identifier.of(entry.getValue()));
                        if (key != Blocks.AIR && value != Blocks.AIR) {
                            blockReplacementMap.put(key, value);
                        }
                    }
                }
            } catch (Exception e) {
                ToughEnvironmentMod.LOGGER.error("Failed to load block_mortar_map.json", e);
            }

        }
    }
}
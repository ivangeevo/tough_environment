package org.tough_environment.datagen;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public abstract class GeneralStringPairProvider implements DataProvider {
    protected final FabricDataOutput dataOutput;
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    protected GeneralStringPairProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        this.dataOutput = dataOutput;
        this.registryLookup = registryLookup;
    }

    /** Implement to set pairs to generate **/
    public abstract void generatePairs(RegistryWrapper.WrapperLookup registryLookup, PairBuilder builder);

    /** Implement to set the path for the map file that generates in the datapack **/
    protected abstract String getMapPath();

    /** Implement to set the folder for the map file to generate in the datapack **/
    protected abstract String getMapFolder();

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        TreeMap<String, String> entries = new TreeMap<>();

        return this.registryLookup.thenCompose(lookup -> {
            generatePairs(lookup, (String key, String value) -> {
                Objects.requireNonNull(key);
                Objects.requireNonNull(value);

                if (entries.containsKey(key)) {
                    throw new RuntimeException("Existing pair key found - " + key + " - Duplicate will be ignored.");
                }

                entries.put(key, value);
            });

            JsonObject pairEntryJson = new JsonObject();

            for (Map.Entry<String, String> entry : entries.entrySet()) {
                pairEntryJson.addProperty(entry.getKey(), entry.getValue());
            }

            return DataProvider.writeToPath(writer, pairEntryJson, this.getMapFilePath());
        });
    }

    private Path getMapFilePath() {
        return dataOutput
                .getResolver(DataOutput.OutputType.DATA_PACK, this.getMapFolder())
                .resolveJson(Identifier.of(dataOutput.getModId(), this.getMapPath()));
    }

    @Override
    public String getName() {
        return "String pair for (%s)".formatted(getMapPath());
    }

    /**
     * A consumer used by {@link GeneralStringPairProvider#generatePairs(RegistryWrapper.WrapperLookup, PairBuilder)}.
     */
    @ApiStatus.NonExtendable
    @FunctionalInterface
    public interface PairBuilder {
        /**
         * Adds a string pair.
         *
         * @param pairKey The key of the pair
         * @param pairValue   The value of the pair
         */
        void add(String pairKey, String pairValue);

        /**
         * Adds a string pair for {@link Block} to {@link Block}.
         *
         * @param blockKey The {@link Block} to get the key from.
         * @param blockValue The {@link Block} to get the value from.
         */
        default void add(Block blockKey, Block blockValue) {
            String key = Registries.BLOCK.getId(blockKey).toString();
            String value = Registries.BLOCK.getId(blockValue).toString();
            add(key, value);
        }

        /**
         * Adds a string pair for {@link Item} to {@link Item}.
         *
         * @param itemKey The {@link Item} to get the key from.
         * @param itemValue The {@link Item} to get the value from.
         */
        default void add(Item itemKey, Item itemValue) {
            String key = Registries.ITEM.getId(itemKey).toString();
            String value = Registries.ITEM.getId(itemValue).toString();
            add(key, value);
        }
    }
}
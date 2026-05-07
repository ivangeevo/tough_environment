package org.btwr.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import org.btwr.tough_environment.block.ModBlocks;

public class TEModelGenerator extends FabricModelProvider {
    public TEModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LAVA_FILLED_COBBLESTONE_LOOSE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LAVA_FILLED_COBBLED_DEEPSLATE_LOOSE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LAVA_FILLED_STONE_BRICKS_LOOSE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LAVA_FILLED_DEEPSLATE_BRICKS_LOOSE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LAVA_PILLOW);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}

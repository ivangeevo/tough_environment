package org.tough_environment.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import org.tough_environment.item.ModItems;
import org.tough_environment.tag.ModTags;

import java.util.concurrent.CompletableFuture;

public class TEItemTagProvider extends FabricTagProvider.ItemTagProvider
{
    public TEItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {

        getOrCreateTagBuilder(ModTags.Items.SMALL_STONES)
                .add(ModItems.SMALL_STONE)
                .add(ModItems.SMALL_STONE_1)
                .add(ModItems.SMALL_STONE_2);

        getOrCreateTagBuilder(ModTags.Items.MODERN_PICKAXES)
                .add(Items.IRON_PICKAXE)
                .add(Items.GOLDEN_PICKAXE)
                .add(Items.DIAMOND_PICKAXE)
                .add(Items.NETHERITE_PICKAXE);

        getOrCreateTagBuilder(ModTags.Items.PRIMITIVE_PICKAXES)
                .add(Items.WOODEN_PICKAXE)
                .add(Items.STONE_PICKAXE);

        getOrCreateTagBuilder(ModTags.Items.MODERN_AXES)
                .add(Items.IRON_AXE)
                .add(Items.GOLDEN_AXE)
                .add(Items.DIAMOND_AXE)
                .add(Items.NETHERITE_AXE);

        getOrCreateTagBuilder(ModTags.Items.MODERN_SHOVELS)
                .add(Items.IRON_SHOVEL)
                .add(Items.GOLDEN_SHOVEL)
                .add(Items.DIAMOND_SHOVEL)
                .add(Items.NETHERITE_SHOVEL);

        getOrCreateTagBuilder(ModTags.Items.MODERN_HOES)
                .add(Items.IRON_HOE)
                .add(Items.GOLDEN_HOE)
                .add(Items.DIAMOND_HOE)
                .add(Items.NETHERITE_HOE);

        getOrCreateTagBuilder(ModTags.Items.MORTARING_ITEMS)
                .add(Items.CLAY_BALL)
                .add(Items.SLIME_BALL);




    }
}

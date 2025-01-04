package org.tough_environment.recipe;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record PackingRecipeInput(ItemConvertible item) implements RecipeInput {

    @Override
    public ItemStack getStackInSlot(int slot) {
        return item.asItem().getDefaultStack();
    }

    @Override
    public int getSize() {
        return 64;
    }
}

package org.btwr.tough_environment.item.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;
import org.btwr.tough_environment.item.ModItems;
import org.btwr.tough_environment.tag.ModTags;

public class ChiselItem extends MiningToolItem {

    public ChiselItem(ToolMaterial material, Settings settings) {
        super(material, ModTags.Mineable.CHISEL, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            if (state.isIn(BTWRConventionalTags.Blocks.STUMP_BLOCKS)) {
                stack.damage(5, miner, EquipmentSlot.MAINHAND);
            }
            else {
                stack.damage(1, miner, EquipmentSlot.MAINHAND);
            }
        }
        return true;
    }

    @Override
    public float getMiningSpeed(ItemStack stack, BlockState state) {
        if (state.isIn(BTWRConventionalTags.Blocks.WEB_BLOCKS)) {
            if (stack.isOf(ModItems.CHISEL_STONE)) {
                return super.getMiningSpeed(stack, state) * 2f;
            }
        }

        return super.getMiningSpeed(stack, state);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        // Primitive chisels do not get damaged in crafting
        if (stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_CHISELS)) {
            return ItemStack.EMPTY;
        }

        if (stack.getDamage() < stack.getMaxDamage() - 1) {
            ItemStack moreDamaged = stack.copy();
            moreDamaged.setDamage(stack.getDamage() + 1);
            return moreDamaged;
        }
        return ItemStack. EMPTY;
    }

}
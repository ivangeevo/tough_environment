package org.tough_environment.item.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.btwr.shared_library.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

public class ChiselItem extends MiningToolItem
{

    public ChiselItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, ModTags.Mineable.CHISEL, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            if (state.isIn(BTWRConventionalTags.Blocks.STUMP_BLOCKS)) {
                stack.damage(5, miner, EquipmentSlot.MAINHAND);
            } else {
                stack.damage(1, miner, EquipmentSlot.MAINHAND);
            }
        }
        return true;
    }

    /**
    @Override
    public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
        BlockPos thisPos = player.getBlockPos();
        SoundEvent craftingSound;
        float volume = 0.1f;
        float pitch;

        if (player.timesCraftedThisTick() == 0 && world.isClient) {

            // Different crafting sounds based on the chisel type
            if (this.getMaterial() == ChiselToolMaterials.WOOD) {
                craftingSound = SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR;
                volume = 0.1f;
                pitch = 1.25f + (world.random.nextFloat() * 0.25f);
                world.playSound(player, thisPos, craftingSound, SoundCategory.BLOCKS, volume, pitch);
            } else if (this.getMaterial() == ChiselToolMaterials.STONE) {
                craftingSound = SoundEvents.BLOCK_ANVIL_LAND;
                volume = 0.1f;
                pitch = world.random.nextFloat() * 0.25f + 1.75f;
                world.playSound(player, thisPos, craftingSound, SoundCategory.BLOCKS, volume, pitch);
            }

            player.tick();
        }
    }
     **/

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        if (stack. getDamage() < stack. getMaxDamage() - 1) {
            ItemStack moreDamaged = stack. copy();
            moreDamaged. setDamage(stack. getDamage() + 1);
            return moreDamaged;
        }
        return ItemStack. EMPTY;
    }
}
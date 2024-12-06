package org.tough_environment.item.items;

import btwr.btwrsl.tag.BTWRConventionalTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.tough_environment.item.ModItems;
import org.tough_environment.tag.ModTags;

public class ChiselItem extends MiningToolItem
{
    public ChiselItem(ToolMaterial toolMaterial, Settings settings)
    {
        super(toolMaterial, ModTags.Mineable.CHISEL, settings);
    }


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner)
    {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f)
        {
            if (state.isIn(BTWRConventionalTags.Blocks.STUMP_BLOCKS))
            {
                stack.damage(5, miner, EquipmentSlot.MAINHAND);
            }
            else
            {
                stack.damage(1, miner, EquipmentSlot.MAINHAND);
            }
        }
        return true;
    }

    @Override
    public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player)
    {
        BlockPos thisPos = player.getBlockPos();
        SoundEvent craftingSound;

        // Different crafting sounds based on the chisel type
        if ( stack.isOf(ModItems.CHISEL_WOOD) )
        {
            craftingSound = SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR;
        }
        else if ( stack.isOf(ModItems.CHISEL_STONE) )
        {
            craftingSound = SoundEvents.BLOCK_ANVIL_LAND;
        }
        else
        {
            craftingSound = SoundEvents.BLOCK_ANVIL_USE;
        }

        world.playSound(player, thisPos, craftingSound, SoundCategory.BLOCKS, 0.2f, 1.2f);
        player.tick();
    }
}

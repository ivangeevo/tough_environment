package org.tough_environment.item.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.tough_environment.item.ModItems;
import org.tough_environment.tag.ModTags;

public class ChiselItem extends MiningToolItem
{
    private final ChiselType chiselType;
    public enum ChiselType { WOOD, STONE, IRON, DIAMOND }
    public ChiselType getChiselType() {
        return chiselType;
    }

    public ChiselItem(float attackDamage, float attackSpeed, ToolMaterials material, ChiselType chiselType, Settings settings)
    {
        super(attackDamage,attackSpeed,material, ModTags.Mineables.CHISEL_MINEABLE,settings);
        this.chiselType = chiselType;

    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player)
    {

        // Register a Fabric event for ticking
        BlockPos thisPos = player.getBlockPos();
        SoundEvent craftingSound;


        if (chiselType == ChiselType.WOOD)
        {

            craftingSound = SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR;
            world.playSound(player, thisPos, craftingSound, SoundCategory.BLOCKS, 0.2f, 1.2f);


        }
        else  if (chiselType == ChiselType.STONE)
        {

            craftingSound = SoundEvents.BLOCK_ANVIL_USE;
            world.playSound(player,thisPos, craftingSound, SoundCategory.BLOCKS,0.2f,0.6f);

        }
        else if (chiselType == ChiselType.IRON)
        {
            craftingSound = SoundEvents.BLOCK_ANVIL_USE;
            world.playSound(player,thisPos, craftingSound, SoundCategory.BLOCKS,0.2f,1.2f);

        }
        player.tick();

    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state)
    {
        if (stack.isOf(ModItems.CHISEL_IRON))
        {
            return super.getMiningSpeedMultiplier(stack, state) / 1.6f;
        }

        return super.getMiningSpeedMultiplier(stack, state);
    }
}
package org.tough_environment.item.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

public class ChiselItem extends MiningToolItem
{
    private final Type chiselType;
    public enum Type
    {
        WOOD, STONE, IRON, DIAMOND
    }

    public Type getType() {
        return chiselType;
    }

    public ChiselItem(float attackDamage, float attackSpeed, ToolMaterials material, Type chiselType, Settings settings)
    {

        super(attackDamage, attackSpeed, material, ModTags.Mineable.CHISEL, settings);
        this.chiselType = chiselType;

    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner)
    {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f)
        {

            if (state.isIn(BTWRConventionalTags.Blocks.STUMP_BLOCKS))
            {
                stack.damage(5, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));

            }
            else
            {
                stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            }

        }
        return true;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state)
    {
        // decrease stone chisel's speed,
        // because it uses the same speed as stone tool materials in vanilla, which is too fast
        if (chiselType == Type.STONE)
        {
            return super.getMiningSpeedMultiplier(stack, state) / 2f;
        }

        if (state.isIn(BTWRConventionalTags.Blocks.STUMP_BLOCKS))
        {
            // diamond chisels are 6x faster against stumps
            if (chiselType == Type.DIAMOND)
            {
                return super.getMiningSpeedMultiplier(stack, state) * 6f;
            }

            // all other chisels are 2x faster against stumps
            return super.getMiningSpeedMultiplier(stack, state) * 2f;
        }

        if ( getType() == Type.IRON || getType() == Type.STONE )
        {
            if ( state.isIn(ModTags.Blocks.STONE_CONVERTING_STRATA3)
                    || state.isIn(ModTags.Blocks.STONE_CONVERTING_STRATA2) )
            {
                return this.miningSpeed / 80f;

            }
        }

        return super.getMiningSpeedMultiplier(stack, state);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player)
    {

        BlockPos thisPos = player.getBlockPos();
        SoundEvent craftingSound;

        if (chiselType == Type.WOOD)
        {
            craftingSound = SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR;
            world.playSound(player, thisPos, craftingSound, SoundCategory.BLOCKS, 0.2f, 1.2f);
        }
        else if (chiselType == Type.STONE)
        {
            craftingSound = SoundEvents.BLOCK_ANVIL_LAND;
            world.playSound(player,thisPos, craftingSound, SoundCategory.BLOCKS,0.2f,1.2f);
        }
        else if (chiselType == Type.IRON || chiselType == Type.DIAMOND)
        {
            craftingSound = SoundEvents.BLOCK_ANVIL_USE;
            world.playSound(player,thisPos, craftingSound, SoundCategory.BLOCKS,0.2f,1.2f);
        }

        player.tick();

    }

}
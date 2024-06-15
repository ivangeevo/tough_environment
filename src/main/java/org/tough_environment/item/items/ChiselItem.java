package org.tough_environment.item.items;

import com.terraformersmc.modmenu.util.mod.Mod;
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
import org.spongepowered.asm.mixin.Unique;
import org.tough_environment.item.ModItems;
import org.tough_environment.tag.BTWRConventionalTags;
import org.tough_environment.tag.ModTags;

public class ChiselItem extends MiningToolItem
{
    private final ChiselType chiselType;
    public enum ChiselType { WOOD, STONE, IRON, DIAMOND }

    public ChiselType getType() {
        return chiselType;
    }

    public ChiselItem(float attackDamage, float attackSpeed, ToolMaterials material, ChiselType chiselType, Settings settings)
    {

        super(attackDamage, attackSpeed, material, ModTags.Mineable.CHISEL_MINEABLE, settings);
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

        if (stack.isOf(ModItems.CHISEL_STONE))
        {
            return super.getMiningSpeedMultiplier(stack, state) / 2f;
        }

        if (state.isIn(BTWRConventionalTags.Blocks.STUMP_BLOCKS))
        {

            if (chiselType == ChiselType.DIAMOND)
            {
                return super.getMiningSpeedMultiplier(stack, state) * 6f;
            }

            return super.getMiningSpeedMultiplier(stack, state) * 2f;
        }

        return super.getMiningSpeedMultiplier(stack, state);
    }



    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player)
    {

        BlockPos thisPos = player.getBlockPos();
        SoundEvent craftingSound;

        if (chiselType == ChiselType.WOOD)
        {
            craftingSound = SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR;
            world.playSound(player, thisPos, craftingSound, SoundCategory.BLOCKS, 0.2f, 1.2f);
        }
        else if (chiselType == ChiselType.STONE)
        {
            craftingSound = SoundEvents.BLOCK_ANVIL_LAND;
            world.playSound(player,thisPos, craftingSound, SoundCategory.BLOCKS,0.2f,1.2f);
        }
        else if (chiselType == ChiselType.IRON || chiselType == ChiselType.DIAMOND)
        {
            craftingSound = SoundEvents.BLOCK_ANVIL_USE;
            world.playSound(player,thisPos, craftingSound, SoundCategory.BLOCKS,0.2f,1.2f);
        }

        player.tick();

    }

    @Unique
    private boolean isPrimitiveTool(ItemStack stack)
    {
        return stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_AXES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_HOES)
                || stack.isIn(BTWRConventionalTags.Items.PRIMITIVE_CHISELS);
    }

    @Unique
    private boolean isModernTool(ItemStack stack)
    {
        return stack.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_AXES)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_HOES)
                || stack.isIn(BTWRConventionalTags.Items.MODERN_CHISELS);
    }

    @Unique
    private boolean isAdvancedTool(ItemStack stack)
    {
        return stack.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                || stack.isIn(BTWRConventionalTags.Items.ADVANCED_AXES)
                || stack.isIn(BTWRConventionalTags.Items.ADVANCED_SHOVELS)
                || stack.isIn(BTWRConventionalTags.Items.ADVANCED_HOES);
    }

}
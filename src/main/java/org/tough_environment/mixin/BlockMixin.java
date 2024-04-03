package org.tough_environment.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.block.blocks.ConvertingBlock;
import org.tough_environment.block.interfaces.DirectionalDroppingBlock;
import org.tough_environment.block.interfaces.StateConvertableBlock;
import org.tough_environment.tag.ModTags;
import org.tough_environment.util.ItemUtils;

import java.util.HashMap;
import java.util.Map;

import static org.tough_environment.block.blocks.ConvertingBlock.BREAK_LEVEL;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements DirectionalDroppingBlock, StateConvertableBlock
{

    public BlockMixin(Settings settings) {
        super(settings);
    }

    // 0.005f hunger whenever a block is placed
    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void injectedOnPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci)
    {
        if (placer instanceof PlayerEntity)
        {
            HungerManager hungerManager = ((PlayerEntity) placer).getHungerManager();
            hungerManager.addExhaustion(0.005f);
        }
    }


    // Injecting our own logic the whole afterBreak method with
    @Inject(method = "afterBreak", at = @At("HEAD"), cancellable = true)
    private void onAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci)
    {

        if (!world.isClient )
        {
            this.setConvertableState(world, pos, state, tool);
        }

        player.incrementStat(Stats.MINED.getOrCreateStat((Block) (Object) this));
        player.addExhaustion(0.2f);
        dropStacksForVanillaOrBTWR(world, player, pos, state, blockEntity, tool);
        ci.cancel();

    }


    @Unique
    private void dropStacksForVanillaOrBTWR(World world, PlayerEntity player, BlockPos pos, BlockState state,
                                            BlockEntity blockEntity, ItemStack tool)
    {


        if ( (state.isIn(ModTags.Blocks.VANILLA_CONVERTING_BLOCKS) || state.getBlock() instanceof ConvertingBlock)
                && !tool.isIn(ModTags.Items.ADVANCED_PICKAXES) )
        {
            ItemUtils.ejectStackFromBlockTowardsFacing(world, player, pos, state, blockEntity, tool, this.getBlockHitSide());
        }
        else
        {
            Block.dropStacks(state, world, pos, blockEntity, player, tool);
        }

    }

    // Method to handle what happens to converting blocks after broken.
    @Override
    public void setConvertableState(World world, BlockPos pos, BlockState state, ItemStack tool)
    {

        Map<Block, BlockState> blockMap = new HashMap<>();

        blockMap.put(Blocks.STONE, ModBlocks.STONE_CONVERTING.getDefaultState());
        blockMap.put(Blocks.GRANITE, ModBlocks.GRANITE_CONVERTING.getDefaultState());
        blockMap.put(Blocks.ANDESITE, ModBlocks.ANDESITE_CONVERTING.getDefaultState());
        blockMap.put(Blocks.CALCITE, ModBlocks.CALCITE_CONVERTING.getDefaultState());
        blockMap.put(Blocks.TUFF, ModBlocks.TUFF_CONVERTING.getDefaultState());
        blockMap.put(Blocks.BLACKSTONE, ModBlocks.BLACKSTONE_CONVERTING.getDefaultState());
        blockMap.put(Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_CONVERTING.getDefaultState());
        blockMap.put(Blocks.BASALT, ModBlocks.BASALT_CONVERTING.getDefaultState());
        blockMap.put(Blocks.END_STONE, ModBlocks.END_STONE_CONVERTING.getDefaultState());


        for (Map.Entry<Block, BlockState> entry : blockMap.entrySet())
         {
             Block originalBlock = entry.getKey();
             BlockState convertedState = entry.getValue();

             if (state.isOf(originalBlock))
             {
                 setStateForStone(world, pos, tool, convertedState);
                 break;
             }

         }

        if (state.isOf(Blocks.DIRT) || state.isOf(Blocks.COARSE_DIRT) || state.isOf(Blocks.DIRT_PATH))
        {
            setStateForDirt(world, pos, state, tool);
        }

        if (state.isIn(ModTags.Blocks.ORES_ALL))
        {

            setStateForOre(world,pos,state,tool);

        }


    }



   @Unique
    private void setStateForStone(World world, BlockPos pos, ItemStack tool, BlockState state)
   {

        if (tool.isIn(ModTags.Items.ADVANCED_PICKAXES)
                || (tool.isIn(ModTags.Items.MODERN_PICKAXES) && !state.isIn(ModTags.Blocks.STONE_STRATA3)) )
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }


        if (tool.isIn(ModTags.Items.PRIMITIVE_PICKAXES) && state.get(BREAK_LEVEL) < 5)
        {
            world.setBlockState(pos, state.with(BREAK_LEVEL, 5));
            return;
        }

        if (tool.isIn(ModTags.Items.MODERN_CHISELS))
        {
            world.setBlockState(pos, state.with(BREAK_LEVEL, 3));
            return;
        }

            world.setBlockState(pos, state.with(BREAK_LEVEL, 0));

   }

    @Unique
    private void setStateForOre(World world, BlockPos pos, BlockState state, ItemStack tool) {

        if (!this.shouldConvertOre(state, tool))
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }

        if (tool.isIn(ModTags.Items.PRIMITIVE_PICKAXES) || tool.isIn(ModTags.Items.PRIMITIVE_CHISELS))
        {
            if (state.isIn(ModTags.Blocks.STONE_ORES))
            {
                world.setBlockState(pos, ModBlocks.STONE_CONVERTING.getDefaultState().with(BREAK_LEVEL, 5));
                return;
            }

            if (state.isIn(ModTags.Blocks.MANTLE_ORES))
            {
                // TODO: add mantle converting block here
                world.setBlockState(pos, ModBlocks.STONE_CONVERTING.getDefaultState().with(BREAK_LEVEL,5));
                return;
            }

            if (state.isIn(ModTags.Blocks.DEEPSLATE_ORES))
            {
                world.setBlockState(pos, ModBlocks.DEEPSLATE_CONVERTING.getDefaultState().with(BREAK_LEVEL,5));
            }

        }


    }

    @Unique
    private boolean shouldConvertOre(BlockState state, ItemStack stack)
    {

        if ( stack.isIn(ModTags.Items.ADVANCED_PICKAXES) )
        {
            return false;
        }

        if ( stack.isIn(ModTags.Items.MODERN_PICKAXES) && !state.isIn(ModTags.Blocks.STONE_STRATA3) )
        {
            return false;
        }


        return (!stack.isIn(ModTags.Items.ADVANCED_PICKAXES)
                && !stack.isIn(ModTags.Items.MODERN_PICKAXES))
                || !state.isIn(ModTags.Blocks.BROKEN_STONE_BLOCKS)  && state.isIn(ModTags.Blocks.STONE_STRATA2);
    }


    @Unique
    private void setStateForDirt(World world, BlockPos pos, BlockState state, ItemStack tool)
    {

        boolean isAboveDirtAndTwoAboveGrass = state.isOf(Blocks.DIRT)
                && (world.getBlockState(pos.up()).isOf(Blocks.DIRT)
                || world.getBlockState(pos.up(1)).isOf(Blocks.GRASS_BLOCK));

        boolean isDirtAbove = world.getBlockState(pos.up()).isOf(Blocks.DIRT);
        boolean isGrassAbove = world.getBlockState(pos.up()).isOf(Blocks.GRASS_BLOCK);


        if (!(tool.isIn(ModTags.Items.MODERN_SHOVELS)))
        {

            if (isAboveDirtAndTwoAboveGrass)
            {
                world.setBlockState(pos.up(1), ModBlocks.DIRT_LOOSE.getDefaultState());
                return;
            }

            if (isDirtAbove || isGrassAbove)
            {
                world.setBlockState(pos.up(), ModBlocks.DIRT_LOOSE.getDefaultState());
                return;
            }

            setAdjacentDirtBlocksOnBreak(world, pos);

        }

        if (tool.isIn(ModTags.Items.MODERN_HOES))
        {
            world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
        }

        world.setBlockState(pos, Blocks.AIR.getDefaultState());

    }

}

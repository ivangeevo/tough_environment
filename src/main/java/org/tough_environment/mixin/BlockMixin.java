package org.tough_environment.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.block.blocks.ConvertingBlock;
import org.tough_environment.block.interfaces.DirectionalDroppingBlock;
import org.tough_environment.block.interfaces.StateConvertableBlock;
import org.tough_environment.state.property.ModProperties;
import org.tough_environment.tag.ModTags;
import org.tough_environment.util.ItemUtils;

import java.util.HashMap;
import java.util.Map;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements DirectionalDroppingBlock, StateConvertableBlock
{


    @Shadow @Final protected StateManager<Block, BlockState> stateManager;

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

        if (!world.isClient)
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
                && !tool.isIn(ModTags.Items.MODERN_PICKAXES) )
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

        Map<BlockState, Block> blockMap = new HashMap<>();

        blockMap.put(Blocks.STONE.getDefaultState(), ModBlocks.STONE_CONVERTING);
        blockMap.put(Blocks.GRANITE.getDefaultState(), ModBlocks.GRANITE_CONVERTING);
        blockMap.put(Blocks.ANDESITE.getDefaultState(), ModBlocks.ANDESITE_CONVERTING);
        blockMap.put(Blocks.CALCITE.getDefaultState(), ModBlocks.CALCITE_CONVERTING);
        blockMap.put(Blocks.TUFF.getDefaultState(), ModBlocks.TUFF_CONVERTING);
        blockMap.put(Blocks.BLACKSTONE.getDefaultState(), ModBlocks.BLACKSTONE_CONVERTING);
        blockMap.put(Blocks.DEEPSLATE.getDefaultState(), ModBlocks.DEEPSLATE_CONVERTING);
        blockMap.put(Blocks.BASALT.getDefaultState(), ModBlocks.BASALT_CONVERTING);
        blockMap.put(Blocks.END_STONE.getDefaultState(), ModBlocks.END_STONE_CONVERTING);


        for (Map.Entry<BlockState, Block> entry : blockMap.entrySet())
         {
             BlockState originalState = entry.getKey();
             Block convertingBlock = entry.getValue();

             if (state.isOf(originalState.getBlock()))
             {
                 setStateForStone(world, pos, tool, convertingBlock);
                 break;
             }

         }

        if (state.isOf(Blocks.DIRT) || state.isOf(Blocks.COARSE_DIRT) || state.isOf(Blocks.DIRT_PATH))
        {
            setStateForDirt(world, pos, state, tool);
        }


    }



   @Unique
    private void setStateForStone(World world, BlockPos pos, ItemStack tool, Block block)
   {

        if (tool.isIn(ModTags.Items.MODERN_PICKAXES))
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }

        if (tool.isIn(ModTags.Items.PRIMITIVE_PICKAXES))
        {
            world.setBlockState(pos, block.getDefaultState().with(ModProperties.BREAK_LEVEL, 5));
            return;
        }

        if (tool.isIn(ModTags.Items.MODERN_CHISELS))
        {
            world.setBlockState(pos, block.getDefaultState().with(ModProperties.BREAK_LEVEL, 3));
            return;
        }

            world.setBlockState(pos, block.getDefaultState().with(ModProperties.BREAK_LEVEL, 0));

   }


    @Unique
    private void setStateForDirt(World world, BlockPos pos, BlockState state, ItemStack tool)
    {

        boolean isAboveDirtAndTwoAboveGrass = state.isOf(Blocks.DIRT)
                && (world.getBlockState(pos.up(2)).isOf(Blocks.DIRT)
                || world.getBlockState(pos.up(2)).isOf(Blocks.GRASS_BLOCK));

        boolean isDirtAbove = world.getBlockState(pos.up()).isOf(Blocks.DIRT);
        boolean isGrassAbove = world.getBlockState(pos.up()).isOf(Blocks.GRASS_BLOCK);


        if (!(tool.isIn(ModTags.Items.MODERN_SHOVELS)))
        {

            if (isAboveDirtAndTwoAboveGrass)
            {
                world.setBlockState(pos.up(2), ModBlocks.DIRT_LOOSE.getDefaultState());
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



    @Shadow
    public abstract Item asItem();

    @Shadow
    protected abstract Block asBlock();
}

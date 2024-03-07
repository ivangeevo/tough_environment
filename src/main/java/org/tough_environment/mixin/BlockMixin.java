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
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
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
import org.tough_environment.item.ModItems;
import org.tough_environment.state.property.ModProperties;
import org.tough_environment.tag.ModTags;
import org.tough_environment.util.ItemUtils;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements DirectionalDroppingBlock, StateConvertableBlock {

    @Shadow public abstract BlockState getDefaultState();

    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void injectedOnPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if (placer instanceof PlayerEntity) {
            HungerManager hungerManager = ((PlayerEntity) placer).getHungerManager();
            hungerManager.addExhaustion(0.05f);
        }
    }

    @Inject(method = "afterBreak", at = @At("HEAD"), cancellable = true)
    private void onAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci) {

        if (!world.isClient)
        {
            this.setConverableStates(world, pos, state, tool);
        }

        player.incrementStat(Stats.MINED.getOrCreateStat((Block) (Object) this));
        player.addExhaustion(0.2f);
        dropStacksForVanillaOrBTWR(world, player, pos, state, blockEntity, tool);
        ci.cancel();

    }

    @Override
    public boolean shouldDirectionalDrop(BlockState state, ItemStack tool) {
        boolean isModernPickaxe = tool.isOf(Items.IRON_PICKAXE) || tool.isOf(Items.GOLDEN_PICKAXE) || tool.isOf(Items.DIAMOND_PICKAXE) || tool.isOf(Items.NETHERITE_PICKAXE);
        boolean isStonePickaxe = tool.isOf(Items.STONE_PICKAXE);
        int breakLevel = state.get(ModProperties.BREAK_LEVEL);

        if (!isModernPickaxe) {
            return false;
        }

        if (isStonePickaxe && (breakLevel == 0  || breakLevel == 1 || breakLevel == 5)) {
            return false;
        }


        return true;
    }

    @Unique
    private void dropStacksForVanillaOrBTWR(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool) {
        boolean isModernPickaxe = tool.isOf(Items.IRON_PICKAXE) || tool.isOf(Items.GOLDEN_PICKAXE)
                || tool.isOf(Items.DIAMOND_PICKAXE) || tool.isOf(Items.NETHERITE_PICKAXE);

        Direction direction = getBlockHitSide();

        if ((state.isIn(ModTags.Blocks.VANILLA_CONVERTING_BLOCKS) || state.getBlock() instanceof ConvertingBlock) && !isModernPickaxe ) {
            ItemUtils.ejectStackFromBlockTowardsFacing(world, pos, state, tool, direction);
        } else {
            Block.dropStacks(state, world, pos, blockEntity, player, tool);
        }
    }

    // Method to handle what happens to converting blocks after broken.
    public void setConverableStates(World world, BlockPos pos, BlockState state, ItemStack tool) {
        if (state.isOf(Blocks.STONE)) {
            this.setStateForStone(world, pos, tool);
        }
        else if (state.isOf(Blocks.DIRT) || state.isOf(Blocks.COARSE_DIRT) || state.isOf(ModBlocks.DIRT_LOOSE)) {
            this.setStateForDirt(world, pos, state, tool);
        }

        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(state));
        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));

    }



   @Unique
    private void setStateForStone(World world, BlockPos pos, ItemStack tool) {
       boolean isModernPickaxe = tool.isOf(Items.IRON_PICKAXE) || tool.isOf(Items.GOLDEN_PICKAXE)
               || tool.isOf(Items.DIAMOND_PICKAXE) || tool.isOf(Items.NETHERITE_PICKAXE);

        if (isModernPickaxe) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return;
        }

        if (tool.isOf(Items.STONE_PICKAXE)) {
            world.setBlockState(pos, ModBlocks.STONE_CONVERTING.getDefaultState().with(ModProperties.BREAK_LEVEL, 5));
            return;

        }

        if (tool.isOf(ModItems.CHISEL_IRON)) {
            world.setBlockState(pos, ModBlocks.STONE_CONVERTING.getDefaultState().with(ModProperties.BREAK_LEVEL, 3));
            return;

        }

            world.setBlockState(pos, ModBlocks.STONE_CONVERTING.getDefaultState());

   }


    @Unique
    private void setStateForDirt(World world, BlockPos pos, BlockState state, ItemStack tool) {
        boolean isAboveDirtAndTwoAboveGrass = state.isOf(Blocks.DIRT) && (world.getBlockState(pos.up(2)).isOf(Blocks.DIRT) || world.getBlockState(pos.up(2)).isOf(Blocks.GRASS_BLOCK));
        boolean isDirtAbove = world.getBlockState(pos.up()).isOf(Blocks.DIRT);
        boolean isGrassAbove = world.getBlockState(pos.up()).isOf(Blocks.GRASS_BLOCK);


        if (!(tool.isOf(Items.IRON_SHOVEL) || tool.isOf(Items.DIAMOND_SHOVEL) || tool.isOf(Items.NETHERITE_SHOVEL))) {

            if (isAboveDirtAndTwoAboveGrass) {
                world.setBlockState(pos.up(2), ModBlocks.DIRT_LOOSE.getDefaultState());
                return;
            }

            if (isDirtAbove || isGrassAbove) {
                world.setBlockState(pos.up(), ModBlocks.DIRT_LOOSE.getDefaultState());
                return;
            }

            setAdjacentDirtBlocksOnBreak(world, pos);

        }

        if (tool.isOf(Items.IRON_HOE) || tool.isOf(Items.DIAMOND_HOE) || tool.isOf(Items.NETHERITE_HOE)) {
            world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
        }

        world.setBlockState(pos, Blocks.AIR.getDefaultState());

    }




    @Shadow
    public abstract Item asItem();

    @Shadow
    protected abstract Block asBlock();
}

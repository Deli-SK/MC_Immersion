package com.deli.immersion.features.betterFarming.events;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class HoeHarvestingEventHandler {

    @SubscribeEvent
    public void onPlayerInteraction(UseHoeEvent event) {
        if (event.world.isRemote)
            return;

        IBlockState blockState = event.world.getBlockState(event.pos);

        Block block = blockState.getBlock();
        if (block instanceof IGrowable) {
            if (!((IGrowable) block).canGrow(event.world, event.pos, blockState, false)) {

                List<ItemStack> drops = block.getDrops(event.world, event.pos, blockState, EnchantmentHelper.getFortuneModifier(event.entityPlayer));

                boolean found = false;
                for (ItemStack itemStack : drops) {
                    // Remove one seed, if possible
                    if (!found && itemStack.getItem() instanceof IPlantable) {
                        found = true;
                        continue;
                    }

                    event.world.spawnEntityInWorld(new EntityItem(event.world, event.pos.getX() + 0.5, event.pos.getY() + 0.5, event.pos.getZ() + 0.5, itemStack));
                }

                event.world.setBlockState(event.pos, block.getDefaultState());
                event.current.damageItem(1, event.entityPlayer);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onBlockDestroy(BlockEvent.BreakEvent event)
    {
        if (event.world.isRemote)
            return;

        IBlockState blockState = event.world.getBlockState(event.pos);

        if (blockState.getBlock() instanceof IGrowable){
            BlockPos bottom = event.pos.down();

            if (event.world.getBlockState(bottom).getBlock() == Blocks.farmland){
                event.world.setBlockState(bottom, Blocks.dirt.getDefaultState());
                event.setCanceled(true);
            }
        }
    }
}

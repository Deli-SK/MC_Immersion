package com.deli.immersion.features.woodProcessing.events;

import com.deli.immersion.features.woodProcessing.crafting.WoodProcessingRecipeHandler;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


/**
 * Created by Deli on 26.01.2016.
 */
public class WooodBreakEventHandler {

    private WoodProcessingRecipeHandler _woodProcessingRecipeHandler;

    public WooodBreakEventHandler(WoodProcessingRecipeHandler woodProcessingRecipeHandler){
        this._woodProcessingRecipeHandler = woodProcessingRecipeHandler;
    }

    @SubscribeEvent
    public void onLogHarvest(BlockEvent.HarvestDropsEvent event){
        if (event.harvester == null || event.state == null)
            return;

        ItemStack heldItem = event.harvester.getHeldItem();
        if (heldItem == null || !(heldItem.getItem() instanceof ItemAxe)){

            for (int i = 0; i < event.drops.size(); i++) {
                event.drops.set(i, this._woodProcessingRecipeHandler.processDrop(event.drops.get(i)));
            }
        }
    }
}

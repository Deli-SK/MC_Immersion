package com.deli.immersion.features.betterFarming;

import com.deli.common.features.FeatureBase;
import com.deli.immersion.features.betterFarming.events.HoeHarvestingEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class BetterFarmingFeature extends FeatureBase {

    @Override
    public void registerEvents(){
        MinecraftForge.EVENT_BUS.register(new HoeHarvestingEventHandler());
    }
}

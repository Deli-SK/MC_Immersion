package com.deli.immersion;

import com.deli.common.crafting.RecipeManager;
import com.deli.common.features.FeatureManager;
import com.deli.immersion.features.betterFarming.BetterFarmingFeature;
import com.deli.immersion.features.toolBalance.ToolBalanceFeature;
import com.deli.immersion.features.woodProcessing.WoodProcessingFeature;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ImmersionMod.MOD_ID, version = ImmersionMod.VERSION)
public class ImmersionMod
{
    public static final String MOD_ID = "immersion";
    public static final String VERSION = "0.1.2";

    private FeatureManager _featureManager = new FeatureManager();

    private void RegisterFeatures() {
        this._featureManager.RegisterFeature(new WoodProcessingFeature());
        this._featureManager.RegisterFeature(new BetterFarmingFeature());
        this._featureManager.RegisterFeature(new ToolBalanceFeature());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        this.RegisterFeatures();
        this._featureManager.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        this._featureManager.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        RecipeManager.getInstance().execute();

        this._featureManager.postInit();
    }
}

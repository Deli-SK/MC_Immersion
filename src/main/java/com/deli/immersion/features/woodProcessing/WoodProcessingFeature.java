package com.deli.immersion.features.woodProcessing;

import com.deli.common.features.FeatureBase;
import com.deli.immersion.features.woodProcessing.crafting.WoodProcessingRecipeHandler;
import com.deli.immersion.features.woodProcessing.events.WooodBreakEventHandler;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class WoodProcessingFeature extends FeatureBase {

    private final WoodProcessingRecipeHandler _woodProcessingRecipeHandler;

    public WoodProcessingFeature() {
        this._woodProcessingRecipeHandler = new WoodProcessingRecipeHandler();
    }

    @Override
    public void init(){
        super.init();

        this._woodProcessingRecipeHandler.findWoodRecipes(CraftingManager.getInstance());
    }

    @Override
    public void registerRecipes() {
        GameRegistry.addRecipe(new ShapelessOreRecipe(Items.stick, "treeSapling"));
    }

    @Override
    public void addModCompatibility() {
        this._woodProcessingRecipeHandler.addCustomProcessingRecipes();
    }

    @Override
    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new WooodBreakEventHandler(this._woodProcessingRecipeHandler));
    }
}

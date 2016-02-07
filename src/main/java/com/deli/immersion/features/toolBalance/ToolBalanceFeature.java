package com.deli.immersion.features.toolBalance;

import com.deli.common.ItemEntry;
import com.deli.common.crafting.RecipeManager;
import com.deli.common.features.FeatureBase;
import com.deli.common.system.IAction;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ToolBalanceFeature extends FeatureBase {

    @Override
    public void registerRecipes(){
        changeCobbleToolsToStone();
    }

    @Override
    public void init() {
        super.init();

        balanceAxes();
        balanceHoes();
        balancePickaxes();
        balanceShovels();
        balanceSwords();
    }

    private void balanceSwords() {
        this.removeTool(Items.wooden_sword);
        this.removeTool(Items.stone_sword);
        this.removeTool(Items.golden_sword);
        this.balanceTool(Items.iron_sword, 256);
        this.balanceTool(Items.diamond_sword, 1024);
    }

    private void balanceShovels() {
        this.removeTool(Items.wooden_shovel);
        this.removeTool(Items.golden_shovel);
        this.balanceTool(Items.stone_shovel, 64);
        this.balanceTool(Items.iron_shovel, 576);
        this.balanceTool(Items.diamond_shovel, 1728);
    }

    private void balancePickaxes() {
        this.removeTool(Items.golden_pickaxe);
        this.balanceTool(Items.wooden_pickaxe, 6);
        this.balanceTool(Items.stone_pickaxe, 30);
        this.balanceTool(Items.iron_pickaxe, 576);
        this.balanceTool(Items.diamond_pickaxe, 1728);
    }

    private void balanceHoes() {
        this.removeTool(Items.wooden_hoe);
        this.removeTool(Items.stone_hoe);
        this.removeTool(Items.golden_hoe);
        this.balanceTool(Items.iron_hoe, 256);
        this.balanceTool(Items.diamond_hoe, 1024);
    }

    private void balanceAxes() {
        this.removeTool(Items.wooden_axe);
        this.removeTool(Items.golden_axe);
        this.balanceTool(Items.stone_axe, 32);
        this.balanceTool(Items.iron_axe, 320);
        this.balanceTool(Items.diamond_axe, 1920);
    }

    private void changeCobbleToolsToStone() {
        IAction<IRecipe> toolProcessor = new IAction<IRecipe>() {
            @Override
            public void execute(IRecipe recipe) {
                changeCobbleToStone(recipe);
            }
        };

        RecipeManager.getInstance()
                .process(Items.stone_axe, toolProcessor)
                .process(Items.stone_hoe, toolProcessor)
                .process(Items.stone_pickaxe, toolProcessor)
                .process(Items.stone_shovel, toolProcessor)
                .process(Items.stone_sword, toolProcessor);
    }

    private void changeCobbleToStone(IRecipe recipe) {
        if (recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe shapedRecipe = (ShapedOreRecipe) recipe;
            Object[] inputs = shapedRecipe.getInput();
            for (int i = 0; i < inputs.length; i++) {
                if (inputs[i] == OreDictionary.getOres("cobblestone")) {
                    inputs[i] = OreDictionary.getOres("stone");
                }
            }
        }
    }

    private void removeTool(Item tool){
        tool.setMaxDamage(1);
        RecipeManager.getInstance().remove(ItemEntry.getItemEntry(tool));
    }

    private void balanceTool(Item tool, int maxDamage){
        tool.setMaxDamage(maxDamage);
    }
}

package com.deli.immersion.features.woodProcessing.crafting;

import com.deli.common.ItemEntry;
import com.deli.common.crafting.RecipeManager;
import com.deli.common.crafting.recipes.ToolRecipe;
import com.deli.common.system.IAction;
import com.deli.common.system.IPredicate;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

public class WoodProcessingRecipeHandler {
    private HashMap<ItemEntry, ItemStack> _logToPlankMap = new HashMap<ItemEntry, ItemStack>();

    private final String ORE_PLANKS = "plankWood";
    private final String ORE_LOGS = "logWood";

    public void findWoodRecipes(CraftingManager manager){
        RecipeManager.getInstance()
                .remove(new IPredicate<IRecipe>() {
                    @Override
                    public Boolean execute(IRecipe recipe) {
                        ItemStack input = getInput(recipe);
                        ItemStack output = getOutput(recipe);
                        return isLog(input) && isPlank(output);
                    }})
                .process(OreDictionary.getOres(ORE_PLANKS), new IAction<IRecipe>() {
                    @Override
                    public void execute(IRecipe recipe) {
                        ItemStack input = getInput(recipe);
                        ItemStack output = getOutput(recipe);
                        if (isLog(input)) {
                            _logToPlankMap.put(new ItemEntry(input), output);
                        }
                    }
                });
    }

    public void addCustomProcessingRecipes(){
        Item[] axes = new Item[]{Items.wooden_axe, Items.stone_axe, Items.golden_axe, Items.iron_axe, Items.diamond_axe};

        for (Map.Entry<ItemEntry, ItemStack> entry: this._logToPlankMap.entrySet()) {
            for (Item axe: axes){
                CraftingManager.getInstance().addRecipe(new ToolRecipe(entry.getValue(), new ItemStack(axe, 1, OreDictionary.WILDCARD_VALUE), entry.getKey().getItemStack()));
            }
        }
    }

    private ItemStack getInput(IRecipe recipe){
        if (recipe instanceof ShapelessRecipes){
            ShapelessRecipes shapeless = (ShapelessRecipes) recipe;
            if (shapeless.getRecipeSize() == 1)
                return shapeless.recipeItems.get(0);
        }

        if (recipe instanceof ShapedRecipes){
            ShapedRecipes shaped = (ShapedRecipes) recipe;
            if (shaped.getRecipeSize() == 1)
                return shaped.recipeItems[0];
        }

        return null;
    }

    private ItemStack getOutput(IRecipe recipe){
        ItemStack recipeOutput = recipe.getRecipeOutput();

        if (recipeOutput == null)
            return null;

        return recipeOutput;
    }

    private boolean isLog(ItemStack item) {
        if (item == null)
            return false;

        for (ItemStack log: OreDictionary.getOres(this.ORE_LOGS)) {
            if (OreDictionary.itemMatches(log, item, false)){
                return true;
            }
        }

        return false;
    }

    private boolean isPlank(ItemStack item) {
        if (item == null)
            return false;

        for (ItemStack log: OreDictionary.getOres(this.ORE_PLANKS)) {
            if (OreDictionary.itemMatches(log, item, false)){
                return true;
            }
        }

        return false;
    }

    public ItemStack processDrop(ItemStack drop){
        if (drop.getItem() == Item.getItemFromBlock(Blocks.crafting_table))
        {
            drop.setItem(Item.getItemFromBlock(Blocks.planks));
        }
        else {
            ItemStack result = this._logToPlankMap.get(new ItemEntry(drop));

            if (result != null)
            {
                ItemStack newDrop = result.copy();
                newDrop.stackSize = 1;
                return newDrop;
            }
        }

        return drop;
    }
}

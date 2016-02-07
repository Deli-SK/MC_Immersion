package com.deli.common.crafting;

import com.deli.common.ItemEntry;
import com.deli.common.system.IAction;
import com.deli.common.system.IPredicate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.*;

public class RecipeManager {
    private List<IPredicate<IRecipe>> _conditionsForRecipesToRemove = new ArrayList<IPredicate<IRecipe>>();
    private Set<ItemEntry> _itemRecipesToRemove = new HashSet<ItemEntry>();

    private Map<ItemEntry, List<IAction<IRecipe>>> _filteredRecipeProcessors = new HashMap<ItemEntry, List<IAction<IRecipe>>>();
    private List<IAction<IRecipe>> _recipeProcessors = new ArrayList<IAction<IRecipe>>();

    private static RecipeManager _instance;

    public static RecipeManager getInstance(){
        if (_instance == null)
            _instance = new RecipeManager();
        return _instance;
    }

    public RecipeManager remove(ItemEntry item){
        this._itemRecipesToRemove.add(item);
        return this;
    }

    public RecipeManager remove(IPredicate<IRecipe> condition){
        this._conditionsForRecipesToRemove.add(condition);
        return this;
    }

    public RecipeManager process(IAction<IRecipe> task){
        this._recipeProcessors.add(task);
        return this;
    }

    public RecipeManager process(Item item, IAction<IRecipe> task) {
        return this.process(ItemEntry.getItemEntry(item), task);
    }

    public RecipeManager process(ItemStack stack, IAction<IRecipe> task) {
        return this.process(ItemEntry.getItemEntry(stack), task);
    }

    public RecipeManager process(List<ItemStack> forItems, IAction<IRecipe> task) {
        for (ItemStack stack: forItems){
            this.process(stack, task);
        }
        return this;
    }

    public RecipeManager process(ItemEntry forItem, IAction<IRecipe> task){
        if (forItem == null)
            return this;

        List<IAction<IRecipe>> actions;
        if (this._filteredRecipeProcessors.containsKey(forItem))
            actions = this._filteredRecipeProcessors.get(forItem);
        else
        {
            actions = new ArrayList<IAction<IRecipe>>();
            this._filteredRecipeProcessors.put(forItem, actions);
        }
        actions.add(task);
        return this;
    }

    public void execute(){
        Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator();
        while (iterator.hasNext()){
            IRecipe recipe = iterator.next();
            if (recipe == null)
                continue;

            ItemEntry key = ItemEntry.getItemEntry(recipe.getRecipeOutput());
            ItemEntry oreKey = ItemEntry.getOreItemEntry(recipe.getRecipeOutput());
            if (key == null || oreKey == null)
                continue;

            for (IAction<IRecipe> processor: this._recipeProcessors){
                processor.execute(recipe);
            }

            if (this._filteredRecipeProcessors.containsKey(key))
                for (IAction<IRecipe> processor: this._filteredRecipeProcessors.get(key))
                    processor.execute(recipe);

            if (this._filteredRecipeProcessors.containsKey(oreKey))
                for (IAction<IRecipe> processor: this._filteredRecipeProcessors.get(oreKey))
                    processor.execute(recipe);


            boolean shouldRemove = this._itemRecipesToRemove.contains(key);

            for (IPredicate<IRecipe> condition: this._conditionsForRecipesToRemove)
                shouldRemove |= condition.execute(recipe);

            if (shouldRemove)
                iterator.remove();
        }
    }
}

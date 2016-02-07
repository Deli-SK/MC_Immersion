package com.deli.common.crafting;

import com.deli.common.crafting.management.IConcatenableRemovableProcessableRecipeExpression;
import com.deli.common.crafting.management.IRemovableProcessableRecipeExpression;
import com.deli.common.crafting.management.RecipeExpression;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Deli on 05.02.2016.
 */
public class RecipeManager {
    private static RecipeManager _instance;

    private List<RecipeExpression> _recipeExpressions = new ArrayList<RecipeExpression>();

    public static RecipeManager getInstance(){
        if (_instance == null)
            _instance = new RecipeManager();
        return _instance;
    }

    public IRemovableProcessableRecipeExpression forAll(){
        RecipeExpression expression = new RecipeExpression();
        this._recipeExpressions.add(expression);
        return expression;
    }

    public IConcatenableRemovableProcessableRecipeExpression forThe(Item item){
        RecipeExpression expression = new RecipeExpression();
        this._recipeExpressions.add(expression);
        return expression.and(item);
    }

    public IConcatenableRemovableProcessableRecipeExpression forThe(Item item, int meta){
        RecipeExpression expression = new RecipeExpression();
        this._recipeExpressions.add(expression);
        return expression.and(item, meta);
    }

    public IConcatenableRemovableProcessableRecipeExpression forThe(Block block){
        RecipeExpression expression = new RecipeExpression();
        this._recipeExpressions.add(expression);
        return expression.and(block);
    }

    public IConcatenableRemovableProcessableRecipeExpression forThe(Block block, int meta){
        RecipeExpression expression = new RecipeExpression();
        this._recipeExpressions.add(expression);
        return expression.and(block, meta);
    }

    public IConcatenableRemovableProcessableRecipeExpression forThe(ItemStack itemStack){
        RecipeExpression expression = new RecipeExpression();
        this._recipeExpressions.add(expression);
        return expression.and(itemStack);
    }

    public IConcatenableRemovableProcessableRecipeExpression forThe(List<ItemStack> itemStacks){
        RecipeExpression expression = new RecipeExpression();
        this._recipeExpressions.add(expression);
        return expression.and(itemStacks);
    }

    public void execute(){
        Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator();
        while (iterator.hasNext()){
            IRecipe recipe = iterator.next();
            boolean shouldRemove = false;
            for (RecipeExpression expressions: this._recipeExpressions){
                shouldRemove |= expressions.execute(recipe);
            }
            if (shouldRemove)
                iterator.remove();
        }
    }
}

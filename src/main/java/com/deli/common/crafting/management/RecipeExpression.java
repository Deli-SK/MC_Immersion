package com.deli.common.crafting.management;

import com.deli.common.ItemEntry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Deli on 05.02.2016.
 */
public class RecipeExpression implements IConcatenableRemovableProcessableRecipeExpression {
    private Set<ItemEntry> _filter = new HashSet<ItemEntry>();
    private List<IRecipeProcessingTask> _tasks = new ArrayList<IRecipeProcessingTask>();
    private boolean _shouldRemove = false;
    private boolean _shouldFilter = false;

    public RecipeExpression(){}

    public void addFilter(ItemEntry entry) {
        this._filter.add(entry);
        this._shouldFilter = true;
    }

    public IConcatenableRemovableProcessableRecipeExpression and(ItemEntry entry) {
        this.addFilter(entry);
        return this;
    }

    @Override
    public IConcatenableRemovableProcessableRecipeExpression and(Item item) {
        return this.and(new ItemEntry(item));
    }

    @Override
    public IConcatenableRemovableProcessableRecipeExpression and(Item item, int meta) {
        return this.and(new ItemEntry(item, meta));
    }

    @Override
    public IConcatenableRemovableProcessableRecipeExpression and(Block block) {
        return this.and(new ItemEntry(block));
    }

    @Override
    public IConcatenableRemovableProcessableRecipeExpression and(Block block, int meta) {
        return this.and(new ItemEntry(block, meta));
    }

    @Override
    public IConcatenableRemovableProcessableRecipeExpression and(ItemStack stack) {
        return this.and(new ItemEntry(stack));
    }

    public IConcatenableRemovableProcessableRecipeExpression and(List<ItemStack> stacks) {
        IConcatenableRemovableProcessableRecipeExpression buffer = this;
        for(ItemStack stack: stacks){
            buffer = buffer.and(stack);
        }
        return buffer;
    }

    @Override
    public IRemovableProcessableRecipeExpression process(IRecipeProcessingTask task) {
        this._tasks.add(task);
        return this;
    }

    @Override
    public void remove() {
        this._shouldRemove = true;
    }

    public boolean execute(IRecipe recipe){
        if (recipe == null)
            return false;

        if (this._shouldFilter) {
            ItemEntry entry = ItemEntry.getItemEntry(recipe.getRecipeOutput());
            if (!this._filter.contains(entry) && !this._filter.contains(ItemEntry.getOreItemEntry(entry))){
                return false;
            }
        }

        for (IRecipeProcessingTask task: this._tasks) {
            task.execute(recipe);
        }

        return this._shouldRemove;
    }
}

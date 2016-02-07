package com.deli.common.crafting.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ToolRecipe implements IRecipe {

    private final ItemStack[] _items;
    private final ItemStack _tool;
    private final ItemStack _output;

    public ToolRecipe(ItemStack output, ItemStack tool, ItemStack ... items) {
        this._output = output;
        this._tool = tool;
        this._items = items;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean foundTool = false;
        boolean[] foundItem = new boolean[this._items.length];

        main: for (int i = 0; i < inv.getSizeInventory(); i++){
            ItemStack invItem = inv.getStackInSlot(i);
            if (invItem != null){
                if (!foundTool && OreDictionary.itemMatches(this._tool, invItem, false)){
                    foundTool = true;
                    continue;
                }
                for (int j = 0; j < this._items.length; j++){
                    if (!foundItem[j] && OreDictionary.itemMatches(this._items[j], invItem, false)){
                        foundItem[j] = true;
                        continue main;
                    }
                }
                return false;
            }
        }

        if (!foundTool)
            return false;

        for (boolean aFoundItem : foundItem) {
            if (!aFoundItem) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this._output.copy();
    }

    @Override
    public int getRecipeSize() {
        return this._items.length + 1;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this._output.copy();
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        ItemStack[] result = new ItemStack[inv.getSizeInventory()];

        for (int i = 0; i < inv.getSizeInventory(); i++){
            result[i] = null;
            ItemStack invItem = inv.getStackInSlot(i);
            if (invItem != null && OreDictionary.itemMatches(this._tool, invItem, false)){
                ItemStack copy = invItem.copy();
                copy.setItemDamage(copy.getItemDamage() + 1);
                if (copy.getItemDamage() < copy.getMaxDamage())
                    result[i] = copy;
            }
        }

        return result;
    }
}

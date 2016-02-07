package com.deli.common.crafting.management;

import net.minecraft.item.crafting.IRecipe;

/**
 * Created by Deli on 05.02.2016.
 */
public interface IRecipeProcessingTask {
    void execute(IRecipe recipe);
}

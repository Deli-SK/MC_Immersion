package com.deli.common.crafting.management;

/**
 * Created by Deli on 05.02.2016.
 */
public interface IRemovableProcessableRecipeExpression extends IRemovableRecipeExpression{
    IRemovableProcessableRecipeExpression process(IRecipeProcessingTask task);
}

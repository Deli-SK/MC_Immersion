package com.deli.common.crafting.management;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Deli on 05.02.2016.
 */
public interface IConcatenableRemovableProcessableRecipeExpression extends IRemovableProcessableRecipeExpression {
    IConcatenableRemovableProcessableRecipeExpression and(Item item);
    IConcatenableRemovableProcessableRecipeExpression and(Item item, int meta);
    IConcatenableRemovableProcessableRecipeExpression and(Block block);
    IConcatenableRemovableProcessableRecipeExpression and(Block block, int meta);
    IConcatenableRemovableProcessableRecipeExpression and(ItemStack stack);
}

package com.deli.common;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemEntry {
    private final Item _item;
    private final int _meta;

    public ItemEntry(ItemStack s){
        this._item = s.getItem();
        this._meta = s.getMetadata();
    }

    public ItemEntry(Item item){
        this._item = item;
        this._meta = 0;
    }

    public ItemEntry(Item item, int meta){
        this._item = item;
        this._meta = meta;
    }

    public ItemEntry(Block block){
        this._item = Item.getItemFromBlock(block);
        this._meta = 0;
    }

    public ItemEntry(Block block, int meta){
        this._item = Item.getItemFromBlock(block);
        this._meta = meta;
    }

    public static ItemEntry getItemEntry(ItemStack itemStack){
        if (itemStack == null)
            return null;
        return new ItemEntry(itemStack);
    }

    public static ItemEntry getItemEntry(Item item){
        if (item == null)
            return null;
        return new ItemEntry(item);
    }

    public static ItemEntry getOreItemEntry(Item item){
        if (item == null)
            return null;
        return new ItemEntry(item, OreDictionary.WILDCARD_VALUE);
    }

    public static ItemEntry getOreItemEntry(ItemStack entry){
        if (entry == null)
            return null;
        return new ItemEntry(entry.getItem(), OreDictionary.WILDCARD_VALUE);
    }

    public ItemStack getItemStack(){
        return this.getItemStack(1);
    }

    public ItemStack getItemStack(int amount){
        return new ItemStack(this._item, amount, this._meta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemEntry itemEntry = (ItemEntry) o;

        return _meta == itemEntry._meta && (_item != null ? _item.equals(itemEntry._item) : itemEntry._item == null);

    }

    @Override
    public int hashCode() {
        int result = _item != null ? _item.hashCode() : 0;
        result = 31 * result + _meta;
        return result;
    }
}

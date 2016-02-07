package com.deli.common.features;

/**
 * Created by Deli on 28.01.2016.
 */
public abstract class FeatureBase implements IFeature {

    @Override
    public void preInit() {
        this.registerBlocks();
        this.registerItems();
        this.registerEvents();
    }

    @Override
    public void init() {
        this.registerRecipes();
    }

    @Override
    public void postInit() {
        this.addModCompatibility();
    }

    public void registerBlocks() {};
    public void registerItems() {};
    public void registerEvents() {};
    public void registerRecipes() {};
    public void addModCompatibility() {};
}

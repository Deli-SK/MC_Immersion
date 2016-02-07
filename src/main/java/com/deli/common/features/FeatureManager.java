package com.deli.common.features;

import java.util.ArrayList;

public class FeatureManager implements IFeature {

    private ArrayList<IFeature> _features = new ArrayList<IFeature>();

    public void RegisterFeature(IFeature feature){
        this._features.add(feature);
    }

    @Override
    public void preInit() {
        for (IFeature feature: this._features)
            feature.preInit();
    }

    @Override
    public void init() {
        for (IFeature feature: this._features)
            feature.init();
    }

    @Override
    public void postInit() {
        for (IFeature feature: this._features)
            feature.postInit();
    }
}

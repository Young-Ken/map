/*
 * Copyright (C) 2018 FarmFriend Co., Ltd. All rights reserved.
 */
package com.caobugs.map.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.caobugs.map.coordinate.projection.api.IProjection;
import com.caobugs.map.layer.LayerManager;
import com.caobugs.map.layer.api.ILayerManager;
import com.caobugs.map.opration.api.IOperation;
import com.caobugs.map.view.api.IMapView;

/**
 * @author Young Ken
 * @since 2018-01
 */
public class MapView extends ViewGroup implements IMapView {

    private ILayerManager mLayerManager;

    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    private void init() {
        mLayerManager = new LayerManager();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public ILayerManager getLayerManager() {
        return mLayerManager;
    }

    @Override
    public IProjection getProjection() {
        return null;
    }

    @Override
    public IOperation getOperation() {
        return null;
    }

    @Override
    public void destroy() {

    }
}

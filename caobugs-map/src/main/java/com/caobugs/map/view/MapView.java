/*
 * Copyright (C) 2018 FarmFriend Co., Ltd. All rights reserved.
 */
package com.caobugs.map.view;

import android.content.Context;
import android.media.audiofx.LoudnessEnhancer;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.caobugs.map.coordinate.projection.Projection;
import com.caobugs.map.coordinate.projection.api.IProjection;
import com.caobugs.map.layer.LayerManager;
import com.caobugs.map.layer.api.ILayerManager;
import com.caobugs.map.model.LatLong;
import com.caobugs.map.model.MapPosition;
import com.caobugs.map.opration.Operation;
import com.caobugs.map.opration.api.IOperation;
import com.caobugs.map.opration.event.MapGestureHandler;
import com.caobugs.map.view.api.IMapView;

/**
 * @author Young Ken
 * @since 2018-01
 */
public class MapView extends ViewGroup implements IMapView {

    private ILayerManager mLayerManager;
    private IProjection mProjection;
    private IOperation mOperation;
    private GestureDetector mGestureDetector;

    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mLayerManager = new LayerManager();
        mProjection = new Projection();
        mOperation = new Operation(new MapPosition(10, new LatLong(0,0)));
        mGestureDetector = new GestureDetector(getContext(), new MapGestureHandler(this));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }
        return true;
    }

    @Override
    public ILayerManager getLayerManager() {
        return mLayerManager;
    }

    @Override
    public IProjection getProjection() {
        return mProjection;
    }

    @Override
    public IOperation getOperation() {
        return mOperation;
    }

    @Override
    public void destroy() {

    }
}

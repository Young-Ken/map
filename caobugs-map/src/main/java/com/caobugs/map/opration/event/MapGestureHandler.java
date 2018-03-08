/*
 * Copyright (C) 2018 FarmFriend Co., Ltd. All rights reserved.
 */
package com.caobugs.map.opration.event;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.caobugs.map.view.api.IMapView;

/**
 * @author Young Ken
 * @since 2018-02
 */
public class MapGestureHandler implements ScaleGestureDetector.OnScaleGestureListener,
        GestureDetector.OnGestureListener {

    private static final String TAG = "MapGestureHandler";
    private IMapView mMapView;
    public MapGestureHandler(IMapView mapView) {

        if (mapView == null) {
            throw new IllegalArgumentException("MapView不能为空");
        }
        mMapView = mapView;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        if (mMapView.getProjection() == null || mMapView.getOperation() == null) {
            return false;
        }
        if (e1.getPointerCount() == 1 && e2.getPointerCount() == 1) {

            mMapView.getOperation().moveCenter(distanceX, distanceY);
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e(TAG, velocityX+" v  "+velocityY);
        return false;
    }
}

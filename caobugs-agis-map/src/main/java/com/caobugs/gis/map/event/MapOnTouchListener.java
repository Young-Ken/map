package com.caobugs.gis.map.event;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.caobugs.gis.map.BaseMap;
import com.caobugs.gis.map.MapStatus;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/6
 */
public class MapOnTouchListener implements GestureDetector.OnGestureListener,
        View.OnTouchListener {
    private BaseMap map = null;
    private OnMapDefaultListener defaultListener = null;
    private int fingleNum = 0;

    public MapOnTouchListener(BaseMap map) {
        this.map = map;
        defaultListener = new MapDefaultListener(map);
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
//        if (fingleNum == 1 || fingleNum == 0) {
//            return false;
//        }
//
//        if (zoomt == 0) {
//            zoomt = detector.getCurrentSpan();
//        }
//
//        if (zoomt - detector.getCurrentSpan() > 100) {
//            zoomt = detector.getCurrentSpan();
//            map.getMapController().zoom(MapStatus.Defualt.ZOOMIN);
//        }
//
//        if (detector.getCurrentSpan() - zoomt > 100) {
//            zoomt = detector.getCurrentSpan();
//            map.getMapController().zoom(MapStatus.Defualt.ZOOMOUT);
//        }

        return false;
    }

    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        map.getMapController().mapScroll(e1.getX() - e2.getX(), e1.getY() - e2.getY());

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        fingleNum = event.getPointerCount();
        if (fingleNum == 1) {
            defaultListener.onMapDefaultEvent(event);
        }
        return false;
    }
}

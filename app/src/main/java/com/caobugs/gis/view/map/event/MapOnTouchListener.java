package com.caobugs.gis.view.map.event;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.caobugs.gis.util.TAG;
import com.caobugs.gis.view.map.BaseMap;
import com.caobugs.gis.view.map.MapStatus;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/6
 */
public class MapOnTouchListener implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener
{
    private BaseMap map = null;
    private OnMapDefaultListener defaultListener = null;
    private int fingleNum = 0;
    public MapOnTouchListener(BaseMap map)
    {
        this.map = map;
        defaultListener = new MapDefaultListener(map);
    }

    public void onLongPress(MotionEvent e)
    {

    }

    private float zoomt = 0;
    @Override
    public boolean onScale(ScaleGestureDetector detector)
    {
        if(fingleNum == 1 || fingleNum == 0)
        {
            return false;
        }

        if(zoomt == 0)
        {
            zoomt = detector.getCurrentSpan();
        }

        if(zoomt - detector.getCurrentSpan() > 100)
        {
            zoomt = detector.getCurrentSpan();
            map.getMapController().zoom(MapStatus.Defualt.ZOOMIN);
        }

        if( detector.getCurrentSpan() - zoomt> 100)
        {
            zoomt = detector.getCurrentSpan();
            map.getMapController().zoom(MapStatus.Defualt.ZOOMOUT);
        }

        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector)
    {
        Log.e(TAG.EVENT,detector.getCurrentSpan()+" "+"onScaleBegin");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector)
    {
        zoomt = 0;
        Log.e(TAG.EVENT,detector.getCurrentSpan()+" "+"onScaleEnd");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        fingleNum = event.getPointerCount();
        if(fingleNum == 1)
        {
            defaultListener.onMapDefaultEvent(event);
        }
        return false;
    }
}

package com.snail.gis.map.event;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.snail.gis.map.BaseMap;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/6
 */
public class MapOnTouchListener implements View.OnTouchListener, GestureDetector.OnGestureListener
{
    private BaseMap map = null;
    private OnMapDefaultListener defaultListener = null;
    public MapOnTouchListener(BaseMap map)
    {
        this.map = map;
        defaultListener = new MapDefaultListener(map);
    }



    @Override
    public boolean onDown(MotionEvent e)
    {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        defaultListener.onMapDefaultEvent(event);
        return true;
    }
}

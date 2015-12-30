package com.snail.gis.map.event;

import android.view.MotionEvent;
import android.view.View;

import com.snail.gis.map.BaseMap;
import com.snail.gis.map.MapView;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/25
 */
public class MapDefaultTool implements OnMapDefaultToolListener
{
    private MapView mapView = null;
    public MapDefaultTool(MapView mapView)
    {
        this.mapView = mapView;
    }
    @Override
    public void onMapDefaultTool(BaseMap map)
    {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        return false;
    }
}

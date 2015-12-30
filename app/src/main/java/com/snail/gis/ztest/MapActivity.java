package com.snail.gis.ztest;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.snail.gis.R;
import com.snail.gis.map.BaseMap;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/17
 */
public class MapActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basemap_layout);
        final BaseMap map = (BaseMap) findViewById(R.id.baseMap);

        ViewTreeObserver vto = map.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                map.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                map.getMapOperation().setDeviceHeight(map.getMeasuredHeight());
                map.getMapOperation().setDeviceWidth(map.getMeasuredWidth());
                map.initMap();
            }
        });
    }
}

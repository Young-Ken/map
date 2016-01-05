package com.snail.gis.ztest;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.snail.gis.R;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.map.BaseMap;
import com.snail.gis.tile.factory.CoordinateSystemEnum;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/17
 */
public class MapActivity extends Activity
{
    BaseMap map = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basemap_layout);
        map = (BaseMap) findViewById(R.id.baseMap);


        ViewTreeObserver vto = map.getViewTreeObserver();

        // attribute:min_x = "12945986.606604"
        //attribute:min_y = "4838237.908444"
       // attribute:max_x = "12963719.997167"
       // attribute:max_y = "4808863.74626"
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                map.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                map.getMapOperation().setDeviceHeight(map.getMeasuredHeight());
                map.getMapOperation().setDeviceWidth(map.getMeasuredWidth());
                map.initMap(CoordinateSystemEnum.GOOGLE_CS, new Envelope(12945986.606604, 12963719.997167, 4838237.908444, 4808863.74626));
            }
        });
    }


}

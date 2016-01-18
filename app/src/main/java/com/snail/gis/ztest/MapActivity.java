package com.snail.gis.ztest;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

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
                map.getMapInfo().setDeviceHeight(map.getMeasuredHeight());
                map.getMapInfo().setDeviceWidth(map.getMeasuredWidth());
                map.initMap(CoordinateSystemEnum.LYG_HH_TILE,new Envelope(118.89889200244264,119.994384167236,34.37661887668826, 35.21442877328082));
                //map.initMap(CoordinateSystemEnum.GOOGLE_CS, new Envelope(12945986.606604, 12963719.997167, 4838237.908444, 4808863.74626));
            }
        });
    }


}

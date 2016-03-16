package com.snail.gis.ztest;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.snail.gis.R;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.view.map.BaseMap;
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
                map.initMap(CoordinateSystemEnum.GOOGLE_CS,
                        new Envelope(13012486.821215, 13032513.322625, 4396586.368211, 4385153.170403));
               // map.initMap(CoordinateSystemEnum.GOOGLE_CS, new
               //         Envelope(13351184.453363707, 13360855.024905564, 3571106.811176191, 3577801.5633670622));




                //30.524172 119.93573
                //30.57594 120.0226
               // map.initMap(CoordinateSystemEnum.LYG_HH_TILE, new Envelope(119.93573, 120.0226, 30.524172, 30.57594));
            }
        });
    }


}

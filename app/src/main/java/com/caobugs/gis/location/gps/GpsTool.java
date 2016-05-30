package com.caobugs.gis.location.gps;

import android.os.AsyncTask;
import android.widget.Toast;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.util.ApplicationContext;
import com.caobugs.gis.util.ToolGPS;
import com.caobugs.gis.view.map.BaseMap;
import com.caobugs.gis.view.map.util.Projection;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/4/26
 */
public class GpsTool
{

    private BaseMap baseMap = null;
    public GpsTool(BaseMap baseMap)
    {
        this.baseMap = baseMap;
    }

    @SuppressWarnings("unchecked")
    public void startLocationClient()
    {
        Toast.makeText(ApplicationContext.getContext(), "开始定位", Toast.LENGTH_LONG).show();

        if(!ToolGPS.isOpenGPS())
        {
            Toast.makeText(ApplicationContext.getContext(), "请打开GPS，打开GPS后从新定位!", Toast.LENGTH_LONG).show();
            return;
        }


        GpsTask gpstask = new GpsTask(ApplicationContext.getContext(), new GpsTaskCallBack()
        {
            @Override
            public void gpsConnectedTimeOut()
            {
                //Toast.makeText(baseMap.getContext(), "定位超时", Toast.LENGTH_LONG).show();
            }

            @Override
            public void gpsConnected(GpsData gpsdata)
            {
                do_gps(gpsdata);
            }

        }, 600 * 1000);
        gpstask.execute();
    }

    private void do_gps(final GpsData gpsdata)
    {
        new AsyncTask<Void, Void, String>()
        {

            @Override
            protected String doInBackground(Void... params)
            {
                return gpsdata.getLatitude() + "," + gpsdata.getLongitude() + "," + gpsdata.getAccuracy();
            }

            @Override
            protected void onPreExecute()
            {
                // dialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result)
            {
                super.onPostExecute(result);

                if (result == null)
                    return;

                String[] strs = result.split(",");
                double x = 0, y = 0;
                for (int i = 0; i < strs.length; i++)
                {
                    y = Double.valueOf(strs[1]);
                    x = Double.valueOf(strs[0]);
                    //39.942970,
                    Coordinate coordinate = Projection.getInstance(baseMap).lonLatToMercator(y, x);
                    baseMap.setMapCenterLevel(coordinate, baseMap.getLevel());
                    baseMap.refresh();
                }

                if (baseMap == null || x == 0 || y == 0)
                    return;
            }

        }.execute();
    }
}

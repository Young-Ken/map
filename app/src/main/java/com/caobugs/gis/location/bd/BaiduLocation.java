package com.caobugs.gis.location.bd;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.location.GpsInfo;
import com.caobugs.gis.location.bd.server.LocationService;
import com.caobugs.gis.util.ApplicationContext;
import com.caobugs.gis.view.map.BaseMap;
import com.caobugs.gis.view.map.util.Projection;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/4/28
 */
public class BaiduLocation
{
    private LocationService locationService = null;

    private BaseMap baseMap = null;
    public BaiduLocation(BaseMap baseMap)
    {
        this.baseMap = baseMap;
        init();
    }

    private void init()
    {
        locationService = ApplicationContext.getLocationService();
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
    }

    public void start()
    {
        locationService.registerListener(mListener);
        locationService.start();
    }

    public boolean isStart()
    {
        return locationService.isStart();
    }

    public void stop()
    {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop();
    }


    /*****
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location)
        {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError)
            {
                GpsInfo gpsInfo = GpsInfo.getInstance();
                if(location.getTime().equals(gpsInfo.getTime()))
                {

                    locationMap(location.getLongitude(), location.getLatitude());
                    return;
                } else
                {
                    gpsInfo.setTime(location.getTime());
                    gpsInfo.setLatitude(location.getLatitude());
                    gpsInfo.setLongitude(location.getLongitude());

                    if(location.getProvince() != null && !location.getProvince().equals(""))
                    {
                        gpsInfo.setProvince(location.getProvince());
                    }
                    if(location.getCity() != null && !location.getCity().equals(""))
                    {
                        gpsInfo.setCity(location.getCity());
                    }
                    if(location.getDistrict() != null && !location.getDistrict().equals(""))
                    {
                        gpsInfo.setCounty(location.getDistrict());
                    }

                    if (location.getLocType() == BDLocation.TypeGpsLocation)
                    {
                        gpsInfo.setTypeLocation(GpsInfo.TYPE_GPS_LOCATION);
                        gpsInfo.setSpeed(location.getSpeed());
                        gpsInfo.setAltitude(location.getAltitude());
                        gpsInfo.setSatelliteNum(location.getSatelliteNumber());
                    } else if (location.getLocType() == BDLocation.TypeNetWorkLocation)
                    {
                        gpsInfo.setTypeLocation(GpsInfo.TYPE_NETWORK_LOCATION);
                    } else if (location.getLocType() == BDLocation.TypeOffLineLocation)
                    {
                        gpsInfo.setTypeLocation(GpsInfo.TYPE_OFFLINE_LOCATION);
                    }
                }
                locationMap(location.getLongitude(), location.getLatitude());
            }
        }
    };

    public void locationMap(double longitude, double latitude)
    {
        //112.401181 32.119723
        Coordinate coordinate = Projection.getInstance(baseMap).lonLatToMercator(longitude, latitude);
        baseMap.setMapCenterLevel(coordinate, baseMap.getLevel());
        baseMap.refresh();
    }
}

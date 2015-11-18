package com.snail.gis.tool;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/9/14
 */
public final class ToolGPS
{
    /**
     *
     */
    private ToolGPS()
    {

    }
    /**
     * 得到LocationManager
     * @return 返回LocationManager 如果设备不支持定位服务返回null
     */
    public static LocationManager getLocationManager()
    {
        return (LocationManager) ApplicationContext.getContext().getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 判断GPS是不是打开
     * @return 返回 true GPS打开
     */
    public static boolean isOpenGPS()
    {
        LocationManager locationManager = getLocationManager();
        if (null == locationManager)
        {
            return false;
        }

        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (gps)
        {
            return true;
        }
        return false;
    }

    /**
     *  判断AGPS是否打开
     * @return 返回 true AGPS打开
     */
    public static boolean isOpenAGPS()
    {
        LocationManager locationManager = getLocationManager();
        if (null == locationManager)
        {
            return false;
        }
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (network)
        {
            return true;
        }
        return false;
    }

    /**
     * 强制打开GPS 但是部分手机会失效
     */
    public static void openGPS()
    {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        intent.addCategory("android.intent.category.ALTERNATIVE");
        intent.setData(Uri.parse("custom:3"));
        try
        {
            PendingIntent.getBroadcast(ApplicationContext.getContext(), 0, intent, 0).send();
        } catch (PendingIntent.CanceledException e)
        {
            e.printStackTrace();
        }
    }
}

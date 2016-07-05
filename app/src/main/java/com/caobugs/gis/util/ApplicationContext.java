package com.caobugs.gis.util;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;

import com.caobugs.gis.R;
import com.caobugs.gis.location.bd.server.LocationService;

/**
 * Created by Young Ken on 2015/8/19.
 */
public class ApplicationContext extends Application
{
    private static ApplicationContext instance;
    private static LocationService locationService = null;
    private static Vibrator vibrator = null;

    public static ApplicationContext getApplication()
    {
        return instance;
    }

    /**
     * 通过ID得到字符串
     */
    public static String getStringByID(int stringID)
    {
        return getContext().getResources().getString(stringID);
    }

    /**
     * 通过ID得到图片
     *
     * @param drawableID 图片的ID
     * @return 返回图片实例
     */
    public static Drawable getDrawableByID(int drawableID)
    {
        if (drawableID == -1)
            return getContext().getResources().getDrawable(R.drawable.default_img);
        return getContext().getResources().getDrawable(drawableID);
    }

    /**
     * 当前主线程的内存大小
     * @return
     */
    public static int getThreadMemory()
    {
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getMemoryClass();
    }

    /**
     *  应用程序最大可用内存 M
     *  @return MB
     */
    public static int getMaxMemory()
    {
        int kb = 1024*1024;
        int result = (int)Runtime.getRuntime().maxMemory()/kb;

        if(result <= 0)
        {
            result = 2;
        }
        return result;
    }

    /**
     * 应用程序已获得内存
     * @return MB
     */
    public static int getTotalMemory()
    {
        int kb = 1024/1024;
        return  (int) Runtime.getRuntime().totalMemory()/ kb;
    }

    /**
     * 应用程序已获得内存中未使用内存
     * @return MB
     */
    public static int getFreeMemoty()
    {
        int kb = 1024/1024;
        return (int) Runtime.getRuntime().freeMemory()/ kb;
    }

    public static Context getContext()
    {
      return getApplication().getApplicationContext();
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        intiBDLocation();
    }

    public void intiBDLocation()
    {
        locationService = new LocationService(getApplicationContext());
        vibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        //SDKInitializer.initialize(getApplicationContext());
    }

    public static LocationService getLocationService()
    {
        return locationService;
    }

    public static String getAppPackageName()
    {
       return getContext().getPackageName();
    }
}

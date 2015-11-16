package com.snail.gis.tool;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.youngken.zy_gis_demo_001.R;

/**
 * Created by Young Ken on 2015/8/19.
 */
public class ApplicationContext extends Application
{
    private static ApplicationContext instance;

    public static ApplicationContext getContext()
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

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
    }
}

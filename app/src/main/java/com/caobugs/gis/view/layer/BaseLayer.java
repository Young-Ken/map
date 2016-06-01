package com.caobugs.gis.view.layer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;

import com.caobugs.gis.util.constants.MapEvent;
import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.view.broadcast.MapBroadcastReceiver;
import com.caobugs.gis.view.map.BaseMap;
import com.caobugs.gis.view.map.MapManger;
import com.caobugs.gis.view.map.MapStatus;


/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/10
 */
public abstract class BaseLayer extends MapBroadcastReceiver
{
    public String mapStatus = MapStatus.Defualt.DEFUALT.name();
    public double moveX = 0;
    public double moveY = 0;

    private boolean isVisible = false;

    /**
     * Layer 的名字，每个layer都有唯一的名字，不能重复
     */
    private String name = null;

    protected BaseMap map = null;


    public abstract void recycle();

    abstract void initLayer();

    abstract void draw(Canvas canvas, Coordinate offSet);

    abstract void draw(Canvas canvas, double x, double y);

    abstract void draw(Canvas canvas);

    /**
     * 设置是否显示
     */
    public void setIsVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    /**
     * 得到图层显示
     *
     * @return 示返回true， 不显示返回false
     */
    public boolean getIsVisible()
    {
        return this.isVisible;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * 得到当前的图层对应的Map
     *
     * @return
     */
    public BaseMap getMap()
    {
        return MapManger.getInstance().getMap();
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
//        mapStatus = intent.getStringExtra(MapEvent.EVENT_TYPE);
//
//        if (mapStatus.equals(MapStatus.Defualt.MOVING.name()))
//        {
//            moveX = intent.getDoubleExtra(MapEvent.KEY_X, 0.0);
//            moveY = intent.getDoubleExtra(MapEvent.KEY_Y, 0.0);
//
//        } else
//        {
//            mapStatus = MapStatus.Defualt.DEFUALT.name();
//            moveY = 0;
//            moveX = 0;
//        }
        //getMap().refresh();
    }
}

package com.snail.gis.view.layer;

import android.graphics.Canvas;

import com.snail.gis.view.layer.event.OnLayerStatusListener;
import com.snail.gis.view.map.BaseMap;
import com.snail.gis.view.map.MapManger;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/10
 */
public abstract class BaseLayer
{
    private boolean isVisible = false;

    private OnLayerStatusListener listener;


    /**
     * Layer 的名字，每个layer都有唯一的名字，不能重复
     */
    private String name = null;

    protected BaseMap map = null;


    abstract void recycle();
    abstract void initLayer();
    abstract void draw(Canvas canvas);

    public void setOnLayerStatusListener(OnLayerStatusListener listener)
    {
        this.listener = listener;
    }

    /**
     * 设置是否显示
     */
    public void setIsVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    /**
     * 得到图层显示
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
     * @return
     */
    public BaseMap getMap()
    {
        return MapManger.getInstance().getMap();
    }

}

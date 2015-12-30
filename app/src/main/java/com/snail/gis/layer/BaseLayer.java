package com.snail.gis.layer;

import com.snail.gis.map.BaseMap;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/10
 */
public abstract class BaseLayer
{
    private boolean isVisible = false;
    protected BaseMap map = null;

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

    /**
     * 得到当前的图层对应的Map
     * @return
     */
    public BaseMap getMap()
    {
        return map;
    }

}

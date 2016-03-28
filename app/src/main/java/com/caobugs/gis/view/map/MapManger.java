package com.caobugs.gis.view.map;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/29
 */
public class MapManger
{
    private volatile static MapManger instance = null;

    /**
     * 全局现在就一个map
     */
    private BaseMap map = null;

    private MapManger()
    {
    }

    /**
     * 单例模式返回MapManger实例
     *
     * @return MapManger实例
     */
    public static MapManger getInstance()
    {
        if (null == instance)
        {
            synchronized (MapManger.class)
            {
                if (null == instance)
                {
                    instance = new MapManger();
                }
            }
        }
        return instance;
    }

    public void setMap(BaseMap map)
    {
        this.map = map;
    }

    public BaseMap getMap()
    {
        return map;
    }
}

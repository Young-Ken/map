package com.snail.gis.layer;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理Map的Layer
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/10
 */
public class MapLayerManger implements ILayerSubject
{
    private List<BaseLayer> arrayList = new ArrayList<>();

    /**
     * 单例模式
     */
    private volatile static MapLayerManger instance = null;

    private MapLayerManger()
    {
    }

    /**
     * 单例模式返回ShapeFileManager实例
     * @return ShapeFileManager实例
     */
    public static MapLayerManger getInstance()
    {
        if (null == instance)
        {
            synchronized (MapLayerManger.class)
            {
                if (null == instance)
                {
                    instance = new MapLayerManger();
                }
            }
        }
        return instance;
    }

    public synchronized boolean addLayer(BaseLayer layer)
    {
       return arrayList.add(layer);
    }

    public synchronized boolean removeLayer(BaseLayer layer)
    {
        return arrayList.remove(layer);
    }

    @Override
    public boolean moveLayer()
    {
        return false;
    }

    @Override
    public boolean zoomLayer()
    {
        return false;
    }

}

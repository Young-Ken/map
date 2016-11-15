package com.caobugs.gis.tile;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/11
 */
public class CoordinateSystemManager
{
    private volatile static CoordinateSystemManager instance = null;

    private CoordinateSystemManager map = null;
    private static CoordinateSystem coordinateSystem = null;
    private CoordinateSystemManager()
    {

    }

    public static CoordinateSystemManager getInstance()
    {
        if (null == instance)
        {
            synchronized (CoordinateSystemManager.class)
            {
                if (null == instance)
                {
                    instance = new CoordinateSystemManager();
                }
            }
        }
        return instance;
    }

    public void setCoordinateSystem(CoordinateSystem system)
    {
        coordinateSystem = system;
    }

    public CoordinateSystem getCoordinateSystem()
    {
        return coordinateSystem;
    }
}

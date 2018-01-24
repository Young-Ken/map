package com.caobugs.gis.coordinate;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/11
 */
public class CoordinateSystemManager
{
    private volatile static CoordinateSystemManager instance = null;

    private CoordinateSystemManager map = null;
    private static ICoordinateSystem ICoordinateSystem = null;
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

    public void setCoordinateSystem(ICoordinateSystem system)
    {
        ICoordinateSystem = system;
    }

    public ICoordinateSystem getCoordinateSystem()
    {
        return ICoordinateSystem;
    }
}

package com.caobugs.gis.tile.factory;

import com.caobugs.gis.tile.CoordinateSystem;
import com.caobugs.gis.tile.CoordinateSystemManager;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/29
 */
public abstract class ACoordinateSystemFactory
{
    abstract CoordinateSystem createCoordinateSystem(ICoordinateSystemEnum coordinateSystemEnum);

    /**
     * 创建坐标系方法,吧坐标系方法加入到坐标管理的类中
     * @param coordinateSystemEnum 坐标系枚举
     * @return 坐标系
     */
    public CoordinateSystem create(ICoordinateSystemEnum coordinateSystemEnum)
    {
        CoordinateSystem system = createCoordinateSystem(coordinateSystemEnum);
        system.initTileInfo();
        CoordinateSystemManager.getInstance().setCoordinateSystem(system);
        return system;
    }
}

package com.snail.gis.tile.factory;

import com.snail.gis.tile.CoordinateSystem;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/29
 */
public abstract class ACoordinateSystemFactory
{
    abstract CoordinateSystem createCoordinateSystem(ICoordinateSystemEnum coordinateSystemEnum);

    /**
     * 创建坐标系方法
     * @param coordinateSystemEnum 坐标系枚举
     * @return 坐标系
     */
    public CoordinateSystem create(ICoordinateSystemEnum coordinateSystemEnum)
    {
        CoordinateSystem system = createCoordinateSystem(coordinateSystemEnum);
        system.initTileInfo();
        return system;
    }
}

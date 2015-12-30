package com.snail.gis.map.util;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.map.BaseMap;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/29
 */
public class Projection
{
    private BaseMap map = null;
    public Projection(BaseMap map)
    {
        this.map = map;
    }

    /**
     * 把屏幕坐标转换成地理坐标
     * @param screenx x
     * @param screeny y
     * @return 点
     */
    public Coordinate toMapPoint(float screenx, float screeny)
    {

        return new Coordinate();
    }

    /**
     * 把地理坐标转换成屏幕坐标
     * @param point 坐标点
     * @return 点
     */
    public Coordinate toScreenPoint(Coordinate point)
    {
        return new Coordinate();
    }
}

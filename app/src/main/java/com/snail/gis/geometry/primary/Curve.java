package com.snail.gis.geometry.primary;

import com.snail.gis.geometry.Coordinate;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/7
 */
public abstract class Curve extends Geometry
{
    /**
     * 起始点
     */
    public Coordinate startPoint;

    /**
     * 结束点
     */
    public Coordinate endPoint;

    /**
     * 曲线的长度
     * @return double
     */
    public abstract double getLength();

    /**
     * 曲线是不是闭合的
     * @return 闭合返回true，不闭合返回false
     */
    public abstract boolean isClose();

    /**
     * 曲线是不环
     * @return 是环返回true，不是环返回false
     */
    public abstract boolean isRing();

}

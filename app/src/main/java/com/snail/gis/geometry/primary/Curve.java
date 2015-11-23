package com.snail.gis.geometry.primary;

import com.snail.gis.geometry.Coordinate;

import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/7
 */
public abstract class Curve extends Geometry
{

    /**
     * 得当当前线的点数
     * @return int
     */
    public abstract int getPointNum();

    /**
     * 曲线的长度
     * @return double
     */
    public abstract double getLength();

    /**
     * 曲线是不是闭合的
     * @return 闭合返回true，不闭合返回false
     */
    public abstract boolean isClosed();

    /**
     * 曲线是不环
     * @return 是环返回true，不是环返回false
     */
   // public abstract boolean isRing();


    /**
     * 返回线的外包络线
     * @param list 线集合
     * @return Envelope
     */
    public Envelope getEnvelope(final List<Coordinate> list )
    {
        double maxX = 0.0;
        double maxY = 0.0;
        double minX = 0.0;
        double minY = 0.0;
        for (Coordinate coordinate : list)
        {
            maxX = Math.max(coordinate.x, maxX);
            maxY = Math.max(coordinate.y, maxY);
            minX = Math.min(coordinate.x, minX);
            minY = Math.min(coordinate.y, minY);
        }

        return new Envelope(maxX, minX, maxY, minY);
    }

    @Override
    public int getDimension()
    {
        return 1;
    }
}

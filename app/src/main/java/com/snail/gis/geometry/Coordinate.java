package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.geometry.primary.Position;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/7
 */
public class Coordinate extends Position
{

    /**
     * 构造函数
     * @param x x
     * @param y y
     */
    public Coordinate(double x, double y)
    {
        this.x = x;
        this.y = y;
    }


    /**
     * 因为是点外接矩形就是点本身
     * @return 外接矩形
     */
    public Envelope getEnvelope()
    {
        return new Envelope(new Coordinate(this.x, this.y));
    }

    /**
     * 比较两个点是否相等
     * @param geometry geometry
     * @return 如果不是Coordinate返回false，如果Coordinate的x,y不相等返回false。
     */
    @Override
    public boolean equals(final Geometry geometry)
    {
        if (geometry instanceof Coordinate)
        {
            Coordinate coordinate = (Coordinate) geometry;
            return ((coordinate.getX() == x) && (coordinate.getY() == y));
        } else {
            return false;
        }
    }

    @Override
    public double getDistance(Geometry geometry)
    {

        return 0;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
}

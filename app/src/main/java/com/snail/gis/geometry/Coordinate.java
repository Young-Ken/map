package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.geometry.primary.Position;
import com.snail.gis.enumeration.Dimension;

import java.util.ArrayList;
import java.util.List;

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
     * 构造函数
     * @param coordinate coordinate
     */
    public Coordinate(Coordinate coordinate)
    {
        this.x = coordinate.x;
        this.y = coordinate.y;
    }

    /**
     * 构造函数
     */
    public Coordinate()
    {
    }

    /**
     * 因为是点外接矩形就是点本身
     * @return 外接矩形
     */
    public Envelope getEnvelope()
    {
        return new Envelope(new Coordinate(this.x, this.y));
    }

    @Override
    public boolean isEmpty()
    {
        return false;
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


    /**
     * 返回点到点的距离
     * @param coordinate Coordinate
     * @return double 返回点到点的距离
     */
    public double distance(Coordinate coordinate)
    {
        double dx = x - coordinate.x;
        double dy = y - coordinate.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public int getDimension()
    {
        return 0;
    }

    @Override
    public int getBoundaryDimension()
    {
        return Dimension.FALSE;
    }

    public int compareTo(Object o)
    {
        Coordinate other = (Coordinate) o;

        if (x < other.x) return -1;
        if (x > other.x) return 1;
        if (y < other.y) return -1;
        if (y > other.y) return 1;
        return 0;
    }

    /**
     * 因为点没有线 返回空的list
     * @return
     */
    @Override
    public List<Coordinate> getLines()
    {
        return new ArrayList<>();
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

package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.geometry.primary.Line;
import com.snail.gis.math.MathUtil;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LineSegment extends Line
{

    /**
     * 线段构造函数
     * @param startPoint 起始点
     * @param endPoint 结束点
     */
    public LineSegment(Coordinate startPoint, Coordinate endPoint)
    {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /**
     * 线段构造函数
     * @param x1 起始点 x
     * @param y1 起始点 y
     * @param x2 结束点 x
     * @param y2 结束点 y
     */
    public LineSegment(double x1, double y1, double x2, double y2)
    {
        this.startPoint = new Coordinate(x1, y1);
        this.endPoint = new Coordinate(x2, y2);
    }

    /**
     * 线段构造函数
     * @param lineSegment line
     */
    public LineSegment(LineSegment lineSegment)
    {
        this(lineSegment.startPoint, lineSegment.endPoint);
    }

    public LineSegment()
    {
        this(new Coordinate(), new Coordinate());
    }


    @Override
    public Envelope getEnvelope()
    {
        return null;
    }

    @Override
    public boolean equals(Geometry geometry)
    {
        return false;
    }

    public double distance(Coordinate p)
    {
        //ssgdgdgdg
        return 0;
    }

    @Override
    public double getLength()
    {
        return MathUtil.distanceTwoPoint(startPoint, endPoint);
    }

    @Override
    public boolean isClose()
    {
        return false;
    }

    @Override
    public boolean isRing()
    {
        return false;
    }

}

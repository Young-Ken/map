package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Curve;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.enumeration.Dimension;
import com.snail.gis.lgorithm.cg.CGAlgorithms;
import com.snail.gis.lgorithm.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LineSegment extends Curve
{

    private Coordinate startPoint;
    private Coordinate endPoint;

    /**
     * 线段构造函数
     */
    public LineSegment()
    {
        this(new Coordinate(), new Coordinate());
    }
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
     * @param lineSegment LineSegment
     */
    public LineSegment(LineSegment lineSegment)
    {
        this(lineSegment.startPoint, lineSegment.endPoint);
    }


    /**
     * 线段返回的线段的外包络线
     * @return
     */
    @Override
    public Envelope getEnvelope()
    {
       return new Envelope(startPoint, endPoint);
    }

    @Override
    public boolean isEmpty()
    {
        return (startPoint == null || endPoint == null);
    }

    @Override
    public boolean equals(Geometry geometry)
    {
        if (geometry instanceof LineSegment)
        {
            LineSegment lineSegment = (LineSegment) geometry;
            return startPoint.equals(lineSegment.startPoint) && endPoint.equals(lineSegment.endPoint);
        } else {
            return false;
        }
    }

    /**
     * 点到线的距离
     * @param p 点p
     * @return double 点到线的距离
     */
    public double distance(Coordinate p)
    {
        return CGAlgorithms.distancePointLine(p,startPoint, endPoint);
    }

    public double distance(LineSegment lineSegment)
    {
        return 0.0;
    }


    @Override
    public int getPointNum()
    {
        if(isEmpty())
        {
            return 0;
        } else
        {
            return 2;
        }
    }

    @Override
    public int getBoundaryDimension()
    {
        return Dimension.FALSE;
    }

    @Override
    public List<Coordinate> getLines()
    {
        List<Coordinate> list = new ArrayList<>();
        list.add(startPoint);
        list.add(endPoint);
        return list;
    }

    @Override
    public double getLength()
    {
        return MathUtil.distanceTwoPoint(startPoint, endPoint);
    }

    @Override
    public boolean isClosed()
    {
        return false;
    }

    public double angle()
    {
        return Math.atan2(startPoint.y - endPoint.y, startPoint.x - endPoint.x);
    }
    public String toString()
    {
        return "LINESTRING( " +
                startPoint.x + " " + startPoint.y
                + ", " +
                endPoint.x + " " + endPoint.y + ")";
    }
}

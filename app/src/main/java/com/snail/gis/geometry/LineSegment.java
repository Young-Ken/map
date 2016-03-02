package com.snail.gis.geometry;

import com.snail.gis.algorithm.MathWorld;
import com.snail.gis.geometry.primary.Envelope;

import com.snail.gis.enumeration.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 线段
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LineSegment implements Comparable, Serializable
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
     * 得到起始点或者结束点
     * @param index 索引
     * @return 0 开始前点 其它结束点
     */
    public Coordinate getCoordinate(int index)
    {
        if (index == 0)
        {
            return startPoint;
        }
        return endPoint;
    }

    /**
     * 设置LineSegment
     * @param another
     */
    public void setCoordinates(LineSegment another)
    {
        setCoordinates(another.startPoint, another.endPoint);
    }

    /**
     * 设置LineSegment
     * @param startPoint 开始点
     * @param endPoint 结束点
     */
    public void setCoordinates(Coordinate startPoint, Coordinate endPoint)
    {
        this.startPoint.x = startPoint.x;
        this.startPoint.y = startPoint.y;
        this.endPoint.x = endPoint.x;
        this.endPoint.y = endPoint.y;
    }


    /**
     * 线段长度
     * @return double
     */
    public double getLength()
    {
        return startPoint.distance(endPoint);
    }

    /**
     * 线段是否水平
     * @return 水平true
     */
    public boolean isHorizontal()
    {
        return startPoint.y == endPoint.y;
    }

    /**
     * 线段是否竖直
     * @return 竖直 true
     */
    public boolean isVertical()
    {
        return startPoint.x == endPoint.x;
    }

    /**
     * 线段返回的线段的外包络线
     * @return
     */
    public Envelope getEnvelope()
    {
       return new Envelope(startPoint, endPoint);
    }

    public boolean isEmpty()
    {
        return (startPoint == null || endPoint == null);
    }



    /**
     * 点到线的距离
     * @param p 点p
     * @return double 点到线的距离
     */
    public double distance(Coordinate p)
    {
       return MathWorld.distancePointSegment(p, startPoint, endPoint);
    }

    /**
     * 线到线的距离
     * @param another LineSegment
     * @return 线到线的距离
     */
    public double distance(LineSegment another)
    {
        return MathWorld.distanceSegmentSegment(startPoint, endPoint, another.startPoint, another.endPoint);
    }

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

    public List<Coordinate> getLines()
    {
        List<Coordinate> list = new ArrayList<>();
        list.add(startPoint);
        list.add(endPoint);
        return list;
    }

    public double angle()
    {
        return Math.atan2(endPoint.y - startPoint.y, endPoint.x - startPoint.x);
    }


    public String toString()
    {
        return "LINESTRING( " +
                startPoint.x + " " + startPoint.y
                + ", " +
                endPoint.x + " " + endPoint.y + ")";
    }
    @Override
    public int compareTo(Object another)
    {
        return 0;
    }
}

package com.snail.gis.geometry;

import java.io.Serializable;

/**
 * 简单点，这个类初始化默认是 （0,0）
 * @author Young-Ken
 * @version 0.1
 * @since 2016/2/23
 */
public class Coordinate implements Comparable, Cloneable, Serializable
{

    /**
     * Coordinate 的 x
     */
    public double x;

    /**
     * Coordinate 的 y
     */
    public double y;

    /**
     * 用于 setOrdinate 和 getOrdinate的 ordinateIndex
     */
    public static final int X = 0;
    public static final int Y = 1;

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
     * 构造函数 x = y = 0
     */
    public Coordinate()
    {
        this(0, 0);
    }

    /**
     * 构造函数
     * @param another 其它 Coordinate
     */
    public Coordinate(Coordinate another)
    {
        this(another.x, another.y);
    }

    /**
     * 设置 Coordinate
     * @param another 其它 Coordinate
     */
    public void setCoordinate(Coordinate another)
    {
        x = another.x;
        y = another.y;
    }
    /**
     * 取得 Coordinate 的x 或者是 y
     * @param ordinateIndex 索引 X，Y
     */

    public double getOrdinate(int ordinateIndex)
    {
        switch (ordinateIndex) {
            case X: return x;
            case Y: return y;
        }
        throw new IllegalArgumentException("没有这 ordinateIndex: " + ordinateIndex);
    }

    /**
     * 设置 Coordinate 的x 或者是 y
     * @param ordinateIndex 索引 X，Y
     * @param value 值
     */
    public void setOrdinate(int ordinateIndex, double value)
    {
        switch (ordinateIndex) {
            case X:
                x = value;
                break;
            case Y:
                y = value;
                break;
            default:
                throw new IllegalArgumentException("没有这 ordinateIndex: " + ordinateIndex);
        }
    }

    @Override
    public int compareTo(Object another)
    {
        Coordinate coordinate = (Coordinate) another;

        if (x < coordinate.x) return -1;
        if (x > coordinate.x) return 1;
        if (y < coordinate.y) return -1;
        if (y > coordinate.y) return 1;
        return 0;
    }

    /**
     * 值相等比较
     * @param another Object对象
     * @return 是 Coordinate对象，值相等返回true
     */
    @Override
    public boolean equals(Object another)
    {
        if (!(another instanceof Coordinate))
        {
            return false;
        }
        Coordinate coordinate = (Coordinate) another;

        if (x != coordinate.x || y != coordinate.y)
        {
            return false;
        }
        return true;
    }

    /**
     * 点到点的距离
     * @param another 其他 Coordinate
     * @return 距离
     */
    public double distance(Coordinate another)
    {
        double dx = x - another.x;
        double dy = y - another.y;

        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    protected Object clone()
    {
        try {
            Coordinate coordinate = (Coordinate) super.clone();
            return coordinate;
        } catch (CloneNotSupportedException e) {

            return null;
        }
    }

    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}

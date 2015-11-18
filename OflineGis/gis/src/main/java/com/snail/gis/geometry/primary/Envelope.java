package com.snail.gis.geometry.primary;

import com.snail.gis.geometry.Coordinate;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/7
 */
public class Envelope
{
    /**
     * x 最小
     */
    private double minX;

    /**
     * x 最大
     */
    private double maxX;

    /**
     * y 最小
     */
    private double minY;

    /**
     * Y 最大
     */
    private double maxY;

    /**
     * 无参构造函数
     */
    public Envelope()
    {
        init();
    }

    /**
     * 构造函数
     *
     * @param x1 x 坐标
     * @param x2 x 坐标
     * @param y1 y 坐标
     * @param y2 y坐标
     */
    public Envelope(double x1, double x2, double y1, double y2)
    {
        init(x1, x2, y1, y2);
    }

    /**
     * 构造方法
     *
     * @param p1 线段
     * @param p2 线段
     */
    public Envelope(Coordinate p1, Coordinate p2)
    {
        init(p1, p2);
    }

    public Envelope(Coordinate p)
    {
        init(p);
    }

    private boolean isNull()
    {
        return maxX < minY;
    }
    /**
     * 得到矩形的宽
     * @return double
     */
    public double getWidth()
    {
        if (isNull())
        {
            return 0;
        }
        return maxY - minY;
    }

    /**
     * 得到矩形的高
     * @return double
     */
    public double getHeight()
    {
        if (isNull())
        {
            return 0;
        }
        return maxX - minX;
    }

    /**
     * 矩形转换
     * @param transX x轴方法增量
     * @param transY y轴增量
     */
    public void translate(final double transX, final double transY)
    {
        if (isNull())
        {
            return;
        }
        init(getMinX() + transX, getMaxX() + transX, getMinY() + transY,
                getMaxY() + transY);
    }

    /**
     * 得到矩形的中心
     * @return 中心点
     */
    public Coordinate centre()
    {
        if (isNull())
        {
            return null;
        }
        return new Coordinate((maxX - minX) / 2.0, (maxY - minY) / 2.0);
    }


    private void init()
    {
        setToNull();
    }

    public void init(Coordinate p1, Coordinate p2)
    {
        init(p1.getX(), p2.getX(), p1.getY(), p2.getY());
    }

    public void init(Envelope env)
    {
        this.minX = env.minX;
        this.maxX = env.maxX;
        this.minY = env.minY;
        this.maxY = env.maxY;
    }

    private void init(double x1, double x2, double y1, double y2)
    {
        if (x1 < x2)
        {
            minX = x1;
            maxX = x2;
        } else
        {
            minX = x2;
            maxX = x1;
        }
        if (y1 < y2)
        {
            minY = y1;
            maxY = y2;
        } else
        {
            minY = y2;
            maxY = y1;
        }
    }

    public void init(Coordinate p)
    {
        init(p.getX(), p.getX(), p.getY(), p.getY());
    }

    /**
     * 这个Envelope根本就不存在，这个这个用作初始化Envelope
     */
    private void setToNull()
    {
        minX = 0;
        maxX = -1;
        minY = 0;
        maxY = -1;
    }

    public double getMaxX()
    {
        return maxX;
    }

    public double getMaxY()
    {
        return maxY;
    }

    public double getMinX()
    {
        return minX;
    }

    public double getMinY()
    {
        return minY;
    }

    /**
     * 从写toString方法
     *
     * @return Envelope toString（）
     */
    public String toString()
    {
        return "Env[" + minX + " : " + maxX + ", " + minY + " : " + maxY + "]";
    }
}

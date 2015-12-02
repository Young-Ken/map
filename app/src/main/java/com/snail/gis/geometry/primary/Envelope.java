package com.snail.gis.geometry.primary;

import com.snail.gis.geometry.Coordinate;

import java.util.ArrayList;
import java.util.List;

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

    /**
     *  如果矩形在矩形外部一定不相交
     * @param other Envelope
     * @return
     */
    public boolean intersects(Envelope other)
    {
        if (isNull() || other.isNull())
        {
            return false;
        }
        return !(other.minX > maxX || other.minY > maxY || other.maxX < minX || other.maxY < minY);
    }
    /**
     *  如果点在矩形外部一定不相交
     * @param point Coordinate
     * @return
     */
    public boolean intersects(Coordinate point)
    {
        if (isNull() || point.isEmpty())
        {
            return false;
        }
        return intersects(point.x, point.y);
    }

    /**
     * 如果点在矩形外部一定不相交
     * @param x x
     * @param y y
     * @return
     */
    public boolean intersects(double x, double y)
    {
        if (isNull())
        {
            return false;
        }
        return ! (x > maxX || x < minX || y > maxY || y < minY);
    }

    /**
     * 矩形包含矩形
     * @param other 矩形
     * @return 包含返回true
     */
    public boolean contains(Envelope other)
    {
        if (isNull())
        {
            return false;
        }
        return (minX <= other.minX && minY <= other.minY && maxX >= other.maxX && maxY >= other.maxY);
    }

    /**
     * 包含点
     * @param point 点
     * @return 包含返回true
     */
    public boolean contains(Coordinate point)
    {
        if (isNull())
        {
            return false;
        }
        return contains(point.x, point.y);
    }

    /**
     * 包含 x ，y
     * @param x x
     * @param y y
     * @return 包含返回true
     */
    public boolean contains(double x, double y)
    {
        if (isNull())
        {
            return false;
        }
        return (x >= minX && x <= maxX && y >= minY && y <= maxY);
    }

    public void expandToInclude(double x, double y) {
        if (isNull()) {
            minX = x;
            maxX = x;
            minY = y;
            maxY = y;
        }
        else {
            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (y < minY) {
                minY = y;
            }
            if (y > maxY) {
                maxY = y;
            }
        }
    }

    /**
     * 把矩形转换成线
     * @return
     */
    public List<Coordinate> getLines()
    {
        List<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(getMinX(), getMinY()));
        list.add(new Coordinate(getMaxX(), getMinY()));
        list.add(new Coordinate(getMaxX(), getMaxY()));
        list.add(new Coordinate(getMinX(), getMaxY()));
        list.add(new Coordinate(getMinX(), getMinY()));
        return list;
    }

    public static boolean intersects(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2)
    {
        double minq = Math.min(q1.x, q2.x);
        double maxq = Math.max(q1.x, q2.x);
        double minp = Math.min(p1.x, p2.x);
        double maxp = Math.max(p1.x, p2.x);

        if (minp > maxq)
            return false;
        if (maxp < minq)
            return false;

        minq = Math.min(q1.y, q2.y);
        maxq = Math.max(q1.y, q2.y);
        minp = Math.min(p1.y, p2.y);
        maxp = Math.max(p1.y, p2.y);

        if (minp > maxq)
        {
            return false;
        }

        if (maxp < minq)
        {
            return false;
        }
        return true;
    }

    public static boolean intersects(Coordinate p1, Coordinate p2, Coordinate q)
    {
        if (((q.x >= (p1.x < p2.x ? p1.x : p2.x)) && (q.x <= (p1.x > p2.x ? p1.x : p2.x))) &&
                ((q.y >= (p1.y < p2.y ? p1.y : p2.y)) && (q.y <= (p1.y > p2.y ? p1.y : p2.y)))) {
            return true;
        }
        return false;
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
        return "Envelope[" + minX + " : " + maxX + ", " + minY + " : " + maxY + "]";
    }
}

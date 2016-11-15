package com.caobugs.gis.algorithm;

import com.caobugs.gis.geometry.Coordinate;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/13
 */
public class MathUtil
{
    /**
     *
     *  这里不进行开放 为了节省效率
     * @param c1 Point
     * @param c2 Point
     * @return 距离的平方
     */
    public static double distanceTwoPointNoSquare(Coordinate c1, Coordinate c2)
    {
        double dx = c1.x - c2.x;
        double dy = c1.y - c2.y;
        return dx * dx + dy * dy;
    }

    /**
     *
     *  这里不进行开放 为了节省效率
     * @param c1 Point
     * @param c2 Point
     * @return 距离的平方
     */
    public static double distanceTwoPoint(Coordinate c1, Coordinate c2)
    {
        return Math.sqrt(distanceTwoPointNoSquare(c1, c2));
    }

    public static double distanceTwoPointNoSquare(double x1, double y1, double x2, double y2)
    {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return dx * dx + dy * dy;
    }

    public static double distanceTwoPoint(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(distanceTwoPointNoSquare(x1, y1, x2, y2));
    }
    public static Coordinate middleTwoPoint(Coordinate c1, Coordinate c2)
    {
        return new Coordinate((c1.x + c2.x)/2, (c1.y + c2.y)/2);
    }

    /**
     * 两个数之间的
     * @param value 要比较的数
     * @param a 数字a
     * @param b 数字b
     * @return 如果在两个数之间返回true
     */
    public static boolean between(double value, double a, double b)
    {
        if (b > a)
        {
            double temp;
            temp = a;
            a = b;
            b = temp;
        }

        if (value <= a)
        {
           if(value >= b)
           {
               return true;
           }
        }

        return false;
    }
}

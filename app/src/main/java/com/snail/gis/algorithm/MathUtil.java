package com.snail.gis.algorithm;

import com.snail.gis.geometry.Coordinate;

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
     * @param c1 Coordinate
     * @param c2 Coordinate
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
     * @param c1 Coordinate
     * @param c2 Coordinate
     * @return 距离的平方
     */
    public static double distanceTwoPoint(Coordinate c1, Coordinate c2)
    {
        return Math.sqrt(distanceTwoPointNoSquare(c1, c2));
    }
}

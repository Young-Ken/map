package com.snail.gis.lgorithm.cg;

import com.snail.gis.geometry.Coordinate;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/13
 */
public class CGAlgorithms
{
    /**
     * 点到线的距离
     * @param p 点p
     * @param A 向量A
     * @param B 向量B
     * @return 距离
     */
    public static double distancePointLine(Coordinate p, Coordinate A, Coordinate B)
    {
        if (A.x == B.x && A.y == B.y)
        {
            return p.distance(A);
        }
        double len2 = (B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y);
        double r = ((p.x - A.x) * (B.x - A.x) + (p.y - A.y) * (B.y - A.y)) / len2;
        if (r <= 0.0)
        {
            return p.distance(A);
        }

        if (r >= 1.0)
        {
            return p.distance(B);
        }

        double s = ((A.y - p.y) * (B.x - A.x) - (A.x - p.x) * (B.y - A.y)) / len2;
        return Math.abs(s) * Math.sqrt(len2);
    }

    public static boolean isPointInRing(Coordinate p, Coordinate[] ring)
    {
        //return locatePointInRing(p, ring) != Location.EXTERIOR;
        return false;
    }
}

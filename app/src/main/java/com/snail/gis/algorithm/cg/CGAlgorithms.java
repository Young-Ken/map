package com.snail.gis.algorithm.cg;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.algorithm.DD;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/13
 */
public class CGAlgorithms
{

    private static final double DP_SAFE_EPSILON = 1e-15;
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

    public static int orientationIndex(Coordinate p1, Coordinate p2, Coordinate q)
    {

        int index = orientationIndexFilter(p1, p2, q);
        if (index <= 1) return index;

        DD dx1 = DD.valueOf(p2.x).selfAdd(-p1.x);
        DD dy1 = DD.valueOf(p2.y).selfAdd(-p1.y);
        DD dx2 = DD.valueOf(q.x).selfAdd(-p2.x);
        DD dy2 = DD.valueOf(q.y).selfAdd(-p2.y);

        return dx1.selfMultiply(dy2).selfSubtract(dy1.selfMultiply(dx2)).signum();
    }

    private static int orientationIndexFilter(Coordinate pa, Coordinate pb, Coordinate pc)
    {
        double detsum;

        double detleft = (pa.x - pc.x) * (pb.y - pc.y);
        double detright = (pa.y - pc.y) * (pb.x - pc.x);
        double det = detleft - detright;

        if (detleft > 0.0) {
            if (detright <= 0.0) {
                return signum(det);
            }
            else {
                detsum = detleft + detright;
            }
        }
        else if (detleft < 0.0) {
            if (detright >= 0.0) {
                return signum(det);
            }
            else {
                detsum = -detleft - detright;
            }
        }
        else {
            return signum(det);
        }

        double errbound = DP_SAFE_EPSILON * detsum;
        if ((det >= errbound) || (-det >= errbound)) {
            return signum(det);
        }

        return 2;
    }

    private static int signum(double x)
    {
        if (x > 0) return 1;
        if (x < 0) return -1;
        return 0;
    }

    public static boolean isPointInRing(Coordinate p, Coordinate[] ring)
    {
        return false;
    }
}

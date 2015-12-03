package com.snail.gis.topology;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.LineSegment;
import com.snail.gis.geometry.primary.Envelope;

/**
 * 线段和线段相交
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/27
 */
public class SegmentIntersectSegment
{
    public int result = -1;

    public final static int NO_INTERSECTION = 0;

    public final static int POINT_INTERSECTION = 1;

    public final static int COLLINEAR_INTERSECTION = 2;

    public int intersects(LineSegment segment1, LineSegment segment2)
    {
        return intersects(segment1.getStartPoint(), segment1.getEndPoint(),
                segment2.getStartPoint(), segment2.getEndPoint());
    }

    public int intersects(Coordinate c1, Coordinate c2, LineSegment segment)
    {
        return intersects(c1, c2, segment.getStartPoint(), segment.getEndPoint());
    }

    /**
     * 判断两条线段相交
     * @param c1 Coordinate
     * @param c2 Coordinate
     * @param p1 Coordinate
     * @param p2 Coordinate
     * @return 相交返回 POINT_INTERSECTION 或者 COLLINEAR_INTERSECTION
     */
    public int intersects(Coordinate a, Coordinate b, Coordinate c, Coordinate d)
    {
        /**
         * 如果 ab 和 cd 的外接矩形不相交就一定不相交
         */
        if (!Envelope.intersects(a, b, c, d))
        {
            return NO_INTERSECTION;
        }

        //判断在ab两侧
        double abc = cross(a, b, c);
        double abd = cross(a, b, d);

        if (abc * abd > 0)
        {
            return NO_INTERSECTION;
        }
        //判断在cd两侧
        double cda = cross(c, d, a);
        double cdb = cross(c, d, b);

        if (cda * cdb > 0)
        {
            return NO_INTERSECTION;
        }

        if (abc == 0 && abd == 0 && cda==0 && cdb==0)
        {
           return computeCollinearIntersection(a, b, c, d);
        }

        if (abc == 0 || abd == 0 || cda == 0 || cdb == 0)
        {
            return POINT_INTERSECTION;
        }

        return NO_INTERSECTION;
    }

    private int computeCollinearIntersection(Coordinate a, Coordinate b, Coordinate c, Coordinate d)
    {
        boolean abc = Envelope.intersects(a, b, c);
        boolean abd = Envelope.intersects(a, b, d);
        boolean cda = Envelope.intersects(c, d, a);
        boolean cdb = Envelope.intersects(c, d, b);

        if (abc && abd)
        {
            return COLLINEAR_INTERSECTION;
        }

        if (cda && cdb)
        {
            return COLLINEAR_INTERSECTION;
        }

        if (a.equals(c) || b.equals(c))
        {
            if (!abd)
            {
               return POINT_INTERSECTION;
            } else
            {
                return COLLINEAR_INTERSECTION;
            }
        }

        if (c.equals(a) || d.equals(a))
        {
            if(!cdb)
            {
                return POINT_INTERSECTION;
            } else
            {
                return COLLINEAR_INTERSECTION;
            }
        }

        if (abc || abd)
        {
            return COLLINEAR_INTERSECTION;
        }

        if (cda || cdb)
        {
            return COLLINEAR_INTERSECTION;
        }

        return NO_INTERSECTION;
    }

    /**
     * 向量 ac ab 的叉积
     * @param a Coordinate
     * @param b Coordinate
     * @param c Coordinate
     * @return 叉积
     */
    private double cross(Coordinate a, Coordinate b, Coordinate c)
    {
        return (b.x - a.x)*(c.y - a.y)- (c.x - a.x) *(b.y - a.y);
    }


    private int signum(double num)
    {
        if (num > 0)
        {
            return 1;
        } else if(num < 0)
        {
            return -1;
        }else {
            return 0;
        }
    }

    public boolean hasIntersection()
    {
        return result != NO_INTERSECTION;
    }
}

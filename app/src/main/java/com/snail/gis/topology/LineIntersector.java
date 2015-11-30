package com.snail.gis.topology;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.LineSegment;
import com.snail.gis.geometry.primary.Envelope;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/27
 */
public class LineIntersector
{

    public boolean intersector(LineSegment segment1, LineSegment segment2)
    {
        return intersector(segment1.getStartPoint(), segment1.getEndPoint(),
                segment2.getStartPoint(), segment2.getEndPoint());
    }

    public boolean intersector(Coordinate c1, Coordinate c2, LineSegment segment)
    {
        return intersector(c1, c2, segment.getStartPoint(), segment.getEndPoint());
    }

    /**
     * @param c1 Coordinate
     * @param c2 Coordinate
     * @param p1 Coordinate
     * @param p2 Coordinate
     * @return
     */
    public boolean intersector(Coordinate a, Coordinate b, Coordinate c, Coordinate d)
    {
        /**
         * 如果 ab 和 cd 的外接矩形不相交就一定不相交
         */
        if(!Envelope.intersects(a, b, c, d))
        {
            return false;
        }

        //判断在ab两侧
        double abBothSides = cross(a, b, c) * cross(a, b, d);

        //判断在cd两侧
        double cdBothSides = cross(c, d, a) * cross(c, d, b);

        if (cdBothSides > 0 || abBothSides > 0)
        {
            return false;
        }


        return true;
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
        // (x2-x1)(y3-y1) - (y2-y1)(x3-x1)
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

}

package com.snail.gis.topology;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/27
 */
public class RectangleLineIntersector
{
    private LineIntersector li = new RobustLineIntersector();

    private Envelope rectEnv;

    private Coordinate diagUp0;
    private Coordinate diagUp1;
    private Coordinate diagDown0;
    private Coordinate diagDown1;

    public RectangleLineIntersector(Envelope rectEnv)
    {
        this.rectEnv = rectEnv;

        /**
         * 0
         */
        diagUp0 = new Coordinate(rectEnv.getMinX(), rectEnv.getMinY());

        /**
         * 3
         */
        diagUp1 = new Coordinate(rectEnv.getMaxX(), rectEnv.getMaxY());

        /**
         * 4
         */
        diagDown0 = new Coordinate(rectEnv.getMinX(), rectEnv.getMaxY());

        /**
         * 2
         */
        diagDown1 = new Coordinate(rectEnv.getMaxX(), rectEnv.getMinY());
    }

    public boolean intersects(Coordinate p0, Coordinate p1)
    {
        Envelope segEnv = new Envelope(p0, p1);
        if (! rectEnv.intersects(segEnv))
        {
            return false;
        }

        if (rectEnv.intersects(p0)) return true;
        if (rectEnv.intersects(p1)) return true;

        if (p0.compareTo(p1) > 0)
        {
            Coordinate tmp = p0;
            p0 = p1;
            p1 = tmp;
        }
        boolean isSegUpwards = false;
        if (p1.y > p0.y)
        {
            isSegUpwards = true;
        }

        if (isSegUpwards)
        {
            li.computeIntersection(p0, p1, diagDown0, diagDown1);
        } else
        {
            li.computeIntersection(p0, p1, diagUp0, diagUp1);
        }

        if (li.hasIntersection())
            return true;
        return false;


    }

    }
}

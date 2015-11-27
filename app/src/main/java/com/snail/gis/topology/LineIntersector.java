package com.snail.gis.topology;

import com.snail.gis.geometry.Coordinate;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/27
 */
public abstract class LineIntersector
{
    public final static int DONT_INTERSECT = 0;
    public final static int DO_INTERSECT = 1;
    public final static int COLLINEAR = 2;

    /**
     * Indicates that line segments do not intersect
     */
    public final static int NO_INTERSECTION = 0;

    /**
     * Indicates that line segments intersect in a single point
     */
    public final static int POINT_INTERSECTION = 1;

    /**
     * Indicates that line segments intersect in a line segment
     */
    public final static int COLLINEAR_INTERSECTION = 2;
    protected Coordinate[][] inputLines = new Coordinate[2][2];
    protected Coordinate[] intPt = new Coordinate[2];
    int result ;
    protected boolean isProper;

    public void computeIntersection(Coordinate p1, Coordinate p2, Coordinate p3, Coordinate p4)
    {
        inputLines[0][0] = p1;
        inputLines[0][1] = p2;
        inputLines[1][0] = p3;
        inputLines[1][1] = p4;
        result = computeIntersect(p1, p2, p3, p4);
    }

    protected abstract int computeIntersect(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2);

    public boolean hasIntersection()
    {
        return result != NO_INTERSECTION;
    }

}

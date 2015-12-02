package com.snail.gis.topology;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;

/**
 * 矩形和线相交
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/27
 */
public class RectangleIntersectSegment
{
    /**
     * 矩形
     */
    Envelope rectangle = null;

    /**
     * 线段和线段相交
     */
    SegmentIntersectSegment lineIntersect = new SegmentIntersectSegment();
    public RectangleIntersectSegment(Envelope rectangle)
    {
        this.rectangle = rectangle;
    }

    /**
     * 矩形和线相交
     * @param p1 Coordinate
     * @param p2 Coordinate
     * @return 相交返回 true 不相交返回 false
     */
    public boolean intersects(Coordinate p1, Coordinate p2)
    {
        Envelope lineEnv = new Envelope(p1, p2);
        if(!rectangle.intersects(lineEnv))
        {
            return false;
        }

        //如果p1，p2其中一个点在矩形内部就相交
        if (rectangle.intersects(p1) || rectangle.intersects(p2))
        {
           return true;
        }

        Coordinate rectangle1 = new Coordinate(rectangle.getMinX(), rectangle.getMinY());
        Coordinate rectangle2 = new Coordinate(rectangle.getMaxX(), rectangle.getMinY());
        Coordinate rectangle3 = new Coordinate(rectangle.getMaxX(), rectangle.getMaxY());
        Coordinate rectangle4 = new Coordinate(rectangle.getMinX(), rectangle.getMaxY());

        //判断矩形的对角线是不是很矩形相交
        if (lineIntersect.intersects(p1, p2, rectangle1, rectangle3) != 2)
        {
            return true;
        }

        if (lineIntersect.intersects(p1, p2, rectangle2, rectangle4) != 2)
        {
            return true;
        }
        return false;
    }
}

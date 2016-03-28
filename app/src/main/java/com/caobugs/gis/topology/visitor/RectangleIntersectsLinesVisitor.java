package com.caobugs.gis.topology.visitor;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.geometry.primary.Geometry;
import com.caobugs.gis.topology.relate.RectangleIntersectSegment;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/12/2
 */
public class RectangleIntersectsLinesVisitor extends ShortCircuitedGeometryVisitor
{
    private boolean intersects = false;
    Envelope rectangle = null;
    RectangleIntersectSegment segmentIntersect;

    public RectangleIntersectsLinesVisitor(Envelope rectangle)
    {
        this.rectangle = rectangle;
        segmentIntersect = new RectangleIntersectSegment(rectangle);
    }

    @Override
    protected void visit(Geometry element)
    {
        Coordinate[] list = element.getCoordinates();
        int size = list.length;
        for (int i = 0; i < size - 1; i++)
        {
            if (segmentIntersect.intersects(list[i],list[i+1]))
            {
                intersects = true;
                return;
            }
        }
    }
    public boolean intersects()
    {
        return intersects;
    }
    @Override
    protected boolean isDone()
    {
        return intersects == true;
    }
}

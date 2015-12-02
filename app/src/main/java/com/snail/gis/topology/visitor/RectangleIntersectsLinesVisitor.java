package com.snail.gis.topology.visitor;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.topology.RectangleLineSegmentIntersect;

import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/12/2
 */
public class RectangleIntersectsLinesVisitor extends ShortCircuitedGeometryVisitor
{
    private boolean intersects = false;
    Envelope rectangle = null;
    RectangleLineSegmentIntersect segmentIntersect;

    public RectangleIntersectsLinesVisitor(Envelope rectangle)
    {
        this.rectangle = rectangle;
        segmentIntersect = new RectangleLineSegmentIntersect(rectangle);
    }

    @Override
    protected void visit(Geometry element)
    {
        List<Coordinate> list = element.getLines();
        int size = list.size();
        for (int i = 0; i < size - 1; i++)
        {
            if (segmentIntersect.intersects(list.get(i),list.get(i+1)))
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

package com.caobugs.gis.topology.visitor;

import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.geometry.primary.Geometry;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/12/2
 */
public class RectangleIntersectsVisitor extends ShortCircuitedGeometryVisitor
{
    private Envelope rectangle = null;
    private boolean intersects = false;
    public RectangleIntersectsVisitor(Envelope rectangle)
    {
        this.rectangle = rectangle;
    }

    @Override
    protected void visit(Geometry geometry)
    {
        Envelope geometryEnv = geometry.getEnvelopeInternal();

        if (!rectangle.intersects(geometryEnv))
        {
            return;
        }

        if (rectangle.contains(geometryEnv))
        {
            intersects = true;
            return;
        }

        if (geometryEnv.getMinX() >= rectangle.getMinX()
                && geometryEnv.getMaxX() <= rectangle.getMaxX())
        {
            intersects = true;
            return;
        }
        if (geometryEnv.getMinY() >= rectangle.getMinY()
                && geometryEnv.getMaxY() <= rectangle.getMaxY())
        {
            intersects = true;
            return;
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

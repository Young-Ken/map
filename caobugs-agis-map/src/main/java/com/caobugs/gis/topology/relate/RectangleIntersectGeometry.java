package com.caobugs.gis.topology.relate;

import com.caobugs.gis.geometry.Polygon;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.geometry.primary.Geometry;
import com.caobugs.gis.topology.visitor.RectangleIntersectsPolygonVisitor;
import com.caobugs.gis.topology.visitor.RectangleIntersectsLinesVisitor;
import com.caobugs.gis.topology.visitor.RectangleIntersectsVisitor;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/23
 */
public class RectangleIntersectGeometry
{
    private Polygon rectangle;
    private Envelope rectEnv;

    public RectangleIntersectGeometry(Polygon rectangle)
    {
        this.rectangle = rectangle;
        rectEnv = rectangle.getEnvelopeInternal();
    }

    public static boolean intersects(Polygon rectangle, Geometry geometry)
    {
        RectangleIntersectGeometry rp = new RectangleIntersectGeometry(rectangle);
        return rp.intersects(geometry);
    }

    public boolean intersects(Geometry geometry)
    {
        if (!rectEnv.intersects(geometry.getEnvelopeInternal()))
        {
            return false;
        }

        RectangleIntersectsVisitor visitor = new RectangleIntersectsVisitor(rectEnv);
        visitor.applyTo(geometry);
        if (visitor.intersects())
        {
            return true;
        }


        RectangleIntersectsPolygonVisitor ecpVisitor = new RectangleIntersectsPolygonVisitor(rectEnv);
        ecpVisitor.applyTo(geometry);
        if (ecpVisitor.intersects())
        {
            return true;
        }

        RectangleIntersectsLinesVisitor ecpSegment = new RectangleIntersectsLinesVisitor(rectEnv);
        ecpSegment.applyTo(geometry);
        if (ecpSegment.intersects())
        {
            return true;
        }
        return false;
    }
}

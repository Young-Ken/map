package com.caobugs.gis.topology.visitor;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.Polygon;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.geometry.primary.Geometry;
import com.caobugs.gis.topology.relate.PointInPolygon;

import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/12/2
 */
public class RectangleIntersectsPolygonVisitor extends ShortCircuitedGeometryVisitor
{
    private boolean intersects = false;
    private Envelope outerRectangle;

    public RectangleIntersectsPolygonVisitor(Envelope outerRectangle)
    {
        this.outerRectangle = outerRectangle;
    }

    public boolean intersects()
    {
        return intersects;
    }


    @Override
    protected void visit(Geometry geometry)
    {
        if (!(geometry instanceof Polygon))
        {
            return;
        }

        Envelope envelope = geometry.getEnvelopeInternal();
        if (!outerRectangle.intersects(envelope))
        {
            return;
        }



        List<Coordinate> list = outerRectangle.getLines();
        Coordinate point;
        for (int i = 0; i < 4; i++)
        {
            point = list.get(i);
            if(!envelope.intersects(point))
            {
                continue;
            }
            int isPointInRing = PointInPolygon.locationPointInRing(point, ((Polygon) geometry).getExteriorRing().getCoordinates());
            if (isPointInRing == 0)
            {
                intersects = true;
                return;
            }
        }
    }

    @Override
    protected boolean isDone()
    {
        return intersects;
    }
}

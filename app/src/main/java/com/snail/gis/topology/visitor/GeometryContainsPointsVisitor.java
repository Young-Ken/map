package com.snail.gis.topology.visitor;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.Polygon;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.lgorithm.cg.CGPointInRing;

import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/12/2
 */
public class GeometryContainsPointsVisitor extends ShortCircuitedGeometryVisitor
{
    private List<Coordinate> pointArray;

    private Envelope outerRectangle;

    private boolean containsPoint = false;

    public GeometryContainsPointsVisitor(Polygon polygon)
    {
        this.pointArray = polygon.getExteriorRing().getPointArray();
        outerRectangle = polygon.getEnvelope();
    }

    public boolean containsPoint()
    {
        return containsPoint;
    }


    @Override
    protected void visit(Geometry geometry)
    {
        if (!(geometry instanceof Polygon))
        {
            return;
        }
        Envelope envelope = geometry.getEnvelope();
        if (!outerRectangle.intersects(envelope))
        {
            return;
        }
        Coordinate point;
        for (int i = 0; i < 4; i++)
        {
            point = pointArray.get(i);
            if(!envelope.intersects(point))
            {
                continue;
            }
            int isPointInRing = CGPointInRing.locationPointInRing(point, ((Polygon) geometry).getExteriorRing().getPointArray());
            if (isPointInRing == 0)
            {
                containsPoint = true;
                return;
            }
        }
    }

    @Override
    protected boolean isDone()
    {
        return containsPoint;
    }
}

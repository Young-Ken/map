package com.snail.gis.topology.visitor;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.Polygon;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.topology.RectangleIntersectSegment;

import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/12/2
 */
public class RectangleIntersectsPolygonVisitor extends ShortCircuitedGeometryVisitor
{
    private boolean intersects = false;
    private List<Coordinate> pointArray;

    private Envelope outerRectangle;

    private boolean containsPoint = false;

    public RectangleIntersectsPolygonVisitor(Polygon polygon)
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

        List<Coordinate> geometryList = geometry.getLines();

        int size = geometryList.size();
        for (int i = 0; i < size; i++)
        {
            if (outerRectangle.intersects(geometryList.get(i)))
            {
                intersects = true;
                return;
            }
        }

        RectangleIntersectSegment intersectSegment = new RectangleIntersectSegment(outerRectangle);

        Coordinate point1 = null;
        Coordinate point2 = null;
        for (int i = 0 ; i < size-1; i++)
        {
            point1 = geometryList.get(i);
            point2 = geometryList.get(i+1);
            if (intersectSegment.intersects(point1, point2))
            {
                intersects = true;
                return;
            }
        }

//        Coordinate point;
//        for (int i = 0; i < 4; i++)
//        {
//            point = pointArray.get(i);
//            if(!envelope.intersects(point))
//            {
//                continue;
//            }
//            int isPointInRing = PointInPolygon.locationPointInRing(point, ((Polygon) geometry).getExteriorRing().getPointArray());
//            if (isPointInRing == 0)
//            {
//                containsPoint = true;
//                return;
//            }
//        }
    }

    @Override
    protected boolean isDone()
    {
        return containsPoint;
    }
}

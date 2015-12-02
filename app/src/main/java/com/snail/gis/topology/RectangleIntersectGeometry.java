package com.snail.gis.topology;

import com.snail.gis.geometry.Polygon;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.topology.visitor.GeometryContainsPointsVisitor;
import com.snail.gis.topology.visitor.RectangleIntersectsLinesVisitor;
import com.snail.gis.topology.visitor.RectangleIntersectsVisitor;

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
        rectEnv = rectangle.getEnvelope();
    }

    public static boolean intersects(Polygon rectangle, Geometry geometry)
    {
        RectangleIntersectGeometry rp = new RectangleIntersectGeometry(rectangle);
        return rp.intersects(geometry);
    }

    public boolean intersects(Geometry geometry)
    {
        if(!rectEnv.intersects(geometry.getEnvelope()))
        {
            return false;
        }

        RectangleIntersectsVisitor visitor = new RectangleIntersectsVisitor(rectEnv);
        visitor.applyTo(geometry);
        if (visitor.intersects())
        {
            return true;
        }


        GeometryContainsPointsVisitor ecpVisitor = new GeometryContainsPointsVisitor(rectangle);
        ecpVisitor.applyTo(geometry);
        if (ecpVisitor.containsPoint())
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

//    class RectangleIntersectsVisitor extends ShortCircuitedGeometryVisitor
//    {
//        private Envelope envelope;
//        private boolean intersects = false;
//        public RectangleIntersectsVisitor(Envelope envelope)
//        {
//            this.envelope = envelope;
//        }
//
//        @Override
//        protected void visit(Geometry element)
//        {
//            Envelope elementEnv = element.getEnvelope();
//
//            if (!envelope.intersects(elementEnv))
//            {
//                return;
//            }
//
//            if (envelope.contains(elementEnv))
//            {
//                intersects = true;
//                return;
//            }
//
//            if (elementEnv.getMinX() >= rectEnv.getMinX()
//                    && elementEnv.getMaxX() <= rectEnv.getMaxX())
//            {
//                intersects = true;
//                return;
//            }
//            if (elementEnv.getMinY() >= rectEnv.getMinY()
//                    && elementEnv.getMaxY() <= rectEnv.getMaxY())
//            {
//                intersects = true;
//                return;
//            }
//        }
//
//        public boolean intersects()
//        {
//            return intersects;
//        }
//        @Override
//        protected boolean isDone()
//        {
//            return intersects == true;
//        }
//    }
//
//    class GeometryContainsPointsVisitor extends ShortCircuitedGeometryVisitor
//    {
//
//        private List<Coordinate> rectSeq;
//
//        private Envelope rectEnv;
//
//        private boolean containsPoint = false;
//
//        public GeometryContainsPointsVisitor(Polygon polygon)
//        {
//            this.rectSeq = polygon.getExteriorRing().getPointArray();
//            rectEnv = polygon.getEnvelope();
//        }
//
//        public boolean containsPoint()
//        {
//           return containsPoint;
//        }
//
//
//        @Override
//        protected void visit(Geometry element)
//        {
//            if (!(element instanceof Polygon))
//            {
//                return;
//            }
//            Envelope envelope = element.getEnvelope();
//            if (!rectEnv.intersects(envelope))
//            {
//                return;
//            }
//            Coordinate point;
//            for (int i = 0; i < 4; i++)
//            {
//                point = rectSeq.get(i);
//                if(!envelope.intersects(point))
//                {
//                    continue;
//                }
//                int isPointInRing = PointInPolygon.locationPointInRing(point, ((Polygon) element).getExteriorRing().getPointArray());
//                if (isPointInRing == 0)
//                {
//                    containsPoint = true;
//                    return;
//                }
//            }
//        }
//
//        @Override
//        protected boolean isDone()
//        {
//            return containsPoint;
//        }
//    }
//
//    class RectangleIntersectsLinesVisitor extends ShortCircuitedGeometryVisitor
//    {
//        private boolean intersects = false;
//        Envelope rectangle = null;
//        RectangleIntersectSegment segmentIntersect;
//        public RectangleIntersectsLinesVisitor(Envelope rectangle)
//        {
//            this.rectangle = rectangle;
//            segmentIntersect = new RectangleIntersectSegment(rectangle);
//        }
//        @Override
//        protected void visit(Geometry element)
//        {
//            List<Coordinate> list = element.getLines();
//
//            int size = list.size();
//            for (int i = 0; i < size - 1; i++)
//            {
//                if (segmentIntersect.intersects(list.get(i),list.get(i+1)))
//                {
//                    intersects = true;
//                    return;
//                }
//            }
//
//        }
//        public boolean intersects()
//        {
//            return intersects;
//        }
//        @Override
//        protected boolean isDone()
//        {
//            return intersects == true;
//        }
//    }

}

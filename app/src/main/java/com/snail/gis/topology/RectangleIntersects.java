package com.snail.gis.topology;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.Polygon;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.geometry.util.ShortCircuitedGeometryVisitor;
import com.snail.gis.lgorithm.cg.CGPointInRing;

import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/23
 */
public class RectangleIntersects
{
    private Polygon rectangle;

    private Envelope rectEnv;

    public RectangleIntersects(Polygon rectangle)
    {
        this.rectangle = rectangle;
        rectEnv = rectangle.getEnvelope();
    }

    public static boolean intersects(Polygon rectangle, Geometry geometry)
    {
        RectangleIntersects rp = new RectangleIntersects(rectangle);
        return rp.intersects(geometry);
    }
    public boolean intersects(Geometry geometry)
    {
        if(!rectEnv.intersects(geometry.getEnvelope()))
        {
            return false;
        }

        EnvelopeIntersectsVisitor visitor = new EnvelopeIntersectsVisitor(rectEnv);
        visitor.applyTo(geometry);
        if (visitor.intersects())
        {
            return true;
        }


        GeometryContainsPointVisitor ecpVisitor = new GeometryContainsPointVisitor(rectangle);
        ecpVisitor.applyTo(geometry);
        if (ecpVisitor.containsPoint())
        {
            return true;
        }

        return false;
    }

    class EnvelopeIntersectsVisitor extends ShortCircuitedGeometryVisitor
    {
        private Envelope envelope;
        private boolean intersects = false;
        public EnvelopeIntersectsVisitor(Envelope envelope)
        {
            this.envelope = envelope;
        }

        @Override
        protected void visit(Geometry element)
        {
            Envelope elementEnv = element.getEnvelope();

            if (!envelope.intersects(elementEnv))
            {
                return;
            }

            if (envelope.contains(elementEnv))
            {
                intersects = true;
                return;
            }

            if (elementEnv.getMinX() >= rectEnv.getMinX()
                    && elementEnv.getMaxX() <= rectEnv.getMaxX())
            {
                intersects = true;
                return;
            }
            if (elementEnv.getMinY() >= rectEnv.getMinY()
                    && elementEnv.getMaxY() <= rectEnv.getMaxY())
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

    class GeometryContainsPointVisitor extends ShortCircuitedGeometryVisitor
    {

        private List<Coordinate> rectSeq;

        private Envelope rectEnv;

        private boolean containsPoint = false;

        public GeometryContainsPointVisitor(Polygon polygon)
        {
            this.rectSeq = polygon.getExteriorRing().getPointArray();
            rectEnv = polygon.getEnvelope();
        }

        public boolean containsPoint()
        {
           return containsPoint;
        }


        @Override
        protected void visit(Geometry element)
        {
            if (!(element instanceof Polygon))
            {
                return;
            }
            Envelope envelope = element.getEnvelope();
            if (!rectEnv.intersects(envelope))
            {
                return;
            }
            Coordinate point;
            for (int i = 0; i < 4; i++)
            {
                point = rectSeq.get(i);
                if(!envelope.intersects(point))
                {
                    continue;
                }
                int isPointInRing = CGPointInRing.locationPointInRing(point, ((Polygon) element).getExteriorRing().getPointArray());
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

    class RectangleIntersectsSegmentVisitor extends ShortCircuitedGeometryVisitor
    {

        @Override
        protected void visit(Geometry element)
        {

        }

        @Override
        protected boolean isDone()
        {
            return false;
        }
    }

}

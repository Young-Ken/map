package com.snail.gis.topology;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.Polygon;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.geometry.util.ShortCircuitedGeometryVisitor;

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


        return false;
    }

    class EnvelopeIntersectsVisitor extends ShortCircuitedGeometryVisitor
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
            Coordinate point = new Coordinate();
            for (int i = 0; i < 4; i++)
            {
                point = rectSeq.get(i);
                if(!envelope.intersects(point))
                {
                    continue;
                }


            }
        }


        @Override
        protected boolean isDone()
        {
            return false;
        }
    }
}

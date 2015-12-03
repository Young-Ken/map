package com.snail.gis.geometry.primary;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.Polygon;
import com.snail.gis.topology.RectangleIntersectGeometry;

import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/7
 */
public abstract class Geometry implements SpatialReferenceSystem,SpatialAnalysisSystem
{

    public abstract Envelope getEnvelope();
    public abstract boolean isEmpty();
    public abstract int getDimension();
    public abstract int getBoundaryDimension();
    public abstract List<Coordinate> getLines();


    /**
     * 判断两个图形是不是相离 ! intersects
     * @param geometry geometry
     * @return
     */
    @Override
    public boolean disjoint(Geometry geometry)
    {
        return ! intersects(geometry);
    }

    /**
     * 判断两个图形是不是相交, 先不考虑环
     * 详见博客
     * @param geometry geometry
     * @return
     */
    @Override
    public boolean intersects(Geometry geometry)
    {
        if (!getEnvelope().intersects(geometry.getEnvelope()))
        {
            return false;
        }

        if (isRectangle())
        {
           return RectangleIntersectGeometry.intersects((Polygon) this, geometry);
        }

        if (geometry.isRectangle())
        {
            return RectangleIntersectGeometry.intersects((Polygon) geometry, this);
        }


        return false;
    }
    public boolean isRectangle()
    {
        // Polygon overrides to check for actual rectangle
        return false;
    }

    protected Envelope getEnvelope(final List<Coordinate> list )
    {
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;

        for (Coordinate coordinate : list)
        {
            maxX = Math.max(coordinate.x, maxX);
            maxY = Math.max(coordinate.y, maxY);
            minX = Math.min(coordinate.x, minX);
            minY = Math.min(coordinate.y, minY);
        }

        return new Envelope(maxX, minX, maxY, minY);
    }
}

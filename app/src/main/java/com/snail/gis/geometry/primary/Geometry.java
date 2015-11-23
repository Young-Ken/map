package com.snail.gis.geometry.primary;

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

        return false;
    }
}

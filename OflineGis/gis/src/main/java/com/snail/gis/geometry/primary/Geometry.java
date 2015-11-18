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
}

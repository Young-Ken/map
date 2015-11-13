package com.snail.gis.geometry.primary;

import com.snail.gis.geometry.Envelope;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/7
 */
public abstract class Geometry implements SpatialReferenceSystem,SpatialAnalysisSystem
{
    /**
     * 得到当前Geometry的外接矩形
     * @return Envelope
     */
    public abstract Envelope getEnvelope();
}

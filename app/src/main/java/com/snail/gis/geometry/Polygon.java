package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.geometry.primary.Surface;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class Polygon extends Surface
{

    private LinearRing linearRing = null;


    @Override
    public Envelope getEnvelope()
    {
        return null;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public boolean equals(Geometry geometry)
    {
        return false;
    }
}

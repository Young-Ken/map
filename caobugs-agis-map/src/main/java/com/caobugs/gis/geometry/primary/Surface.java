package com.caobugs.gis.geometry.primary;

import com.caobugs.gis.geometry.GeometryFactory;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/7
 */
public abstract class Surface extends Geometry
{
    public Surface(GeometryFactory factory)
    {
        super(factory);
    }

    public abstract double area();
}

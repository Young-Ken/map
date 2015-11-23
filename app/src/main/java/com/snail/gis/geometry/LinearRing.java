package com.snail.gis.geometry;

import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LinearRing extends LineString
{
    public LinearRing()
    {
        super();
    }

    public LinearRing(List<Coordinate> list)
    {
        super(list);
    }

    public LinearRing(LineString lineString)
    {
        super(lineString);
    }

    public LinearRing(LinearRing linearRing)
    {
        this(linearRing.pointArray);
    }


    @Override
    public boolean isClosed()
    {
        return super.isClosed();
    }

}

package com.snail.gis.geometry;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LineRing extends LineString
{


    public LineRing()
    {
        super();
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public int getPointNum()
    {
        return 0;
    }
}

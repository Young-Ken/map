package com.caobugs.gis.geometry;

import com.caobugs.gis.util.constants.Dimension;

/**
 * 线环类
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LinearRing extends LineString
{

    public LinearRing(Coordinate[] points)
    {
        this(points, 0);
    }

    public LinearRing(Coordinate[] points, int SRID)
    {
        this(points, new GeometryFactory(SRID));
        validateConstruction();
    }

    private LinearRing(Coordinate[] points, GeometryFactory factory)
    {
        this(factory.getCoordinateSequenceFactory().create(points), factory);
    }

    public LinearRing(CoordinateSequence points, GeometryFactory factory)
    {
        super(points, factory);
        validateConstruction();
    }

    private void validateConstruction()
    {
        if(!isEmpty() && !super.isClosed())
        {
            throw new IllegalArgumentException("线环个环没有关闭");
        }

        if(getCoordinateSequence().size() >= 1 && getCoordinateSequence().size() < 4)
        {
            throw new IllegalArgumentException("线环的点数 >=1 并且小于 4");
        }
    }

    @Override
    public int getBoundaryDimension()
    {
        return Dimension.FALSE;
    }

    @Override
    public String getGeometryType()
    {
        return "LinearRing";
    }

}

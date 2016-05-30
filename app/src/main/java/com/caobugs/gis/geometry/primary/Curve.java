package com.caobugs.gis.geometry.primary;

import com.caobugs.gis.util.constants.Dimension;
import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.GeometryFactory;
import com.caobugs.gis.geometry.CoordinateSequence;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/7
 */
public abstract class Curve extends Geometry
{
    protected CoordinateSequence points;

    public Curve(GeometryFactory factory)
    {
        super(factory);
    }

    @Override
    public Coordinate getCoordinate()
    {
        return isEmpty() ? null : points.getCoordinate(0);
    }

    @Override
    public Coordinate[] getCoordinates()
    {
        return points.toCoordinateArray();
    }

    @Override
    public boolean isEmpty()
    {
        return points.size() == 0;
    }

    @Override
    public int getDimension()
    {
        return 1;
    }

    @Override
    public int getNumPoints()
    {
        return points.size();
    }

    @Override
    public Geometry getBoundary()
    {
        return null;
    }

    /**
     * 如果是关闭的返回 Dimension.FALSE
     * @return
     */
    @Override
    public int getBoundaryDimension()
    {
        if(isClosed())
        {
            return Dimension.FALSE;
        }
        return 0;
    }

    @Override
    protected Envelope computeEnvelopeInternal()
    {
        if (isEmpty())
        {
            return new Envelope();
        }
        return points.expandEnvelope(new Envelope());
    }

    public boolean isClosed()
    {
        if (isEmpty())
        {
            return false;
        }
        return getCoordinateN(0).equals(getCoordinateN(getNumPoints() - 1));
    }

    public Coordinate getCoordinateN(int index)
    {
        return points.getCoordinate(index);
    }

}

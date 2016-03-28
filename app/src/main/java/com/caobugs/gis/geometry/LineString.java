package com.caobugs.gis.geometry;

import com.caobugs.gis.geometry.primary.Curve;


/**
 * A LineString is a Curve with linear interpolation between points.
 * Each consecutive pair of points defines a line segment.
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LineString extends Curve
{
    /**
     * 点集合
     */
    protected CoordinateSequence points;

    public LineString(Coordinate points[], int SRID)
    {
        super(new GeometryFactory(SRID));
        init(getFactory().getCoordinateSequenceFactory().create(points));
    }

    public LineString(CoordinateSequence points, GeometryFactory factory)
    {
        super(factory);
        init(points);
    }

    private void init(CoordinateSequence points)
    {
        if (points == null)
        {
            points = getFactory().getCoordinateSequenceFactory().create(new Coordinate[]{});
        }

        if (points.size() == 1)
        {
            throw new IllegalArgumentException("初始化线的定必须 >=2");
        }
        this.points = points;
    }



    @Override
    public String getGeometryType()
    {
        return "LineString";
    }


    @Override
    public int compareTo(Object another)
    {
        return 0;
    }

    public CoordinateSequence getCoordinateSequence()
    {
        return points;
    }
}

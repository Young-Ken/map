package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;

import java.io.Serializable;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/2/23
 */
public class GeometryFactory implements Serializable
{
    /**
     * Returns the Spatial Reference System ID for this Geometry.
     */
    private int SRID;

    private CoordinateSequenceFactory coordinateSequenceFactory;

    public int getSRID()
    {
        return SRID;
    }

    public GeometryFactory(int SRID)
    {
        this(SRID, getDefaultCoordinateSequenceFactory());
    }

    public GeometryFactory(int SRID, CoordinateSequenceFactory coordinateSequenceFactory)
    {
        this.SRID = SRID;
        this.coordinateSequenceFactory = coordinateSequenceFactory;
    }

    private static CoordinateSequenceFactory getDefaultCoordinateSequenceFactory()
    {
        return CoordinateArraySequenceFactory.getInstance();
    }

    public CoordinateSequenceFactory getCoordinateSequenceFactory()
    {
        return coordinateSequenceFactory;
    }

    public Point createPoint(CoordinateSequence coordinateSequence)
    {
        return new Point(coordinateSequence ,this);
    }

    public Point createPoint(Coordinate coordinate)
    {
        return createPoint(coordinate != null ? getCoordinateSequenceFactory().create(new Coordinate[]{coordinate}) : null);
    }

    public LineString createLineString(Coordinate[] coordinates)
    {
        return createLineString(coordinates != null ? getCoordinateSequenceFactory().create(coordinates) : null);
    }

    public LineString createLineString(CoordinateSequence coordinates)
    {
        return new LineString(coordinates, this);
    }

    public Polygon createPolygon(LinearRing shell, LinearRing[] holes) {
        return new Polygon(shell, holes, this);
    }


    public Polygon createPolygon(CoordinateSequence coordinates)
    {
        return createPolygon(createLinearRing(coordinates));
    }

    public LinearRing createLinearRing(Coordinate[] coordinates)
    {
        return createLinearRing(coordinates != null ? getCoordinateSequenceFactory().create(coordinates) : null);
    }

    public LinearRing createLinearRing(CoordinateSequence coordinates)
    {
        return new LinearRing(coordinates, this);
    }

    public Polygon createPolygon(Coordinate[] coordinates)
    {
        return createPolygon(createLinearRing(coordinates));
    }

    public Polygon createPolygon(LinearRing shell)
    {
        return createPolygon(shell, null);
    }

    public Geometry toGeometry(Envelope envelope)
    {
        if(envelope.isEmpty())
        {
            return createPoint((CoordinateSequence) null);
        }

        if (envelope.getMinX() == envelope.getMaxX() && envelope.getMinY() == envelope.getMaxY())
        {
            return createPoint(new Coordinate(envelope.getMinX(), envelope.getMinY()));
        }

        if (envelope.getMinX() == envelope.getMaxX()
                || envelope.getMinY() == envelope.getMaxY()) {
            return createLineString(new Coordinate[]{new Coordinate(envelope.getMinX(), envelope.getMinY()),
                    new Coordinate(envelope.getMaxX(), envelope.getMaxY())});
        }

        return createPolygon(createLinearRing(new Coordinate[]{
                new Coordinate(envelope.getMinX(), envelope.getMinY()),
                new Coordinate(envelope.getMinX(), envelope.getMaxY()),
                new Coordinate(envelope.getMaxX(), envelope.getMaxY()),
                new Coordinate(envelope.getMaxX(), envelope.getMinY()),
                new Coordinate(envelope.getMinX(), envelope.getMinY())
        }), null);
    }

}

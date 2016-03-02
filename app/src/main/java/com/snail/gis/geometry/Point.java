package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.enumeration.Dimension;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/7
 */
public class Point extends Geometry
{

    private CoordinateSequence coordinates;

    /**
     * 构造方法
     * @param coordinate Coordinate对象
     * @param SRID SRID
     */
    public Point(Coordinate coordinate, int SRID)
    {
        super(new GeometryFactory(SRID));
        init(getFactory().getCoordinateSequenceFactory().create(coordinate != null ? new Coordinate[]{coordinate} : new Coordinate[]{}));
    }

    /**
     * 构造方法
     * @param coordinates 点集合对象
     * @param factory GeometryFactory
     */
    public Point(CoordinateSequence coordinates, GeometryFactory factory)
    {
        super(factory);
        init(coordinates);
    }

    /**
     * 初始化方法
     * @param coordinates CoordinateSequence
     */
    private void init(CoordinateSequence coordinates)
    {
        if (coordinates == null)
        {
            coordinates = getFactory().getCoordinateSequenceFactory().create(new Coordinate[]{});
        }

        this.coordinates = coordinates;
    }

    /**
     * @return String Point
     */
    @Override
    public String getGeometryType()
    {
        return "Point";
    }

    /**
     * 得到第一个点
     *
     * @return Coordinate
     */
    @Override
    public Coordinate getCoordinate()
    {
        return coordinates.size() != 0 ? coordinates.getCoordinate(0) : null;
    }

    /**
     * @return Coordinate array
     */
    @Override
    public Coordinate[] getCoordinates()
    {
        return isEmpty() ? new Coordinate[]{} : new Coordinate[]{getCoordinate()};
    }

    /**
     * 是否为空
     * @return
     */
    @Override
    public boolean isEmpty()
    {
        return getCoordinate() == null;
    }

    @Override
    public int getDimension()
    {
        return 0;
    }

    @Override
    public int getNumPoints()
    {
        return isEmpty() ? 0 : 1;
    }

    @Override
    public Geometry getBoundary()
    {
        return null;
    }

    @Override
    public int getBoundaryDimension()
    {
        return Dimension.FALSE;
    }

    @Override
    protected Envelope computeEnvelopeInternal()
    {
        if (isEmpty())
        {
            return new Envelope();
        }

        Envelope envelope = new Envelope();
        envelope.expandToInclude(coordinates.getX(0), coordinates.getY(0));
        return envelope;
    }

    @Override
    public int compareTo(Object another)
    {
        Point point = (Point)another;
        return getCoordinate().compareTo(point.getCoordinate());
    }

    /**
     * 点对象默认返回的是简单类型
     * @return true
     */
    @Override
    public boolean isSimple()
    {
        return true;
    }

    public double getX()
    {
        if (getCoordinate() == null)
        {
            throw new IllegalStateException("点为空");
        }
        return getCoordinate().x;
    }

    public double getY()
    {
        if (getCoordinate() == null)
        {
            throw new IllegalStateException("点为空");
        }
        return getCoordinate().y;
    }

    public CoordinateSequence getCoordinateSequence()
    {
        return coordinates;
    }

    /**
     * 添加克隆方法
     * @return Point 克隆对象
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        Point point = (Point)super.clone();
        point.coordinates = (CoordinateSequence) coordinates.clone();
        return point;
    }
}

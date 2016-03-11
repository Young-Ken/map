package com.snail.gis.geometry;


import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.geometry.primary.Surface;

/**
 * Created by Young-Ken on 2015/11/22.
 */
public class Polygon extends Surface
{
    /**
     * 多个内环
     */
    protected LinearRing[] holes = null;

    /**
     * 一个外环
     */
    protected LinearRing shell = null;


    /**
     * 构造函数，只有一个外环，没有内环
     * @param shell 外环
     * @param SRID SRID
     */
    public Polygon(LinearRing shell, int SRID)
    {
        this(shell, new LinearRing[]{}, new GeometryFactory(SRID));
    }

    /**
     * 构造函数 即有内环，又有外环
     * @param shell 外环
     * @param holes 内环 array
     * @param factory GeometryFactory
     */
    public Polygon(LinearRing shell, LinearRing[] holes, GeometryFactory factory)
    {
        super(factory);
        if(shell == null)
        {
            shell = getFactory().createLinearRing((CoordinateSequence)null);
        }

        if(holes == null)
        {
            holes = new LinearRing[]{};
        }

        if(hasNullElements(holes))
        {
            throw new IllegalArgumentException("内环的数据不能有空");
        }

        if(shell.isEmpty() && hasNullElements(holes))
        {
            throw new IllegalArgumentException("外环数据为空，但内环不为空");
        }

        this.shell = shell;
        this.holes = holes;
    }

    @Override
    public String getGeometryType()
    {
        return "Polygon";
    }

    /**
     * 取外环的第一个点
     * @return Coordinate
     */
    public Coordinate getCoordinate()
    {
        return shell.getCoordinate();
    }

    @Override
    public Coordinate[] getCoordinates()
    {
        if(isEmpty())
        {
            return new Coordinate[]{};
        }

        Coordinate[] coordinates = new Coordinate[getNumPoints()];
        int k = -1;

        Coordinate[] shellCoordinates = shell.getCoordinates();
        for (int x = 0; x < shellCoordinates.length; x++)
        {
            k++;
            coordinates[k] = shellCoordinates[x];
        }

        for(int i = 0; i < holes.length; i++)
        {
            Coordinate[] childCoordinates = holes[i].getCoordinates();
            for(int j = 0; j < childCoordinates.length; i++)
            {
                k++;
                coordinates[k] = childCoordinates[j];
            }
        }
        return coordinates;
    }

    public LinearRing getExteriorRing()
    {
        return shell;
    }

    @Override
    public boolean isEmpty()
    {
       return shell.isEmpty();
    }


    @Override
    public int getDimension()
    {
        return 2;
    }

    /**
     * 内环和外环的点数和
     * @return
     */
    @Override
    public int getNumPoints()
    {
        int pointNo = shell.getNumPoints();

        for(int i = 0; i < holes.length; i++)
        {
            pointNo += holes[i].getNumPoints();
        }
        return pointNo;
    }

    @Override
    public Geometry getBoundary()
    {
        return null;
    }

    @Override
    public int getBoundaryDimension()
    {
        return 1;
    }

    @Override
    protected Envelope computeEnvelopeInternal()
    {
        return null;
    }

    @Override
    public double area()
    {
        return 0;
    }

    @Override
    public int compareTo(Object another)
    {
        return 0;
    }


    /**
     * 判断环是不是矩形,详见博客
     * @return 如果是矩形返回true
     */
//    public boolean isRectangle()
//    {
//        if(exteriorRing.getPointNum() != 5) return false;
//        if(exteriorRing.isEmpty()) return false;
//        if(getNumInteriorRing() != 0) return false;
//
//        Envelope envelope = getEnvelope();
//
//        double envelopeHeight = envelope.getHeight();
//        double envelopeWidth = envelope.getWidth();
//        for (int i = 0 ; i < 5; i ++)
//        {
//            Point point = exteriorRing.getPoint(i);
//            double x = point.getX();
//            double y = point.getY();
//
//            if (!(x == envelope.getMaxX() || x == envelope.getMinX()))
//            {
//                return false;
//            }
//
//            if(!(y == envelope.getMaxY() || y == envelope.getMinY()))
//            {
//                return false;
//            }
//        }
//
//        if (envelopeHeight == 0 || envelopeWidth == 0)
//        {
//            return true;
//        }
//
//        Point upCoor = exteriorRing.getPoint(0);
//        Point nextCoor = exteriorRing.getPoint(1);
//
//        double doubleEnvelopeHeight = envelopeHeight * envelopeHeight;
//        double doubleEnvelopWidth = envelopeWidth * envelopeWidth;
//        for (int i = 1; i < 4; i ++)
//        {
//            double length = MathUtil.distanceTwoPointNoSquare(upCoor,nextCoor);
//            if (length > doubleEnvelopeHeight && length > doubleEnvelopWidth)
//            {
//                return false;
//            }
//            upCoor = exteriorRing.getPoint(i);
//            nextCoor = exteriorRing.getPoint(i+1);
//        }
//        return true;
//    }

}

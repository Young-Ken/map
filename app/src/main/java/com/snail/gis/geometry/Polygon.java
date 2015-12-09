package com.snail.gis.geometry;


import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.geometry.primary.Surface;
import com.snail.gis.algorithm.MathUtil;

import java.util.List;

/**
 * Created by Young-Ken on 2015/11/22.
 */
public class Polygon extends Surface
{
    /**
     * 多个内环
     */
    protected LinearRing[] inLine = null;
    protected LinearRing exteriorRing = null;

    public Polygon()
    {
        exteriorRing = new LinearRing();
    }

    public Polygon(LinearRing line)
    {
        this();
        exteriorRing = line;
    }

    public Polygon(LinearRing outLine, LinearRing[] inLine)
    {
        this(outLine);
        this.inLine = inLine;
    }


    @Override
    public int getDimension()
    {
        return 2;
    }

    @Override
    public int getBoundaryDimension()
    {
        return 1;
    }

    /**
     * 暂时返回外环
     * @return
     */
    @Override
    public List<Coordinate> getLines()
    {
        return exteriorRing.pointArray;
    }

    /**
     * 判断环是不是矩形,详见博客
     * @return 如果是矩形返回true
     */
    public boolean isRectangle()
    {
        if(exteriorRing.getPointNum() != 5) return false;
        if(exteriorRing.isEmpty()) return false;
        if(getNumInteriorRing() != 0) return false;

        Envelope envelope = getEnvelope();

        double envelopeHeight = envelope.getHeight();
        double envelopeWidth = envelope.getWidth();
        for (int i = 0 ; i < 5; i ++)
        {
            Coordinate coordinate = exteriorRing.getPoint(i);
            double x = coordinate.getX();
            double y = coordinate.getY();

            if (!(x == envelope.getMaxX() || x == envelope.getMinX()))
            {
                return false;
            }

            if(!(y == envelope.getMaxY() || y == envelope.getMinY()))
            {
                return false;
            }
        }

        if (envelopeHeight == 0 || envelopeWidth == 0)
        {
            return true;
        }

        Coordinate upCoor = exteriorRing.getPoint(0);
        Coordinate nextCoor = exteriorRing.getPoint(1);

        double doubleEnvelopeHeight = envelopeHeight * envelopeHeight;
        double doubleEnvelopWidth = envelopeWidth * envelopeWidth;
        for (int i = 1; i < 4; i ++)
        {
            double length = MathUtil.distanceTwoPointNoSquare(upCoor,nextCoor);
            if (length > doubleEnvelopeHeight && length > doubleEnvelopWidth)
            {
                return false;
            }
            upCoor = exteriorRing.getPoint(i);
            nextCoor = exteriorRing.getPoint(i+1);
        }
        return true;
    }

    public boolean isInLineEmpty()
    {
        return (inLine == null || inLine.length == 0);
    }

    public int getNumInteriorRing()
    {
        if (isInLineEmpty())
        {
            return 0;
        }
        return inLine.length;
    }

    public LinearRing getExteriorRing()
    {
        return exteriorRing;
    }
    @Override
    public Envelope getEnvelope()
    {
        return getEnvelope(exteriorRing.pointArray);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean equals(Geometry geometry)
    {
        return false;
    }
}

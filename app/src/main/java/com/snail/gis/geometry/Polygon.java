package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.geometry.primary.Surface;
import com.snail.gis.math.MathUtil;

import java.util.List;

/**
 * Created by Young-Ken on 2015/11/22.
 */
public class Polygon extends Surface
{

    protected LinearRing outLine = null;
    protected LinearRing[] inLine = null;

    public Polygon(LinearRing line)
    {
        outLine = new LinearRing(line);
    }

    public Polygon(LinearRing outLine, LinearRing[] inLine)
    {
        this(outLine);
        this.inLine = inLine;
    }


    public boolean isRectangle()
    {
        if(outLine.getPointNum() != 5) return false;
        if(outLine.isEmpty()) return false;
        if(getNumInteriorRing() != 0) return false;

        Envelope envelope = getEnvelope();

        double envelopeHeight = envelope.getHeight();
        double envelopeWidth = envelope.getWidth();
        for (int i = 0 ; i < 5; i ++)
        {
            Coordinate coordinate = outLine.getPoint(i);
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

        Coordinate upCoor = outLine.getPoint(0);
        Coordinate nextCoor = outLine.getPoint(1);

        double doubleEnvelopeHeight = envelopeHeight * envelopeHeight;
        double doubleEnvelopWidth = envelopeWidth * envelopeWidth;
        for (int i = 1; i < 4; i ++)
        {
            double length = MathUtil.distanceTwoPointNoSquare(upCoor,nextCoor);
            if (length > doubleEnvelopeHeight && length > doubleEnvelopWidth)
            {
                return false;
            }
            upCoor = outLine.getPoint(i);
            nextCoor = outLine.getPoint(i+1);
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
    @Override
    public Envelope getEnvelope()
    {
        return getEnvelope(outLine.list);
    }


    private Envelope getEnvelope(final List<Coordinate> list )
    {
        double maxX = 0.0;
        double maxY = 0.0;
        double minX = 0.0;
        double minY = 0.0;
        for (Coordinate coordinate : list)
        {
            maxX = Math.max(coordinate.x, maxX);
            maxY = Math.max(coordinate.y, maxY);
            minX = Math.min(coordinate.x, minX);
            minY = Math.min(coordinate.y, minY);
        }

        return new Envelope(maxX, minX, maxY, minY);
    }
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean equals(Geometry geometry) {
        return false;
    }
}

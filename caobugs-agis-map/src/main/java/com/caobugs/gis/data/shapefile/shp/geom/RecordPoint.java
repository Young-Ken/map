package com.caobugs.gis.data.shapefile.shp.geom;


import com.caobugs.gis.data.shapefile.shp.exception.ShapeException;
import com.caobugs.gis.geometry.Coordinate;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/4
 */
public class RecordPoint extends RecordGeometry
{


    private Coordinate coordinate = null;

    /**
     *  从写读取点
     * @param byteBuffer
     * @throws IOException
     */
    @Override
    public void read(ByteBuffer byteBuffer, RandomAccessFile accessFile) throws IOException
    {
        if (byteBuffer == null)
        {
            throw ShapeException.throwException(ShapeException.TYPE.BYTE_IS_NULL);
        }
        readRecordHeaders(byteBuffer);
        coordinate = new Coordinate(byteBuffer.getDouble(), byteBuffer.getDouble());
    }

    @Override
    void write(ByteBuffer byteBuffer) throws IOException
    {
        if (byteBuffer == null)
        {
            throw ShapeException.throwException(ShapeException.TYPE.BYTE_IS_NULL);
        }
    }


    public Coordinate getCoordinate()
    {
        return coordinate;
    }

    @Override
    public String toString()
    {
        return coordinate.toString();
    }
}

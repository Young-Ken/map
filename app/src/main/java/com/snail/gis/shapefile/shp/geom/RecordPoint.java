package com.snail.gis.shapefile.shp.geom;

import com.youngken.shapefile.shp.exception.ShapeException;

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

    /**
     * x ����
     */
    private double x = 0;

    /**
     * y ����
     */
    private double y = 0;

    /**
     *  ��д��ȡ��
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
        x = byteBuffer.getDouble();
        y = byteBuffer.getDouble();
    }

    @Override
    void write(ByteBuffer byteBuffer) throws IOException
    {
        if (byteBuffer == null)
        {
            throw ShapeException.throwException(ShapeException.TYPE.BYTE_IS_NULL);
        }
    }

    /**
     * get
     * @return x
     */
    public double getX()
    {
        return x;
    }

    /**
     * get
     * @return y
     */
    public double getY()
    {
        return y;
    }


}

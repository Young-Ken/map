package com.snail.gis.shapefile.shp.geom;


import com.snail.gis.geometry.Coordinate;
import com.snail.gis.shapefile.shp.exception.ShapeException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/9
 */
public class RecordLine extends RecordGeometry
{
    /**
     * 外接矩形，为了节省内存就不用对象封装了 顺序为Xmin, Ymin, Xmax,Ymax.
     */
    private double[] box = new double[4];

    /**
     * 表示构成当前线目标的子线段的个数
     */
    private int numParts = -1;

    /**
     * 点的个数
     */
    private int numPoints = -1;

    private int[] parts;

    private Coordinate[] points;

    /**
     *
     * @param byteBuffer
     * @throws IOException
     */

    @Override
    public void read(ByteBuffer byteBuffer, RandomAccessFile accessFile) throws IOException
    {
        if (byteBuffer == null || accessFile == null)
        {
            throw ShapeException.throwException(ShapeException.TYPE.BYTE_IS_NULL);
        }

        readRecordHeaders(byteBuffer);
        box[0] = byteBuffer.getDouble();
        box[1] = byteBuffer.getDouble();
        box[2] = byteBuffer.getDouble();
        box[3] = byteBuffer.getDouble();
        numParts = byteBuffer.getInt();
        parts = new int[numParts];
        numPoints = byteBuffer.getInt();
        points = new Coordinate[numPoints];

        ByteBuffer lineBuffer = ByteBuffer.allocate(numPoints*16 + numParts*4);
        FileChannel channel = accessFile.getChannel();
        channel.read(lineBuffer);
        lineBuffer.position(0);
        lineBuffer.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < numParts; i++)
        {
            parts[i] = lineBuffer.getInt();
        }

        for (int i = 0; i < numPoints; i++)
        {
           points[i] = new Coordinate(lineBuffer.getDouble(), lineBuffer.getDouble());
        }
    }

    @Override
    void write(ByteBuffer buffer) throws IOException
    {

    }
}

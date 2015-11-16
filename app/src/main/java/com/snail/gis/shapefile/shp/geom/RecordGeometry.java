package com.snail.gis.shapefile.shp.geom;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/4
 */
public abstract class RecordGeometry
{
    /**
     * Ĭ��shapeTypeΪ0 Ҳ����null
     */
    private int shapeType = 0;
    /**
     *  ����Ǵ�1��ʼ
     */
    private int recordNumber = 0;

    /**
     * contentLength
     */
    private int contentLength = 0;

    abstract void read(ByteBuffer byteBuffer, RandomAccessFile accessFile) throws IOException;
    abstract void write(ByteBuffer byteBuffer) throws IOException;

    public void readRecordHeaders(ByteBuffer byteBuffer) throws IOException
    {
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        recordNumber = byteBuffer.getInt();
        contentLength = byteBuffer.getInt();
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        shapeType = byteBuffer.getInt();
    }

    /**
     * get
     * @return ShapeType
     */
    public int getShapeType()
    {
        return shapeType;
    }

    /**
     * get
     * @return RecordNumber
     */
    public int getRecordNumber()
    {
        return recordNumber;
    }

    /**
     * get
     * @return ContentLength
     */
    public int getContentLength()
    {
        return contentLength;
    }
}

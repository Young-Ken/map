package com.snail.gis.data.shapefile.shp.factory;

import com.snail.gis.data.shapefile.shp.ShapeFile;
import com.snail.gis.data.shapefile.shp.ShapeFileHeader;
import com.snail.gis.data.shapefile.shp.ShapeFileManger;
import com.snail.gis.data.shapefile.shp.exception.ShapeException;
import com.snail.gis.data.shapefile.shp.geom.RecordLine;
import com.snail.gis.data.shapefile.shp.geom.RecordPoint;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/4
 */
public class CreateGeometry extends ShapeFactory
{
    private volatile static CreateGeometry instance = null;
    private ShapeFileManger manger = ShapeFileManger.getInstance();

    private CreateGeometry()
    {

    }

    public static CreateGeometry getInstance()
    {
        if (null == instance)
        {
            synchronized (CreateGeometry.class)
            {
                if (null == instance)
                {
                    instance = new CreateGeometry();
                }
            }
        }
        return instance;
    }


    @Override
    public void createGeometry(ShapeFileHeader header, RandomAccessFile accessFile) throws IOException
    {
        if (header == null || accessFile == null)
        {
            throw ShapeException.throwException(ShapeException.TYPE.FILE_IS_NULL);
        }

        if (header.getShapeType().isPointType())
        {
            manger.addShapeFile(createPoint(header, accessFile));
        } else if (header.getShapeType().isLineType())
        {
            manger.addShapeFile(createLine(header, accessFile));
        }
    }

    private ShapeFile createPoint(ShapeFileHeader header, RandomAccessFile accessFile) throws IOException
    {
        ShapeFile shapeFile = new ShapeFile();
        shapeFile.setHeader(header);
        FileChannel channel = accessFile.getChannel();
        int fileLength = header.getFileLength();
        RecordPoint shapePoint = null;
        ByteBuffer pointBuffer = null;
        int pointNum = (fileLength * 2 - 100) / 28;
        for (int i = 0; i < pointNum; i++)
        {
            pointBuffer = ByteBuffer.allocate(28);
            channel.read(pointBuffer);
            pointBuffer.position(0);
            shapePoint = new RecordPoint();
            shapePoint.read(pointBuffer, null);
            shapeFile.addGeometry(shapePoint);
        }
        if (pointBuffer == null)
        {
            throw ShapeException.throwException(ShapeException.TYPE.BYTE_IS_NULL);
        }
        return shapeFile;
    }

    private ShapeFile createLine(ShapeFileHeader header, RandomAccessFile accessFile) throws IOException
    {
        ShapeFile shapeFile = new ShapeFile();
        shapeFile.setHeader(header);
        FileChannel channel = accessFile.getChannel();

        boolean whileBool = true;
        while (whileBool)
        {
            ByteBuffer byteBuffer = ByteBuffer.allocate(52);
            if(channel.read(byteBuffer) == -1)
            {
                whileBool = false;
                break;
            }

            byteBuffer.position(0);
            RecordLine line = new RecordLine();
            line.read(byteBuffer, accessFile);
            shapeFile.addGeometry(line);
        }
       // channel.close();
        return shapeFile;
    }
}

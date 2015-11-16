package com.snail.gis.shapefile.shp;

import com.snail.gis.shapefile.shp.exception.ShapeException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/10/29
 */
public class ShapeFileHeader
{
    public static final int MAGIC = 9994;

    public static final int VERSION = 1000;

    private int fileCode = -1;

    private int fileLength = -1;

    private int version = -1;

    private ShapeType shapeType = ShapeType.UNDEFINED;

    private String shapeName = null;
    private double minX;

    private double maxX;

    private double minY;

    private double maxY;

    public void read(ByteBuffer file, boolean strict) throws java.io.IOException
    {
        if(file == null)
        {
            throw ShapeException.throwException(ShapeException.TYPE.BYTE_IS_NULL);
        }

        file.order(ByteOrder.BIG_ENDIAN);
        fileCode = file.getInt();
        checkMagic(strict);
        file.position(file.position() + 20);
        fileLength = file.getInt();
        file.order(ByteOrder.LITTLE_ENDIAN);
        version = file.getInt();
        checkVersion(strict);
        shapeType = ShapeType.forID(file.getInt());

        minX = file.getDouble();
        minY = file.getDouble();
        maxX = file.getDouble();
        maxY = file.getDouble();
        file.order(ByteOrder.BIG_ENDIAN);
        file.position(file.position() + 32);
    }

    private void checkMagic(boolean strict) throws java.io.IOException
    {
        if (fileCode != MAGIC)
        {
           throw ShapeException.throwException(ShapeException.TYPE.MAGIC_IS_ERROR, "文件码为" + fileCode);
        }
    }

    private void checkVersion(boolean strict) throws java.io.IOException
    {
        if (version != VERSION)
        {
           throw ShapeException.throwException(ShapeException.TYPE.VERSION_IS_ERROR, "文件版本是" + version);
        }
    }

    public ShapeType getShapeType()
    {
        return shapeType;
    }

    public int getVersion()
    {
        return version;
    }

    public int getFileLength()
    {
        return fileLength;
    }

    public double getMinX()
    {
        return minX;
    }

    public double getMinY()
    {
        return minY;
    }

    public double getMaxX()
    {
        return maxX;
    }

    public double getMaxY()
    {
        return maxY;
    }

    public void setShapeName(String name)
    {
        this.shapeName = name;
    }
    public String getShapeName()
    {
        return shapeName;
    }
    public String toString()
    {
        String res = new String("ShapeFileHeader[ size " + fileLength + " version " + version
                + " shapeType " + shapeType + " bounds " + minX + "," + minY + "," + maxX + ","
                + maxY + " ]");
        return res;
    }
}

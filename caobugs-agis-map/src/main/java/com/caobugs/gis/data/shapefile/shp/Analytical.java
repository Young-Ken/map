package com.caobugs.gis.data.shapefile.shp;

import com.caobugs.gis.data.shapefile.event.OnShapeStatusListener;
import com.caobugs.gis.data.shapefile.shp.exception.ShapeException;
import com.caobugs.gis.data.shapefile.shp.factory.CreateGeometry;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/6
 */
public class Analytical
{
    private volatile static Analytical instance = null;
    private ShapeFileManger manger = ShapeFileManger.getInstance();
    private OnShapeStatusListener onShapeStatusListener = null;

    private Analytical(OnShapeStatusListener onShapeStatusListener)
    {
        this.onShapeStatusListener = onShapeStatusListener;
    }

    /**
     * 单例模式
     * @return Analytical
     */
    public static Analytical getInstance(OnShapeStatusListener onShapeStatusListener)
    {
        if (null == instance)
        {
            synchronized (Analytical.class)
            {
                if (null == instance)
                {
                    instance = new Analytical(onShapeStatusListener);
                }
            }
        }
        return instance;
    }

    /**
     * 解析shape文件
     * @param accessFile RandomAccessFile
     * @throws IOException
     */
    public void analyticalShape(RandomAccessFile accessFile, String shapeName) throws IOException
    {
        if (accessFile == null)
        {
            throw ShapeException.throwException(ShapeException.TYPE.FILE_IS_NULL);
        }

        ShapeFileHeader header = null;
        FileChannel channel = accessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        channel.read(byteBuffer);
        byteBuffer.position(0);
        header = readHead(byteBuffer);

        header.setShapeName(shapeName);
        CreateGeometry createGeometry = CreateGeometry.getInstance();
        createGeometry.createGeometry(header, accessFile);
        onShapeStatusListener.onShapeStatusChanged(shapeName);
    }

    private ShapeFileHeader readHead(ByteBuffer byteBuffer) throws IOException
    {
        if(byteBuffer == null)
        {
            throw ShapeException.throwException(ShapeException.TYPE.BYTE_IS_NULL);
        }
        ShapeFileHeader header = new ShapeFileHeader();
        header.read(byteBuffer, true);
        return header;
    }

    public void setOnShapeStatusListener(OnShapeStatusListener onShapeStatusListener)
    {
        this.onShapeStatusListener = onShapeStatusListener;
    }

}

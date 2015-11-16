package com.snail.gis.shapefile.shp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/4
 */
public class ShapeFileManger
{

    /**
     * ShapeFile ����
     */
    private List<ShapeFile> list = new ArrayList<>();
    /**
     * ����ģʽ
     */
    private volatile static ShapeFileManger instance = null;

    private ShapeFileManger()
    {
    }

    /**
     * ����ģʽ����ShapeFileManagerʵ��
     * @return ShapeFileManagerʵ��
     */
    public static ShapeFileManger getInstance()
    {
        if (null == instance)
        {
            synchronized (ShapeFileManger.class)
            {
                if (null == instance)
                {
                    instance = new ShapeFileManger();
                }
            }
        }
        return instance;
    }

    public void addShapeFile(ShapeFile shapeFile)
    {
        synchronized (ShapeFileManger.class)
        {
            list.add(shapeFile);
        }
    }

    public void removeShapeFile(ShapeFile shapeFile)
    {
        synchronized (ShapeFileManger.class)
        {
            list.remove(shapeFile);
        }
    }

    public void removeShapeFile(int index)
    {
        synchronized (ShapeFileManger.class)
        {
            list.remove(index);
        }
    }

    public List<ShapeFile> getList()
    {
        return list;
    }
}

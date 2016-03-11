package com.snail.gis.data.shapefile.shp;

import com.snail.gis.data.shapefile.shp.geom.RecordGeometry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/4
 */
public class ShapeFile
{
    private ShapeFileHeader header;
    private List<RecordGeometry> list = null;
    public ShapeFile()
    {
        list = new ArrayList<>();
    }

    public void addGeometry(RecordGeometry geometry)
    {
        synchronized(ShapeFile.class)
        {
            list.add(geometry);
        }
    }

    public void setHeader(ShapeFileHeader header)
    {
        this.header = header;
    }

    public List<RecordGeometry> getList()
    {
        return list;
    }

    public ShapeFileHeader getHeader()
    {
        return header;
    }
}

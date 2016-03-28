package com.caobugs.gis.data.shapefile.shp.factory;

import com.caobugs.gis.data.shapefile.shp.ShapeFileHeader;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/4
 */
public abstract class ShapeFactory
{
    abstract void createGeometry(ShapeFileHeader header, RandomAccessFile accessFile) throws IOException;
}

package com.snail.gis.shapefile.shp.factory;

import com.snail.gis.shapefile.shp.ShapeFileHeader;

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

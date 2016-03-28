package com.caobugs.gis.data.shapefile.cache;

import java.util.ArrayList;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/28
 */
public class ShapeCache
{
    /**
     * 点缓存
     */
    private float[] points = null;

    /**
     * 线缓存
     */
    private ArrayList<float[]> lines = new ArrayList<>();

    public void setPoints(float[] points)
    {
        this.points = points;
    }

    public float[] getPoints()
    {
        return points;
    }

    public ArrayList<float[]> getLines()
    {
        return lines;
    }



    public void desdory()
    {
        points = null;
        lines.clear();
    }
}

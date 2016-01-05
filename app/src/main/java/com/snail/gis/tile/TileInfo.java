package com.snail.gis.tile;

import com.snail.gis.geometry.Coordinate;

/**
 * 存储TileInfo的类
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/14
 */
public class TileInfo
{
    protected int DPI = 96;
    protected Coordinate originPoint = null;
    protected double[] resolutions;
    protected double[] scales;
    protected int tileHeight = 256;
    protected int tileWidth = 256;

    /**
     * 如果坐标系是投影就是地图周长一半，其他的自己计算
     * @return double
     */
    public double getConverParameter()
    {
        return originPoint.getY();
    }
    public void setOriginPoint(Coordinate originPoint)
    {
        this.originPoint = originPoint;
    }

    public Coordinate getOriginPoint()
    {
        return originPoint;
    }


    public int getDPI()
    {
        return DPI;
    }

    public void setDPI(int DPI)
    {
        this.DPI = DPI;
    }

    public double[] getResolutions()
    {
        return resolutions;
    }

    public void setResolutions(double[] resolutions)
    {
        this.resolutions = resolutions;
    }

    public double[] getScales()
    {
        return scales;
    }

    public void setScales(double[] scales)
    {
        this.scales = scales;
    }

    public int getTileHeight()
    {
        return tileHeight;
    }

    public int getTileWidth()
    {
        return tileWidth;
    }

    public void setTileHeight(int tileHeight)
    {
        this.tileHeight = tileHeight;
    }

    public void setTileWidth(int tileWidth)
    {
        this.tileWidth = tileWidth;
    }

}

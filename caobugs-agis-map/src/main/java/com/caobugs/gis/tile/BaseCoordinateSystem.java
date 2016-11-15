package com.caobugs.gis.tile;


import com.caobugs.gis.geometry.Coordinate;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/17
 */
public class BaseCoordinateSystem implements CoordinateSystem
{
    private double originResolution = 0.0;
    private double originScale = 0.0;
    private int maxLevel = 0;
    private TileInfo tileInfo = null;

    public BaseCoordinateSystem()
    {
        tileInfo   = new TileInfo();
    }
    public BaseCoordinateSystem(Coordinate originPoint, double originScale, double originResolution, int maxLevel)
    {
        this();
        tileInfo.setOriginPoint(originPoint);
        this.originScale = originScale;
        this.originResolution = originResolution;
        this.maxLevel = maxLevel;
    }

    public BaseCoordinateSystem(Coordinate originPoint, double originScale, double originResolution, int maxLevel, int dpi, int tileWidth, int tileHeight)
    {
        this(originPoint, originScale, originResolution, maxLevel);
        tileInfo.setDPI(dpi);
        tileInfo.setTileHeight(tileHeight);
        tileInfo.setTileWidth(tileWidth);
    }

    /**
     * 初始化比例尺算法
     */
    @Override
    public void initTileInfo()
    {
        double[] scales = new double[maxLevel];
        double[] resolutions = new double[maxLevel];
        for (int i = 0; i < maxLevel; i++)
        {
            double tileSquare = Math.pow(2, i);
            scales[i] = originScale / tileSquare;
            resolutions[i] =  originResolution / tileSquare;
        }
        tileInfo.setResolutions(resolutions);
        tileInfo.setScales(scales);
    }

    @Override
    public TileInfo getTileInfo()
    {
        return tileInfo;
    }

}

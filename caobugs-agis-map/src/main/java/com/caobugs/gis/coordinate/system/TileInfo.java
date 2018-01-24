package com.caobugs.gis.coordinate.system;

import com.caobugs.gis.geometry.Coordinate;


/**
 * 存储TileInfo的类
 *
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/14
 */
public class TileInfo {
    private int maxLevel = 21;
    private int DPI = 96;
    private Coordinate originPoint = null;

    /**
     * 　ground resolution = (cos(latitude * pi/180) * 2 * pi * 6378137 meters) / (256 * 2level pixels)
     * 初始化的时候latitude为0，则cos(latitude * pi/180)为1，level为0 2^0 = 1
     * 最后就是地图周长/256
     */
    private double[] resolutions;
    /**
     * 256 * 2level / screen dpi / 0.0254 / (cos(latitude * pi/180) * 2 * pi * 6378137)
     * 比例尺= 1 : (cos(latitude * pi/180) * 2 * pi * 6378137 * screen dpi) / (256 * 2level * 0.0254)
     * map scale = 1 : ground resolution * screen dpi / 0.0254 meters/inch
     */
    private double[] scales;

    private int tileSize = 256;

    /**
     * 如果坐标系是投影就是地图周长一半，其他的自己计算
     *
     * @return double
     */
    public double getConverParameter() {
        return originPoint.y;
    }

    public void setOriginPoint(Coordinate originPoint) {
        this.originPoint = originPoint;
    }

    public Coordinate getOriginPoint() {
        return originPoint;
    }


    public int getDPI() {
        return DPI;
    }

    public void setDPI(int DPI) {
        this.DPI = DPI;
    }

    public double[] getResolutions() {
        return resolutions;
    }

    public void setResolutions(double[] resolutions) {
        this.resolutions = resolutions;
    }

    public double[] getScales() {
        return scales;
    }

    public void setScales(double[] scales) {
        this.scales = scales;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}

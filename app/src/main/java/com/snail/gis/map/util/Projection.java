package com.snail.gis.map.util;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.map.BaseMap;
import com.snail.gis.map.MapManger;
import com.snail.gis.tile.TileInfo;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/29
 */
public class Projection
{
    private BaseMap map = null;
    private TileInfo tileInfo = null;
    public Projection()
    {
        map = MapManger.getInstance().getMap();
        tileInfo = map.getTileInfo();
    }

    /**
     * 把屏幕坐标转换成地理坐标
     * @param screenx x
     * @param screeny y
     * @return 点
     */
    public Coordinate toMapPoint(float screenX, float screenY)
    {
        double x = ((map.getEnvelope().getMinX())/map.getResolution() + screenX)*map.getResolution();
        double y = ((map.getEnvelope().getMinY())/map.getResolution() + screenY)*map.getResolution();
        return new Coordinate(x,y);
    }

    /**
     * 把地理坐标转换成屏幕坐标
     * @param point 坐标点
     * @return 点
     */
    public Coordinate toScreenPoint(double pointX, double pointY)
    {
        double x = (pointX - (map.getMapCenter().getX() - map.getWidth()/2 * map.getResolution()))/map.getResolution();
        double y = (pointY - (map.getMapCenter().getY() - map.getHeight()/2 * map.getResolution()))/map.getResolution();
        return new Coordinate(x, y);
    }
}

package com.caobugs.gis.map.util;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.tile.CoordinateSystemManager;
import com.caobugs.gis.map.BaseMap;
import com.caobugs.gis.tile.TileInfo;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/29
 */
public class Projection {
    private BaseMap map = null;

    private static double piExcept360 = Math.PI / 360;
    private static double piExcept180 = Math.PI / 180;
    private TileInfo tileInfo = CoordinateSystemManager.getInstance().getCoordinateSystem().getTileInfo();
    private double originPointX = tileInfo.getOriginPoint().x;
    private double originPointY = tileInfo.getOriginPoint().y;
    private double earthRExcept180 = originPointX / 180;

    /**
     * 单例模式
     */
    private volatile static Projection instance = null;

    private Projection(BaseMap map) {
        this.map = map;
        this.tileInfo = CoordinateSystemManager.getInstance().getCoordinateSystem().getTileInfo();
    }

    /**
     * 单例模式返回ShapeFileManager实例
     *
     * @return ShapeFileManager实例
     */
    public static Projection getInstance(BaseMap map) {
        if (null == instance) {
            synchronized (Projection.class) {
                if (null == instance) {
                    instance = new Projection(map);
                }
            }
        }
        return instance;
    }

    /**
     * 把屏幕坐标转换成地理坐标
     *
     * @param screenX x
     * @param screenY y
     * @return 点
     */
    public Coordinate toMapPoint(float screenX, float screenY) {

        double x = ((map.getMapInfo().getCurrentEnvelope().getMinX())
                / map.getMapInfo().getCurrentResolution() + screenX)
                * map.getMapInfo().getCurrentResolution();
        double y = ((map.getMapInfo().getCurrentEnvelope().getMinY())
                / map.getMapInfo().getCurrentResolution() + screenY)
                * map.getMapInfo().getCurrentResolution();
        return new Coordinate(x, y);
    }

    public Coordinate earthTransFormImage(double x, double y) {
        return new Coordinate(Math.abs(tileInfo.getOriginPoint().x - x),
                Math.abs(tileInfo.getOriginPoint().y - y));
    }

    public Coordinate earthTransFormImage(Coordinate coordinate) {
        double x = Math.abs(originPointX - coordinate.x);
        double y = Math.abs(originPointY - coordinate.y);
        coordinate.x = x;
        coordinate.y = y;
        return coordinate;
    }


    public Coordinate imageTransFromEarth(Coordinate coordinate) {
        double x = coordinate.x - Math.abs(originPointX);
        double y = Math.abs(coordinate.y - originPointY);

        coordinate.x = x;
        coordinate.y = y;
        return coordinate;
    }

    /**
     * 图片坐标转成地图坐标
     *
     * @param x
     * @param y
     * @return
     */
    public Coordinate imageTransFromEarth(double x, double y) {
        x = x - Math.abs(originPointX);
        y = Math.abs(y - originPointY);
        return new Coordinate(x, y);
    }

    /**
     * 把图片墨卡托坐标转换成经纬坐标
     *
     * @param x double
     * @param y double
     * @return Coordinate
     */
    public Coordinate imageMercatorTransToLonLat(double x, double y) {
        Coordinate coordinate;
        coordinate = imageTransFromEarth(x, y);
        coordinate = mercatorToLonLat(coordinate.x, coordinate.y);
        return coordinate;
    }

    /**
     * 把地理坐标转换成屏幕坐标
     *
     * @param pointX 坐标点
     * @param pointY 坐标点
     * @return 点
     */
    public Coordinate toScreenPoint(double pointX, double pointY) {
        double x = (pointX - map.getMapInfo().getCurrentEnvelope().getMinX())
                / map.getMapInfo().getCurrentResolution();
        double y = (pointY - map.getMapInfo().getCurrentEnvelope().getMinY())
                / map.getMapInfo().getCurrentResolution();
        return new Coordinate(x, y);
    }

    public Coordinate toScreenPoint(Coordinate coordinate) {
        double x = (coordinate.x - map.getMapInfo().getCurrentEnvelope().getMinX())
                / map.getMapInfo().getCurrentResolution();
        double y = (coordinate.y - map.getMapInfo().getCurrentEnvelope().getMinY())
                / map.getMapInfo().getCurrentResolution();
        coordinate.x = x;
        coordinate.y = y;
        return coordinate;
    }

    public Coordinate toScreenPoint(Coordinate coordinate, double minX, double minY, double resolution) {
        double x = (coordinate.x - minX) / resolution;
        double y = (coordinate.y - minY) / resolution;
        coordinate.x = x;
        coordinate.y = y;
        return coordinate;
    }
    /**
     * outOfChina
     *
     * @param lng
     * @param lat
     * @return {boolean}
     * @描述: 判断是否在国内，不在国内则不做偏移
     */
    private static boolean outOfChina(Double lng, Double lat) {
        return (lng < 72.004 || lng > 137.8347) || ((lat < 0.8293 || lat > 55.8271) || false);
    }

    public Coordinate lonLatToMercator(double x, double y) {
        double toX = x * earthRExcept180;
        double toY = Math.log(Math.tan((90 + y) * piExcept360)) / piExcept180;
        toY = toY * earthRExcept180;
        return new Coordinate(toX, toY);
    }

    public Coordinate lonLatToMercator(Coordinate coordinate) {
        double toX = coordinate.x * earthRExcept180;
        double toY = Math.log(Math.tan((90 + coordinate.y) * piExcept360)) / piExcept180;
        toY = toY * earthRExcept180;
        coordinate.x = toX;
        coordinate.y = toY;
        return coordinate;
    }

    public Coordinate mercatorToLonLat(double x, double y) {
        double toX = x / 20037508.342787 * 180;
        double toY = y / 20037508.342787 * 180;
        toY = 180 / Math.PI * (2 * Math.atan(Math.exp(toY * Math.PI / 180)) - Math.PI / 2);
        return new Coordinate(toX, toY);
    }
}

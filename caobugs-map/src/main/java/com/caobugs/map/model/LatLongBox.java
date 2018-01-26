package com.caobugs.map.model;

import com.caobugs.map.model.api.ILatLong;
import com.caobugs.map.model.api.ILatLongBox;

/**
 * @author Young Ken
 * @since 2018-01
 */
public class LatLongBox implements ILatLongBox {

    private double minLongitude;
    private double maxLongitude;
    private double minLatitude;
    private double maxLatitude;

    /**
     * 无参构造函数
     */
    public LatLongBox() {
        init();
    }

    /**
     * 构造方法
     *
     * @param latLong 线段
     * @param another 线段
     */
    public LatLongBox(ILatLong latLong, ILatLong another) {
        init(latLong, another);
    }

    public LatLongBox(ILatLongBox another) {
        init(another);
    }

    /**
     * 得到矩形的宽
     *
     * @return double
     */
    public double getWidth() {
        if (isEmpty()) {
            return 0;
        }
        return maxLongitude - minLongitude;
    }

    /**
     * 得到矩形的高
     *
     * @return double
     */
    public double getHeight() {
        if (isEmpty()) {
            return 0;
        }
        return maxLatitude - minLatitude;
    }

    /**
     * 矩形转换
     *
     * @param transX x轴方法增量
     * @param transY y轴增量
     */
    public void translate(final double transX, final double transY) {
        if (isEmpty()) {
            return;
        }
        init(getMinLongitude() + transX, getMaxLongitude() + transX, getMinLatitude() + transY,
                getMaxLatitude() + transY);
    }

    /**
     * 得到矩形的中心
     *
     * @return 中心点
     */
    public ILatLong getCenter() {
        if (isEmpty()) {
            return null;
        }
        return new LatLong((maxLongitude - minLongitude) / 2.0 + minLongitude, (maxLatitude - minLatitude) / 2.0 + minLatitude);
    }

    private void init() {
        setToNull();
    }

    public void init(ILatLong latLong, ILatLong another) {
        init(latLong.getLongitude(), another.getLongitude(),
                latLong.getLatitude(), another.getLatitude());
    }

    public void init(ILatLongBox box) {
        this.minLongitude = box.getMinLongitude();
        this.maxLongitude = box.getMaxLongitude();
        this.minLatitude = box.getMinLatitude();
        this.maxLatitude = box.getMaxLatitude();
    }

    private void init(double x1, double x2, double y1, double y2) {
        if (x1 < x2) {
            minLongitude = x1;
            maxLongitude = x2;
        } else {
            minLongitude = x2;
            maxLongitude = x1;
        }
        if (y1 < y2) {
            minLatitude = y1;
            maxLatitude = y2;
        } else {
            minLatitude = y2;
            maxLatitude = y1;
        }
    }

    /**
     * 这个Envelope根本就不存在，这个这个用作初始化Envelope
     */
    public void setToNull() {
        minLongitude = 0;
        maxLongitude = -1;
        minLatitude = 0;
        maxLatitude = -1;
    }

    public boolean isEmpty() {
        return maxLongitude < minLongitude;
    }

    /**
     * 如果矩形在矩形外部一定不相交
     *
     * @param other Envelope
     * @return
     */
    public boolean intersects(ILatLongBox other) {
        if (isEmpty() || other.isEmpty()) {
            return false;
        }
        return !(other.getMinLatitude() > maxLongitude
                || other.getMinLatitude() > maxLatitude
                || other.getMaxLongitude() < minLongitude
                || other.getMaxLatitude() < minLatitude);
    }

    /**
     * 如果点在矩形外部一定不相交
     *
     * @param latLong Point
     * @return
     */
    public boolean intersects(ILatLong latLong) {
        if (isEmpty()) {
            return false;
        }
        return intersects(latLong.getLongitude(), latLong.getLatitude());
    }

    /**
     * 如果点在矩形外部一定不相交
     *
     * @param x x
     * @param y y
     * @return
     */
    public boolean intersects(double x, double y) {
        if (isEmpty()) {
            return false;
        }
        return !(x > maxLongitude || x < minLongitude || y > maxLatitude || y < minLatitude);
    }

    /**
     * 矩形包含矩形
     *
     * @param other 矩形
     * @return 包含返回true
     */
    public boolean contains(ILatLongBox other) {
        if (isEmpty()) {
            return false;
        }
        return (minLongitude <= other.getMinLongitude()
                && minLatitude <= other.getMinLatitude()
                && maxLongitude >= other.getMaxLongitude()
                && maxLatitude >= other.getMaxLatitude());
    }

    /**
     * 包含点
     *
     * @param latLong 点
     * @return 包含返回true
     */
    public boolean contains(ILatLong latLong) {
        if (isEmpty()) {
            return false;
        }
        return contains(latLong.getLongitude(), latLong.getLatitude());
    }

    /**
     * 包含 x ，y
     *
     * @param longitude x
     * @param latitude y
     * @return 包含返回true
     */
    public boolean contains(double longitude, double latitude) {
        if (isEmpty()) {
            return false;
        }
        return (longitude >= minLongitude && longitude <= maxLongitude && latitude >= minLatitude && latitude <= maxLatitude);
    }

    /**
     * 扩展外接矩形
     *
     * @param another
     */
    public void expandToInclude(ILatLong another) {
        expandToInclude(another.getLongitude(), another.getLatitude());
    }

    /**
     * 扩展外接矩形
     *
     * @param longitude
     * @param latitude
     */
    public void expandToInclude(double longitude, double latitude) {
        if (isEmpty()) {
            minLongitude = longitude;
            maxLongitude = longitude;
            minLatitude = latitude;
            maxLatitude = latitude;
        } else {
            if (longitude < minLongitude) {
                minLongitude = longitude;
            }
            if (longitude > maxLongitude) {
                maxLongitude = longitude;
            }
            if (latitude < minLatitude) {
                minLatitude = latitude;
            }
            if (latitude > maxLatitude) {
                maxLatitude = latitude;
            }
        }
    }

    public double getArea() {
        if (!isEmpty()) {
            return getHeight() * getWidth();
        }
        return 0;
    }

    public double getMaxLongitude() {
        return maxLongitude;
    }

    public double getMaxLatitude() {
        return maxLatitude;
    }

    public double getMinLongitude() {
        return minLongitude;
    }

    public double getMinLatitude() {
        return minLatitude;
    }

    /**
     * 从写toString方法
     *
     * @return Envelope toString（）
     */
    public String toString() {
        return "LatLongBox[" + minLongitude + " : " + maxLongitude + ", " + minLatitude + " : " + maxLatitude + "]";
    }
}

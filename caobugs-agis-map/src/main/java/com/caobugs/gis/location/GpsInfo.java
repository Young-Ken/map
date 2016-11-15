package com.caobugs.gis.location;

/**
 * 用于存储当前的gps定位的省市信息
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/10
 */
public class GpsInfo
{
    public static final int TYPE_GPS_LOCATION = 0;
    public static final int TYPE_NETWORK_LOCATION = 1;
    public static final int TYPE_OFFLINE_LOCATION = 2;

    private int typeLocation = -1;


    /**
     * 省
     */
    private String province = null;

    /**
     * 市
     */
    private String city = null;

    /**
     * 区
     */
    private String county = null;

    /**
     * 镇
     */
    private String town = null;

    /**
     * 村
     */
    private String village = null;

    /**
     * 纬度
     */
    private double latitude = 0;

    /**
     * 经度
     */
    private double longitude = 0;

    /**
     * 经度
     */
    private float accuracy = 0;

    /**
     * 海拔高度
     */
    private double altitude = 0;

    /**
     *
     */
    private float bearing = 0;

    /**
     * 速度
     */
    private float speed = 0;

    /**
     * 时间
     */
    private String time = null;

    /**
     * 单例模式
     */
    private volatile static GpsInfo instance = null;

    private int satelliteNum = 0;


    private GpsInfo()
    {

    }

    public static GpsInfo getInstance()
    {
        if (null == instance)
        {
            synchronized (GpsInfo.class)
            {
                if (null == instance)
                {
                    instance = new GpsInfo();
                }
            }
        }
        return instance;
    }

    public boolean isEmpty()
    {
        return time == null || time.equals("");
    }

    public int getSatelliteNum()
    {
        return satelliteNum;
    }

    public void setSatelliteNum(int satelliteNum)
    {
        this.satelliteNum = satelliteNum;
    }

    public int getTypeLocation()
    {
        return typeLocation;
    }

    public void setTypeLocation(int typeLocation)
    {
        this.typeLocation = typeLocation;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCity()
    {
        return city;
    }

    public String getCounty()
    {
        return county;
    }

    public String getProvince()
    {
        return province;
    }

    public String getTown()
    {
        return town;
    }

    public String getVillage()
    {
        return village;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setCounty(String county)
    {
        this.county = county;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public void setTown(String town)
    {
        this.town = town;
    }

    public void setVillage(String village)
    {
        this.village = village;
    }
}
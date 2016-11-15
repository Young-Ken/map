package com.caobugs.gis.view.control;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/7/13
 */
public class PositionRunTimeData
{

    public static enum Position
    {
        province,
        city,
        county,
        town,
        village;

        private Position()
        {

        }
    }

    /**
     * 单例模式
     */
    private volatile static PositionRunTimeData instance = null;

    private PositionRunTimeData()
    {

    }

    public static PositionRunTimeData getInstance()
    {
        if (null == instance)
        {
            synchronized (PositionRunTimeData.class)
            {
                if (null == instance)
                {
                    instance = new PositionRunTimeData();
                }
            }
        }
        return instance;
    }

    private String provinceName = null;
    private String cityName = null;
    private String countyName = null;
    private String townName = null;
    private String villageName = null;
    private String villageID = null;

    public String getProvinceName()
    {
        return provinceName;
    }

    public String getCityName()
    {
        return cityName;
    }

    public String getCountyName()
    {
        return countyName;
    }

    public String getTownName()
    {
        return townName;
    }

    public String getVillageName()
    {
        return villageName;
    }

    public String getVillageID()
    {
        return villageID;
    }

    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public void setCountyName(String countyName)
    {
        this.countyName = countyName;
    }

    public void setTownName(String townName)
    {
        this.townName = townName;
    }

    public void setVillageName(String villageName)
    {
        this.villageName = villageName;
    }

    public void setVillageID(String villageID)
    {
        this.villageID = villageID;
    }
}

package com.caobugs.gis.vo;

import com.caobugs.gis.geometry.LinearRing;

import java.io.Serializable;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/4
 */
public class Farmland implements Serializable
{
    public static final String ID = "id";
    public static final String FARMNAME = "farmName";
    public static final String ADDRESS = "address";
    public static final String AERA = "area";
    public static final String FARMGROM = "farmGeom";
    public static final String TEL = "tel";
    public static final String TIME = "time";

    private int id = 0;
    private String farmName = null;
    private String address = null;
    private double area = 0;
    private LinearRing farmGeom = null;
    private String tel = null;
    private String time = null;


    public int getId()
    {
        return id;
    }

    public String getFarmName()
    {
        return farmName;
    }

    public String getTel()
    {
        return tel;
    }

    public String getAddress()
    {
        return address;
    }

    public double getArea()
    {
        return area;
    }

    public LinearRing getFarmGeom()
    {
        return farmGeom;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setFarmName(String farmName)
    {
        this.farmName = farmName;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setArea(double area)
    {
        this.area = Double.parseDouble(String.format("%.2f", area));
    }

    public void setFarmGeom(LinearRing farmGeom)
    {
        this.farmGeom = farmGeom;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }
}

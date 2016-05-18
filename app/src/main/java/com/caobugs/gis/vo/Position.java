package com.caobugs.gis.vo;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/16
 */
public class Position
{
    private String id = null;
    private String positionID = null;
    private String name = null;

    public Position()
    {

    }

    public Position(String positionID, String name)
    {
        this.positionID = positionID;
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPositionID()
    {
        return positionID;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPositionID(String positionID)
    {
        this.positionID = positionID;
    }
}

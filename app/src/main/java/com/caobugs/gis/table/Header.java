package com.caobugs.gis.table;


/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/3
 */
public class Header
{

    private String name;
    private int type;

    public int getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setType(int type)
    {
        this.type = type;
    }

}

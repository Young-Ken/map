package com.caobugs.gis.data.db;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/17
 */
public class ConnectDB
{
    private String dataName = null;
    private String userName = null;
    private String password = null;

    public void setDataName(String dataName)
    {
        this.dataName = dataName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getDataName()
    {
        return dataName;
    }

    public String getPassword()
    {
        return password;
    }
}

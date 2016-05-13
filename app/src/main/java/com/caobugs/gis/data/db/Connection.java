package com.caobugs.gis.data.db;


import com.caobugs.gis.tool.file.ToolStorage;


public class Connection
{

    private String path = "/AZY/DB/DataSource.db";
   // private String password = "TYGJ";

    private jsqlite.Database dataCollectDB;
    private static String dbFile;

    public Connection()
    {
        path = ToolStorage.getSDCordFile().toString() + path;
    }

    public void initRun()
    {
    }

    public void close()
    {
        try
        {
            dataCollectDB.close();
            dataCollectDB = null;
        } catch (jsqlite.Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean open()
    {
        if (dataCollectDB == null)
        {
            try
            {
                dataCollectDB = SpatialDB.newInstance(path, "");
            } catch (jsqlite.Exception e)
            {
                e.printStackTrace();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return true;
    }

    public jsqlite.Database getDataCollectDB()
    {
        return dataCollectDB;
    }
}

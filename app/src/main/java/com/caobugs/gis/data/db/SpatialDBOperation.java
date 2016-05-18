package com.caobugs.gis.data.db;


import android.util.Log;

import com.caobugs.gis.enumeration.ConstantFile;
import com.caobugs.gis.tool.file.ToolStorage;


public class SpatialDBOperation
{

    private String path = null;
    private jsqlite.Database dataCollectDB;

    public SpatialDBOperation()
    {
        path = ToolStorage.getSDCordFile().toString() +"/"+ ConstantFile.ROOT+"/"+ConstantFile.DB_FILE+"/"+"dataSource.db";
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
        boolean result = true;
        if (dataCollectDB == null)
        {
            try
            {
                dataCollectDB = SpatialDB.newInstance(path, "");
            } catch (jsqlite.Exception e)
            {
                result = false;
                e.printStackTrace();
            }
        }
        return result;
    }

    public jsqlite.Database getDataCollectDB()
    {
        return dataCollectDB;
    }
}

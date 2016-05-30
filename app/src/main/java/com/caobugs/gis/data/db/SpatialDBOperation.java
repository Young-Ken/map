package com.caobugs.gis.data.db;


import com.caobugs.gis.util.constants.ConstantFile;
import com.caobugs.gis.util.file.ToolStorage;


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
                if(SpatialDB.SingletonHolder.dataCollectDB != null)
                {
                    dataCollectDB = SpatialDB.SingletonHolder.dataCollectDB;
                }else {
                    dataCollectDB = SpatialDB.newInstance(path, "");
                }


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

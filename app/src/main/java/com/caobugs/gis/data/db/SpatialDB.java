package com.caobugs.gis.data.db;

import java.io.File;

import android.util.Log;

import jsqlite.Constants;

public class SpatialDB
{

    private static final String TAG = "SpatialDatabase";

    private static class SingletonHolder
    {
        public static jsqlite.Database dataCollectDB = new jsqlite.Database();
    }

    private SpatialDB()
    {
    }

    public static jsqlite.Database newInstance(String dbFile, String decrypt)
            throws Exception
    {
        Boolean isOpen = openDBConnection(SingletonHolder.dataCollectDB,
                dbFile, decrypt);
        if (isOpen)
        {
            Log.i(TAG, "open openDBConnection success!");
            return SingletonHolder.dataCollectDB;
        } else
        {
            Log.i(TAG, "open openDBConnection faile!");
            throw new Exception("连接数据库失败！");
        }

    }

    private static Boolean openDBConnection(jsqlite.Database database,
                                            String dbFile, String decrypt) throws Exception
    {
        try
        {
            // 判断数据库文件是否存在
            File file = new File(dbFile);
            if (!file.exists())
            {
                return false;
            }
            // 设置属性文件的存储目录
            // SETTING_FILES_PATH = file.getParentFile().getAbsolutePath();
            // 尝试打开数据库
            database.open(file.toString(), Constants.SQLITE_OPEN_READWRITE);
            if (decrypt != "")
            {
                database.key(decrypt);
            }
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }
}

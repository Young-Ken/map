package com.caobugs.gis.data.db;

import java.io.File;

import android.util.Log;
import android.widget.Toast;

import com.caobugs.gis.tool.ApplicationContext;
import com.caobugs.gis.tool.TAG;

import jsqlite.*;

public class SpatialDB
{

    /**
     * 单例模式
     */
    private volatile static SpatialDB instance = null;

    private Boolean dataIsOpen = false;

    private SpatialDB()
    {
    }

    /**
     * 单例模式返回ShapeFileManager实例
     * @return ShapeFileManager实例
     */
    public static SpatialDB getInstance()
    {
        if (null == instance)
        {
            synchronized (SpatialDB.class)
            {
                if (null == instance)
                {
                    instance = new SpatialDB();
                }
            }
        }
        return instance;
    }

    public static class SingletonHolder
    {
        public static jsqlite.Database dataCollectDB = null;
    }



    public static jsqlite.Database newInstance(String dbFile, String decrypt)
            throws jsqlite.Exception
    {
        SingletonHolder.dataCollectDB = new jsqlite.Database();
        Boolean isOpen = openDBConnection(SingletonHolder.dataCollectDB,
                dbFile, decrypt);
        if (isOpen)
        {
            Log.i(TAG.DATABASE, "open openDBConnection success!");
            return SingletonHolder.dataCollectDB;
        } else
        {
            Toast.makeText(ApplicationContext.getContext(),"打开数据库失败，请联系开发人员",Toast.LENGTH_LONG).show();
            throw new jsqlite.Exception("连接数据库失败！");
        }
    }

    public static jsqlite.Database getDataCollectDB()
    {
        return SingletonHolder.dataCollectDB;
    }

    /**
     * 数据库正常打开
     * @param database Database
     * @param dbFile 数据库路径
     * @param decrypt 密码
     * @return boolean
     */
    private static Boolean openDBConnection(jsqlite.Database database, String dbFile, String decrypt)
    {
        boolean result = true;
        try
        {
            // 判断数据库文件是否存在
            File file = new File(dbFile);
            if (!file.exists())
            {
                result = false;
            }
            // 尝试打开数据库
            database.open(file.toString(), Constants.SQLITE_OPEN_READWRITE);
            if (!decrypt.equals(""))
            {
                database.key(decrypt);
            }
        } catch (jsqlite.Exception e)
        {
            result = false;
            Toast.makeText(ApplicationContext.getContext(),"打开数据库错误,请联系开发人员!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return result;
    }
}

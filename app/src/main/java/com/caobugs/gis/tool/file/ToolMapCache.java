package com.caobugs.gis.tool.file;


import android.util.Log;

import com.caobugs.gis.enumeration.ConstantFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 这个缓存sd卡级别的缓存
 * @author Young Ken
 * @version 0.1
 * @since 2015/9/30
 */
public class ToolMapCache
{
    /**
     * 默认切片缓存
     */
    //public static final String MAPCACHE_PATH = "MapCache";

    /**
     * 拼接缓存切片地址
     *
     * @param path  路径
     * @param level 级别
     * @param col   行
     * @param row   列
     * @return 切片路径
     */
    public static String getMapCachePath(final String path, final int level, final int col, final int row)
    {
        StringBuffer resultPath = new StringBuffer();
        File sdPath = ToolStorage.getSDCordFile();
        resultPath.append(sdPath);
        resultPath.append(File.separator);
        resultPath.append(ConstantFile.ROOT);
        resultPath.append(File.separator);
        resultPath.append(ConstantFile.MAP_CACHE);
        resultPath.append(File.separator);
        resultPath.append(path);
        resultPath.append(String.format(File.separator + "%d" + File.separator + "%d_%d.ZY", level, col, row));
        return resultPath.toString();
    }


    /**
     * 判断切片是否存在
     *
     * @param path  路径
     * @param level 级别
     * @param col   行
     * @param row   列
     * @return true 存在 false 不存在
     */
    public static boolean isExistByte(final String path, final int level, final int col, final int row)
    {
        File file = ToolFile.createFile(getMapCachePath(path, level, col, row));
        return file.exists() && !file.getPath().equals("");
    }

    /**
     * 把Byte写入到sdcard中
     *
     * @param bytes byte数组
     * @param path  路径
     * @param level 级别
     * @param col   行
     * @param row   列
     * @return true 写入成功 false 写入不成功
     */
    public synchronized static boolean saveByte(final byte[] bytes, final String path, final int level, final int col, int row)
    {
        File file = ToolFile.createFile(getMapCachePath(path, level, col, row));

        if (file.getPath().equals(""))
        {
            return false;
        }

        File parentFile = ToolFile.createFile(file.getParent());



        if (!parentFile.exists())
        {
            return parentFile.mkdirs() && writeToBytes(bytes, file);
        } else
        {
            return file.isDirectory() && writeToBytes(bytes, file);
        }
    }

    /**
     * 从sdcard中读取切片
     *
     * @param path  路径
     * @param level 级别
     * @param col   行
     * @param row   列
     * @return true 写入成功 false 写入不成功
     */
    public synchronized static byte[] getByte(final String path, final int level, final int col, final int row) throws IOException
    {
        InputStream is = null;
        ByteArrayOutputStream bos = null;

        if (!isExistByte(path, level, col, row))
            return null;

        is = new FileInputStream(getMapCachePath(path, level, col, row));
        bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];

        int bytesRead = -1;
        while ((bytesRead = is.read(b)) != -1)
        {
            bos.write(b, 0, bytesRead);
        }

        return bos.toByteArray();
    }

    public static boolean writeToBytes(byte bytes[], File file)
    {
        return writeToBytes(bytes, file.getPath());
    }

    public static boolean writeToBytes(byte bytes[], String fileName)
    {
        boolean result = true;
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(fileName, true);
            fos.write(bytes);
            fos.flush();
        } catch (Exception e)
        {
            result = false;
            e.printStackTrace();
        } finally
        {
            try
            {
                if (null != fos)
                {
                    fos.close();
                } else
                {
                    result = false;
                }
            } catch (IOException e)
            {
                result = false;
                e.printStackTrace();
            }
        }
        return result;
    }
}

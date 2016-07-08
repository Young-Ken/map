package com.caobugs.gis.util.file;

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
     * 拼接缓存切片地址
     *
     * @param tileType  路径
     * @param level 级别
     * @param col   行
     * @param row   列
     * @return 切片路径
     */
    public static String getMapCachePath(final String tileType, final int level, final int col, final int row)
    {
        StringBuffer resultPath = new StringBuffer(ToolStorage.getInternalMapCachePath());
        resultPath.append(getMapCacheMosaicPath(tileType,level,col,row));
        return resultPath.toString();
    }

    public static String getMapCacheMosaicPath(final String titleType, final int level, final int col, final int row)
    {
        StringBuffer resultPath = new StringBuffer(File.separator);
        resultPath.append(titleType);
        resultPath.append(String.format(File.separator + "%d" + File.separator + "%d" + File.separator + "%d.ZY", level, col, row));
        String result = resultPath.toString();
        resultPath.delete(0,resultPath.length());
        return result;
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
        return !file.getPath().equals("") && file.exists();
    }

    public static String isExistByte(String path)
    {
        StringBuffer tilePath = null;
        if(ToolStorage.getCanUserFile() == null || ToolStorage.getCanUserFile().size() == 0)
        {
            return null;
        }

        if(ToolStorage.getCanUserFile().size() == 1)
        {
            tilePath = new StringBuffer(ToolStorage.getInternalMapCachePath()).append(path);
            if (!checkTileFile(tilePath.toString()).equals(""))
            {
                return tilePath.toString();
            }
        }
        else if(ToolStorage.getCanUserFile().size() >= 2)
        {
            tilePath = new StringBuffer(ToolStorage.getInternalMapCachePath()).append(path);
            if (!checkTileFile(tilePath.toString()).equals(""))
            {
                return tilePath.toString();
            }
            tilePath = new StringBuffer(ToolStorage.getExtendMapCachePath()).append(path);
            if (!checkTileFile(tilePath.toString()).equals(""))
            {
                return tilePath.toString();
            }
        }
        return null;
    }

    public static String checkTileFile(String path)
    {
        File file = new File(path);
        if(file.exists())
        {
            return path;
        }
        return "";
    }


    /**
     * 从sdcard中读取切片
     *
     * @param tileType  路径
     * @param level 级别
     * @param col   行
     * @param row   列
     * @return true 写入成功 false 写入不成功
     */
    public synchronized static byte[] getByte(final String tileType, final int level, final int col, final int row) throws IOException
    {
        InputStream is = null;
        ByteArrayOutputStream bos = null;

        String mosaicPath = getMapCacheMosaicPath(tileType, level, col, row);
        String titlePath = isExistByte(mosaicPath);
        if (titlePath == null)
            return null;

        is = new FileInputStream(titlePath);
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

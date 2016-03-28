package com.caobugs.gis.tool.file;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * @author Young Ken
 * @since 2015/8/25
 * @version 0.1
 */

public class ToolStorage
{
    /**
     * 判断SDCard是否存在
     * @return true 存在 false 不存在
     */
    public static boolean isExistSDCard()
    {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 取得sd路径
     * @return File
     */
    public static File getSDCordFile()
    {
       return isExistSDCard() == true ?  Environment.getExternalStorageDirectory() : null;
    }

    private static StatFs getStatFs()
    {
        File file = getSDCordFile();
        return file != null ? new StatFs(file.getPath()) : null;
    }


    /**
     * 得到sdcard剩余空间
     * @return 剩余空间字节数目
     */
    public static long getSDFreeSize()
    {
        StatFs sf = getStatFs();
        if (sf == null)
        {
            return -1;
        }
        long blockSize = sf.getBlockSize();
        long freeBlocks = sf.getAvailableBlocks();
        return (freeBlocks * blockSize)/1048576;  //1048576 = 1024*1024
    }

    public static long getSDAllSize()
    {
        StatFs sf = getStatFs();
        if (sf == null)
            return -1;
        long blockSize = sf.getBlockSize();
        long allBlocks = sf.getBlockCount();
        return (allBlocks * blockSize)/1048576; //1048576 = 1024*1024
    }
}

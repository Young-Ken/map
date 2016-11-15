package com.caobugs.gis.util.file;

import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Young Ken on 2015/8/25.
 */
public class ToolFile
{

    /**
     * 创建文件夹
     * @param path 路径
     * @return 返回创建的文件夹
     */
    public static File createFile(final String path)
    {
        return new File(path);
    }

    /**
     * 创建文件夹
     * @param path 路径
     * @return 成功返回 true 失败 返回false
     */
    public static boolean createNewFile(String path)
    {
        File file = createFile(path);
        try
        {
            return file.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 文件夹是否存在
     * @param path 路径
     * @return true 存在 false 不存在
     */
    public static boolean isDirectory(final String path)
    {
        File file = createFile(path);
        return !file.equals(null) && file.isDirectory();
    }

    /**
     * 存在文件
     * @param path 路径
     * @return true 存在 false 不存在
     */
    public static boolean isFile(final String path)
    {
        File file = createFile(path);
        return !file.equals(null) && file.isFile();
    }


    public static File appendFile(File file, String ... paths)
    {
        if (null != file)
        {
            for (String fileString : paths)
            {
                file = appendFile(file, fileString);
            }
            return file;
        } else {
            return null;
        }
    }

    /**
     * 在文件后追加文件
     * @param file 文件
     * @return 一个新创建的文件
     */
    public static File appendFile(File file, String path)
    {
        if (null != file)
        {
            return  createFile(file.getPath() + File.separator + path);
        } else {
            return null;
        }
    }

    public static String[] getFileNameExtensName(String fileName)
    {
        String[] restlt = {null, null};
        restlt[0] = getFileNameNoEx(fileName);
        restlt[1] = getExtensionName(fileName);
        return restlt;
    }

    /**
     * 根据文件名 返回 扩展名
     * @param filename 文件名
     * @return 扩展名
     */
    public static String getExtensionName(String filename)
    {
        int dot = getDot(filename);
        if (dot == -1)
        {
            return null;
        } else {
            if ((dot > -1) && (dot < (filename.length() - 1)))
            {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 根据文件名 返回 文件名
     * @param filename 文件名
     * @return 文件名
     */
    public static String getFileNameNoEx(String filename)
    {
        int dot = getDot(filename);
        if (dot == -1)
        {
            return null;
        } else {
            if ((dot > -1) && (dot < (filename.length())))
            {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 用点分割
     * @param fileName 文件名
     * @return 不成功返回 -1
     */
    public static int getDot(String fileName)
    {
        if ((fileName != null) && (fileName.length() > 0))
        {
            return fileName.lastIndexOf('.');
        } else {
            return -1;
        }
    }


    public static boolean mkdir(String path)
    {
        try
        {
            if (!path.endsWith(File.separator))
                path = path + File.separator;

            File file = createFile(path);
            boolean isExists = file.exists();
            if (!isExists)
            {
                isExists = file.mkdir();
                if (isExists)
                {
                    return true;
                } else
                {
                    return false;
                }
            } else
            {
                return false;
            }
        } catch (Exception err)
        {
            Log.e("error",err.toString());
            return false;
        }
    }
}

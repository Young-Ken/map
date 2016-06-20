package com.caobugs.gis.tile.downtile.httputil;

import android.util.Log;

import com.caobugs.gis.util.TAG;
import com.caobugs.gis.util.constants.ConstantFile;
import com.caobugs.gis.util.file.ToolMapCache;
import com.caobugs.gis.util.file.ToolStorage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/12
 */
/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/12
 */
public class TileDownloader
{
    public static final int DEFAULT_CONNECT_TIMEOUT = 15 * 1000;
    public static final int DEFAULT_READ_TIMEOUT = 30 * 1000;
    public static final int DEFAULT_BUFFER_SIZE = 32 * 1024;
    private String requestMethod = "POST";
    private HttpURLConnection connection = null;
    private URL url = null;
    public HttpURLConnection createConnection(String path)
    {

        HttpURLConnection conn = null;
        try
        {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
            conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return conn;
    }

    public boolean getStream(String path, String tileType, int level, int col, int row)
    {
        boolean restult = false;
        connection = createConnection(path);
        InputStream inputStream = null;
        if (connection != null)
        {
            try
            {
                if((connection.getResponseCode() == 404))
                {
                    Log.e(TAG.DOWNTILESERVER, connection.getResponseCode() + level + "  level " + col + "  col  " + row + "  row");
                    restult = false;
                } else if (connection.getResponseCode() == 200)
                {
                    inputStream = connection.getInputStream();
                    byte[] bytes = getBytes(inputStream);
                    saveByte(bytes, tileType, level, col, row);
                    restult = true;
                }else {
                    Log.e(TAG.DOWNTILESERVER, connection.getResponseCode() + level + "  level " + col + "  col  " + row + "  row");
                    Log.e(TAG.DOWNTILESERVER, path+"");
                    restult = false;
                }

            } catch (Exception e)
            {
                Log.e(TAG.DOWNTILESERVER, e.toString() + "  " + level + "  level " + col + "  col  " + row + "  row");
                Log.e(TAG.DOWNTILESERVER, path+"");
                e.printStackTrace();
                restult =  false;
            } finally
            {
                connection.disconnect();
                try
                {
                    if (inputStream != null)
                    {
                        inputStream.close();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return restult;
        }
        return restult;
    }

    public byte[] getBytes(InputStream is)
    {
        int len;
        int size = 1024;
        byte[] buf = null;
        if (is instanceof ByteArrayInputStream)
        {
            try
            {
                size = is.available();
                buf = new byte[size];
                is.read(buf, 0, size);
            } catch (IOException e)
            {
                e.printStackTrace();
            }finally
            {
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        } else
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];
            try
            {
                while ((len = is.read(buf, 0, size)) != -1)
                {
                    bos.write(buf, 0, len);
                }
                buf = bos.toByteArray();
            } catch (IOException e)
            {
                e.printStackTrace();
            } finally
            {
                try
                {
                    bos.close();
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return buf;
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
    public boolean saveByte(final byte[] bytes, final String tileType, final int level, final int col, int row)
    {
        File file = new File(ToolMapCache.getMapCachePath(tileType, level, col, row));

        if (file.getPath().equals(""))
        {
            return false;
        }

        File parentFile = new File(file.getParent());

        if (parentFile.exists())
        {
            return !file.isDirectory() && writeToBytes(bytes, file);
        } else
        {
            return parentFile.mkdirs() && writeToBytes(bytes, file);
        }
    }

    public boolean writeToBytes(byte bytes[], File file)
    {
        return writeToBytes(bytes, file.getPath());
    }

    public boolean writeToBytes(byte bytes[], String fileName)
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
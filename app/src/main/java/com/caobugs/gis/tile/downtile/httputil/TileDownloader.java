package com.caobugs.gis.tile.downtile.httputil;

import android.util.Log;

import com.caobugs.gis.util.TAG;
import com.caobugs.gis.util.file.ToolMapCache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    public static final int DEFAULT_CONNECT_TIMEOUT = 5 * 1000;
    public static final int DEFAULT_READ_TIMEOUT = 20 * 1000;
    public static final int DEFAULT_BUFFER_SIZE = 32 * 1024;
    private String requestMethod = "POST";

    public HttpURLConnection createConnection(String path)
    {
        URL url = null;
        HttpURLConnection conn = null;
        try
        {
            url = new URL(path);
            if (!url.getPath().equals(""))
            {
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod(requestMethod);
                conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
                conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return conn;
    }

    public boolean getStream(String path, String tileType, int level, int col, int row)
    {
        HttpURLConnection connection = createConnection(path);
        InputStream inputStream = null;
        if (connection != null)
        {
            try
            {
                if((connection.getResponseCode() == 404))
                {
                    Log.e(TAG.DOWNTILESERVER, "404" + level + "  level " + col + "  col  " + row + "  row");
                    return false;
                }
                inputStream = connection.getInputStream();
                byte[] bytes = getBytes(inputStream);
                ToolMapCache.saveByte(bytes, tileType, level, col, row);
                //Log.d(TAG.DOWNTILESERVER, "  " + level + "  level " + col + "  col  " + row + "  row   " + bytes.length);
            } catch (Exception e)
            {
                e.printStackTrace();
                Log.e(TAG.DOWNTILESERVER, e.toString() + "  " + level + "  level " + col + "  col  " + row + "  row");
                return false;
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
        }
        return true;
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
}
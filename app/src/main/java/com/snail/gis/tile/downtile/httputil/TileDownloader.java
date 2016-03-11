package com.snail.gis.tile.downtile.httputil;

import android.util.Log;

import com.snail.gis.tool.file.ToolMapCache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
    public HttpURLConnection createConnection(String path)
    {
        URL url = null;
        HttpURLConnection conn = null;
        try
        {
            url = new URL(path);
            if (url != null)
            {
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
                conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
            }
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return conn;
    }

    public void getStream(String path, String tileType, int level, int col, int row)
    {
        HttpURLConnection connection = createConnection(path);
        InputStream inputStream = null;
        if (connection != null)
        {
            try
            {
                if((connection.getResponseCode() == 404))
                {
                    Log.e("404", "404" + level + "  level " + col + "  col  " + row + "  row");
                    return;
                }
                inputStream = connection.getInputStream();
                byte[] bytes = getBytes(inputStream);
                ToolMapCache.saveByte(bytes, tileType, level, col, row);

                Log.e("SAVETILE", level+ "  level "+col+"  col  "+row+"  row" + bytes.length+"  bytes.length  ");
            } catch (Exception e)
            {
                e.printStackTrace();
                Log.e("SAVETILE", e.toString() + "  " + level + "  level " + col + "  col  " + row + "  row");
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

                return;
            }

        }
    }

    public byte[] getBytes(InputStream is) throws IOException
    {

        int len;
        int size = 1024;
        byte[] buf;

        if (is instanceof ByteArrayInputStream) {
            size = is.available();
            buf = new byte[size];
            len = is.read(buf, 0, size);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];
            while ((len = is.read(buf, 0, size)) != -1)
                bos.write(buf, 0, len);
            buf = bos.toByteArray();
            bos.close();
        }
        return buf;
    }
}
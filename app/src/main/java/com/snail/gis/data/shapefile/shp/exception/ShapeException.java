package com.snail.gis.data.shapefile.shp.exception;

import java.io.IOException;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/5
 */
public class ShapeException extends Exception
{
    public static enum TYPE
    {
        BYTE_IS_NULL,
        MAGIC_IS_ERROR,
        VERSION_IS_ERROR,
        FILE_IS_NULL;

        private TYPE()
        {

        }
    }
    public static IOException throwException(TYPE type)
    {
       return throwException(type, "");
    }

    public static IOException throwException(TYPE type, String errorString)
    {
        StringBuffer error = new StringBuffer("ShapeFile 异常，");
        switch (type)
        {
            case BYTE_IS_NULL:
                error.append("byte 为空。");
                break;
            case MAGIC_IS_ERROR:
                error.append("文件不对。");
                break;
            case VERSION_IS_ERROR:
                error.append("文件版本不对");
                break;
            case FILE_IS_NULL:
                error.append("文件为空");
                break;
            default:
                error.append("未知异常。");
                break;
        }
        return new IOException(error.append(errorString).toString());
    }
}

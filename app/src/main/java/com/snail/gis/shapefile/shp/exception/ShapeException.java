package com.snail.gis.shapefile.shp.exception;

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
        StringBuffer error = new StringBuffer("ShapeFile �쳣��");
        switch (type)
        {
            case BYTE_IS_NULL:
                error.append("byte Ϊ�ա�");
                break;
            case MAGIC_IS_ERROR:
                error.append("�ļ����ԡ�");
                break;
            case VERSION_IS_ERROR:
                error.append("�ļ��汾����");
                break;
            case FILE_IS_NULL:
                error.append("�ļ�Ϊ��");
                break;
            default:
                error.append("δ֪�쳣��");
                break;
        }
        return new IOException(error.append(errorString).toString());
    }
}

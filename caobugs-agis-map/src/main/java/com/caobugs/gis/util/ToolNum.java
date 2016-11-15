package com.caobugs.gis.util;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/14
 */
public class ToolNum
{
    public static double parseDouble(String string)
    {
        if(string == null)
        {
            return Double.NaN;
        }
        return Double.parseDouble(string);
    }
}

package com.caobugs.gis.enumeration;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/24
 */
public class Location
{
    /**
     * 内部关系
     */
    public final static int INTERIOR = 0;

    /**
     * 边关系
     */
    public final static int BOUNDARY = 1;

    /**
     * 外部关系
     */
    public final static int EXTERIOR = 2;

    /**
     *  没有关系
     */
    public final static int NONE = -1;

    /**
     *
     *@param  locationValue   EXTERIOR, BOUNDARY, INTERIOR, NONE
     *@return char e,b,i,-
     */
    public static char toLocationSymbol(int locationValue) {
        switch (locationValue) {
            case EXTERIOR:
                return 'e';
            case BOUNDARY:
                return 'b';
            case INTERIOR:
                return 'i';
            case NONE:
                return '-';
        }
        throw new IllegalArgumentException("未知类型: " + locationValue);
    }
}

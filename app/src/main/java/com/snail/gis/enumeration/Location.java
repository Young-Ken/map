package com.snail.gis.enumeration;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/24
 */
public class Location
{
    public final static int INTERIOR = 0;
    /**
     * The location value for the boundary of a geometry.
     * Also, DE-9IM row index of the boundary of the first geometry and column index of
     *  the boundary of the second geometry.
     */
    public final static int BOUNDARY = 1;
    /**
     * The location value for the exterior of a geometry.
     * Also, DE-9IM row index of the exterior of the first geometry and column index of
     *  the exterior of the second geometry.
     */
    public final static int EXTERIOR = 2;

    /**
     *  Used for uninitialized location values.
     */
    public final static int NONE = -1;

    /**
     *  Converts the location value to a location symbol, for example, <code>EXTERIOR => 'e'</code>
     *  .
     *
     *@param  locationValue  either EXTERIOR, BOUNDARY, INTERIOR or NONE
     *@return                either 'e', 'b', 'i' or '-'
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
        throw new IllegalArgumentException("Unknown location value: " + locationValue);
    }
}

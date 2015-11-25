package com.snail.gis.geometry.util;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.LinearRing;
import com.snail.gis.geometry.Polygon;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/23
 */
public class ContainsUtil
{
    public static boolean containsPointInPolygon(Coordinate p, Polygon poly)
    {
        if (poly.isEmpty()) return false;
        LinearRing shell = (LinearRing) poly.getExteriorRing();
        if (! isPointInRing(p, shell)) return false;
        // now test if the point lies in or on the holes
        for (int i = 0; i < poly.getNumInteriorRing(); i++)
        {
          //  LinearRing hole = (LinearRing) poly.getInteriorRingN(i);
         //   if (isPointInRing(p, hole)) return false;
        }
        return true;
    }
    private static boolean isPointInRing(Coordinate p, LinearRing ring)
    {
        if (! ring.getEnvelope().intersects(p))
            return false;
       // return CGAlgorithms.isPointInRing(p, ring.getCoordinates());
        return false;
    }
}

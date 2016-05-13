package com.caobugs.gis.tool;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.LinearRing;
import com.caobugs.gis.geometry.Polygon;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.view.map.BaseMap;
import com.caobugs.gis.view.map.util.Projection;

import java.util.List;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/3
 */
public class GeomToString
{
    private static String kindPolygon = "POLYGON";
    private static String startStr = "((";
    private static String endStr = "))";
    private static String spaceStr = " ";
    private static String spaceAndCommaStr = ", ";

    // POLYGON((MINX MINY, MAXX MINY, MAXX MAXY, MINX MAXY, MINX MINY))
    public static String geomToString(Envelope envelope)
    {
        StringBuffer str = new StringBuffer();
        str.append(kindPolygon);
        str.append(startStr);

        str.append(envelope.getMinX());
        str.append(spaceStr);
        str.append(envelope.getMinY());
        str.append(spaceAndCommaStr);

        str.append(envelope.getMaxX());
        str.append(spaceStr);
        str.append(envelope.getMinY());
        str.append(spaceAndCommaStr);

        str.append(envelope.getMaxX());
        str.append(spaceStr);
        str.append(envelope.getMaxY());
        str.append(spaceAndCommaStr);

        str.append(envelope.getMinX());
        str.append(spaceStr);
        str.append(envelope.getMaxY());
        str.append(spaceAndCommaStr);

        str.append(envelope.getMinX());
        str.append(spaceStr);
        str.append(envelope.getMinY());

        str.append(endStr);

        return str.toString();
    }

    public static String geomToStringWEB(Envelope envelope, BaseMap map)
    {
        List<Coordinate> lists = envelope.getLines();

        Envelope tempE = new Envelope();

        for (Coordinate coordinate : lists)
        {
            Coordinate tempC = Projection.getInstance(map).imageMercatorTransToLonLat(coordinate.x, coordinate.y);
            tempE.expandToInclude(tempC);
        }
       return geomToString(tempE);
    }


    public static String polygonToString(LinearRing linearRing)
    {
        StringBuffer str = new StringBuffer();
        str.append(kindPolygon);
        str.append(startStr);

        for(Coordinate coordinate : linearRing.getCoordinates())
        {
            str.append(coordinate.x);
            str.append(spaceStr);
            str.append(coordinate.y);
            str.append(spaceAndCommaStr);
        }

        str.append(linearRing.getCoordinates()[0].x);
        str.append(spaceStr);
        str.append(linearRing.getCoordinates()[0].y);

        str.append(endStr);

        return str.toString();
    }
}

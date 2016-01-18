package com.snail.gis.map;

import com.snail.gis.geometry.Coordinate;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/6
 */
public interface IMapController
{
    boolean mapScroll(double x, double y);
    boolean zoomIn();
    boolean zoomOut();
    boolean zoomTo(Coordinate coordinate, int level);
    boolean refresh();
}

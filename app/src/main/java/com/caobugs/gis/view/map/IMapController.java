package com.caobugs.gis.view.map;

import com.caobugs.gis.geometry.Coordinate;

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
    boolean zoomTo(Coordinate point, int level);
    boolean zoom(MapStatus.Defualt mapStatus);
    void refresh();
}

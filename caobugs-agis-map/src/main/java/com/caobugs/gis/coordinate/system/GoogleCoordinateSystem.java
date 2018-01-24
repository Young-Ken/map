package com.caobugs.gis.coordinate.system;

import com.caobugs.gis.coordinate.BaseCoordinateSystem;
import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.map.model.LatLng;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/17
 */
public class GoogleCoordinateSystem extends BaseCoordinateSystem
{
    public GoogleCoordinateSystem()
    {
        super(new Coordinate(-20037508.342787, 20037508.342787), new LatLng(85.05112878, -180),
                591657527.591555, 156543.033928023, 21, 96, 256, 256);
    }
}

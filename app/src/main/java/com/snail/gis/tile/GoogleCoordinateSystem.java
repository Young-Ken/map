package com.snail.gis.tile;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.Point;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/17
 */
public class GoogleCoordinateSystem extends BaseCoordinateSystem
{
    public GoogleCoordinateSystem()
    {
        super(new Coordinate(-20037508.342787, 20037508.342787),
                591657527.591555, 156543.033928023, 21, 96, 256, 256);
    }
}

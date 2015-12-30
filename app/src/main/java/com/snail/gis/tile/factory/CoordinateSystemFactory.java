package com.snail.gis.tile.factory;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.tile.CoordinateSystem;
import com.snail.gis.tile.GoogleCoordinateSystem;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/29
 */
public class CoordinateSystemFactory extends ACoordinateSystemFactory
{
    @Override
    public CoordinateSystem createCoordinateSystem(final ICoordinateSystemEnum coordinateSystemEnum)
    {
        if (coordinateSystemEnum instanceof GoogleCoordinateSystem)
        {
            return new GoogleCoordinateSystem();
        }
        return null;
    }
}

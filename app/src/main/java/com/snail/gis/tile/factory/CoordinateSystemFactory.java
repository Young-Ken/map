package com.snail.gis.tile.factory;

import com.snail.gis.tile.CoordinateSystem;
import com.snail.gis.tile.GoogleCoordinateSystem;
import com.snail.gis.tile.LYGCoordinateSystem;

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
        if (coordinateSystemEnum.toString().equals(CoordinateSystemEnum.GOOGLE_CS.toString()))
        {
            return new GoogleCoordinateSystem();
        } else if(coordinateSystemEnum.toString().equals(CoordinateSystemEnum.LYG_HH_TILE.toString()))
        {
            return new LYGCoordinateSystem();
        }
        return null;
    }
}

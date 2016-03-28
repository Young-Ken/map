package com.caobugs.gis.tile.factory;

import com.caobugs.gis.tile.CoordinateSystem;
import com.caobugs.gis.tile.GoogleCoordinateSystem;
import com.caobugs.gis.tile.LYGCoordinateSystem;
import com.caobugs.gis.tile.OpenStreeCoordinateSystem;

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
        } else if(coordinateSystemEnum.toString().equals(CoordinateSystemEnum.OPEN_STREET_MAP.toString()))
        {
            return new OpenStreeCoordinateSystem();
        }
        return null;
    }
}

package com.snail.gis.tile.factory;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/29
 */
public enum CoordinateSystemEnum implements ICoordinateSystemEnum
{

    GOOGLE_CS,
    LYG_HH_TILE;

    @Override
    public String getName()
    {
       return getName().toString();
    }
}

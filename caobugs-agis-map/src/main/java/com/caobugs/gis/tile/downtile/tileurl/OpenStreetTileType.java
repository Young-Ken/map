package com.caobugs.gis.tile.downtile.tileurl;

import com.caobugs.gis.tile.downtile.factory.IURLEnum;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/14
 */
public enum OpenStreetTileType implements IURLEnum
{
    TILE_OS,
    CYCLE_OS,
    TRANSPORT_OS,
    MAPQUEST_OS;

    @Override
    public String getName()
    {
        return this.name().toString();
    }
}

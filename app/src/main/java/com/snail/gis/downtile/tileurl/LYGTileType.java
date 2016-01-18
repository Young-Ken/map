package com.snail.gis.downtile.tileurl;

import com.snail.gis.downtile.factory.IURLEnum;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/14
 */
public enum LYGTileType implements IURLEnum
{
    LYG_HK_TILE;
    @Override
    public String getName()
    {
        return this.name().toString();
    }
}

package com.snail.gis.tile.downtile.factory;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/9/16
 */
public enum TiledLayerEnum implements IURLEnum
{
    /**
     * google 切片Layer
     */
    TILED_LAYER_GOOGLE,

    /**
     * 天地图切片Layer
     */
    TILED_LAYER_TDT;

    @Override
    public String getName()
    {
        return getName().toString();
    }

}

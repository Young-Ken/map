package com.snail.gis.tile;

import com.snail.gis.geometry.Coordinate;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/17
 */
public interface CoordinateSystem
{
    /**
     * 初始化tile信息
     */
    void initTileInfo();

    /**
     * 得到Tile的方法
     * @return TileInfo
     */
    TileInfo getTileInfo();
}

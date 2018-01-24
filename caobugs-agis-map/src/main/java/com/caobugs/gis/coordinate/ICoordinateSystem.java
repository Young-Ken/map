package com.caobugs.gis.coordinate;

import com.caobugs.gis.tile.TileInfo;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/17
 */
public interface ICoordinateSystem
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

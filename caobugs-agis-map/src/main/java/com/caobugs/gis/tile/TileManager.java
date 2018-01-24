/*
 * Copyright (C) 2018 FarmFriend Co., Ltd. All rights reserved.
 */
package com.caobugs.gis.tile;

import com.caobugs.gis.coordinate.CoordinateSystemManager;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.map.BaseMap;
import com.caobugs.gis.map.MapInfo;

/**
 * @author Young Ken.
 */
public class TileManager {

    private BaseMap mMap;
    private MapInfo mMapInfo;
    private TileInfo mTileInfo;

    public TileManager(BaseMap map) {
        mMap = map;
        mMapInfo = map.getMapInfo();
        mTileInfo = CoordinateSystemManager.getInstance().getCoordinateSystem().getTileInfo();
    }

    public int[] getCurrentScreenTiles() {








        Envelope currentEnvelope = mMapInfo.getCurrentEnvelope();
        int minTileNumX = getTileNum(currentEnvelope.getMinX() + Math.abs(mTileInfo.getOriginPoint().x));
        int minTileNumY = getTileNum(Math.abs(mTileInfo.getOriginPoint().y) - currentEnvelope.getMinY());
        int maxTileNumX = getTileNum(currentEnvelope.getMaxX() + + Math.abs(mTileInfo.getOriginPoint().x));
        int maxTileNumY = getTileNum(Math.abs(mTileInfo.getOriginPoint().y) - currentEnvelope.getMaxY());
        int result[] = {minTileNumX, minTileNumY, maxTileNumX, maxTileNumY};
        return result;
    }

    private int  getTileNum(double num) {
        return (int) (num / mMapInfo.getCurrentResolution() / mTileInfo.getTileWidth());
    }
}

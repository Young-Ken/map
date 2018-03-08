/*
 * Copyright (C) 2018 FarmFriend Co., Ltd. All rights reserved.
 */
package com.caobugs.map.layer;

import android.graphics.Canvas;

import com.caobugs.map.coordinate.projection.MercatorProjection;
import com.caobugs.map.layer.tile.downtile.DownTile;
import com.caobugs.map.layer.tile.downtile.httputil.TileDownloader;
import com.caobugs.map.layer.tile.downtile.tileurl.GoogleTiledTypes;
import com.caobugs.map.layer.tile.downtile.tileurl.GoogleURL;
import com.caobugs.map.model.api.ILatLongBox;

/**
 * @author Young Ken
 * @since 2018-02
 */
public class TileLayer extends BaseLayer {

    @Override
    public void draw(ILatLongBox box, int level, Canvas canvas) {

        int tileLeft = MercatorProjection.longitudeToTileX(box.getMinLongitude(), (byte) level);
        int tileTop = MercatorProjection.latitudeToTileY(box.getMaxLatitude(), (byte) level);
        int tileRight = MercatorProjection.longitudeToTileX(box.getMaxLongitude(), (byte) level);
        int tileBottom = MercatorProjection.latitudeToTileY(box.getMinLatitude(), (byte) level);

        DownTile downTile = new DownTile(new GoogleURL(GoogleTiledTypes.GOOGLE_IMAGE), level, level);
        try {
            downTile.downTile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setVisible() {

    }
}

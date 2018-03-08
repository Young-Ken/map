/*
 * Copyright (C) 2018 FarmFriend Co., Ltd. All rights reserved.
 */
package com.caobugs.map.opration;

import com.caobugs.map.coordinate.projection.MercatorProjection;
import com.caobugs.map.model.api.ILatLong;
import com.caobugs.map.model.api.IMapPosition;
import com.caobugs.map.opration.api.IOperation;

/**
 * @author Young Ken
 * @since 2018-01
 */
public class Operation implements IOperation {

    private Object synObject = new Object();
    private IMapPosition mMapPosition;

    public Operation(IMapPosition mapPosition) {

        if (mapPosition == null) {
            throw new IllegalArgumentException("mapPosition不能为空");
        }
        mMapPosition = mapPosition;
    }
    @Override
    public void setCenter(ILatLong center) {

        mMapPosition.setCenter(center);
    }

    @Override
    public void moveCenter(double distanceX, double distanceY) {
        synchronized (synObject) {
            long mapSize = MercatorProjection.getMapSize((byte) mMapPosition.getLevel(), MercatorProjection.getTitleSize());
            double currentY = MercatorProjection.latitudeToPixelY(
                    getMapPosition().getCenter().getLatitude(), mapSize);
            double currentX = MercatorProjection.longitudeToPixelX(
                    getMapPosition().getCenter().getLongitude(), mapSize);
            ILatLong latLong = MercatorProjection.fromPixels(currentX + distanceX, currentY + distanceY, mapSize);
            setCenter(latLong);
        }
    }

    @Override
    public void zoom(int level) {

        mMapPosition.setLevel(level);
    }

    /**
     * TODO 不能超过最大级别
     */
    @Override
    public void zoomOut() {

        mMapPosition.setLevel(mMapPosition.getLevel() + 1);
    }

    @Override
    public void zoomIn() {

        if (mMapPosition.getLevel() != 0) {
            mMapPosition.setLevel(mMapPosition.getLevel() - 1);
        }
    }

    @Override
    public IMapPosition getMapPosition() {
        return mMapPosition;
    }
}

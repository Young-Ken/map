/*
 * Copyright (C) 2018 FarmFriend Co., Ltd. All rights reserved.
 */
package com.caobugs.map.opration.api;

import android.graphics.Point;

import com.caobugs.map.model.api.ILatLong;
import com.caobugs.map.model.api.IMapPosition;

/**
 * @author Young Ken
 * @since 2018-01
 */
public interface IOperation {

    void setCenter(ILatLong center);
    void moveCenter(double distanceX, double distanceY);
    void zoom(int level);
    void zoomOut();
    void zoomIn();

    /**
     * 存储地图的中心点和当前的级别
     * @return IMapPosition
     */
    IMapPosition getMapPosition();
}

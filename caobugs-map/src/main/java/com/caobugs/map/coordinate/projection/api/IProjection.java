/*
 * Copyright (C) 2018 FarmFriend Co., Ltd. All rights reserved.
 */
package com.caobugs.map.coordinate.projection.api;

import android.graphics.Point;

import com.caobugs.map.model.api.ILatLong;

/**
 * @author Young Ken
 * @since 2018-01
 */
public interface IProjection {

    Point toPixels(ILatLong latLong);
    ILatLong fromPixels(Point point);
}

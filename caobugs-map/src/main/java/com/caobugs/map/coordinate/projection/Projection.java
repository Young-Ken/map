/*
 * Copyright (C) 2018 FarmFriend Co., Ltd. All rights reserved.
 */
package com.caobugs.map.coordinate.projection;

import android.graphics.Point;

import com.caobugs.map.coordinate.projection.api.IProjection;
import com.caobugs.map.model.api.ILatLong;

/**
 * @author Young Ken
 * @since 2018-01
 */
public class Projection implements IProjection {

    @Override
    public Point toPixels(ILatLong latLong) {
        return null;
    }

    @Override
    public ILatLong fromPixels(Point point) {
        return null;
    }
}

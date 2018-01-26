/*
 * Copyright (C) 2018 FarmFriend Co., Ltd. All rights reserved.
 */
package com.caobugs.map.opration.api;

import com.caobugs.map.model.api.ILatLong;

/**
 * @author Young Ken
 * @since 2018-01
 */
public interface IOperation {

    void setCenter(ILatLong center);
    void zoom(float level);
    void zoomOut();
    void zoomIn();
}

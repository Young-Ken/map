package com.caobugs.map.layer.api;

import android.graphics.Canvas;

import com.caobugs.map.model.api.ILatLongBox;

/**
 * @author Young Ken
 * @since 2018-01
 */
public interface ILayer {

    void draw(ILatLongBox box, float level, Canvas canvas);
    void setVisible();
}

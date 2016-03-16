package com.snail.gis.view.map;
import android.graphics.Canvas;

import com.snail.gis.geometry.Coordinate;

import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.view.layer.BaseLayer;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/18
 */
public interface IBaseMap
{
    boolean addLayer(BaseLayer layer);
    boolean removeLayer(BaseLayer layer);
    double getMapHeight();
    double getMapWidth();

    Envelope getFullMap();
    Coordinate getMapCenter();
    Envelope getEnvelope();

    void setMapCenter(Coordinate center);
    double getResolution();
    double getScale();
    int getLevel();

    void refresh();
}

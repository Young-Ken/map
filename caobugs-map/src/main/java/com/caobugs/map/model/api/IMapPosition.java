package com.caobugs.map.model.api;

/**
 * @author Young Ken
 * @since 2018-01
 */
public interface IMapPosition {

    float getLevel();
    ILatLong getCenter();
    void setLevel(float level);
    void setCenter(ILatLong center);
}

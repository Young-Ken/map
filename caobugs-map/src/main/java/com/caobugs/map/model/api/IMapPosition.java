package com.caobugs.map.model.api;

/**
 * @author Young Ken
 * @since 2018-01
 */
public interface IMapPosition {

    int getLevel();
    ILatLong getCenter();
    void setLevel(int level);
    void setCenter(ILatLong center);
}

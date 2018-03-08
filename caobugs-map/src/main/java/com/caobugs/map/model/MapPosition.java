package com.caobugs.map.model;

import com.caobugs.map.model.api.ILatLong;
import com.caobugs.map.model.api.IMapPosition;

/**
 * @author Young Ken
 * @since 2018-01
 */
public class MapPosition implements IMapPosition {

    private int mLevel;
    private ILatLong mCenter;

    public MapPosition(int level, ILatLong center) {

        setLevel(level);
        setCenter(center);
    }

    @Override
    public int getLevel() {
        return mLevel;
    }

    @Override
    public ILatLong getCenter() {
        return mCenter;
    }

    @Override
    public void setLevel(final int level) {
        if (level < 0) {
            throw new IllegalArgumentException("level不能小于零");
        }
        mLevel = level;
    }

    @Override
    public void setCenter(ILatLong center) {
        if (center == null) {
            throw new IllegalArgumentException("center不能为空");
        }
        mCenter = center;
    }
}

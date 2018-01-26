package com.caobugs.map.model;

import com.caobugs.map.model.api.ILatLong;

/**
 * @author Young Ken
 * @since 2018-01
 */
public class LatLong implements ILatLong {

    private double mLatitude;
    private double mLongitude;

    public LatLong(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    @Override
    public double getLatitude() {
        return mLatitude;
    }

    @Override
    public double getLongitude() {
        return mLongitude;
    }

    @Override
    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    @Override
    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }
}

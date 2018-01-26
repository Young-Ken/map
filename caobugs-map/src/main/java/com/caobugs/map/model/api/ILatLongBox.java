package com.caobugs.map.model.api;

/**
 * @author Young Ken
 * @since 2018-01
 */
public interface ILatLongBox {

    double getMinLongitude();
    double getMaxLongitude();
    double getMinLatitude();
    double getMaxLatitude();
    /**
     * 包含关系
     * @param another 另一个ILatLongBox
     * @return 包含返回true，要不然返回false
     */
    boolean contains(ILatLongBox another);
    boolean contains(ILatLong latLong);
    /**
     * 相交关系
     * @param another 另一个ILatLongBox
     * @return 包含返回true，要不然返回false
     */
    boolean intersects(ILatLongBox another);
    boolean intersects(ILatLong latLong);

    void expandToInclude(ILatLong latLong);
    void translate(final double longitude, final double latitude);
    ILatLong getCenter();
    boolean isEmpty();
}

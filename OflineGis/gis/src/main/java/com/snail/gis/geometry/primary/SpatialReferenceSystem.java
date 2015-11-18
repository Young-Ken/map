package com.snail.gis.geometry.primary;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public interface SpatialReferenceSystem
{
    /**
     * 相等 判断两个Geometry 是不是相等
     * @param geometry geometry
     * @return 相等返回true 不相等返回false
     */
    boolean equals(Geometry geometry);


    /**
     * 不相交 判断两个Geometry 是不是不相交
     * @param geometry geometry
     * @return 不相交返回 true
     */
    //boolean disjoint(Geometry geometry);

    /**
     * 相交 判断两个Geometry 是不是不相交
     * @param geometry geometry
     * @return 相交返回 true
     */
    //boolean intersects(Geometry geometry);
    //boolean touches(Geometry geometry);
    //boolean crosses(Geometry geometry);

    /**
     * a.Contains(b) ⇔ b.Within(a)
     * @param geometry
     * @return
     */
    //boolean within(Geometry geometry);

    /**
     * 包含 判断两个Geometry 是不是包含关系
     * @param geometry geometry
     * @return 如果包含 返回 true
     */
    //boolean contains(Geometry geometry);
    //boolean overlaps(Geometry geometry);
    //boolean relate(Geometry geometry);
}

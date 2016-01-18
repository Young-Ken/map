package com.snail.gis.downtile.tileurl;


import com.snail.gis.downtile.factory.IURLEnum;

/**
 * 把行列号转换成url
 * @author Young Ken
 * @since 2015/8/26.
 */
public interface BaseTiledURL
{
    /**
     * 根据级别，行，列组装URL
     * @param level 地图当前级别
     * @param col 行
     * @param row 列
     * @return url 字符
     */
    String getTiledServiceURL(int level, int col, int row);

    /**
     * 当前服务枚举
     * @return 当前服务枚举
     */
    IURLEnum getMapServiceType();
}

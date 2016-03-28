package com.caobugs.gis.data;

import com.caobugs.gis.geometry.primary.Geometry;
import com.caobugs.gis.style.symbol.Symbol;

import java.util.Map;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/2
 */
public interface Feature
{
    Object getAttributeValue(String columnName);

    Map<String, Object> getAttributes();

    long getId();
    /**
     * 取得当前的Geometry
     * @return Geometry
     */
    Geometry getGeometry();

    /**
     * 取得当前的Symbol
     * @return Symbol
     */
    Symbol getSymbol();
}

package com.caobugs.gis.data;

import com.caobugs.gis.geometry.primary.Geometry;
import com.caobugs.gis.style.symbol.Symbol;

import java.util.Map;

/**
 *
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/3
 */
public class Graphic implements Feature
{
    private Geometry geometry = null;
    private Symbol symbol = null;
    private Map<String, Object> attributes = null;
    private long id = -1;
    public Graphic(Geometry geometry, Symbol symbol)
    {
        this.geometry = geometry;
        this.symbol = symbol;
    }

    public Graphic(Geometry geometry, Symbol symbol, Map<String, Object> attributes)
    {
        this(geometry, symbol);
        this.attributes = attributes;
    }


    @Override
    public Object getAttributeValue(String columnName)
    {
        return getAttributes().get(columnName);
    }

    @Override
    public Map<String, Object> getAttributes()
    {
        return attributes;
    }

    @Override
    public long getId()
    {
        return 0;
    }

    @Override
    public Geometry getGeometry()
    {
        return geometry;
    }

    @Override
    public Symbol getSymbol()
    {
        return symbol;
    }
}

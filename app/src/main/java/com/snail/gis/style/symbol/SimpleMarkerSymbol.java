package com.snail.gis.style.symbol;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/2
 */
public class SimpleMarkerSymbol extends MarkerSymbol
{
    private int color = Color.argb(255, 255, 0, 0);
    private float size;
    private Paint.Style style;

    public void setColor(int color)
    {
        this.color = color;
    }

    public void setSize(float size)
    {
        this.size = size;
    }

    public void setStyle(Paint.Style style)
    {
        this.style = style;
    }

    public float getSize()
    {
        return size;
    }

    public int getColor()
    {
        return color;
    }

    public Paint.Style getStyle()
    {
        return style;
    }

    public SimpleMarkerSymbol(int color, int size, Paint.Style style)
    {
        this.setColor(color);
        this.setSize((float)size);
        this.setStyle(style);
    }

    public SimpleMarkerSymbol(SimpleMarkerSymbol symbol) throws Exception
    {
        this.setColor(symbol.getColor());
        this.setSize(symbol.getSize());
        this.setStyle(symbol.getStyle());
    }

}

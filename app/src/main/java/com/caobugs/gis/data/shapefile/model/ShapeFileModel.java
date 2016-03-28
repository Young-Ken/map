package com.caobugs.gis.data.shapefile.model;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/10/10
 */
public class ShapeFileModel
{
    private int color = 0;
    private int size = 0;
    private String type = null;
    private String src = null;
    private String symbolType = null;
    private int maxScale = -1;
    private int minScale = -1;

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public String getSrc()
    {
        return src;
    }

    public void setSrc(String src)
    {
        this.src = src;
    }

    public String getSymbolType()
    {
        return symbolType;
    }

    public void setSymbolType(String symbolType)
    {
        this.symbolType = symbolType;
    }

    public int getMaxScale()
    {
        return maxScale;
    }

    public void setMaxScale(int maxScale)
    {
        this.maxScale = maxScale;
    }

    public int getMinScale()
    {
        return minScale;
    }

    public void setMinScale(int minScale)
    {
        this.minScale = minScale;
    }
}

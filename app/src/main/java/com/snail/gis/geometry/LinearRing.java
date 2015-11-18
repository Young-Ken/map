package com.snail.gis.geometry;

import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LinearRing extends LineString
{
    public LinearRing()
    {
        super();
    }

    public LinearRing(List<Coordinate> list)
    {
        this();
        init(list);
    }

    public LinearRing(LineString lineString)
    {
        this();
        init(lineString);
    }

    private void init(LineString lineString)
    {
        for (Coordinate coordinate : lineString.list)
        {
            this.list.add(coordinate);
        }
    }

    private void init(List<Coordinate> list)
    {
        for(Coordinate coordinate : list)
        {
           this.list.add(coordinate);
        }
    }

    @Override
    public boolean isClosed()
    {
        return getPointNum() > 4;
    }


    @Override
    public boolean isEmpty()
    {
        return this.list.size() < 3;
    }

    @Override
    public int getPointNum()
    {
       return this.list.size();
    }
}

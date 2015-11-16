package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Curve;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LineString extends Curve
{

    private List<Coordinate> list = null;
    public LineString()
    {
        list = new ArrayList<>();
    }

    public LineString(Coordinate[] coordinates)
    {
        this();
        init(coordinates);
    }

    public LineString(LineString lineString)
    {
        this();
        init(lineString);
    }

    private void init(Coordinate[] coordinates)
    {
        for(Coordinate coordinate : coordinates)
        {
            list.add(coordinate);
        }
    }

    private void init(LineString lineString)
    {
        List<Coordinate> tempList = lineString.getList();
        for (Coordinate coordinate : tempList)
        {
            list.add(coordinate);
        }
    }

    public List<Coordinate> getList()
    {
        return list;
    }


    @Override
    public int getPointNum()
    {
        return list.size();
    }

    @Override
    public double getLength()
    {
        double result = 0.0;
        for (Coordinate coordinate : list)
        {
           // result  = result + MathUtil.distanceTwoPoint(coordinate)
        }
        return 0;
    }

    @Override
    public boolean isClose()
    {
        return false;
    }

    @Override
    public boolean isRing()
    {
        return false;
    }


    @Override
    public boolean equals(Geometry geometry)
    {
       return true;
    }

    @Override
    public Envelope getEnvelope()
    {
       return getEnvelope(list);
    }

    @Override
    public boolean isEmpty()
    {
        return (list.size() == 0);
    }
}

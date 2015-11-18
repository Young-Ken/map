package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Curve;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.math.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LineString extends Curve
{

    protected List<Coordinate> list = null;
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
        for (Coordinate coordinate : coordinates)
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

    /**
     * 得到线的长度
     * @return
     */
    @Override
    public double getLength()
    {
        double result = 0.0;
        int size = list.size();

        for (int i = 0; i < size-1; i++)
        {
            result = result + MathUtil.distanceTwoPoint(list.get(i),list.get(i+1));
        }

        return result;
    }

    /**
     * 得到第一个点
     * @return Coordinate
     */
    public Coordinate getStartPoint()
    {
        return getPoint(0);
    }

    /**
     * 得到最后一个点
     * @return Coordinate
     */
    public Coordinate getEndPoint()
    {
        return getPoint(getPointNum() - 1);
    }

    /**
     *  通过index得到LineString上的点，这里直接用list的get()方法
     * @param index 索引号
     * @return Coordinate
     */
    public Coordinate getPoint(int index)
    {
        return list.get(index);
    }

    @Override
    public boolean isClosed()
    {
        return !isEmpty() && getPoint(0).equals(getPoint(getPointNum() - 1));
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

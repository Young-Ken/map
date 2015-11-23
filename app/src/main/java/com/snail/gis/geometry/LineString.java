package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Curve;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.math.MathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LineString extends Curve
{

    /**
     * 点的集合，每个线都是点的集合
     */
    protected List<Coordinate> list = null;

    /**
     * 构造函数
     */
    public LineString()
    {
        list = new ArrayList<>();
    }

    /**
     * 构造函数
     * @param coordinates 点的数组
     */
    public LineString(Coordinate[] coordinates)
    {
        this();
        init(coordinates);
    }

    public LineString(List<Coordinate> coordinates)
    {
        this();
        init(coordinates);
    }

    /**
     * 构造函数
     * @param lineString 本身线对象
     */
    public LineString(LineString lineString)
    {
        this();
        init(lineString);
    }

    /**
     * 构造函数
     * @param coordinates Coordinate List 集合
     */
    private void init(List<Coordinate> coordinates)
    {
        list.addAll(coordinates);
    }
    /**
     * 初始化函数
     * @param coordinates Coordinate数组
     */
    private void init(Coordinate[] coordinates)
    {
        list =  Arrays.asList(coordinates);
    }

    /**
     * 初始化函数
     * @param lineString LineString
     */
    private void init(LineString lineString)
    {
        list.addAll(lineString.getList());
    }

    public List<Coordinate> getList()
    {
        return list;
    }


    /**
     * 返回线对象的点数
     * @return int
     */
    @Override
    public int getPointNum()
    {
        return getList().size();
    }

    /**
     * 得到线的长度
     * @return double
     */
    @Override
    public double getLength()
    {
        double result = 0.0;
        int size = getPointNum();
        for (int i = 0; i < size - 1; i++)
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
        return getList().get(index);
    }

    @Override
    public boolean isClosed()
    {
        return !isEmpty() && getPoint(0).equals(getPoint(getPointNum() - 1));
    }

    @Override
    public int getDimension()
    {
        return 1;
    }

    @Override
    public int getBoundaryDimension()
    {
        return 0;
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

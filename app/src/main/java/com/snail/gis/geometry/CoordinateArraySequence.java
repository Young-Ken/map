package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Envelope;

import java.io.Serializable;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/1
 */
public class CoordinateArraySequence implements CoordinateSequence, Serializable
{
    /**
     * 三个维度
     */
    private int dimension = 3;

    /**
     * 点集合
     */
    private Coordinate[] coordinates;

    /**
     * 构造函数
     * @param coordinates
     */
    public CoordinateArraySequence(Coordinate[] coordinates)
    {
        this(coordinates, 3);
    }

    /**
     * 构造函数
     * @param coordinates
     * @param dimension
     */
    public CoordinateArraySequence(Coordinate[] coordinates, int dimension)
    {
        this.coordinates = coordinates;
        this.dimension = dimension;
        if(coordinates == null)
        {
            this.coordinates = new Coordinate[0];
        }
    }

    /**
     * 构造函数
     * @param size
     */
    public CoordinateArraySequence(int size)
    {
        coordinates = new Coordinate[size];
        for(int i = 0; i < size; i++)
        {
            coordinates[i] = new Coordinate();
        }
    }

    /**
     * 构造函数
     * @param size
     * @param dimension
     */
    public CoordinateArraySequence(int size, int dimension)
    {
        coordinates = new Coordinate[size];
        this.dimension = dimension;

        for(int i = 0; i < size; i++)
        {
            coordinates[i] = new Coordinate();
        }
    }

    public CoordinateArraySequence(CoordinateSequence coordinateSequence)
    {
        if (coordinateSequence != null)
        {
            dimension = coordinateSequence.getDimension();
            coordinates = new Coordinate[coordinateSequence.size()];
        } else
        {
            coordinates = new Coordinate[0];
        }
        for (int i = 0; i < coordinates.length; i++)
        {
            coordinates[i] = coordinateSequence.getCoordinateCopy(i);
        }
    }

    /**
     * 维度值
     * @return
     */
    @Override
    public int getDimension()
    {
        return dimension;
    }

    /**
     * 当前索引的点
     * @param index 当前索引
     * @return
     */
    @Override
    public Coordinate getCoordinate(int index)
    {
        return coordinates[index];
    }

    /**
     * 当前索引的X
     * @param index 当前索引
     * @return
     */
    @Override
    public double getX(int index)
    {
        return coordinates[index].x;
    }

    /**
     * 当前索引的Y
     * @param index 当前索引
     * @return
     */
    @Override
    public double getY(int index)
    {
        return coordinates[index].y;
    }

    /**
     * 只返还X,y
     * @param index 索引
     * @param ordinateIndex 维度
     * @return 不存在的维度返回 NaN
     */
    @Override
    public double getOrdinate(int index, int ordinateIndex)
    {
        switch (ordinateIndex) {
            case CoordinateSequence.X:  return coordinates[index].x;
            case CoordinateSequence.Y:  return coordinates[index].y;
        }
        return Double.NaN;
    }

    public int size() {
        return coordinates.length;
    }


    /**
     * 设置当前的索引，维度对应的值
     * @param index 索引
     * @param ordinateIndex 维度
     * @param value 值
     */
    @Override
    public void setOrdinate(int index, int ordinateIndex, double value)
    {
        switch (ordinateIndex) {
            case CoordinateSequence.X:
                coordinates[index].x = value;
                break;
            case CoordinateSequence.Y:
                coordinates[index].y = value;
                break;
            default:
                throw new IllegalArgumentException("不存在当前的维度值");
        }
    }

    /**
     * 设置x和y的值
     * @param index 索引
     * @param x x
     * @param y y
     */
    public void setOrdinate(int index ,double x, double y)
    {
        coordinates[index].x = x;
        coordinates[index].y = y;
    }

    /**
     * 设置x和y的值
     * @param index 索引
     * @param coordinate coordinate
     */
    public void setOrdinate(int index, Coordinate coordinate)
    {
        setOrdinate(index, coordinate.x, coordinate.y);
    }

    /**
     * 扩展当前的外接矩形
     * @param another Envelope
     * @return
     */
    @Override
    public Envelope expandEnvelope(Envelope another)
    {
        for (int i = 0; i < coordinates.length; i++)
        {
            another.expandToInclude(coordinates[i]);
        }
        return another;
    }

    /**
     *
     * @param i
     * @return
     */
    @Override
    public Coordinate getCoordinateCopy(int i)
    {
        return new Coordinate(coordinates[i]);
    }

    /**
     *
     * @return
     */
    @Override
    public Coordinate[] toCoordinateArray() {
        return coordinates;
    }


    public String toString()
    {
        if (coordinates.length > 0)
        {
            StringBuffer strBuf = new StringBuffer(17 * coordinates.length);
            strBuf.append('(');
            strBuf.append(coordinates[0]);
            for (int i = 1; i < coordinates.length; i++)
            {
                strBuf.append(", ");
                strBuf.append(coordinates[i]);
            }
            strBuf.append(')');
            return strBuf.toString();
        } else
        {
            return "()";
        }
    }

    public Object clone()
    {
        Coordinate[] cloneCoordinates = new Coordinate[size()];
        for (int i = 0; i < coordinates.length; i++) {
            cloneCoordinates[i] = (Coordinate) coordinates[i].clone();
        }
        return new CoordinateArraySequence(cloneCoordinates);
    }
}

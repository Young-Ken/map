package com.snail.gis.geometry;

import java.io.Serializable;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/1
 */
public class CoordinateArraySequenceFactory implements CoordinateSequenceFactory, Serializable
{

    private volatile static CoordinateArraySequenceFactory instance = null;
    private CoordinateArraySequenceFactory()
    {
    }
    public static CoordinateArraySequenceFactory getInstance()
    {
        if (null == instance)
        {
            synchronized (CoordinateArraySequenceFactory.class)
            {
                if (null == instance)
                {
                    instance = new CoordinateArraySequenceFactory();
                }
            }
        }
        return instance;
    }

    /**
     *
     * @param coordinates Coordinate[]
     * @return
     */
    @Override
    public CoordinateSequence create(Coordinate[] coordinates)
    {
        return new CoordinateArraySequence(coordinates);
    }

    @Override
    public CoordinateSequence create(CoordinateSequence coordinateSequence)
    {
        return new CoordinateArraySequence(coordinateSequence);
    }

    @Override
    public CoordinateSequence create(int size, int dimension)
    {
        if(dimension > 2)
        {
            throw new IllegalArgumentException("维度必须 <= 2");
        }
        return new CoordinateArraySequence(size, dimension);
    }
}

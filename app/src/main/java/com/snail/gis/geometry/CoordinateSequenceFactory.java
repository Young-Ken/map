package com.snail.gis.geometry;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/2/24
 */
public interface CoordinateSequenceFactory
{
    /**
     * 创建CoordinateSequence
     * @param coordinates Coordinate[]
     * @return CoordinateSequence
     */
    CoordinateSequence create(Coordinate[] coordinates);

    /**
     * 创建CoordinateSequence
     * @param coordinateSequence CoordinateSequence
     * @return CoordinateSequence
     */
    CoordinateSequence create(CoordinateSequence coordinateSequence);

    /**
     * 创建CoordinateSequence
     * @param size CoordinateSequence的大小
     * @param dimension 当前维度
     * @return CoordinateSequence
     */
    CoordinateSequence create(int size, int dimension);
}

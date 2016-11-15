package com.caobugs.gis.geometry;

import com.caobugs.gis.geometry.primary.Envelope;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/2/24
 */
public interface CoordinateSequence extends Cloneable
{
    public static final int X = 0;
    public static final int Y = 1;
    public static final int M = 2;

    /**
     * 返回当前维度 默认是3
     * @return int
     */
    int getDimension();

    /**
     * 根据索引得到当前的Coordinate对象
     * @param index 当前索引
     * @return Coordinate对象
     */
    Coordinate getCoordinate(int index);

    int size();
    /**
     * 返回当前索引的x
     * @param index 当前索引
     * @return 当前的值
     */
    double getX(int index);

    /**
     * 返回当前索引的y
     * @param index 当前索引
     * @return 当前的值
     */
    double getY(int index);

    /**
     * 根据当前维度和索引等到当前的数值
     * @param index 索引
     * @param ordinateIndex 维度
     * @return 值
     */
    double getOrdinate(int index, int ordinateIndex);

    /**
     * 根据当前维度和索引设置当前的数值
     * @param index 索引
     * @param ordinateIndex 维度
     * @param value 值
     */
    void setOrdinate(int index, int ordinateIndex, double value);

    /**
     * 对当前的包络线进行扩展
     * @param another Envelope
     * @return 扩展后的新包络线
     */
    Envelope expandEnvelope(Envelope another);

    /**
     * 拷贝当前索引的的数值
     * @param i
     * @return
     */
    Coordinate getCoordinateCopy(int i);

    /**
     *
     * @return
     */
    Coordinate[] toCoordinateArray();

    /**
     * 克隆方法
     * @return
     */
    Object clone();
}

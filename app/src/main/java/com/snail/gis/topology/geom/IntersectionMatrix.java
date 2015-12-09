package com.snail.gis.topology.geom;

import com.snail.gis.enumeration.Dimension;
import com.snail.gis.enumeration.Location;

/**
 *
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/8
 */
public class IntersectionMatrix implements Cloneable
{
    private int [][] matrix;

    /**
     * 构造函数
     */
    public IntersectionMatrix()
    {
        matrix = new int[3][3];
        initMatrix();
    }



    /**
     * 初始化Matrix
     */
    public void initMatrix()
    {
        initMatrix(Dimension.FALSE);
    }

    /**
     * 用value初始化
     * @param value int
     */
    public void initMatrix(int value)
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                matrix[i][j] = value;
            }
        }
    }

    public void setMatrix(int row, int column, int value)
    {
        matrix[row][column] = value;
    }

    public int getMatrix(int row, int column)
    {
        return matrix[row][column];
    }

    @Override
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                buffer.setCharAt(3*i+j, Dimension.toDimensionSymbol(matrix[i][j]));
            }
        }
        return buffer.toString();
    }

}

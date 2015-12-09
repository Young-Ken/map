package com.snail.gis.graph;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/8
 */
public class Position
{
    /**
     * 在上
     */
    public static final int NO = 0;

    /**
     *
     */
    public static final int LEFT = 1;

    /**
     *
     */
    public static final int RIGHT = 2;

    /**
     *
     */
    public static int opposite(int position)
    {
        if (position == LEFT)
        {
            return RIGHT;
        }
        if (position == RIGHT)
        {
            return LEFT;
        }
        return position;
    }
}

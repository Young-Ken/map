package com.snail.gis.graph;

import com.snail.gis.enumeration.Location;


/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/8
 */
public class TopologyLocation
{

    int location[];
    public TopologyLocation(int on)
    {
        init(1);
        location[Position.NO] = on;
    }

    private void init(int size)
    {
        location = new int[size];
        setLocation(Location.NONE);
    }

    public void setLocation(int value)
    {
        for (int i = 0; i < location.length; i++)
        {
            location[i] = value;
        }
    }
}

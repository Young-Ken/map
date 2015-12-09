package com.snail.gis.graph;

import com.snail.gis.enumeration.Location;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/8
 */
public class Label
{
    private TopologyLocation elt[] = new TopologyLocation[2];

    public Label(int onLocation)
    {
        elt[0] = new TopologyLocation(onLocation);
        elt[1] = new TopologyLocation(onLocation);
    }

    public Label(int indexGeom, int onLocation)
    {
        elt[0] = new TopologyLocation(Location.NONE);
        elt[1] = new TopologyLocation(Location.NONE);
        elt[indexGeom].setLocation(onLocation);
    }
}

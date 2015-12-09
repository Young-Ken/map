package com.snail.gis.graph;

import com.snail.gis.enumeration.Location;
import com.snail.gis.geometry.Coordinate;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/8
 */
public class Node extends GraphComponent
{
    protected Coordinate coordinate = null;
    protected EdgeEndStar edges = null;
    public Node(Coordinate coordinate, EdgeEndStar edages)
    {
        this.coordinate = coordinate;
        this.edges = edages;
        label = new Label(0, Location.NONE);
    }
}

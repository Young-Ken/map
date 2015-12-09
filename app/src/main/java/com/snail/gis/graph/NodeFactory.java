package com.snail.gis.graph;

import com.snail.gis.geometry.Coordinate;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/8
 */
public class NodeFactory
{
    public Node createNode(Coordinate coordinate)
    {
        return new Node(coordinate, null);
    }
}

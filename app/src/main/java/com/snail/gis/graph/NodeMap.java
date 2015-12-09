package com.snail.gis.graph;

import com.snail.gis.geometry.Coordinate;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/8
 */
public class NodeMap
{
    protected Map<Coordinate, Node> nodeMap = new TreeMap<>();
    protected NodeFactory nodeFactory;

    public NodeMap (NodeFactory factory)
    {
        this.nodeFactory = factory;
    }

    public Node addNode(Coordinate coordinate)
    {
        Node node = (Node)nodeMap.get(coordinate);
        if (node == null)
        {
            node = nodeFactory.createNode(coordinate);
            nodeMap.put(coordinate, node);
        }
        return node;
    }
}

package com.snail.gis.graph;

import com.snail.gis.algorithm.BoundaryNodeRule;
import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Geometry;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/8
 */
public class GeometryGraph extends PlanarGraph
{
    private int argIndex = -1;
    private Geometry parentGeom = null;
    private  BoundaryNodeRule boundaryNodeRule = null;

    public GeometryGraph(int argIndex, Geometry parentGeom)
    {
        this(argIndex, parentGeom, BoundaryNodeRule.OGC_SFS_BOUNDARY_RULE);
    }

    public GeometryGraph(int argIndex, Geometry parentGeom, BoundaryNodeRule boundaryNodeRule)
    {
        this.argIndex = argIndex;
        this.parentGeom = parentGeom;
        this.boundaryNodeRule = boundaryNodeRule;

        if (parentGeom != null)
        {
            add(parentGeom);
        }
    }

    private void add(Geometry geometry)
    {
        if (geometry.isEmpty())
        {
            return;
        }

        if (geometry instanceof Coordinate)
        {

        }
    }

    private void add(Coordinate coordinate)
    {

    }

    private void insertPoint(int argIndex, Coordinate coordinate, int onLocation)
    {

    }
}

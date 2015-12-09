package com.snail.gis.topology.relate.opration;

import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.topology.geom.IntersectionMatrix;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/8
 */
public class RelateOperation extends GeometryGraphOperation
{
    public static IntersectionMatrix relate(Geometry a, Geometry b)
    {
        RelateOperation opration = new RelateOperation();
        IntersectionMatrix matrix = new IntersectionMatrix();
        return matrix;
    }


}

package com.caobugs.gis.geometry.primary;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.GeometryFactory;
import com.caobugs.gis.geometry.Polygon;
import com.caobugs.gis.topology.relate.RectangleIntersectGeometry;

import java.io.Serializable;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/7
 */
public abstract class Geometry implements SpatialAnalysisSystem, Cloneable, Comparable, Serializable
{
    /**
     * 图形工厂
     */
    protected final GeometryFactory factory;

    /**
     * 外接矩形
     */
    protected Envelope envelope;
    /**
     * Returns the Spatial Reference System ID for this Geometry.
     */
    protected int SRID;
    public Geometry(GeometryFactory factory)
    {
        this.factory = factory;
        this.SRID = factory.getSRID();
    }


    /**
     * 设置SRID
     */
    public void setSRID(int SRID)
    {
        this.SRID = SRID;
    }

    /**
     * 得到Geometry类型
     * @return Geometry 类型
     */
    public abstract String getGeometryType();

    /**
     * 得到当前的Coordinate
     * @return Coordinate
     */
    public abstract Coordinate getCoordinate();

    /**
     * 得当当前的Coordinate集合
     * @return
     */
    public abstract Coordinate[] getCoordinates();

    /**
     * Geometry是否为空
     * @return
     */
    public abstract boolean isEmpty();

    /**
     * 返回当前Geometory 的维度
     * @return
     */
    public abstract int getDimension();

    /**
     * 当前的点数量
     * @return
     */
    public abstract int getNumPoints();

    /**
     * 放回边界
     * @return
     */
    public abstract Geometry getBoundary();

    public abstract int getBoundaryDimension();

    protected abstract Envelope computeEnvelopeInternal();

    /**
     * 参考 SFS 方法有待完善
     * @return
     */
    public boolean isSimple()
    {
        return false;
    }


    public Geometry getEnvelope()
    {
        return getFactory().toGeometry(getEnvelopeInternal());
    }
    public Envelope getEnvelopeInternal()
    {
        if (envelope == null) {
            envelope = computeEnvelopeInternal();
        }
        return new Envelope(envelope);
    }


    /**
     * 检查数据有没有空项
     * @param array Object array
     * @return 有空项返回true
     */
    protected static boolean hasNullElements(Object[] array)
    {
        for(int i = 0; i < array.length; i++)
        {
            if(array[i] == null)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查数据有没有空项
     * @param geometries Geometry array
     * @return 不是空返回tue
     */
    protected static boolean hasNonEmptyElements(Geometry[] geometries)
    {
        for (int i = 0; i < geometries.length; i++)
        {
            if (!geometries[i].isEmpty())
            {
                return true;
            }
        }
        return false;
    }

    public GeometryFactory getFactory() {
        return factory;
    }
//    public abstract Envelope getEnvelope();
//    public abstract boolean isEmpty();
//    public abstract int getDimension();
//    public abstract int getBoundaryDimension();
//    public abstract List<Point> getLines();
//
//
//    /**
//     * 判断两个图形是不是相离 ! intersects
//     * @param geometry geometry
//     * @return
//     */
//    @Override
//    public boolean disjoint(Geometry geometry)
//    {
//        return ! intersects(geometry);
//    }
//
    /**
     * 判断两个图形是不是相交, 先不考虑环
     * 详见博客
     * @param geometry geometry
     * @return
     */
    public boolean intersects(Geometry geometry)
    {
        if (!getEnvelope().intersects(geometry.getEnvelope()))
        {
            return false;
        }

        if (isRectangle())
        {
           return RectangleIntersectGeometry.intersects((Polygon) this, geometry);
        }

        if (geometry.isRectangle())
        {
            return RectangleIntersectGeometry.intersects((Polygon) geometry, this);
        }


        return false;
    }
    public boolean isRectangle()
    {
        // Polygon overrides to check for actual rectangle
        return false;
    }
//
//    protected Envelope getEnvelope(final List<Point> list )
//    {
//        double maxX = Double.MIN_VALUE;
//        double maxY = Double.MIN_VALUE;
//        double minX = Double.MAX_VALUE;
//        double minY = Double.MAX_VALUE;
//
//        for (Point point : list)
//        {
//            maxX = Math.max(point.x, maxX);
//            maxY = Math.max(point.y, maxY);
//            minX = Math.min(point.x, minX);
//            minY = Math.min(point.y, minY);
//        }
//
//        return new Envelope(maxX, minX, maxY, minY);
//    }
//
//    public GeometryFactory getFactory()
//    {
//        return factory;
//    }
}

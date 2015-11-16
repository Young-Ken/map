package com.snail.gis.shapefile.shp;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/10/29
 */
public final class ShapeType
{
    /** Represents a Null shape (id = 0). */
    public static final ShapeType NULL = new ShapeType(0, "Null");
    /** Represents a Point shape (id = 1). */
    public static final ShapeType POINT = new ShapeType(1, "Point");
    /** Represents a PointZ shape (id = 11). */
    public static final ShapeType POINT_Z = new ShapeType(11, "PointZ");
    /** Represents a PointM shape (id = 21). */
    public static final ShapeType POINT_M = new ShapeType(21, "PointM");
    /** Represents an Arc shape (id = 3). */
    public static final ShapeType LINE = new ShapeType(3, "Line");
    /** Represents an ArcZ shape (id = 13). */
    public static final ShapeType LINE_Z = new ShapeType(13, "LineZ");
    /** Represents an ArcM shape (id = 23). */
    public static final ShapeType LINE_M = new ShapeType(23, "LineM");
    /** Represents a Polygon shape (id = 5). */
    public static final ShapeType POLYGON = new ShapeType(5, "Polygon");
    /** Represents a PolygonZ shape (id = 15). */
    public static final ShapeType POLYGON_Z = new ShapeType(15, "PolygonZ");
    /** Represents a PolygonM shape (id = 25). */
    public static final ShapeType POLYGON_M = new ShapeType(25, "PolygonM");
    /** Represents a MultiPoint shape (id = 8). */
    public static final ShapeType MULTIPOINT = new ShapeType(8, "MultiPoint");
    /** Represents a MultiPointZ shape (id = 18). */
    public static final ShapeType MULTIPOINT_Z = new ShapeType(18, "MultiPointZ");
    /** Represents a MultiPointZ shape (id = 28). */
    public static final ShapeType MULTIPOINT_M = new ShapeType(28, "MultiPointM");

    /** Represents an Undefined shape (id = -1). */
    public static final ShapeType UNDEFINED = new ShapeType(-1, "Undefined");

    /** The integer id of this ShapeType. */
    public final int id;
    /**
     * The human-readable name for this ShapeType.<br>
     * Could easily use ResourceBundle for internationialization.
     */
    public final String name;

    /**
     * Creates a new instance of ShapeType. Hidden on purpose.
     *
     * @param id The id.
     * @param name The name.
     */
    protected ShapeType(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String toString()
    {
        return name;
    }

    public boolean isMultiPoint()
    {
        boolean mp = true;
        if (this == UNDEFINED)
        {
            mp = false;
        } else if (this == NULL)
        {
            mp = false;
        } else if (this == POINT || this == POINT_M || this == POINT_Z)
        {
            mp = false;
        }
        return mp;
    }

    public boolean isPointType()
    {
        return id % 10 == 1;
    }

    public boolean isLineType()
    {
        return id % 10 == 3;
    }

    public boolean isPolygonType()
    {
        return id % 10 == 5;
    }

    public boolean isMultiPointType()
    {
        return id % 10 == 8;
    }

    public static ShapeType forID(int id)
    {
        ShapeType t;
        switch (id)
        {
            case 0:
                t = NULL;
                break;
            case 1:
                t = POINT;
                break;
            case 11:
                t = POINT_Z;
                break;
            case 21:
                t = POINT_M;
                break;
            case 3:
                t = LINE;
                break;
            case 13:
                t = LINE_Z;
                break;
            case 23:
                t = LINE_M;
                break;
            case 5:
                t = POLYGON;
                break;
            case 15:
                t = POLYGON_Z;
                break;
            case 25:
                t = POLYGON_M;
                break;
            case 8:
                t = MULTIPOINT;
                break;
            case 18:
                t = MULTIPOINT_Z;
                break;
            case 28:
                t = MULTIPOINT_M;
                break;
            default:
                t = UNDEFINED;
                break;
        }
        return t;
    }
}
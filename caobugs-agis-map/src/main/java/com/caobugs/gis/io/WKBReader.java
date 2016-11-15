package com.caobugs.gis.io;

import java.io.IOException;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.LineString;
import com.caobugs.gis.geometry.LinearRing;
import com.caobugs.gis.geometry.Point;
import com.caobugs.gis.geometry.primary.Geometry;
import com.caobugs.gis.io.impl.WKBConstants;


public class WKBReader
{
    private ByteOrderDataInStream dis = new ByteOrderDataInStream();
    private int inputDimension = 2;
    private double[] ordValues;
    private boolean hasSRID = false;

    public Geometry read(byte[] bytes) throws Exception
    {
        try
        {
            return read(new ByteArrayInStream(bytes));
        } catch (IOException ex)
        {
            throw new Exception("Unexpected IOException caught: "
                    + ex.getMessage());
        }
    }

    public Geometry read(InStream is) throws Exception
    {
        dis.setInStream(is);
        Geometry g = readGeometry();
        return g;
    }

    private Geometry readGeometry() throws Exception
    {
        byte byteOrderWKB = dis.readByte();
        int byteOrder = byteOrderWKB == WKBConstants.wkbNDR ? ByteOrderValues.LITTLE_ENDIAN
                : ByteOrderValues.BIG_ENDIAN;
        dis.setOrder(byteOrder);
        int typeInt = dis.readInt();
        int geometryType = typeInt & 0xff;

        // determine if Z values are present
        boolean hasZ = (typeInt & 0x80000000) != 0;
        inputDimension =  hasZ ? 3 : 2;
        // determine if SRIDs are present
        hasSRID = (typeInt & 0x20000000) != 0;

        int SRID = 0;
        if (hasSRID) {
            SRID = dis.readInt();
        }



        // only allocate ordValues buffer if necessary
        if (ordValues == null || ordValues.length < inputDimension)
            ordValues = new double[inputDimension];

        Geometry geom = null;
        switch (geometryType)
        {
            case WKBConstants.wkbPoint:
                geom = readCoordinateSequencePoint();
                break;
            case WKBConstants.wkbLineString:
                geom = readCoordinateSequenceLine();
                break;

            case WKBConstants.wkbPolygon:
                geom = readCoordinateSequencePolygon();
                break;
            default:
                throw new Exception("没有对应的 WKB 类型 " + geometryType);
        }
        return geom;
    }

    private Geometry readCoordinateSequencePoint() throws Exception

    {
        Point geom = null;
        readCoordinate();
        if (ordValues.length == 2)
        {
            geom = new Point(new Coordinate(ordValues[0], ordValues[1]));
        } else
        {
            throw new Exception(
                    "readCoordinateSequencePoint() : WBK 读取点错");
        }

        return geom;
    }

    private Geometry readCoordinateSequenceLine() throws Exception
    {
        int size = dis.readInt();
        Coordinate[] coordinates = new Coordinate[size];
        for (int i = 0; i < size; i++)
        {
            readCoordinate();
            if (ordValues.length == 2)
            {
                coordinates[i] = new Coordinate(ordValues[0], ordValues[1]);
            } else
            {
                throw new Exception(
                        "readCoordinateSequenceLine() : WBK 读取线错误");
            }

        }
        LineString geom = new LineString(coordinates);
        return geom;

    }

    private Geometry readCoordinateSequencePolygon() throws  Exception
    {
        int size = dis.readInt();

        if (size != 1)
        {
            throw new Exception(
                    "readCoordinateSequencePolygon() : WBK 读取环错误");
        }
        LineString lineString = (LineString) readCoordinateSequenceLine();

        Coordinate[] coordinates = new Coordinate[lineString.getNumPoints()];

        for(int i = 0; i < coordinates.length; i++)
        {
            coordinates[i] = lineString.getCoordinateN(i);
        }
        LinearRing ring = new LinearRing(coordinates);
        return ring;
    }

    private void readCoordinate() throws Exception
    {
        for (int i = 0; i < inputDimension; i++)
        {
            ordValues[i] = dis.readDouble();
        }
    }
}

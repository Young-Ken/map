package com.caobugs.gis.data.db;


import jsqlite.Constants;
import jsqlite.Exception;
import jsqlite.Stmt;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.LinearRing;
import com.caobugs.gis.io.WKBReader;
import com.caobugs.gis.vo.Farmland;

import java.util.ArrayList;


public class ResultStmt
{
    private static final String TAG = "ResultStmt";
    public Stmt stmt;

    public ResultStmt(Stmt stmt) throws Exception
    {
        this.stmt = stmt;
    }

    public void dispose()
    {
        stmt = null;
    }

    public int toIndex(String fieldName)
    {
        try
        {
            for (int i = 0; i < stmt.column_count(); i++)
            {
                if (fieldName.equals(stmt.column_name(i)))
                    return i;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }


    public boolean nextRow()
    {

        try
        {
            return stmt.step();
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean isNull(String fieldName)
    {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isNull(int index)
    {
        // TODO Auto-generated method stub
        return false;
    }

    public long getLong(String fieldName)
    {
        return getLong(toIndex(fieldName));
    }

    public long getLong(int index)
    {
        try
        {
            return stmt.column_long(index);
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public Object getObject(String fieldName)
    {
        return this.getObject(toIndex(fieldName));
    }


    public Object getObject(int index)
    {
        try
        {
            switch (stmt.column_type(index))
            {
                case Constants.SQLITE_INTEGER:
                    return getLong(index);
                case Constants.SQLITE_FLOAT:
                    return getDouble(index);
                case Constants.SQLITE_BLOB:
                    return getBytes(index);
                case Constants.SQLITE3_TEXT:
                    return getString(index);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    public String getString(String fieldName)
    {
        return getString(toIndex(fieldName));
    }


    public String getString(int index)
    {
        try
        {
            return stmt.column_string(index);
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public int getInt(String fieldName)
    {
        return getInt(toIndex(fieldName));
    }


    public int getInt(int index)
    {
        try
        {
            return stmt.column_int(index);
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }


    public double getDouble(String fieldName)
    {

        return getDouble(toIndex(fieldName));
    }


    public double getDouble(int index)
    {
        try
        {
            return stmt.column_double(index);
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public byte[] getBytes(String fieldName)
    {

        return getBytes(toIndex(fieldName));
    }


    public byte[] getBytes(int index)
    {
        try
        {
            return stmt.column_bytes(index);
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    public boolean close()
    {
        try
        {
            stmt.close();
            return true;
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


    public Coordinate getConnection()
    {
        return null;
    }

}

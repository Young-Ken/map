package com.caobugs.gis.data.db;

import com.caobugs.gis.geometry.LinearRing;
import com.caobugs.gis.io.WKBReader;
import com.caobugs.gis.vo.Farmland;
import com.caobugs.gis.vo.Position;

import java.util.ArrayList;

import jsqlite.*;
import jsqlite.Exception;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/16
 */
public class PositionResultStmt extends ResultStmt
{
    private ArrayList<Position> positions = null;

    public PositionResultStmt(Stmt stmt) throws jsqlite.Exception
    {
        super(stmt);
        if (stmt == null)
        {
            throw new Exception("ResultStmt() stmt 为空");
        }
        addPostion();
    }

    private ArrayList<Position> addPostion() throws jsqlite.Exception
    {
        try
        {
            positions = new ArrayList<>();
            Position position = null;
            while (stmt.step())
            {
                int columnCount = stmt.column_count();
                position = new Position();

                boolean isRepeat = false;

                for (int i = 0; i < columnCount; i++)
                {
                    int columnType = stmt.column_type(i);
                    String name = stmt.column_name(i).toLowerCase();
                    if ("pid".equals(name))
                    {
                        position.setPositionID(getString(0));
                    }else if("name".equals(name))
                    {
                        position.setName(getString(1));
                    }
                }
                positions.add(position);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return positions;
    }

    public ArrayList<Position> getPositions()
    {
        return positions;
    }
}

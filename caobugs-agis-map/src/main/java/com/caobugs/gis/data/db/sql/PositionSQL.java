package com.caobugs.gis.data.db.sql;

import com.caobugs.gis.data.db.SpatialDBOperation;
import com.caobugs.gis.data.db.PositionResultStmt;
import com.caobugs.gis.data.db.ResultStmt;
import com.caobugs.gis.vo.Position;

import java.lang.Exception;
import java.util.ArrayList;

import jsqlite.*;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/16
 */
public class PositionSQL
{
    public PositionSQL()
    {

    }

    public ArrayList<Position> selectByCounty(String countyName)
    {
        countyName = "宜城市";
        if(countyName == null || "".equals(countyName))
        {
            countyName = "宜城市";
        }
        SpatialDBOperation spatialDBOperation = new SpatialDBOperation();
        spatialDBOperation.open();
        String sql = "SELECT DISTINCT(town_id) as pid, town_name as name from j_position j where j.county_name= '"+countyName+"'";
        ArrayList<Position> positions = null;
        try
        {
            PositionResultStmt positionResultStmt = (PositionResultStmt) executeQuery(sql, spatialDBOperation.getDataCollectDB());
            positions = positionResultStmt.getPositions();
            positions.set(0,new Position("-1","请选择地市"));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return positions;
    }

    public ArrayList<Position> selectByTown(String townID)
    {
        SpatialDBOperation spatialDBOperation = new SpatialDBOperation();
        spatialDBOperation.open();
        String sql = "SELECT DISTINCT(j.village_id) as pid, j.village_name as name from j_position j where j.town_id='"+townID+"'";
        ArrayList<Position> positions = null;
        try
        {
            PositionResultStmt positionResultStmt = (PositionResultStmt) executeQuery(sql, spatialDBOperation.getDataCollectDB());
            positions = positionResultStmt.getPositions();
            positions.add(0,new Position("-1","请选择地市"));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return positions;
    }



    public ResultStmt executeQuery(String sql, jsqlite.Database database)
    {
        Stmt stmt = null;
        try
        {
            stmt = database.prepare(sql);
            return (new PositionResultStmt(stmt));
        } catch (jsqlite.Exception e)
        {
            e.printStackTrace();
            return null;
        }finally
        {
            try
            {
                if(stmt != null)
                {
                    stmt.close();
                }
            } catch (jsqlite.Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

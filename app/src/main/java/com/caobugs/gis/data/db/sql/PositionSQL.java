package com.caobugs.gis.data.db.sql;

import com.caobugs.gis.data.db.SpatialDBOperation;
import com.caobugs.gis.data.db.PositionResultStmt;
import com.caobugs.gis.data.db.ResultStmt;
import com.caobugs.gis.vo.Position;

import java.util.ArrayList;

import jsqlite.Stmt;

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
        SpatialDBOperation spatialDBOperation = new SpatialDBOperation();
        spatialDBOperation.open();
        String sql = "SELECT DISTINCT(town_id) as pid, town_name as name from j_position j where j.county_name= '宜城市'";
        ArrayList<Position> positions = null;
        try
        {
            PositionResultStmt positionResultStmt = (PositionResultStmt) executeQuery(sql, spatialDBOperation.getDataCollectDB());
            positions = positionResultStmt.getPositions();
            positions.set(0,new Position("-1","请选择地市"));
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            spatialDBOperation.close();
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
            positions.set(0,new Position("-1","请选择地市"));
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            spatialDBOperation.close();
        }
        return positions;
    }



    public ResultStmt executeQuery(String sql, jsqlite.Database database) throws Exception
    {
        Stmt stmt;
        stmt = database.prepare(sql.toString());
        return (new PositionResultStmt(stmt));
    }
}

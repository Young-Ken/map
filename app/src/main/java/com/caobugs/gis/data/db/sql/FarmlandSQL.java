package com.caobugs.gis.data.db.sql;


import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.caobugs.gis.data.db.Connection;
import com.caobugs.gis.data.db.FarmlandResultStmt;
import com.caobugs.gis.data.db.ResultStmt;
import com.caobugs.gis.tool.ApplicationContext;
import com.caobugs.gis.tool.GeomToString;
import com.caobugs.gis.view.layer.FarmlandLayer;
import com.caobugs.gis.view.map.MapManger;
import com.caobugs.gis.vo.Farmland;

import java.util.ArrayList;

import jsqlite.Stmt;

public class FarmlandSQL
{
	private FarmlandLayer farmlandLayer = null;

	public FarmlandSQL()
	{
		farmlandLayer = new FarmlandLayer();
		MapManger.getInstance().getMap().addLayer(farmlandLayer);
	}

	public void select()
	{

	}

	public void selectFarmlandByEnvelop(String envelop)
	{
		Connection connection = new Connection();
		connection.open();
		String sql = "select id,tel,farmname,address,area,AsBinary(geom) as geom from farmland where within(geom,GeomFromText('"+envelop+"'))";
		FarmlandResultStmt resultStmt = (FarmlandResultStmt) executeQuery(sql,
				connection.getDataCollectDB(),
				farmlandLayer.getFarmlands());
		ArrayList<Farmland> farmlands = resultStmt.getFarmlands();
		farmlandLayer.setFarmlands(farmlands);
		connection.close();
	}

	public FarmlandLayer selectFarmLandByPoint(String point)
	{
		Connection connection = new Connection();
		connection.open();
		String sql = "select id,tel,farmname,address,area,AsBinary(geom) as geom from farmland where within(GeomFromText('" + point + "'),geom)";
		Log.e("wwwwwwww", sql);
		FarmlandResultStmt resultStmt = null;
		try
		{
			resultStmt = (FarmlandResultStmt) executeQuery(sql, connection.getDataCollectDB());
			ArrayList<Farmland> farmlands = resultStmt.getFarmlands();
			if (farmlands.size() == 1)
			{
				farmlandLayer.setSelected(farmlands.get(0));
				return farmlandLayer;
			} else
			{
				Toast.makeText(ApplicationContext.getContext(), "选中了多条，不能进行删除操作", Toast.LENGTH_LONG).show();
				return null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}finally
		{
			connection.close();
		}

		//return null;
	}

	public boolean delete(int id)
	{
		Connection connection = new Connection();
		connection.open();
		StringBuffer sql = new StringBuffer("delete from farmland where id = "+id+"");
		boolean result = false;
		try
		{
			ResultStmt stmt = executeQuery(sql.toString(), connection.getDataCollectDB());
			result = true;
		} catch (Exception e)
		{
			result = false;
			Toast.makeText(ApplicationContext.getContext(), "删除失败", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} finally
		{
			connection.close();
		}
		return result;
	}


	public void insert(Farmland farmland)
	{
		if(farmland.getTel() == null || farmland.getFarmName() == null || farmland.getAddress() == null)
		{
			Toast.makeText(ApplicationContext.getContext(), "数据为空操作失败，从新绘制",Toast.LENGTH_LONG).show();
			return;
		}
		Connection connection = new Connection();
		connection.open();

		StringBuffer sql = new StringBuffer("Insert into farmland(tel,farmname,address,geom) values (" +
				""+farmland.getTel()+",'"+farmland.getFarmName()+"','"+farmland.getAddress()+"',GeomFromText('"+ GeomToString.polygonToString(farmland.getFarmGeom())+"'))");
		try
		{
			ResultStmt resultStmt =  executeQuery(sql.toString(), connection.getDataCollectDB());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		connection.close();
	}

	public ResultStmt executeQuery(String sql, jsqlite.Database database, ArrayList<Farmland> arrayList)
	{
		Stmt stmt;
		try
		{
			stmt = database.prepare(sql.toString());
			return (new FarmlandResultStmt(stmt, arrayList));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public ResultStmt executeQuery(String sql, jsqlite.Database database) throws Exception
	{
		Stmt stmt;
		stmt = database.prepare(sql.toString());
		return (new FarmlandResultStmt(stmt));
	}
}

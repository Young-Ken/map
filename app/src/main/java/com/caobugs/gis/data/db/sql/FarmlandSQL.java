package com.caobugs.gis.data.db.sql;

import android.util.Log;
import android.widget.Toast;

import com.caobugs.gis.data.db.SpatialDBOperation;
import com.caobugs.gis.data.db.FarmlandResultStmt;
import com.caobugs.gis.data.db.ResultStmt;
import com.caobugs.gis.util.ApplicationContext;
import com.caobugs.gis.util.GeomToString;
import com.caobugs.gis.view.layer.FarmlandLayer;
import com.caobugs.gis.view.map.MapManger;
import com.caobugs.gis.vo.Farmland;

import java.lang.Exception;
import java.util.ArrayList;

import jsqlite.*;

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
		SpatialDBOperation spatialDBOperation = new SpatialDBOperation();
		spatialDBOperation.open();
		String sql = "select id,tel,farmname,address,area(Transform(setsrid(geom,4326),900913))/10000 as area,AsBinary(geom) as geom from farmland where within(geom,GeomFromText('"+envelop+"'))";
		Log.e("sql",sql);
		FarmlandResultStmt resultStmt = (FarmlandResultStmt) executeQuery(sql,
				spatialDBOperation.getDataCollectDB(),
				farmlandLayer.getFarmlands());
		ArrayList<Farmland> farmlands = resultStmt.getFarmlands();
		farmlandLayer.setFarmlands(farmlands);
		//spatialDBOperation.close();
	}

	public FarmlandLayer selectFarmLandByPoint(String point)
	{
		SpatialDBOperation spatialDBOperation = new SpatialDBOperation();
		spatialDBOperation.open();
		String sql = "select id,tel,farmname,address,area(Transform(setsrid(geom,4326),900913))/10000 as area,AsBinary(geom) as geom from farmland where within(GeomFromText('" + point + "'),geom)";
		Log.e("sql",sql);
		FarmlandResultStmt resultStmt = null;
		try
		{
			resultStmt = (FarmlandResultStmt) executeQuery(sql, spatialDBOperation.getDataCollectDB());
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
		}

	}

	public boolean delete(int id)
	{
		SpatialDBOperation spatialDBOperation = new SpatialDBOperation();
		spatialDBOperation.open();
		StringBuffer sql = new StringBuffer("delete from farmland where id = "+id+"");
		boolean result = false;
		try
		{
			ResultStmt stmt = executeQuery(sql.toString(), spatialDBOperation.getDataCollectDB());
			result = true;
		} catch (Exception e)
		{
			result = false;
			Toast.makeText(ApplicationContext.getContext(), "删除失败", Toast.LENGTH_LONG).show();
			e.printStackTrace();
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
		SpatialDBOperation spatialDBOperation = new SpatialDBOperation();
		spatialDBOperation.open();

		StringBuffer sql = new StringBuffer("Insert into farmland(tel,farmname,address,geom) values (" +
				""+farmland.getTel()+",'"+farmland.getFarmName()+"','"+farmland.getAddress()+"',GeomFromText('"+ GeomToString.polygonToString(farmland.getFarmGeom())+"'))");
		try
		{
			ResultStmt resultStmt =  executeQuery(sql.toString(), spatialDBOperation.getDataCollectDB());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public ResultStmt executeQuery(String sql, jsqlite.Database database, ArrayList<Farmland> arrayList)
	{
		Stmt stmt = null;
		try
		{
			stmt = database.prepare(sql);
			return (new FarmlandResultStmt(stmt, arrayList));
		} catch (jsqlite.Exception e)
		{
			e.printStackTrace();
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

		return null;
	}

	public ResultStmt executeQuery(String sql, jsqlite.Database database)
	{
		Stmt stmt = null;
		try
		{
			stmt = database.prepare(sql);
			return (new FarmlandResultStmt(stmt));
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

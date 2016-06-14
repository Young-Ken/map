package com.caobugs.gis.data.db.sql;

import android.util.Log;
import android.widget.ArrayAdapter;
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
import java.util.Date;

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
		String sql = "select id,tel,farmname,address,area(Transform(setsrid(geom,4326),900913))*0.0015 as area,AsBinary(geom) as geom from farmland where within(geom,GeomFromText('"+envelop+"'))";
		Log.e("sql",sql);
		FarmlandResultStmt resultStmt = (FarmlandResultStmt) executeQuery(sql,
				farmlandLayer.getFarmlands());
		ArrayList<Farmland> farmlands = resultStmt.getFarmlands();
		farmlandLayer.setFarmlands(farmlands);
	}

	public FarmlandLayer selectFarmLandByPoint(String point)
	{
		String sql = "select id,tel,farmname,address,area(Transform(setsrid(geom,4326),900913))*0.0015 as area,AsBinary(geom) as geom from farmland where within(GeomFromText('" + point + "'),geom)";
		Log.e("sql",sql);
		FarmlandResultStmt resultStmt = null;
		try
		{
			resultStmt = (FarmlandResultStmt) executeQuery(sql);
			ArrayList<Farmland> farmlands = resultStmt.getFarmlands();
			if (farmlands.size() == 0)
			{
				Toast.makeText(ApplicationContext.getContext(), "没有选中数据，不能进行删除操作", Toast.LENGTH_LONG).show();
				return null;
			} else
			{
				farmlandLayer.setSelected(farmlands.get(0));
				return farmlandLayer;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

	}

	public boolean delete(int id)
	{
		StringBuffer sql = new StringBuffer("delete from farmland where id = "+id+"");
		boolean result = false;
		try
		{
			ResultStmt stmt = executeQuery(sql.toString());
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
		if(farmland.getAddress() == null)
		{
			Toast.makeText(ApplicationContext.getContext(), "数据为空操作失败，从新绘制",Toast.LENGTH_LONG).show();
			return;
		}

		farmland.setTel(farmland.getTel() == null ? farmland.getTel() : "0");
		StringBuffer sql = new StringBuffer("Insert into farmland(tel,farmname,address,geom,time) values (" +
				""+farmland.getTel()+",'"+farmland.getFarmName()+"','"+farmland.getAddress()+"',GeomFromText('"+ GeomToString.polygonToString(farmland.getFarmGeom())+"'),"+new Date().getTime()+")");

		Log.e("sql",sql.toString());
		try
		{
			ResultStmt resultStmt =  executeQuery(sql.toString());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public boolean update(Farmland farmland)
	{
		boolean result = false;
		if(farmland.getTel() == null || farmland.getFarmName() == null)
		{
			Toast.makeText(ApplicationContext.getContext(), "数据为空操作失败，从新绘制",Toast.LENGTH_LONG).show();
			return false;
		}

		StringBuffer sql = new StringBuffer("UPDATE farmland SET tel = "+farmland.getTel()+", farmname= '"+farmland.getFarmName()+"' where id = "+farmland.getId()+"");
		try
		{
			ResultStmt resultStmt =  executeQuery(sql.toString());
			result = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}


	public boolean updateUploadFarmland(String id, String status)
	{
		boolean result = false;
		StringBuffer sql = new StringBuffer("UPDATE farmland SET state = "+status+" where id = "+id+"");
		FarmlandResultStmt resultStmt = (FarmlandResultStmt) executeQuery(sql.toString());
		return true;
	}

	public ArrayList<Farmland> queryCanUploadFarmland()
	{
		ArrayList<Farmland> farmlands = new ArrayList<>();
		StringBuffer sql = new StringBuffer("select id,tel,farmname,address,0 as area,AsBinary(geom), time as geom from farmland where state = 2");
		FarmlandResultStmt resultStmt = (FarmlandResultStmt) executeQuery(sql.toString(), farmlands);
		return resultStmt.getFarmlands();
	}


	public ResultStmt executeQuery(String sql, ArrayList<Farmland> arrayList)
	{
		Stmt stmt = null;

		try
		{
			SpatialDBOperation spatialDBOperation = new SpatialDBOperation();
			spatialDBOperation.open();
			stmt = spatialDBOperation.getDataCollectDB().prepare(sql);
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

	public ResultStmt executeQuery(String sql)
	{
		Stmt stmt = null;
		try
		{
			SpatialDBOperation spatialDBOperation = new SpatialDBOperation();
			spatialDBOperation.open();
			stmt = spatialDBOperation.getDataCollectDB().prepare(sql);
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

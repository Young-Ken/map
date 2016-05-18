package com.caobugs.gis.data.db.sql;

import java.util.concurrent.Callable;


import android.util.Log;

import com.caobugs.gis.data.db.SpatialDBOperation;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.tool.GeomToString;

public class ExecuteSQLThread implements Callable<String>
{

	protected String sql = null;
	private SpatialDBOperation spatialDBOperation = null;
	public ExecuteSQLThread(String sql)
	{
		this.sql = sql;
		spatialDBOperation = new SpatialDBOperation();
	}

	public ExecuteSQLThread()
	{

	}

	@Override
	public synchronized String call() throws Exception
	{
		executeQuery();
		return "success";
	}

	public void executeWithin()
	{

	}

	// GEOGRAPHY_GEO_PD_POLE
	public void executeWithin(String tableName, Envelope envelope)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select OID as EID,OID as FID,AsBinary(geom) as SHAPE from " + tableName + " where within(geom,'" + GeomToString.geomToString(envelope) + "')");
		sql.append("and " + tableName + ".ROWID IN (");
		sql.append(inSql(tableName, envelope));
		sql.append(")");
		this.sql = sql.toString();
		Log.i("sql", this.sql);
	}

	public String inSql(String tableName, Envelope envelope)
	{
		String geomStr = GeomToString.geomToString(envelope) + "'))";
		Log.i("Demo", geomStr);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT pkid FROM ");
		sql.append("idx_" + tableName + "_geom");
		sql.append(" where xmin > ");
		sql.append(envelope.getMinX());
		sql.append(" AND xmax < ");
		sql.append(envelope.getMaxX());
		sql.append(" AND ymin > ");
		sql.append(envelope.getMinY());
		sql.append(" AND ymax < ");
		sql.append(envelope.getMaxY());
		return sql.toString();
	}

	public synchronized void executeQuery()
	{
		Log.i("Thread", "executeQuery()     " + Thread.currentThread().getName());
		spatialDBOperation.open();
		//ResultStmt cursor = spatialDBOperation.executeQuery(sql);

	}

	public void setSql(String sql)
	{
		this.sql = sql;
	}
}
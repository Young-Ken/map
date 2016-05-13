package com.caobugs.gis.data.db.sql;

public class ExecuteSQL
{


	protected String sql = null;

	public ExecuteSQL()
	{

	}

	public void text()
	{

	}

//	protected String getSpaceOrAttTableName(String str, boolean spaceOrAtt)
//	{
//		if(str == null) return null;
//
//		String[] tableNames = str.split(",");
//		if(tableNames.length == 0) return null;
//
//		if(spaceOrAtt)
//			return tableNames[0];
//		else
//			return tableNames[1];
//	}
//
//	// GEOGRAPHY_GEO_PD_POLE
//	protected void executeWithin(String tableName, IFeatureLayer featureLayer)
//	{
//		IEnvelope envelope = (IEnvelope) featureLayer.getEnvelope().project(IGeometry.WGS84);
//		IFields fields = featureLayer.getFeatureClass().getFields();
//
//		Iterator<IField> iterator = fields.iterator();
//
//		StringBuffer buffer = new StringBuffer();
//
//		while(iterator.hasNext())
//		{
//			IField field = iterator.next();
//			String name = field.name;
//			if(name == "OID" || name == "EID" || name == "FID")
//				continue;
//			buffer.append(name);
//			buffer.append(",");
//		}
//		String[] names = tableName.split(",");
//		StringBuffer sql = new StringBuffer();
//		sql.append("select g.SUBTYPEID ,g.SYMBOLID,g.SYMBOLSIZE,g.SYMBOLCOLOR,g.OID as EID from "+names[1]+" as g where ");
////		sql.append("select g.SUBTYPEID ,g.SYMBOLID,g.SYMBOLSIZE,g.SYMBOLCOLOR,a.OID as EID,a.OID as FID,AsBinary(geom) as SHAPE from "
////				+ names[0] + " as a,"+names[1]+" as g where a.id=g.EQUIPOBJID and within(geom,'",AsBinary(geom) as SHAPE
////				+ GeomToString.geomToString(envelope) + "')");
//		sql.append("g.OID IN (");
//		sql.append(inSql(names[1], envelope));
//		sql.append(")");
//		this.sql = sql.toString();
//		Log.i("debugTime", sql+";");
//	}
//
//	protected String executeWithinForCursor(String tableName, IFeatureLayer featureLayer)
//	{
//		IEnvelope envelope = (IEnvelope) featureLayer.getEnvelope().project(IGeometry.WGS84);
//		IFields fields = featureLayer.getFeatureClass().getFields();
//
//		Iterator<IField> iterator = fields.iterator();
//
//		StringBuffer buffer = new StringBuffer();
//
//		while(iterator.hasNext())
//		{
//			IField field = iterator.next();
//			String name = field.name;
//			if(name == "OID" || name == "EID" || name == "FID")
//				continue;
//			buffer.append(name);
//			buffer.append(",");
//		}
//		String[] names = tableName.split(",");
//		StringBuffer sql = new StringBuffer();
//		sql.append("select AsBinary(geom) as SHAPE from "+names[1]+" as g where ");
////		sql.append("select g.SUBTYPEID ,g.SYMBOLID,g.SYMBOLSIZE,g.SYMBOLCOLOR,a.OID as EID,a.OID as FID,AsBinary(geom) as SHAPE from "
////				+ names[0] + " as a,"+names[1]+" as g where a.id=g.EQUIPOBJID and within(geom,'",
////				+ GeomToString.geomToString(envelope) + "')");
//		sql.append("g.OID IN (");
//		sql.append(inSql(names[1], envelope));
//		sql.append(")");
//		Log.i("debugTime", sql+";");
//		return sql.toString();
//	}
//
//	protected String inSql(String tableName, IEnvelope envelope)
//	{
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT pkid FROM ");
//		sql.append("idx_"+tableName+"_geom");
//		sql.append(" where xmin > ");
//		sql.append(envelope.XMin);
//		sql.append(" AND xmax < ");
//		sql.append(envelope.XMax);
//		sql.append(" AND ymin > ");
//		sql.append(envelope.YMin);
//		sql.append(" AND ymax < ");
//		sql.append(envelope.YMax);
//		//sql.append(" limit 2700");
//		return sql.toString();
//	}
//
//	protected boolean executeQuery(IConnection jdbc, String tableName,IFeatureLayer featureLayer)
//	{
//		executeWithin(tableName, featureLayer);
//		String curSql=executeWithinForCursor(tableName, featureLayer);
//		try {
//			long time=System.currentTimeMillis();
//			ICursor cursor = jdbc.executeQueryNew(sql,curSql);
//			long time1=System.currentTimeMillis();
//			Log.i("debugTime", "executeQueryNew��ʱ�䣺"+(time1-time));
//			//		cursor.nextRow()
//			if (cursor != null) {
////				if (!cursor.nextRow()) {
////					return false;
////				}
//				if(!cursor.hasResult()){
//					return false;
//				}
//				FeatureClassObj f =(FeatureClassObj) featureLayer.getFeatureClass();
//				f.clearAll();
//				try {
//					f.resultRunForAndroid(cursor);
//				} catch (java.lang.Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		} catch (java.lang.Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		jdbc.close();
//		return true;
//
//	}
//
//
//	public void run(IConnection jdbc , String sql)
//	{
//
//			Date date = new Date();
//			int count = 0;
//
//			ICursor cursor = jdbc.executeQuery(sql);
//
//			if(cursor != null)
//			{
//				//FeatureClassObj f = (FeatureClassObj)featureLayer.getFeatureClass();
//				//f.resultRunForAndroid(cursor);
//			}
//
//			Log.i("time", (new Date().getTime() - date.getTime())+" ------ "+count);
//
//		//executeQuery22
//	}
//	public void run2(Connection jdbc , String sql)
//	{
//
//			Date date = new Date();
//			int count = 0;
//
//			Stmt stmt = jdbc.executeQuery22(sql);
//
//			try {
//				while(stmt.step())
//				{
//					count++;
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			Log.i("time", (new Date().getTime() - date.getTime())+" -------- "+count);
//
//		//executeQuery22
//	}
}

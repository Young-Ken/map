package com.caobugs.gis.io;

import com.caobugs.gis.geometry.primary.Geometry;

public class TOGeometry
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Geometry toGeometry(Object obj)
	{
		if (obj == null)
			return null;
		try {
			return new WKBReader().read((byte[]) obj);
//			return new WKTReader().read(obj.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

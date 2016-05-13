package com.caobugs.gis.location.gps;


public interface GpsTaskCallBack {

	public void gpsConnected(GpsData gpsdata);
	
	public void gpsConnectedTimeOut();
	
}

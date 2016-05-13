package com.caobugs.gis.data.db;

public class Database 
{
	static
	  {
	    try
	    {
	      System.loadLibrary("libjsqlite");
	    } catch (Throwable t) {
	      System.err.println("Unable to load sqlite_jni: " + t);
	    }
	  }
}

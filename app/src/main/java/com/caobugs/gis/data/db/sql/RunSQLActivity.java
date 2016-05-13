package com.caobugs.gis.data.db.sql;

import android.app.Activity;
import android.os.Bundle;

public class RunSQLActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		new FarmlandSQL().select();
	}
}

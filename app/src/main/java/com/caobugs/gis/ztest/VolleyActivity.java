package com.caobugs.gis.ztest;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/6/1
 */
public class VolleyActivity extends Activity
{
    private RequestQueue requestQueue = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Volley.newRequestQueue(this.getApplicationContext());

    }
}

package com.caobugs.gis.tile.service;

import android.os.Binder;
import android.util.Log;

import com.caobugs.gis.util.TAG;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/30
 */
public class DownTileBinder extends Binder
{
    public void startDownLoad()
    {
        Log.d(TAG.DOWNTILESERVER, "sssss");
    }
}

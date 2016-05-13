package com.caobugs.gis.tile.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.caobugs.gis.tool.TAG;


/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/30
 */
public class DownTileService extends Service
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.e(TAG.DOWNTILESERVER,"onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(TAG.DOWNTILESERVER,"onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG.DOWNTILESERVER,"onDestroy()");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.e(TAG.DOWNTILESERVER,"onBind()");
        return new DownTileBinder();
    }

}

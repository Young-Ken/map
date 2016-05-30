package com.caobugs.gis.view.appview;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.caobugs.gis.R;
import com.caobugs.gis.tile.CoordinateSystemManager;
import com.caobugs.gis.tile.downtile.factory.TiledLayerFactory;
import com.caobugs.gis.tile.downtile.tileurl.BaseTiledURL;
import com.caobugs.gis.tile.downtile.tileurl.GoogleTiledTypes;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.tile.TileInfo;
import com.caobugs.gis.tile.service.DownTileBinder;
import com.caobugs.gis.util.TAG;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/13
 */
public class DownTile extends Activity
{
    private DownTileBinder myBinder;
    private double mapMaxX;
    private double mapMinX;
    private double mapMaxY;
    private double mapMinY;
    private int mapMaxLevel;
    private int mapMinLevel;
    private EditText editTextMinX = null;
    private EditText editTextMaxX = null;
    private EditText editTextMinY = null;
    private EditText editTextMaxY = null;
    private EditText editTextMaxLevel = null;
    private EditText editTextMinLevel = null;
    private TextView currentLevel = null;
    private TextView currentPercent = null;

    private LinearLayout layout;

    TileInfo tileInfo = null;
    private static double piExcept360 = Math.PI / 360;
    private static double piExcept180 = Math.PI / 180;

    private static double earthRExcept180 = 20037508.342787 / 180;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_activity_layout);

        layout = (LinearLayout) findViewById(R.id.downActivity);

        currentLevel = (TextView) findViewById(R.id.currentLevel);
        currentLevel.setText("aaaaa");
        currentPercent = (TextView) findViewById(R.id.currentPercent);

        tileInfo = CoordinateSystemManager.getInstance().getCoordinateSystem().getTileInfo();
        Button downButton = (Button) findViewById(R.id.downButton);
        downButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getEditTextValue();
                checkTileInfo();
                downTile();
            }
        });

    }

    public void getEditTextValue()
    {
        String tempString = null;
        editTextMinX = (EditText) findViewById(R.id.minXText);
        tempString = editTextMinX.getText().toString();
        if ("".equals(tempString))
        {
            setTextIsError(editTextMinX, getResources().getString(R.string.edit_text_null_error));
            return;
        }
        mapMinX = Double.parseDouble(editTextMinX.getText().toString());
        mapMinX = lonLatToMercatorX(mapMinX);

        editTextMinY = (EditText) findViewById(R.id.minYText);
        tempString = editTextMinY.getText().toString();
        if ("".equals(tempString))
        {
            setTextIsError(editTextMinY, getResources().getString(R.string.edit_text_null_error));
            return;
        }
        mapMinY = Double.parseDouble(editTextMinY.getText().toString());
        mapMinY = lonLatToMercatorY(mapMinY);

        editTextMaxX = (EditText) findViewById(R.id.maxXText);
        tempString = editTextMaxX.getText().toString();
        if ("".equals(tempString))
        {
            setTextIsError(editTextMaxX, getResources().getString(R.string.edit_text_null_error));
            return;
        }
        mapMaxX = Double.parseDouble(editTextMaxX.getText().toString());
        mapMaxX = lonLatToMercatorX(mapMaxX);

        editTextMaxY = (EditText) findViewById(R.id.maxYText);
        tempString = editTextMaxY.getText().toString();
        if ("".equals(tempString))
        {
            setTextIsError(editTextMaxY, getResources().getString(R.string.edit_text_null_error));
            return;
        }
        mapMaxY = Double.parseDouble(editTextMaxY.getText().toString());
        mapMaxY = lonLatToMercatorY(mapMaxY);

        editTextMaxLevel = (EditText) findViewById(R.id.mapMaxLevel);
        tempString = editTextMaxLevel.getText().toString();
        if ("".equals(tempString))
        {
            setTextIsError(editTextMaxLevel, getResources().getString(R.string.edit_text_null_error));
            return;
        }
        mapMaxLevel = Integer.parseInt(editTextMaxLevel.getText().toString());

        editTextMinLevel = (EditText) findViewById(R.id.mapMinLevel);
        tempString = editTextMinLevel.getText().toString();
        if ("".equals(tempString))
        {
            setTextIsError(editTextMinLevel, getResources().getString(R.string.edit_text_null_error));
            return;
        }
        mapMinLevel = Integer.parseInt(editTextMinLevel.getText().toString());
    }


    public void checkTileInfo()
    {
        if (tileInfo == null)
        {
            return;
        }

        if (mapMaxX > Math.abs(tileInfo.getOriginPoint().x))
        {
            setTextIsError(editTextMaxX, "最大级别不能大于地图设置的最大级别");
            return;
        }
        if (mapMaxY > Math.abs(tileInfo.getOriginPoint().y))
        {
            setTextIsError(editTextMaxY, "最小级别不能大于最大级别");
            return;
        }
        if (mapMinX < -Math.abs(tileInfo.getOriginPoint().x))
        {
            setTextIsError(editTextMinX, "ssss");
            return;
        }
        if (mapMinY < -Math.abs(tileInfo.getOriginPoint().y))
        {
            setTextIsError(editTextMinY, "ssssss");
            return;
        }
        if (mapMaxLevel > tileInfo.getResolutions().length-1)
        {
            setTextIsError(editTextMaxLevel, "sssss");
            return;
        }

        if (mapMinLevel > mapMinLevel)
        {
            setTextIsError(editTextMaxLevel, "sssss");
            return;
        }
    }


    public void setTextIsError(EditText editText, String errorStr)
    {

        editText.setTextColor(Color.RED);
        editText.setError(errorStr, null);
        editText.setHintTextColor(Color.RED);
    }

    private ServiceConnection connection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            myBinder = (DownTileBinder) service;
            myBinder.startDownLoad();
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            Log.e(TAG.DOWNTILESERVER, "onServiceDisconnected      " + name);
        }
    };

    private Handler mHandler = new Handler()
    {
        public synchronized void handleMessage (Message msg) {
        //此方法在ui线程运行
            switch(msg.what) {
                case 0:
                    currentLevel.setText(msg.arg1+"");
                case 1:
                    currentPercent.setText(msg.arg1+"");
                    break;
            }
        }
    };

    public void downTile()
    {
        //(1.2938604970292658E7, 4869419.604634653)   (1.2969764297641108E7, 4856528.876808467)
        new Thread(runnable).start();

    }

    Runnable runnable = new Runnable() {

        @Override
        public void run()
        {
           // TileInfo tileInfo = new CoordinateSystemFactory().create(CoordinateSystemEnum.GOOGLE_CS).getTileInfo();
            BaseTiledURL baseTiledURL = TiledLayerFactory.getInstance().createTiledURL(GoogleTiledTypes.GOOGLE_IMAGE);
            com.caobugs.gis.tile.downtile.DownTile downTile = new com.caobugs.gis.tile.downtile.DownTile(tileInfo, baseTiledURL
                    ,new Envelope(mapMinX, mapMaxX, mapMinY, mapMaxY),mapMinLevel,mapMaxLevel,mHandler);
            try
            {
                downTile.run();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };
    public double lonLatToMercatorX(double x)
    {
        double toX = x * earthRExcept180;
        return toX;
    }

    public double lonLatToMercatorY(double y)
    {
        double toY = Math.log(Math.tan((90 + y) * piExcept360)) / piExcept180;
        toY = toY * earthRExcept180;
        return toY;
    }

}

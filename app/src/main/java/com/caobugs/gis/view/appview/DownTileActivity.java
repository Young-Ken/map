package com.caobugs.gis.view.appview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.caobugs.gis.R;
import com.caobugs.gis.tile.CoordinateSystemManager;
import com.caobugs.gis.tile.downtile.DownTile;
import com.caobugs.gis.tile.downtile.factory.TiledLayerFactory;
import com.caobugs.gis.tile.downtile.tileurl.BaseTiledURL;
import com.caobugs.gis.tile.downtile.tileurl.GoogleTiledTypes;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.tile.TileInfo;
import com.caobugs.gis.tile.service.DownTileBinder;
import com.caobugs.gis.util.ApplicationContext;
import com.caobugs.gis.util.TAG;
import com.caobugs.gis.vo.Farmland;

import java.util.ArrayList;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/13
 */
public class DownTileActivity extends Activity
{
    private DownTileBinder myBinder;
    private double mapMaxX;
    private double mapMinX;
    private double mapMaxY;
    private double mapMinY;
    //private int mapMaxLevel;
    //private int mapMinLevel;
    private EditText editTextMinX = null;
    private EditText editTextMaxX = null;
    private EditText editTextMinY = null;
    private EditText editTextMaxY = null;
    //private EditText editTextMaxLevel = null;
    //private EditText editTextMinLevel = null;
    private TextView currentLevel = null;
    private TextView currentPercent = null;

    private LinearLayout layout;

    TileInfo tileInfo = null;
    private static double piExcept360 = Math.PI / 360;
    private static double piExcept180 = Math.PI / 180;

    private static double earthRExcept180 = 20037508.342787 / 180;
    private Button downButton = null;
    private DownTile downTile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_activity_layout);

        layout = (LinearLayout) findViewById(R.id.downActivity);

        currentLevel = (TextView) findViewById(R.id.currentLevel);
        currentLevel.setText("开始下载");
        currentPercent = (TextView) findViewById(R.id.currentPercent);

        tileInfo = CoordinateSystemManager.getInstance().getCoordinateSystem().getTileInfo();
        getEditTextValue();
        getEnvelope();

        downButton = (Button) findViewById(R.id.downButton);
        downButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                downButton.setEnabled(true);
                checkTileInfo();
                saveEnvelope();
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

        //        editTextMaxLevel = (EditText) findViewById(R.id.mapMaxLevel);
        //        tempString = editTextMaxLevel.getText().toString();
        //        if ("".equals(tempString))
        //        {
        //            setTextIsError(editTextMaxLevel, getResources().getString(R.string.edit_text_null_error));
        //            return;
        //        }
        //        mapMaxLevel = Integer.parseInt(editTextMaxLevel.getText().toString());
        //
        //        editTextMinLevel = (EditText) findViewById(R.id.mapMinLevel);
        //        tempString = editTextMinLevel.getText().toString();
        //        if ("".equals(tempString))
        //        {
        //            setTextIsError(editTextMinLevel, getResources().getString(R.string.edit_text_null_error));
        //            return;
        //        }
        //        mapMinLevel = Integer.parseInt(editTextMinLevel.getText().toString());
    }


    public void saveEnvelope()
    {
        SharedPreferences settings = getSharedPreferences("setting", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("mapMinX", editTextMinX.getText().toString());
        editor.putString("mapMaxX", editTextMaxX.getText().toString());
        editor.putString("mapMinY", editTextMinY.getText().toString());
        editor.putString("mapMaxY", editTextMaxY.getText().toString());
        editor.commit();
    }

    public void getEnvelope()
    {
        SharedPreferences settings = getSharedPreferences("setting", 0);
        String mapMinXValue = settings.getString("mapMinX", "");
        String mapMaxXValue = settings.getString("mapMaxX", "");
        String mapMinYValue = settings.getString("mapMinY", "");
        String mapMaxYValue = settings.getString("mapMaxY", "");

        if (!mapMinXValue.equals("") &&
                !mapMinYValue.equals("") &&
                !mapMaxXValue.equals("") &&
                !mapMaxYValue.equals(""))
        {
            editTextMinX.setText(mapMinXValue);
            editTextMaxX.setText(mapMaxXValue);
            editTextMinY.setText(mapMinYValue);
            editTextMaxY.setText(mapMaxYValue);
        }
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
        public synchronized void handleMessage(Message msg)
        {
            //此方法在ui线程运行
            switch (msg.what)
            {
                case 0:
                    currentLevel.setText(msg.arg1 + "");
                case 1:
                    int num = msg.arg1;
                    if (num == 0)
                    {
                        downButton.setEnabled(false);
                    }
                    currentPercent.setText(msg.arg1 + "");
                    break;
            }
        }
    };

    public void downTile()
    {
        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable()
    {

        @Override
        public void run()
        {
            BaseTiledURL baseTiledURL = TiledLayerFactory.getInstance().createTiledURL(GoogleTiledTypes.GOOGLE_IMAGE);
            downTile = new DownTile(tileInfo, baseTiledURL, new Envelope(mapMinX, mapMaxX, mapMinY, mapMaxY), 0, 20, mHandler);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if ("0".equals(currentPercent.getText().toString()))
            {
                finish();
            } else
            {
                new AlertDialog.Builder(this).setTitle("系统提示").setMessage("确定返回").setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                }).setNegativeButton("返回", new DialogInterface.OnClickListener()
                {
                    @Override

                    public void onClick(DialogInterface dialog, int which)
                    {

                    }

                }).show();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(downTile != null)
        {
            downTile.destroy();
        }
    }
}

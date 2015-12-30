package com.snail.gis.map;

import android.view.MotionEvent;
import android.view.View;

import com.snail.gis.algorithm.MathUtil;
import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.tile.TileInfo;

/**
 * 这先简单处理
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/11
 */
public class MapOperation implements View.OnTouchListener
{

    public MapOperation()
    {

    }

    /**
     * 当前地图的外包络线
     */
    private Envelope currentEnvelope = new Envelope();

    /**
     * 全图外包络线
     */
    private Envelope fullEnvelope = new Envelope();

    /**
     * 设备的高
     */
    private int deviceHeight = 0;

    /**
     * 设备宽
     */
    private int deviceWidth = 0;

    private int currentLevel = 0;

    /**
     * 当前分辨率
     */
    private double currentResolution = 0;

    /**
     * 当前比例尺
     */
    private double currentScale = 0;

    /**
     * 初始化当前的级别和分辨率还有比例尺
     */
    public void init()
    {
        double resolution;
        if (getFullEnvelope().getWidth() > getFullEnvelope().getHeight())
        {
            resolution = getFullEnvelope().getWidth() / getDeviceWidth();
        } else
        {
            resolution = getFullEnvelope().getHeight() /getDeviceHeight();
        }

        TileInfo tileInfo = MapManger.getInstance().getMap().getTileInfo();
        double[] resolutions = tileInfo.getResolutions();
        for (int i = 0; i < resolutions.length - 1; i++)
        {
            if (MathUtil.between(resolution, resolutions[i], resolutions[i + 1]))
            {
                setCurrentLevel(i);
                setCurrentResolution(resolutions[i]);
                setCurrentScale(tileInfo.getScales()[i]);
            }
        }
    }

    public void setCurrentResolution(double currentResolution)
    {
        this.currentResolution = currentResolution;
    }

    public double getCurrentResolution()
    {
        return currentResolution;
    }

    public double getCurrentScale()
    {
        return currentScale;
    }

    public void setCurrentScale(double currentScale)
    {
        this.currentScale = currentScale;
    }

    public Envelope getCurrentEnvelope()
    {
        return currentEnvelope;
    }

    public void setCurrentEnvelope(Envelope currentEnvelope)
    {
        this.currentEnvelope = currentEnvelope;
    }

    public int getCurrentLevel()
    {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel)
    {
        this.currentLevel = currentLevel;
    }

    public void setDeviceHeight(int deviceHeight)
    {
        this.deviceHeight = deviceHeight;
    }

    public void setDeviceWidth(int deviceWidth)
    {
        this.deviceWidth = deviceWidth;
    }

    public int getDeviceHeight()
    {
        return deviceHeight;
    }

    public int getDeviceWidth()
    {
        return deviceWidth;
    }

    public void setFullEnvelope(Envelope fullEnvelope)
    {
        this.fullEnvelope = fullEnvelope;
    }

    public Envelope getFullEnvelope()
    {
        return fullEnvelope;
    }

    private Coordinate actionDown = new Coordinate(0,0);
    private Coordinate actionUp = new Coordinate(0,0);
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            actionDown.x = event.getX();
            actionDown.y = event.getY();
        } else if(event.getAction() == MotionEvent.ACTION_UP)
        {
            actionUp.x = event.getX();
            actionUp.y = event.getY();
        } else if(event.getAction() == MotionEvent.ACTION_MOVE)
        {

        }


        return false;
    }
}

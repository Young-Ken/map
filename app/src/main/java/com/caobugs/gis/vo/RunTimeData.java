package com.caobugs.gis.vo;

import java.util.ArrayList;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/16
 */
public class RunTimeData
{
    /**
     * 单例模式
     */
    private volatile static RunTimeData instance = null;

    private RunTimeData()
    {

    }

    public static RunTimeData getInstance()
    {
        if (null == instance)
        {
            synchronized (RunTimeData.class)
            {
                if (null == instance)
                {
                    instance = new RunTimeData();
                }
            }
        }
        return instance;
    }

    private ArrayList<Position> town = null;
    private ArrayList<Position> village = null;

    private int selectedTown = -1;
    private int selectedVillage = -1;

    public ArrayList<Position> getTown()
    {
        return town;
    }

    public ArrayList<Position> getVillage()
    {
        return village;
    }

    public void setTown(ArrayList<Position> town)
    {
        this.town = town;
    }

    public void setVillage(ArrayList<Position> village)
    {
        this.village = village;
    }

    public int getSelectedTown()
    {
        return selectedTown;
    }

    public int getSelectedVillage()
    {
        return selectedVillage;
    }

    public void setSelectedTown(int selectedTown)
    {
        this.selectedTown = selectedTown;
    }

    public void setSelectedVillage(int selectedVillage)
    {
        this.selectedVillage = selectedVillage;
    }
}

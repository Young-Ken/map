package com.snail.gis.tile.util;

import android.util.Log;

import com.snail.gis.map.BaseMap;
import com.snail.gis.map.MapManger;
import com.snail.gis.tile.TileInfo;
import com.snail.gis.tool.file.ToolMapCache;

import java.io.IOException;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/4
 */
public class TileTool2
{
    private BaseMap map = null;
    private TileInfo tileInfo = null;
    private String tilePath = null;

    public double moveX = 0.0;
    public double moveY = 0.0;
    public TileTool2(String tilePath)
    {
        this.map = MapManger.getInstance().getMap();
        tileInfo = map.getTileInfo();
        this.tilePath = tilePath;
    }

    public byte[][][] getTile()
    {
        int[] tileNum = getFirstTile();
        return getByteTile(map.getLevel(), tileNum[0], tileNum[1], tileNum[2], tileNum[3]);
    }

    private int[] getFirstTile()
    {

        int minTileNumX = getTileNum(map.getEnvelope().getMinX() + tileInfo.getConverParameter());
        int minTileNumY = getTileNum(tileInfo.getConverParameter() - map.getEnvelope().getMaxY());

        minTileNumX = subTileNum(minTileNumX);
        minTileNumY = subTileNum(minTileNumY);

        moveX = getMoveDistance(map.getEnvelope().getMinX() + tileInfo.getConverParameter());
        moveY = getMoveDistance(tileInfo.getConverParameter() - map.getEnvelope().getMaxY());

        int maxTileNumX = getTileNum(map.getEnvelope().getMaxX() + tileInfo.getConverParameter());
        int maxTileNumY = getTileNum(tileInfo.getConverParameter() - map.getEnvelope().getMinY());

        maxTileNumX = addTileNum(maxTileNumX);
        maxTileNumY = addTileNum(maxTileNumY);

        int result[] = {minTileNumX, minTileNumY, maxTileNumX, maxTileNumY};
        return result;

    }

    public byte[][][] getByteTile(int level, int minTileNumX, int minTileNumY, int maxTileNumX, int maxTileNumY)
    {
        byte[] result[][] = null;
        int tileNumX = maxTileNumX - minTileNumX;
        int tileNumY = maxTileNumY - minTileNumY;
        result = new byte[tileNumX + 1][tileNumY + 1][];

        for (int i = 0; i <= tileNumX; i++)
        {
            for (int j = 0; j <= tileNumY; j++)
            {
                result[i][j] = getByte(level, minTileNumX + i, minTileNumY + j);
            }
        }
        return result;
    }

    public byte[] getByte(int level, int col, int row)
    {
        try
        {
            byte[] bytes = ToolMapCache.getByte(tilePath, level, col, row);
            if(bytes != null)
            {
                Log.e("BaseMap", level + "   " + col + "  " + row + "  " + bytes.length);
            }else
            {
                Log.e("BaseMap", level + "   " + col + "  " + row + " null " );
            }

            return bytes;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }



    private double getMoveDistance(double num)
    {
        return (int) (num / map.getResolution() % tileInfo.getTileWidth());
    }

    private int getTileNum(double num)
    {
        return (int) (num / map.getResolution() / tileInfo.getTileWidth());
    }

    private int addTileNum(int num)
    {
        if (num != Math.pow(2,map.getLevel()))
        {
             num ++;
        }
        return num;
    }

    private int subTileNum(int num)
    {
        if (num != 0)
        {
           num --;
        }
        return num;
    }

}

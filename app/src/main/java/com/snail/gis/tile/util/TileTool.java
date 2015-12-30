package com.snail.gis.tile.util;

import android.util.Log;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.map.BaseMap;
import com.snail.gis.map.MapManger;
import com.snail.gis.tile.TileInfo;
import com.snail.gis.tool.file.ToolMapCache;

import java.io.IOException;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/29
 */
public class TileTool
{

    private BaseMap map = null;
    private TileInfo tileInfo = null;

    public TileTool()
    {
        this.map = MapManger.getInstance().getMap();
        tileInfo = map.getTileInfo();
    }

    public int getTileNum(double mapSize ,int tileSize)
    {
        return (int)((mapSize / 2 - tileSize / 2) / tileSize + 1) * 2 + 1;
    }

    public int[] getCenterTile()
    {
        Coordinate center = map.getMapCenter();
        int col = 0;
        int row = 0;
        if(center.getY() > 0)
        {
            if (center.getX() > 0)
            {
                col = (int) ((center.getX() + tileInfo.getOriginPoint().getY()) / map.getResolution()/tileInfo.getTileWidth());
                row = (int) ((tileInfo.getOriginPoint().getY() - center.getY()) / map.getResolution()/tileInfo.getTileHeight());
            }
        }

        return new int[]{col,row};
    }

    public Bytes[][] getByteTile(int level, int col, int row)
    {
        Bytes result[][] = null;
        int xNum = getTileNum(map.getMapWidth(), tileInfo.getTileWidth());
        int yNum = getTileNum(map.getMapHeight(), tileInfo.getTileHeight());
        col = col - 1 - xNum/2;
        row = row - 1 - yNum/2;
        result = new Bytes[xNum][yNum];
        for (int i = 0; i < xNum; i++)
        {
            for (int j = 0; j < yNum; j++)
            {
                result[i][j].bytes = getByte(level, col + i, row + j);
            }
        }
        return result;
    }

    public byte[] getByte(int level, int col, int row)
    {
        try
        {
            byte[] bytes =  ToolMapCache.getByte("GOOGLE_VECTOR", level, col, row);
            Log.e("BaseMap", level + "   " + col + "  " + row + "  " + bytes.length);
            return bytes;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    class Bytes
    {
        public byte[] bytes = null;
    }
}

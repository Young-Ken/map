package com.caobugs.gis.tile.downtile;

import android.util.Log;

import com.caobugs.gis.tile.downtile.httputil.TileDownloader;
import com.caobugs.gis.tile.downtile.tileurl.BaseTiledURL;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.tile.TileInfo;
import com.caobugs.gis.tool.file.ToolMapCache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/11
 */
public class DownTile
{
    private int minLevel = -1;
    private int maxLevel = -1;
    private Envelope mapEnv = new Envelope();

    private TileInfo tileInfo = null;
    private BaseTiledURL tiledURL = null;

    private TileDownloader tileDownloader = new TileDownloader();

    public DownTile(TileInfo tileInfo, BaseTiledURL tiledURL,Envelope downEnv, int minLevel, int maxLevel)
    {
        this.tiledURL = tiledURL;
        this.tileInfo = tileInfo;
        this.mapEnv.init(downEnv);
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public void run() throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(7);
        if(maxLevel < minLevel)
        {
            throw new RuntimeException("最大级别一定要大于最小级别");
        }

        for(int i = minLevel; i <= maxLevel; i++)
        {
            int [] tileNum = getStartTile(i);
            int minTileNumX = tileNum[0];
            int minTileNumY = tileNum[1];
            int maxTileNumX = tileNum[2];
            int maxTileNumY = tileNum[3];

            for (int col = minTileNumX; col <= maxTileNumX; col ++)
            {
                for (int row = minTileNumY; row <= maxTileNumY; row ++)
                {
                    threadPool.submit(new TileDownThread(i, col, row));
                }
            }
        }
    }

    public int[] getStartTile(int level)
    {
        int minTileNumX = getTileNum(mapEnv.getMinX() + (-tileInfo.getOriginPoint().x), level );
        int minTileNumY = getTileNum(tileInfo.getOriginPoint().y - mapEnv.getMaxY(), level);
        int maxTileNumX = getTileNum(mapEnv.getMaxX() + (-tileInfo.getOriginPoint().x), level);
        int maxTileNumY = getTileNum(tileInfo.getOriginPoint().y - mapEnv.getMinY(), level);
        Log.e("RUN",(maxTileNumX - minTileNumX) +"  tileNum " + (maxTileNumY - minTileNumY)+"  tileNum");
        int result[] = {minTileNumX, minTileNumY, maxTileNumX, maxTileNumY};
        return result;

    }


    private int getTileNum(double num, int level)
    {
        return (int) (num / tileInfo.getResolutions()[level] / tileInfo.getTileWidth());
    }

    private int addTileNum(int num)
    {
        if (num != Math.pow(2,maxLevel))
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

    class TileDownThread extends Thread
    {
        String path = null;
        int level = 0;
        int col = 0;
        int row = 0;
        public TileDownThread(int level, int col, int row)
        {
            this.level = level;
            this.col = col;
            this.row = row;
            path = tiledURL.getTiledServiceURL(level, col, row);
        }

        @Override
        public void run()
        {
           // super.run();

            if (ToolMapCache.isExistByte(tiledURL.getMapServiceType().getName(), level,col,row))
            {
                return;
            }
            tileDownloader.getStream(path, tiledURL.getMapServiceType().getName(), level, col, row);
        }
    }
}

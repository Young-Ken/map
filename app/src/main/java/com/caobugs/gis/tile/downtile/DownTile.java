package com.caobugs.gis.tile.downtile;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.caobugs.gis.tile.downtile.httputil.TileDownloader;
import com.caobugs.gis.tile.downtile.tileurl.BaseTiledURL;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.tile.TileInfo;
import com.caobugs.gis.util.TAG;
import com.caobugs.gis.util.file.ToolMapCache;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
    private Handler handler = null;
    private AtomicInteger downTileNum = new AtomicInteger(0);
    private AtomicInteger releaseNum = new AtomicInteger(0);
    private ArrayList<ArrayList<int[]>> taskArray = new ArrayList<>();
    private ExecutorService threadPool = null;

    public DownTile(TileInfo tileInfo, BaseTiledURL tiledURL, Envelope downEnv, int minLevel, int maxLevel)
    {
        this.tiledURL = tiledURL;
        this.tileInfo = tileInfo;
        this.mapEnv.init(downEnv);
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public DownTile(TileInfo tileInfo, BaseTiledURL tiledURL, Envelope downEnv, int minLevel, int maxLevel, Handler handler)
    {
        this(tileInfo, tiledURL, downEnv, minLevel, maxLevel);
        this.handler = handler;
    }

    public void run() throws Exception
    {
        threadPool = Executors.newScheduledThreadPool(7);
        if (maxLevel < minLevel)
        {
            throw new RuntimeException("最大级别一定要大于最小级别");
        }

        int num = 0;
        ArrayList<int[]> tileInfos = null;
        for (int i = minLevel; i <= maxLevel; i++)
        {
            Message msg = handler.obtainMessage();
            msg.what = 0;
            msg.arg1 = i;
            msg.sendToTarget();

            int[] tileNum = getStartTile(i);
            int minTileNumX = tileNum[0];
            int minTileNumY = tileNum[1];
            int maxTileNumX = tileNum[2];
            int maxTileNumY = tileNum[3];
            downTileNum.getAndAdd((maxTileNumX - minTileNumX) * (maxTileNumY - minTileNumY));

            for (int col = minTileNumX; col < maxTileNumX; col++)
            {
                for (int row = minTileNumY; row < maxTileNumY; row++)
                {
                    if (num == 0)
                    {
                        tileInfos = new ArrayList<>();
                    }
                    tileInfos.add(new int[]{i, col, row});
                    num++;
                    if (num == 100)
                    {
                        num = 0;
                        taskArray.add(tileInfos);
                    }
                }
            }
        }


        for (int i = 0; i < taskArray.size(); i++)
        {
            ArrayList<int[]> arrayList = taskArray.get(i);
            releaseNum.set(arrayList.size());
            for (int j = 0; j < arrayList.size(); j++)
            {
                int[] tileInfo = arrayList.get(j);
                threadPool.submit(new TileDownThread(tileInfo[0], tileInfo[1], tileInfo[2]));
            }
            while (!(releaseNum.get() == 0))
            {

            }
        }
    }

    public int[] getStartTile(int level)
    {
        int minTileNumX = getTileNum(mapEnv.getMinX() + (-tileInfo.getOriginPoint().x), level);
        int minTileNumY = getTileNum(tileInfo.getOriginPoint().y - mapEnv.getMaxY(), level);
        int maxTileNumX = getTileNum(mapEnv.getMaxX() + (-tileInfo.getOriginPoint().x), level);
        int maxTileNumY = getTileNum(tileInfo.getOriginPoint().y - mapEnv.getMinY(), level);
        Log.i(TAG.DOWNTILESERVER, "x轴切片的数量 = " + (maxTileNumX - minTileNumX) + "y轴切片的数量 = " + (maxTileNumY - minTileNumY));
        return new int[]{minTileNumX, minTileNumY, maxTileNumX, maxTileNumY};

    }

    private int getTileNum(double num, int level)
    {
        return (int) (num / tileInfo.getResolutions()[level] / tileInfo.getTileWidth());
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
            downTileNum.decrementAndGet();
            if(downTileNum.get() % 10 == 0 || downTileNum.get() <= 10)
            {
                Message msg1 = handler.obtainMessage();
                msg1.what = 1;
                msg1.arg1 = downTileNum.get();
                msg1.sendToTarget();
            }
            releaseNum.decrementAndGet();
            if (ToolMapCache.isExistByte(tiledURL.getMapServiceType().getName(), level, col, row))
            {
                Log.d(TAG.DOWNTILESERVER, "  " + level + "  level " + col + "  col  " + row + "  row   " + "已经存在");
            } else
            {
                tileDownloader.getStream(path, tiledURL.getMapServiceType().getName(), level, col, row);
            }
        }
    }

   public void destroy()
   {
       try
       {
           threadPool.shutdown();
           while (!threadPool.awaitTermination(1, TimeUnit.SECONDS))
           {
               Log.e(TAG.DOWNTILESERVER, "线程池没有关闭");
           }

       } catch (InterruptedException e)
       {
           e.printStackTrace();
       }
       Log.e(TAG.DOWNTILESERVER, "线程池已经关闭");
   }
}

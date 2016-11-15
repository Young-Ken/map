package com.caobugs.gis.view.layer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.tile.CoordinateSystemManager;
import com.caobugs.gis.tile.TileInfo;
import com.caobugs.gis.tile.cache.MemoryTileCache;
import com.caobugs.gis.tile.downtile.httputil.TileDownloader;
import com.caobugs.gis.util.ApplicationContext;
import com.caobugs.gis.util.TAG;
import com.caobugs.gis.util.file.ToolMapCache;
import com.caobugs.gis.view.map.MapManger;

import java.io.IOException;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/8
 */
public class TileLayer extends BaseLayer
{
    private MemoryTileCache memoryTileCache = null;
    private TileInfo tileInfo = null;
    private String tilePath = null;
    public double loadX = 0.0;
    public double loadY = 0.0;

    private Paint paint = new Paint();
    private int[] maxAndMinNum = new int[4];
    private int level = -1;

    public TileLayer(String tilePath)
    {
        this.tilePath = tilePath;
        tileInfo = CoordinateSystemManager.getInstance().getCoordinateSystem().getTileInfo();
        memoryTileCache = new MemoryTileCache(2*1024*1024);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(30);
    }


    private byte[][][] getTile()
    {
        int[] tileNum = getStartTile();
        maxAndMinNum = tileNum;
        return getByteTile(getMap().getLevel(), tileNum[0], tileNum[1], tileNum[2], tileNum[3]);
    }

    private int[] getStartTile()
    {
        int minTileNumX = getTileNum(getMap().getEnvelope().getMinX());
        int minTileNumY = getTileNum(getMap().getEnvelope().getMinY());

        loadX = getMoveDistance(getMap().getEnvelope().getMinX());
        loadY = getMoveDistance(getMap().getEnvelope().getMinY());

        int maxTileNumX = getTileNum(getMap().getEnvelope().getMaxX());
        int maxTileNumY = getTileNum(getMap().getEnvelope().getMaxY());

        int result[] = {minTileNumX, minTileNumY, maxTileNumX, maxTileNumY};
        return result;
    }

    private byte[][][] getByteTile(int level, int minTileNumX, int minTileNumY, int maxTileNumX, int maxTileNumY)
    {
        byte[] result[][];
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

    private byte[] getByte(int level, int col, int row)
    {
        try
        {
            this.level = level;
            String tileKey = appendTileString(level, col, row);
            byte[] tempBytes = memoryTileCache.get(tileKey);
            if(tempBytes != null)
            {
                return tempBytes;
            }

            byte[] bytes = ToolMapCache.getByte(tilePath, level, col, row);

            if(bytes != null && bytes.length != 0)
            {
                memoryTileCache.put(tileKey, bytes);
            }else
            {
                Log.d("BaseMap", level + "   " + col + "  " + row + " null " );
            }

            return bytes;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    private void drawTile(Canvas canvas, double x, double y)
    {
        if (maxAndMinNum == null)
        {
            maxAndMinNum = getStartTile();
        }

        int minX = maxAndMinNum[0];
        int minY = maxAndMinNum[1];
        int maxX = maxAndMinNum[2];
        int maxY = maxAndMinNum[3];

        for (int i = minX; i < maxX; i++)
        {
            for (int j = minY; j < maxY; j++)
            {
                byte[] bytes = memoryTileCache.get(appendTileString(level, i, j));
                if (bytes == null)
                {
                    bytes =  getByte(level,i,j);
                    if(bytes == null)
                    {
                        continue;
                    }
                }

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                if(bitmap == null)
                    continue;
                canvas.drawBitmap(bitmap, (float) ((i-minX) * 256 - loadX  + x), (float) ((j-minY) * 256 - loadY + y), paint);
            }
        }
    }

    private void drawTile(Canvas canvas)
    {
        byte[][][] tileBytes = getTile();
        if (tileBytes == null)
            return;

        int xNum = tileBytes.length;
        int yNum = tileBytes[0].length;

        for (int i = 0; i < xNum; i++)
        {
            for (int j = 0; j < yNum; j++)
            {
                byte[] bytes = tileBytes[i][j];
                if (bytes == null)
                {
                    continue;
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                if(bitmap == null)
                {
                     continue;
                }
                canvas.drawBitmap(bitmap, (float) (i * 256 - loadX ), (float) (j * 256 - loadY ), paint);
            }
        }
    }

    private double getMoveDistance(double num)
    {
        return (int) (num / getMap().getResolution() % tileInfo.getTileWidth());
    }

    private int getTileNum(double num)
    {
        return (int) (num / getMap().getResolution() / tileInfo.getTileWidth());
    }


    private String appendTileString(int level, int col, int row)
    {
        StringBuffer tilePath = new StringBuffer();
        tilePath.append(level).append("_").append(col).append("_").append(row);
        return tilePath.toString();
    }

    @Override
    public void recycle()
    {
        memoryTileCache.destroy();
    }

    @Override
    void initLayer()
    {

    }

    @Override
    void draw(Canvas canvas, Coordinate offSet)
    {

    }

    @Override
    void draw(Canvas canvas, double x, double y)
    {
        drawTile(canvas, x, y);
    }

    @Override
    void draw(Canvas canvas)
    {
        double moveX = MapManger.getInstance().getMap().getMapInfo().moveX;
        double moveY = MapManger.getInstance().getMap().getMapInfo().moveY;
        Log.e(TAG.TILE,moveX+"    "+moveY);
        if (moveX != 0&& moveY != 0)
        {
           drawTile(canvas, moveX, moveY);
        } else
        {
            drawTile(canvas);
        }
    }
}

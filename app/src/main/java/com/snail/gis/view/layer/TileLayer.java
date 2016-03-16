package com.snail.gis.view.layer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.tile.CoordinateSystemManager;
import com.snail.gis.tile.TileInfo;
import com.snail.gis.tile.cache.MemoryTileCache;
import com.snail.gis.tool.ApplicationContext;
import com.snail.gis.tool.TAG;
import com.snail.gis.tool.file.ToolMapCache;
import com.snail.gis.view.map.MapManger;

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
        memoryTileCache = new MemoryTileCache(ApplicationContext.getMaxMemory());
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

        Log.e(TAG.TILE, loadX+"  loadX  "+loadY+"   loadY");
        int maxTileNumX = getTileNum(getMap().getEnvelope().getMaxX());
        int maxTileNumY = getTileNum(getMap().getEnvelope().getMaxY());

        int result[] = {minTileNumX, minTileNumY, maxTileNumX, maxTileNumY};
        return result;
    }

    private byte[][][] getByteTile(int level, int minTileNumX, int minTileNumY, int maxTileNumX, int maxTileNumY)
    {
        byte[] result[][] = null;
        int tileNumX = maxTileNumX - minTileNumX;
        int tileNumY = maxTileNumY - minTileNumY;
        Log.e("BaseMap", tileNumX + " tileNumX " + tileNumY + " tileNumY ");
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
            if(bytes != null)
            {
                Log.e("BaseMap", level + "   " + col + "  " + row + "  " + bytes.length);
                memoryTileCache.put(tileKey, bytes);
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

                    continue;
                //                Matrix matrix = new Matrix();
                //                matrix.postTranslate((int)(loadX + i * 256 + moveX), (int)(loadY + j * 256  + moveY));
                //                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                //                canvas.drawBitmap(bitmap, matrix, paint);
                Log.e("RUN", i + "   " + j + "    " + bytes.length);
                // Log.e("RUN",x +"  moveX   "+y +"  moveY vvvvvvvvvvvv");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.e(TAG.TILE, ((i-minX) * 256 + loadX + 5 * (i-minX) + x) + "  x的偏移  " + "i = " + (i-minX) + " " + ((j-minY) * 256 + loadY + 5 * (j-minY) + y) + "  y的偏移  " + "j = " + (j-minY));
                canvas.drawBitmap(bitmap, (float) ((i-minX) * 256 - loadX  + x), (float) ((j-minY) * 256 - loadY + y), paint);

                canvas.drawPoint((float) (i * 256 + loadX  + x), (float) (j * 256 + loadY + y), paint);

            }
            canvas.drawPoint((float) (100), (float) (100), paint);

        }
        Log.e(TAG.TILE, x + "  moveX   " + y + "  moveY");
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

                    continue;
                //                Matrix matrix = new Matrix();
                //                matrix.postTranslate((int)(loadX + i * 256 + moveX), (int)(loadY + j * 256  + moveY));
                //                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                //                canvas.drawBitmap(bitmap, matrix, paint);
                Log.e("RUN", i + "   " + j + "    " + bytes.length);
                // Log.e("RUN",x +"  moveX   "+y +"  moveY vvvvvvvvvvvv");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.e(TAG.TILE, (i * 256 + loadX ) + "  x的偏移  " + "i = " + i + " " + (j * 256 + loadY ) + "  y的偏移  " + "j = " + j);
                canvas.drawBitmap(bitmap, (float) (i * 256 - loadX ), (float) (j * 256 - loadY ), paint);

                canvas.drawPoint((float) (i * 256 -loadX ), (float) (j * 256 - loadY ), paint);

            }
            canvas.drawPoint((float) (100), (float) (100), paint);
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
        StringBuffer buffer = new StringBuffer();
        buffer.append(level).append("_").append(col).append("_").append(row);
        return buffer.toString();
    }

    @Override
    void recycle()
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
        if (moveX != 0&& moveY != 0)
        {
           drawTile(canvas, moveX, moveY);
        } else
        {
            drawTile(canvas);
        }
    }
}

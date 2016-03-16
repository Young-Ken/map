package com.snail.gis.tile.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import com.snail.gis.tile.CoordinateSystemManager;
import com.snail.gis.tile.cache.MemoryTileCache;
import com.snail.gis.tool.ApplicationContext;
import com.snail.gis.view.map.BaseMap;
import com.snail.gis.view.map.MapManger;
import com.snail.gis.tile.TileInfo;
import com.snail.gis.tool.file.ToolMapCache;

import java.io.IOException;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/4
 */
public class TileTool
{
    public static double moveX = 0;
    public static double moveY = 0;
//    Log.e("RUN",moveX +"  moveX   "+moveY +"  moveY vvvvvvvvvvvv");
//    private BaseMap map = null;
//    private TileInfo tileInfo = null;
//    private String tilePath = null;
//    private Paint paint = new Paint();
//
//    private MemoryTileCache memoryTileCache = null;
//
//    public double loadX = 0.0;
//    public double loadY = 0.0;
//
//
//
//    public TileTool(String tilePath)
//    {
//        this.map = MapManger.getInstance().getMap();
//        tileInfo = CoordinateSystemManager.getInstance().getCoordinateSystem().getTileInfo();
//        this.tilePath = tilePath;
//        memoryTileCache = new MemoryTileCache(ApplicationContext.getMaxMemory());
//    }
//
//
//    public byte[][][] getTile()
//    {
//        int[] tileNum = getStartTile();
//        return getByteTile(map.getLevel(), tileNum[0], tileNum[1], tileNum[2], tileNum[3]);
//    }
//
//    public int[] getStartTile()
//    {
//        int minTileNumX = getTileNum(map.getEnvelope().getMinX());
//        int minTileNumY = getTileNum(map.getEnvelope().getMinY());
//
//        loadX = getMoveDistance(map.getEnvelope().getMinX());
//        loadY = getMoveDistance(map.getEnvelope().getMinY());
//
//        int maxTileNumX = getTileNum(map.getEnvelope().getMaxX());
//        int maxTileNumY = getTileNum(map.getEnvelope().getMaxY());
//
//        int result[] = {minTileNumX, minTileNumY, maxTileNumX, maxTileNumY};
//        return result;
//    }
//
//    public byte[][][] getByteTile(int level, int minTileNumX, int minTileNumY, int maxTileNumX, int maxTileNumY)
//    {
//        byte[] result[][] = null;
//        int tileNumX = maxTileNumX - minTileNumX;
//        int tileNumY = maxTileNumY - minTileNumY;
//        Log.e("BaseMap", tileNumX +" tileNumX "+tileNumY +" tileNumY ");
//        result = new byte[tileNumX + 1][tileNumY + 1][];
//
//        for (int i = 0; i <= tileNumX; i++)
//        {
//            for (int j = 0; j <= tileNumY; j++)
//            {
//                result[i][j] = getByte(level, minTileNumX + i, minTileNumY + j);
//            }
//        }
//        return result;
//    }
//
//    public byte[] getByte(int level, int col, int row)
//    {
//        try
//        {
//            String tileKey = appendTileString(level, col, row);
//            byte[] tempBytes = memoryTileCache.get(tileKey);
//            if(tempBytes != null)
//            {
//                return tempBytes;
//            }
//
//            byte[] bytes = ToolMapCache.getByte(tilePath, level, col, row);
//            if(bytes != null)
//            {
//                Log.e("BaseMap", level + "   " + col + "  " + row + "  " + bytes.length);
//                memoryTileCache.put(tileKey, bytes);
//            }else
//            {
//                Log.e("BaseMap", level + "   " + col + "  " + row + " null " );
//            }
//
//            return bytes;
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public void drawTile(Canvas canvas)
//    {
//        byte[][][] tileBytes = getTile();
//        if (tileBytes == null)
//            return;
//        int xNum = tileBytes.length;
//        int yNum = tileBytes[0].length;
//
//        for (int i = 0; i < xNum; i++)
//        {
//            for (int j = 0; j < yNum; j++)
//            {
//                byte[] bytes = tileBytes[i][j];
//                if (bytes == null)
//                    continue;
////                Matrix matrix = new Matrix();
////                matrix.postTranslate((int)(loadX + i * 256 + moveX), (int)(loadY + j * 256  + moveY));
////                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
////                canvas.drawBitmap(bitmap, matrix, paint);
//                Log.e("RUN",moveX +"  moveX   "+moveY +"  moveY vvvvvvvvvvvv");
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                canvas.drawBitmap(bitmap, (float)(i * 256 - loadX + 5 * i + moveX), (float)(j * 256 - loadY + 5 * j + moveY), paint);
//            }
//        }
//        Log.e("RUN",moveX +"  moveX   "+moveY +"  moveY");
//        moveY = 0;
//        moveX = 0;
//    }
//
//    private double getMoveDistance(double num)
//    {
//        return (int) (num / map.getResolution() % tileInfo.getTileWidth());
//    }
//
//    private int getTileNum(double num)
//    {
//        return (int) (num / map.getResolution() / tileInfo.getTileWidth());
//    }
//
//    public String appendTileString(int level, int col, int row)
//    {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(level).append("_").append(col).append("_").append(row);
//        return buffer.toString();
//    }
}

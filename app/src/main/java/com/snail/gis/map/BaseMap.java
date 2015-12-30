package com.snail.gis.map;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.snail.gis.R;
import com.snail.gis.algorithm.MathUtil;
import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.layer.BaseLayer;
import com.snail.gis.map.util.Projection;
import com.snail.gis.tile.CoordinateSystem;
import com.snail.gis.tile.TileInfo;
import com.snail.gis.tile.factory.CoordinateSystemEnum;
import com.snail.gis.tile.factory.CoordinateSystemFactory;
import com.snail.gis.tile.util.TileTool;
import com.snail.gis.tool.ToolNum;
import com.snail.gis.tool.file.ToolMapCache;

import java.io.IOException;

/**
 * 先考虑单线程加载
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/10
 */
public abstract class BaseMap extends View implements IBaseMap
{
    private MapLayerManger mapLayerManger = MapLayerManger.getInstance();
    private MapManger mapManger = MapManger.getInstance();
    private MapOperation mapOperation = new MapOperation();
    private TileTool tileTool = new TileTool();
    private Projection projection = null;
    private Context context = null;
    private AttributeSet attrs = null;
    private CoordinateSystem coordinateSystem = null;
    private Canvas canvas = null;
    private TileInfo tileInfo = null;
    private Object [][] tileArray = null;

    public BaseMap(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        this.setBackgroundColor(Color.GRAY);
        mapManger.setMap(this);
        projection = new Projection(this);
        mapOperation.setFullEnvelope(setOriginalEnvelope(context, attrs));
    }

    /**
     * 初始化地图控件
     */
    public void initMap()
    {
        initCoordinateSystem();
        mapOperation.init();
        getTile();
    }

    /**
     * 初始化坐标系统
     */
    public void initCoordinateSystem()
    {
        coordinateSystem = new CoordinateSystemFactory().create(CoordinateSystemEnum.GOOGLE_CS);
        tileInfo = coordinateSystem.getTileInfo();
    }


    /**
     * 先加载一张中心点的切片做实验
     */
    public void getTile()
    {
        double resolution;
        if (getFullMap().getWidth() > getFullMap().getHeight())
        {
            resolution = getFullMap().getWidth() / getWidth();
        } else
        {
            resolution = getFullMap().getHeight() /getHeight();
        }

        double[] resolutions = tileInfo.getResolutions();
        for (int i = 0; i < resolutions.length - 1; i++)
        {
           if (MathUtil.between(resolution, resolutions[i], resolutions[i+1]))
           {
               mapOperation.setCurrentLevel(i);
               Coordinate center = getMapCenter();
               int col = 0;
               int row = 0;
               if(center.getY() > 0)
               {
                   if (center.getX() > 0)
                   {
                       col = (int) ((center.getX() + tileInfo.getOriginPoint().getY()) / resolutions[i]/tileInfo.getTileWidth());
                       row = (int) ((tileInfo.getOriginPoint().getY() - center.getY()) / resolutions[i]/tileInfo.getTileHeight());
                   }
               }
               loadTile(i, col, row);
               invalidate();
               return;
           }
        }
    }

    public byte[] loadTile(int level, int col, int row)
    {
        int xNum = getTileNum(getMapWidth(), tileInfo.getTileWidth());
        int yNum = getTileNum(getMapHeight(), tileInfo.getTileHeight());
        col = col - 1 - xNum/2;
        row = row - 1 - yNum/2;
        tileArray = new Object[xNum][yNum];
        for (int i = 0; i < xNum; i++)
        {
            for (int j = 0; j < yNum; j++)
            {
                tileArray[i][j] = getByte(level, col + i, row + j);
            }
        }


        return null;
    }

    public byte[] getByte(int level, int col, int row)
    {
        try
        {
            byte[] bytes =  ToolMapCache.getByte("GOOGLE_VECTOR", level, col , row );
            Log.e("BaseMap", level + "   " + col + "  " + row + "  " + bytes.length);
            return bytes;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private int getTileNum(double mapSize ,int tileSize)
    {
        return (int)((mapSize / 2 - tileSize / 2) / tileSize + 1) * 2 + 1;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        this.canvas = canvas;

        Paint p = new Paint();
        int xNum = tileArray.length;
        int yNum = tileArray[0].length;

        float deviationX = (xNum * tileInfo.getTileWidth() - getWidth()) / 2;
        float deviationY = (yNum * tileInfo.getTileHeight() - getHeight()) / 2;
        for (int i = 0; i < xNum; i++)
        {
            for (int j = 0; j <yNum; j++)
            {
                byte[] bytes = (byte[]) tileArray[i][j];
                if(bytes == null)
                    continue;
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                canvas.drawBitmap(bitmap, i*256-deviationX, j*256-deviationY, p);
            }
        }
    }


    /**
     * 根据xml设置的属性初始化地图的边框大小
     * @param context context
     * @param attrs attrs
     * @return 初始化大小
     */
    public Envelope setOriginalEnvelope(Context context, AttributeSet attrs)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapAttribute);
        if (typedArray != null)
        {
            double minX = ToolNum.parseDouble(typedArray.getString(R.styleable.mapAttribute_min_x));
            double minY = ToolNum.parseDouble(typedArray.getString(R.styleable.mapAttribute_min_y));
            double maxX = ToolNum.parseDouble(typedArray.getString(R.styleable.mapAttribute_max_x));
            double maxY = ToolNum.parseDouble(typedArray.getString(R.styleable.mapAttribute_max_y));

            if (!(Double.isNaN(minX) || Double.isNaN(minY) || Double.isNaN(maxX) || Double.isNaN(maxY)))
            {
                return new Envelope(minX, maxX, minY, maxY);
            }
            typedArray.recycle();
        }
        return null;
    }


    @Override
    public boolean addLayer(BaseLayer layer)
    {
        return mapLayerManger.addLayer(layer);
    }

    @Override
    public boolean removeLayer(BaseLayer layer)
    {
        return mapLayerManger.removeLayer(layer);
    }


    @Override
    public double getMapHeight()
    {
       return getMapOperation().getDeviceHeight();
    }

    @Override
    public double getMapWidth()
    {
        return getMapOperation().getDeviceWidth();
    }

    @Override
    public Coordinate getMapCenter()
    {
        return getFullMap().getCentre();
    }

    @Override
    public Envelope getFullMap()
    {
        return getMapOperation().getFullEnvelope();
    }

    public MapOperation getMapOperation()
    {
        if(mapOperation == null)
            return new MapOperation();
        return mapOperation;
    }

    public TileInfo getTileInfo()
    {
        return tileInfo;
    }

    @Override
    public double getResolution()
    {
       return getMapOperation().getCurrentResolution();
    }

    @Override
    public double getScale()
    {
        return getMapOperation().getCurrentScale();
    }
}

package com.snail.gis.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.layer.BaseLayer;
import com.snail.gis.layer.MapLayerManger;
import com.snail.gis.map.event.MapOnTouchListener;
import com.snail.gis.map.util.Projection;
import com.snail.gis.tile.CoordinateSystem;
import com.snail.gis.tile.TileInfo;
import com.snail.gis.tile.factory.CoordinateSystemEnum;
import com.snail.gis.tile.factory.CoordinateSystemFactory;
import com.snail.gis.tile.util.TileTool;

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
    private MapInfo mapInfo = new MapInfo();
    private TileTool tileTool = null;
    private Projection projection = null;
    private CoordinateSystem coordinateSystem = null;
    private MapController mapController = null;
    private TileInfo tileInfo = null;


    public BaseMap(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.setBackgroundColor(Color.GRAY);
        mapManger.setMap(this);
        mapController = new MapController(this);
        this.setOnTouchListener(new MapOnTouchListener(this));
    }

    /**
     * 初始化地图控件
     */
    public void initMap(CoordinateSystemEnum systemEnum, Envelope fullEnvelope)
    {
        initCoordinateSystem(systemEnum);
        mapInfo.setFullEnvelope(fullEnvelope);
        /**
         * 需要map做参数的初始化在这个之后
         */
        tileTool = new TileTool("LYG_HK_TILE");
        projection = Projection.getInstance(this);
        getTile();

    }

    /**
     * 初始化坐标系统
     */
    public void initCoordinateSystem(CoordinateSystemEnum systemEnum)
    {
        coordinateSystem = new CoordinateSystemFactory().create(systemEnum);
        tileInfo = coordinateSystem.getTileInfo();
    }


    /**
     * 先加载一张中心点的切片做实验
     */
    public void getTile()
    {
        refresh();
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        tileTool.drawTile(canvas);

        Coordinate cente = projection.toScreenPoint(getMapCenter().getX(), getMapCenter().getY());
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle((float)cente.getX(), (float)cente.getY(),50,paint);
    }

    @Override
    public void refresh()
    {
        invalidate();
    }

    /**
     * 根据xml设置的属性初始化地图的边框大小,这个方法只有在构造函数中才能使用，影响结构，不想用
     * @return 初始化大小
     */
//    public Envelope setOriginalEnvelope()
//    {
//
//        TypedArray typedArray = this.getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.mapAttribute, 0, 0);
//        // TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.mapAttribute);
//        if (typedArray != null)
//        {
//            double minX = ToolNum.parseDouble(typedArray.getString(R.styleable.mapAttribute_min_x));
//            double minY = ToolNum.parseDouble(typedArray.getString(R.styleable.mapAttribute_min_y));
//            double maxX = ToolNum.parseDouble(typedArray.getString(R.styleable.mapAttribute_max_x));
//            double maxY = ToolNum.parseDouble(typedArray.getString(R.styleable.mapAttribute_max_y));
//
//            if (!(Double.isNaN(minX) || Double.isNaN(minY) || Double.isNaN(maxX) || Double.isNaN(maxY)))
//            {
//                return new Envelope(minX, maxX, minY, maxY);
//            }
//            typedArray.recycle();
//        }
//        return null;
//    }

    public TileTool getTileTool()
    {
        return tileTool;
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
        return getMapInfo().getDeviceHeight();
    }

    @Override
    public double getMapWidth()
    {
        return getMapInfo().getDeviceWidth();
    }

    @Override
    public Coordinate getMapCenter()
    {
        return getMapInfo().getCurrentCenter();
    }

    @Override
    public Envelope getFullMap()
    {
        return getMapInfo().getFullEnvelope();
    }

    public MapInfo getMapInfo()
    {
        if (mapInfo == null)
            return new MapInfo();
        return mapInfo;
    }

    public MapController getMapController()
    {
        return mapController;
    }

    public TileInfo getTileInfo()
    {
        return tileInfo;
    }

    @Override
    public Envelope getEnvelope()
    {
        return getMapInfo().getCurrentEnvelope();
    }

    @Override
    public void setMapCenter(Coordinate center)
    {
        getMapInfo().setCurrentCenter(center);
    }

    @Override
    public double getResolution()
    {
        return getMapInfo().getCurrentResolution();
    }

    @Override
    public double getScale()
    {
        return getMapInfo().getCurrentScale();
    }

    @Override
    public int getLevel()
    {
        return getMapInfo().getCurrentLevel();
    }

}

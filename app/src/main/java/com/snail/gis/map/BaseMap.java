package com.snail.gis.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.layer.BaseLayer;
import com.snail.gis.map.util.Projection;
import com.snail.gis.tile.CoordinateSystem;
import com.snail.gis.tile.TileInfo;
import com.snail.gis.tile.factory.CoordinateSystemEnum;
import com.snail.gis.tile.factory.CoordinateSystemFactory;
import com.snail.gis.tile.util.TileTool;
import com.snail.gis.tile.util.TileTool2;

/**
 * 先考虑单线程加载
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/10
 */
public abstract class BaseMap extends View implements IBaseMap, View.OnTouchListener
{
    private MapLayerManger mapLayerManger = MapLayerManger.getInstance();
    private MapManger mapManger = MapManger.getInstance();
    private MapOperation mapOperation = new MapOperation();
    private TileTool2 tileTool = null;
    private Projection projection = null;
    private CoordinateSystem coordinateSystem = null;
    private Canvas canvas = null;
    private TileInfo tileInfo = null;
    private byte[] tileBytes[][] = null;

    public BaseMap(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.setBackgroundColor(Color.GRAY);
        mapManger.setMap(this);
        this.setOnTouchListener(this);
    }

    /**
     * 初始化地图控件
     */
    public void initMap(CoordinateSystemEnum systemEnum, Envelope fullEnvelope)
    {
        initCoordinateSystem(systemEnum);
        mapOperation.setFullEnvelope(fullEnvelope);
        /**
         * 需要map做参数的初始化在这个之后
         */
        tileTool = new TileTool2("GOOGLE_VECTOR");
        projection = new Projection();

        getTile();
     //   projection.toScreenPoint(12945986.606604, 4838237.908444);
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
        tileBytes = tileTool.getTile();
        invalidate();
    }


    public void moveMap()
    {
        setMapCenter(projection.toMapPoint((float)getMapWidth()/2, (float)getMapHeight()/2));
        getTile();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        this.canvas = canvas;

        Paint p = new Paint();
        if(tileBytes == null)
            return;
        int xNum = tileBytes.length;
        int yNum = tileBytes[0].length;
        float deviationX ;
        float deviationY ;

        deviationX = (float) tileTool.moveX;
        deviationY = (float) tileTool.moveY;


        for (int i = 0; i < xNum; i++)
        {
            for (int j = 0; j < yNum; j++)
            {
                byte[] bytes = tileBytes[i][j];
                if (bytes == null)
                    continue;
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                canvas.drawBitmap(bitmap, i * 256 - deviationX + 5 * i - x, j * 256 - deviationY  + 5 * j - y, p);
            }
        }
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

    private Coordinate tempCenter = null;
    private Coordinate move = null;

    private float x;
    private float y;
    private boolean isMove = false;
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        Log.e("RUN", event.getAction() + "  ");
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                tempCenter = new Coordinate(event.getX(), event.getY());
                isMove = false;
                return true;
            case MotionEvent.ACTION_MOVE:
            {
                isMove = true;
               // move = new Coordinate(event.getX(),event.getY());
                move = new Coordinate(event.getX(),event.getY());

//                float x = (float)(getWidth()/2 + (move.getX() - tempCenter.getX()));
//                float y = (float)(getHeight()/2 + ( move.getY() - tempCenter.getY()));

                x = (float) (tempCenter.getX() - move.getX());
                y = (float) (tempCenter.getY() - move.getY());

                Log.e("RUN",x+"xxxx" +"  "+ y +"  yyy ");
               // Coordinate coordinate = new Coordinate(getMapCenter().getX() + x,( getMapCenter().getY() - y));
               // setMapCenter(coordinate);
               // Log.e("RUN", x + "    " + y+"   ");
               // getTile();
              //  tempCenter = projection.toMapPoint(event.getX(), event.getY());
                invalidate();
                break;
            }

            case MotionEvent.ACTION_UP:
                isMove = false;
                Coordinate coordinate1 =  projection.toMapPoint((float)tempCenter.getX(), (float)tempCenter.getY());

                float xx = (float) (coordinate1.getX() - projection.toMapPoint(event.getX(),event.getY()).getX());
                float yy = (float) (coordinate1.getY() - projection.toMapPoint(event.getX(),event.getY()).getY());


                 Coordinate coordinate = new Coordinate(getMapCenter().getX() + xx,( getMapCenter().getY() - yy));
                 setMapCenter(coordinate);
                Log.e("RUN", x + "    " + y+"   ");
                x = 0;
                y =0;
                 getTile();
                  tempCenter = projection.toMapPoint(event.getX(), event.getY());
                break;
        }

        return false;
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
        return getMapOperation().getCurrentCenter();
    }

    @Override
    public Envelope getFullMap()
    {
        return getMapOperation().getFullEnvelope();
    }

    public MapOperation getMapOperation()
    {
        if (mapOperation == null)
            return new MapOperation();
        return mapOperation;
    }

    public TileInfo getTileInfo()
    {
        return tileInfo;
    }

    @Override
    public Envelope getEnvelope()
    {
        return getMapOperation().getCurrentEnvelope();
    }

    @Override
    public void setMapCenter(Coordinate center)
    {
        getMapOperation().setCurrentCenter(center);
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

    @Override
    public int getLevel()
    {
        return getMapOperation().getCurrentLevel();
    }
}

package com.caobugs.gis.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.view.layer.BaseLayer;
import com.caobugs.gis.view.layer.MapLayerManger;
import com.caobugs.gis.view.layer.TileLayer;
import com.caobugs.gis.map.event.MapOnTouchListener;
import com.caobugs.gis.map.event.OnMapStatusChangeListener;
import com.caobugs.gis.map.util.Projection;
import com.caobugs.gis.tile.factory.CoordinateSystemEnum;
import com.caobugs.gis.tile.factory.CoordinateSystemFactory;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/10
 */
public abstract class BaseMap extends ViewGroup implements IBaseMap {
    private MapLayerManger mapLayerManger = MapLayerManger.getInstance();
    private MapManger mapManger = MapManger.getInstance();
    private MapInfo mapInfo = new MapInfo();
    private Projection projection = null;
    private MapController mapController = null;
    //private ScaleGestureDetector scaleGestureDetector = null;
    private View glassView = null;
    private OnMapStatusChangeListener mapStatusChanged = null;

    /**
     * 当前Map的默认事件
     */
    private MapOnTouchListener mapDefaultTouch = null;

    /**
     * 暂时在这里
     */
    public BaseMap(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(Color.GRAY);
        mapManger.setMap(this);
        mapController = new MapController(this);
        this.setMapOnTouchListener(new MapOnTouchListener(this));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count == 0) {
            return;
        } else {
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                if (child.getVisibility() != GONE) {
                    child.layout(l, t, r, b);
                }
            }
        }
    }

    /**
     * 初始化地图控件
     */
    public void initMap(CoordinateSystemEnum systemEnum, Envelope fullEnvelope) {
        initCoordinateSystem(systemEnum);
        initMapInfo(fullEnvelope);

        /**
         * 需要map做参数的初始化在这个之后
         */
        MapLayerManger.getInstance().addLayer(new TileLayer("GOOGLE_IMAGE"));
        projection = Projection.getInstance(this);
        // getTile();
        // ReadShapeFile readShapeFile = new ReadShapeFile();
    }

    private void initGlass() {
        glassView = new View(getContext());
        addView(glassView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * 初始化坐标系统
     */
    private void initCoordinateSystem(CoordinateSystemEnum systemEnum) {
        new CoordinateSystemFactory().create(systemEnum);
    }

    /**
     * 初始化MapInfo
     *
     * @param fullEnvelope
     */
    private void initMapInfo(Envelope fullEnvelope) {
        mapInfo.setFullEnvelope(fullEnvelope);
    }


    /**
     * 先加载一张中心点的切片做实验
     */
    public void getTile() {
        refresh();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        //canvas.translate((float) this.getMapInfo().moveX, (float) this.getMapInfo().moveY);
        mapLayerManger.draw(canvas);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    /**
     * 设置map默认事件，将来还要编辑事件
     *
     * @param mapDefaultTouch
     */
    public void setMapOnTouchListener(MapOnTouchListener mapDefaultTouch) {
        this.mapDefaultTouch = mapDefaultTouch;
        //scaleGestureDetector = new ScaleGestureDetector(this.getContext(), mapDefaultTouch);
    }

    /**
     * 设置地图的状态改变事件
     *
     * @param mapStatusChangedListener
     */
    public void setMapStatusChangedListener(OnMapStatusChangeListener mapStatusChangedListener) {
        this.mapStatusChanged = mapStatusChangedListener;
    }

    public OnMapStatusChangeListener getMapStatusChangedListener() {
        return mapStatusChanged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointCount = event.getPointerCount();

        if (pointCount == 1) {
            this.setOnTouchListener(mapDefaultTouch);
        } else {
            this.setOnTouchListener(null);
        }

//        boolean retv = this.scaleGestureDetector.onTouchEvent(event);
//        if(!this.scaleGestureDetector.isInProgress())
//        {
//            retv = this.scaleGestureDetector.onTouchEvent(event);
//        }

        return true;
    }

    @Override
    public void refresh() {
        invalidate();
    }

    @Override
    public boolean addLayer(BaseLayer layer) {
        return mapLayerManger.addLayer(layer);
    }

    @Override
    public boolean removeLayer(BaseLayer layer) {
        return mapLayerManger.removeLayer(layer);
    }


    @Override
    public double getMapHeight() {
        return getMapInfo().getDeviceHeight();
    }

    @Override
    public double getMapWidth() {
        return getMapInfo().getDeviceWidth();
    }

    @Override
    public Coordinate getMapCenter() {
        return getMapInfo().getCurrentCenter();
    }

    @Override
    public Envelope getFullMap() {
        return getMapInfo().getFullEnvelope();
    }

    public MapInfo getMapInfo() {
        if (mapInfo == null)
            return new MapInfo();
        return mapInfo;
    }

    public MapController getMapController() {
        return mapController;
    }

//    public TileInfo getTileInfo()
//    {
//        return tileInfo;
//    }

//    @Override
//    public Envelope getEnvelope() {
//        return getMapInfo().getCurrentEnvelope();
//    }

    @Override
    public void setMapCenter(Coordinate center) {
        getMapInfo().setCurrentCenter(center);
    }

    public void setMapCenterLevel(Coordinate center, int level) {
        getMapInfo().setCurrentCenterAndLevel(center, level);
    }

    @Override
    public int getLevel() {
        return getMapInfo().getCurrentLevel();
    }

    public void setLevel(int level) {
        getMapInfo().setCurrentLevel(level);
    }

    public Projection getProjection() {
        return projection;
    }
}

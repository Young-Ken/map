package com.caobugs.gis.view.map;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.util.ApplicationContext;
import com.caobugs.gis.util.LogUtil;
import com.caobugs.gis.view.map.event.OnMapStatusChangeListener;
import com.caobugs.gis.view.map.util.Projection;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/6
 */
public class MapController implements IMapController
{
    private BaseMap map = null;
    public MapController(BaseMap map)
    {
        this.map = map;
    }

    /**
     * 监听当前map的放大缩小漫游的事件的发生，用回调去处理当前的事件变化
     */
    //private OnMapStatusChangeListener mapStatusChanged = null;

    @Override
    public boolean mapScroll(double x, double y)
    {

        if(getMapStaticChange() != null)
        {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(MapStatus.Defualt.MOVING.name(), x + "," + y);
            intent.putExtras(bundle);
            getMapStaticChange().onMapStatusChanged(MapStatus.Defualt.MOVING.name(), intent);
        }

        map.getMapInfo().moveX = x;
        map.getMapInfo().moveY = y;
        map.refresh();
        return false;
    }

    @Override
    public boolean zoomIn()
    {
      return zoomTo(map.getMapCenter(), map.getLevel() - 1);
    }

    @Override
    public boolean zoomOut()
    {
       return zoomTo(map.getMapCenter(), map.getLevel() + 1);
    }

    @Override
    public boolean zoomTo(Coordinate point, int level)
    {

        if (level > map.getMapInfo().getMapMaxLevel())
        {
            Toast.makeText(ApplicationContext.getContext(), "当前级别大于地图最大级别", Toast.LENGTH_LONG).show();
            return false;
        } else if (level == map.getMapInfo().getMapMaxLevel())
        {
            Toast.makeText(ApplicationContext.getContext(), "最大级别"+level, Toast.LENGTH_LONG).show();
        } else if (level == 0)
        {
            Toast.makeText(ApplicationContext.getContext(), "最小级别", Toast.LENGTH_LONG).show();
        } else if (level < 0)
        {
            Toast.makeText(ApplicationContext.getContext(), "当前级别小于地图最小级别", Toast.LENGTH_LONG).show();
            return false;
        }else {
            Toast.makeText(ApplicationContext.getContext(), "当前级别"+level, Toast.LENGTH_SHORT).show();
        }

        map.getMapInfo().moveX = 0;
        map.getMapInfo().moveY = 0;
        map.setCurrentCenterImage(point);
        map.getMapInfo().initMapInfo(level);

        if (getMapStaticChange() != null)
        {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(MapStatus.Defualt.ZOOM.name(), (level));
            intent.putExtras(bundle);
            getMapStaticChange().onMapStatusChanged(MapStatus.Defualt.ZOOM.name(), intent);
        }

        map.refresh();
        return true;
    }

    @Override
    public boolean zoom(MapStatus.Defualt mapStatus)
    {
        switch (mapStatus)
        {
            case ZOOMIN:
                zoomIn();
                break;
            case ZOOMOUT:
                zoomOut();
                break;
            default:
                LogUtil.i("没有选项");
                break;
        }

        return false;
    }

    //    public boolean zoom(Envelope curEvn, Envelope lastEnv, Coordinate center)
//    {
//        Coordinate leftTop = new Coordinate(Projection.getInstance(map).toMapPoint((float)curEvn.getMinX(), (float)curEvn.getMinY()));
//        Coordinate rightBoom = new Coordinate(Projection.getInstance(map).toMapPoint((float)curEvn.getMaxX(), (float)curEvn.getMaxY()));
//        Envelope tempEnv = new Envelope(leftTop, rightBoom);
//
//        zoomLevel(curEvn, lastEnv);
//
//        Log.e("RUN", curEvn.getWidth() + "curEvn.getWidth()" + curEvn.getHeight() + "curEvn.getHeight()" + lastEnv.getWidth() + "lastEnv.getWidth()" + lastEnv.getHeight() + "lastEnv.getHeight()");
//
//        map.refresh();
//
//        return true;
//    }

//    public boolean zoomTemp(int d)
//    {
//        if(d > 0)
//        {
//            zoomOut();
//        }else if(d < 0)
//        {
//            zoomIn();
//        }
//
//        map.refresh();
//        return true;
//    }

//    private void zoomLevel(Envelope curEvn, Envelope lastEnv)
//    {
//        if (curEvn.getArea() > lastEnv.getArea())
//        {
//            zoomOut();
//        }else {
//            zoomIn();
//        }
//    }

//    public void setMapInfo(int level)
//    {
//        TileInfo tileInfo = CoordinateSystemManager.getInstance().getCoordinateSystem().getTileInfo();
//        double[] resolutions = tileInfo.getResolutions();
//        map.getMapInfo().setCurrentLevel(level);
//        map.getMapInfo().setCurrentResolution(resolutions[level]);
//        map.getMapInfo().setCurrentScale(tileInfo.getScales()[level]);
//        map.getMapInfo().setCurrentEnvelope(map.getMapInfo().calculationEnvelope());
//    }

    public boolean scrollTo(double downX, double downY, double upX, double upY)
    {
        Projection projection = Projection.getInstance(map);
        Coordinate downPoint = projection.toMapPoint((float) downX, (float) downY);
        Coordinate upPoint = projection.toMapPoint((float)upX, (float)upY);

        Coordinate mapCenter = new Coordinate(map.getMapCenter().x - (upPoint.x - downPoint.x),
                map.getMapCenter().y - (upPoint.y - downPoint.y));


        map.getMapInfo().moveX = 0;
        map.getMapInfo().moveY = 0;
        map.setCurrentCenterImage(mapCenter);
        map.getMapInfo().initMapInfo(map.getLevel());

        if(getMapStaticChange() != null)
        {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(MapStatus.Defualt.SCROLLTO.name(), 0);
            intent.putExtras(bundle);
            getMapStaticChange().onMapStatusChanged(MapStatus.Defualt.SCROLLTO.name(),intent);
        }

        map.refresh();
        return true;
    }

    @Override
    public void refresh()
    {
        map.invalidate();
    }

    public OnMapStatusChangeListener getMapStaticChange()
    {
        return map.getMapStatusChangedListener();
    }
}

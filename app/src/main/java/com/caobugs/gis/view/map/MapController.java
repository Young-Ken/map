package com.caobugs.gis.view.map;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.tile.CoordinateSystemManager;
import com.caobugs.gis.view.map.event.MapStatusChanged;
import com.caobugs.gis.view.map.event.OnMapStatusChangeListener;
import com.caobugs.gis.view.map.util.Projection;
import com.caobugs.gis.tile.TileInfo;

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
        if(map.getLevel() == 0)
        {
            return false;
        }
        zoomTo(map.getMapCenter(), map.getLevel() - 1);

        if(getMapStaticChange() != null)
        {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(MapStatus.Defualt.ZOOM.name(), (map.getLevel() - 1));
            intent.putExtras(bundle);
            getMapStaticChange().onMapStatusChanged(MapStatus.Defualt.ZOOM.name(), intent);
        }
        return false;
    }

    @Override
    public boolean zoomOut()
    {


        if(map.getLevel() == CoordinateSystemManager.getInstance()
                .getCoordinateSystem().getTileInfo().getResolutions().length - 1)
        {
            return false;
        }

        if(getMapStaticChange() != null)
        {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(MapStatus.Defualt.ZOOM.name(), (map.getLevel() + 1));
            intent.putExtras(bundle);
            getMapStaticChange().onMapStatusChanged(MapStatus.Defualt.ZOOM.name(),intent);
        }

        zoomTo(map.getMapCenter(), map.getLevel() + 1);
        return false;
    }

    @Override
    public boolean zoomTo(Coordinate point, int level)
    {
        //map.getMapInfo().setMoving(0, 0);

        map.getMapInfo().moveX = 0;
        map.getMapInfo().moveY = 0;
        map.setCurrentCenterImage(point);
        setMapInfo(level);
        map.refresh();
        return false;
    }

    public boolean zoom(Envelope curEvn, Envelope lastEnv, Coordinate center)
    {
        Coordinate leftTop = new Coordinate(Projection.getInstance(map).toMapPoint((float)curEvn.getMinX(), (float)curEvn.getMinY()));
        Coordinate rightBoom = new Coordinate(Projection.getInstance(map).toMapPoint((float)curEvn.getMaxX(), (float)curEvn.getMaxY()));
        Envelope tempEnv = new Envelope(leftTop, rightBoom);

        zoomLevel(curEvn, lastEnv);

        Log.e("RUN", curEvn.getWidth() + "curEvn.getWidth()" + curEvn.getHeight() + "curEvn.getHeight()" + lastEnv.getWidth() + "lastEnv.getWidth()" + lastEnv.getHeight() + "lastEnv.getHeight()");

        map.refresh();

        return true;
    }

    public boolean zoomTemp(int d)
    {
        if(d > 0)
        {
            zoomOut();
        }else if(d < 0)
        {
            zoomIn();
        }
        map.refresh();
        return true;
    }

    private void zoomLevel(Envelope curEvn, Envelope lastEnv)
    {
        if (curEvn.getArea() > lastEnv.getArea())
        {
            zoomOut();
        }else {
            zoomIn();
        }
    }

    public void setMapInfo(int level)
    {
        TileInfo tileInfo = CoordinateSystemManager.getInstance().getCoordinateSystem().getTileInfo();
        double[] resolutions = tileInfo.getResolutions();
        map.getMapInfo().setCurrentLevel(level);
        map.getMapInfo().setCurrentResolution(resolutions[level]);
        map.getMapInfo().setCurrentScale(tileInfo.getScales()[level]);
        map.getMapInfo().setCurrentEnvelope(map.getMapInfo().calculationEnvelope());
    }

    public boolean scrollTo(double downX, double downY, double upX, double upY)
    {
        Projection projection = Projection.getInstance(map);
        Coordinate downPoint = projection.toMapPoint((float) downX, (float) downY);
        Coordinate upPoint = projection.toMapPoint((float)upX, (float)upY);

        Coordinate mapCenter = new Coordinate(map.getMapCenter().x - (upPoint.x - downPoint.x),
                map.getMapCenter().y - (upPoint.y - downPoint.y));
        zoomTo(mapCenter, map.getLevel());


        if(getMapStaticChange() != null)
        {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(MapStatus.Defualt.SCROLLTO.name(), 0);
            intent.putExtras(bundle);
            getMapStaticChange().onMapStatusChanged(MapStatus.Defualt.SCROLLTO.name(),intent);
        }


        return true;
    }

    @Override
    public boolean refresh()
    {
        map.invalidate();
        return false;
    }

    public OnMapStatusChangeListener getMapStaticChange()
    {
        return map.getMapStatusChangedListener();
    }
}

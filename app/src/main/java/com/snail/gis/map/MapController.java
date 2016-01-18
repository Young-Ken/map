package com.snail.gis.map;
import android.util.Log;

import com.snail.gis.algorithm.MathUtil;
import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.map.util.Projection;
import com.snail.gis.tile.TileInfo;

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

    @Override
    public boolean mapScroll(double x, double y)
    {
        double moveX = map.getTileTool().moveX;
        double moveY = map.getTileTool().moveY;
        map.getTileTool().moveX = moveX + x;
        map.getTileTool().moveY = moveY + y;
        map.refresh();
        return false;
    }

    @Override
    public boolean zoomIn()
    {
        zoomTo(map.getMapCenter(), map.getLevel() -1);
        return false;
    }

    @Override
    public boolean zoomOut()
    {
        zoomTo(map.getMapCenter(), map.getLevel() + 1);
        return false;
    }

    @Override
    public boolean zoomTo(Coordinate coordinate, int level)
    {
        map.setMapCenter(coordinate);
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

    private void zoomLevel(Envelope curEvn, Envelope lastEnv)
    {
        if (curEvn.getArea() > lastEnv.getArea())
        {
            zoomOut();
        }else {
            zoomIn();
        }






//        double resolution ;
//        if (curEvn.getWidth() > curEvn.getHeight())
//        {
//            resolution = curEvn.getWidth() / map.getWidth();
//        } else
//        {
//            resolution = curEvn.getHeight() / map.getHeight();
//        }
//
//        TileInfo tileInfo = map.getTileInfo();
//        double[] resolutions = tileInfo.getResolutions();
//        for (int i = 0; i < resolutions.length - 1; i++)
//        {
//            if (MathUtil.between(resolution, resolutions[i], resolutions[i + 1]))
//            {
//                if(i == map.getLevel())
//                {
//                    return;
//                }
//
//                map.getMapInfo().setCurrentLevel(i);
//                map.getMapInfo().setCurrentResolution(resolutions[i]);
//                map.getMapInfo().setCurrentScale(tileInfo.getScales()[i]);
//                map.getMapInfo().setCurrentEnvelope(map.getMapInfo().calculationEnvelope());
//            }
//        }
    }

    public void setMapInfo(int level)
    {
        TileInfo tileInfo = map.getTileInfo();
        double[] resolutions = tileInfo.getResolutions();
        map.getMapInfo().setCurrentLevel(level);
        map.getMapInfo().setCurrentResolution(resolutions[level]);
        map.getMapInfo().setCurrentScale(tileInfo.getScales()[level]);
        map.getMapInfo().setCurrentEnvelope(map.getMapInfo().calculationEnvelope());
    }

    public boolean scrollTo(double downX, double downY, double upX, double upY)
    {
        Projection projection = Projection.getInstance(map);
        Coordinate downPoint = projection.toMapPoint((float)downX, (float)downY);
        Coordinate upPoint = projection.toMapPoint((float)upX, (float)upY);

        Coordinate mapCenter = new Coordinate(map.getMapCenter().getX() - (upPoint.getX() - downPoint.getX()),  map.getMapCenter().getY() + (upPoint.getY() - downPoint.getY()));
        zoomTo(mapCenter, map.getLevel());
        return true;
    }

    @Override
    public boolean refresh()
    {
        map.invalidate();
        return false;
    }
}

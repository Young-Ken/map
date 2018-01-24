package com.caobugs.gis.map;
import android.graphics.Point;
import android.widget.Toast;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.util.ApplicationContext;
import com.caobugs.gis.util.LogUtil;
import com.caobugs.gis.map.event.OnMapStatusChangeListener;
import com.caobugs.gis.map.util.Projection;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/6
 */
public class MapController implements IMapController {
    private BaseMap map = null;

    public MapController(BaseMap map) {
        this.map = map;
    }

    /**
     * 监听当前map的放大缩小漫游的事件的发生，用回调去处理当前的事件变化
     */
    //private OnMapStatusChangeListener mapStatusChanged = null;
    @Override
    public boolean mapScroll(double x, double y) {

        Coordinate currentCenter = map.getMapInfo().getCurrentCenter();
        currentCenter = map.getProjection().toScreenPoint(currentCenter);

        Point point = new Point((int)currentCenter.x, (int)currentCenter.y);
        point = new Point((int) (point.x / 2 + x), (int) (point.y /2 + y));
        map.getMapInfo().setCurrentCenter(map.getProjection().toMapPoint(point.x, point.y));
        map.refresh();
        return false;
    }

    @Override
    public boolean zoomIn() {
        return zoomTo(map.getMapCenter(), map.getLevel() - 1);
    }

    @Override
    public boolean zoomOut() {
        return zoomTo(map.getMapCenter(), map.getLevel() + 1);
    }

    @Override
    public boolean zoomTo(Coordinate point, int level) {

        if (level > map.getMapInfo().getMapMaxLevel()) {
            Toast.makeText(ApplicationContext.getContext(), "当前级别大于地图最大级别", Toast.LENGTH_SHORT).show();
            return false;
        } else if (level == map.getMapInfo().getMapMaxLevel()) {
            Toast.makeText(ApplicationContext.getContext(), "最大级别" + level, Toast.LENGTH_SHORT).show();
        } else if (level == 0) {
            Toast.makeText(ApplicationContext.getContext(), "最小级别", Toast.LENGTH_SHORT).show();
        } else if (level < 0) {
            Toast.makeText(ApplicationContext.getContext(), "当前级别小于地图最小级别", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(ApplicationContext.getContext(), "当前级别" + level, Toast.LENGTH_SHORT).show();
        }

        map.getMapInfo().setCurrentCenter(point);
        map.getMapInfo().setCurrentLevel(level);
        map.refresh();
        return true;
    }

    @Override
    public boolean zoom(MapStatus.Defualt mapStatus) {
        switch (mapStatus) {
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

    public boolean scrollTo(double downX, double downY, double upX, double upY) {
        Projection projection = Projection.getInstance(map);
        Coordinate downPoint = projection.toMapPoint((float) downX, (float) downY);
        Coordinate upPoint = projection.toMapPoint((float) upX, (float) upY);

        Coordinate mapCenter = new Coordinate(map.getMapCenter().x - (upPoint.x - downPoint.x),
                map.getMapCenter().y - (upPoint.y - downPoint.y));

//        map.getMapInfo().moveX = 0;
//        map.getMapInfo().moveY = 0;
        map.getMapInfo().setCurrentCenter(mapCenter);
        map.getMapInfo().setCurrentLevel(map.getLevel());

        map.refresh();
        return true;
    }

    @Override
    public void refresh() {
        map.invalidate();
    }

    public OnMapStatusChangeListener getMapStaticChange() {
        return map.getMapStatusChangedListener();
    }
}

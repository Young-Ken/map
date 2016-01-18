package com.snail.gis.map.event;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import com.snail.gis.algorithm.MathUtil;
import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.map.BaseMap;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/25
 */
public class MapDefaultListener implements OnMapDefaultListener
{
    private static final String TAG = MapDefaultListener.class.getName();
    private static final int DEFAULT_MODE = 0;
    private static final int MODE_MOVE = 1;
    private static final int MODE_ZOOM = 2;

    private BaseMap map = null;
    private Coordinate downCoor = new Coordinate();
    private Coordinate upCoor = new Coordinate();
    private Envelope downEnv = new Envelope();
    private int mode = DEFAULT_MODE;
    public MapDefaultListener(BaseMap map)
    {
        this.map = map;

    }

    @Override
    public void onMapDefaultEvent(MotionEvent event)
    {
        int action = event.getAction();
        long time = 0;
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
            {
                downCoor.x = event.getX();
                downCoor.y = event.getY();
                break;
            }

            case MotionEvent.ACTION_MOVE:
            {
                if(event.getDownTime() - time < 100)
                {
                    return;
                }

                int pointNum = event.getPointerCount();
                if (pointNum == 1)
                {
                    mode = MODE_MOVE;
                    Log.e(TAG,"MOVE" + pointNum + "    pointNum");
                    double disX = event.getX() - downCoor.getX();
                    double disY = event.getY() - downCoor.getY();

                    if (Math.abs(disX) < 20f && Math.abs(disY) < 20f)
                    {
                        return;
                    }
                    map.getMapController().mapScroll(disX, disY);
                    break;
                }else if(pointNum >= 2 )
                {
                    mode = MODE_ZOOM;
                    Log.e(TAG,"ZOOM" + pointNum + "    pointNum");

                    Envelope tempEnv = new Envelope();
                    for (int i = 0; i < 2; i++)
                    {
                        tempEnv.expandToInclude(event.getX(i), event.getY(i));
                    }
                    if(downEnv.isEmpty())
                    {
                        downEnv.init(tempEnv);
                    }

                    if(!(Math.abs(tempEnv.getWidth() - downEnv.getWidth()) > 20 || Math.abs(tempEnv.getHeight() - downEnv.getHeight()) > 20))
                    {
                        return;
                    }
                    map.getMapController().zoom(tempEnv, downEnv, map.getMapCenter());
                    downEnv.init(tempEnv);
                    break;
                }
            }

            case MotionEvent.ACTION_UP:
            {
                //把移动的位置变为0
                map.getTileTool().moveY = 0;
                map.getTileTool().moveX = 0;
                if(mode == MODE_MOVE)
                {
                    map.getMapController().scrollTo(downCoor.getX(), downCoor.getY(), event.getX(), event.getY());
                }
                mode = DEFAULT_MODE;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP:
                mode = DEFAULT_MODE;
                downEnv.setToNull();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
            {

            }
        }
    }
}

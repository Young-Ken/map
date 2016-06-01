package com.caobugs.gis.view.map.event;


import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.view.map.BaseMap;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/25
 */
public class MapDefaultListener implements OnMapDefaultListener
{
    private static final String TAG = MapDefaultListener.class.getName();

    /**
     * 暂时支持四种模式分别是
     * 点击
     * 移动
     * 滑动（甩）
     * 长按(暂时不支持)
     */
    private static final int DEFAULT_MODE = 0;
    private static final int MODE_CLICK = 1;
    private static final int MODE_MOVE = 2;
    private static final int MODE_FLING = 3;
    private static final int MODE_LONG_CLICK = 4;

    /**
     * 当前正在执行的动作
     */
    private static final int EVENTING = 0;
    private static final int EVENT_DOWN = 1;
    private static final int EVENT_MOVING = 2;
    private static final int EVENT_UP = 3;

    /**
     * 速度,用判断甩手势
     */
    private double speed = 0;

    /**
     * 如果触碰太短将视为事件无效
     */
    private static final int EVENT_EFFECTIVE_TIME = 50;

    /**
     * 有效的移动距离
     */
    private static final double EVENT_EFFECTIVE_DIS = 20f;

    /**
     * 如果超出这回时间将不认为是点击事件，是长按事件
     */
    private static final int EVENT_CLICK_TIME = 500;

    /**
     * 当前的临界值，如果速度超过这个就是滑动
     */
    private static final int SPEED_CV = 1000;

    /**
     * 累积移动距离
     */
    private int moveDistance = 0;

    private float velocityX ;
    private float velocityY;

    private VelocityTracker velocityTracker;
    private BaseMap map = null;
    private Coordinate down = new Coordinate();
    private Coordinate up = new Coordinate();
    //private Coordinate moving = new Coordinate();
    //private Coordinate moveLast = new Coordinate();
    private double moveingX = 0;
    private double moveingY = 0;

//    private double moveLastX = 0;
//    private double moveLastY = 0;


    private long downTime = -1;


    private int mode = DEFAULT_MODE;
    private int eventing = EVENTING;
    private int maxFlingVelocity;

    public MapDefaultListener(BaseMap map)
    {
        this.map = map;
        maxFlingVelocity = ViewConfiguration.get(map.getContext()).getScaledMaximumFlingVelocity();
    }

    @Override
    public void onMapDefaultEvent(MotionEvent event)
    {
        int action = event.getAction();

        //只支持一个手指操作
        int pointCount = event.getPointerCount();
        if (pointCount != 1)
        {
            return;
        }

        initVelocityTracker(event);
        final VelocityTracker tempVT = velocityTracker;

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
            {
                eventing = EVENT_DOWN;
                down.x = event.getX();
                down.y = event.getY();
                downTime = event.getDownTime();
                Log.d(TAG, "ACTION_DOWN");
                break;
            }

            case MotionEvent.ACTION_MOVE:
            {

                double moveDistanceX;
                double moveDistanceY;

                moveingX = event.getX();
                moveingY = event.getY();
                double md = 0;

                moveDistanceX = moveingX - down.x;
                moveDistanceY = moveingY - down.y;


                md = Math.abs(moveDistanceX) + Math.abs(moveDistanceY);

                // 没有做down方法，直接进入moving中
                //首次进入事件中
                if (eventing == EVENT_DOWN || eventing == EVENTING)
                {
                    //长按屏幕进入Move事件，第一进入把事件改成down
                    if (eventing == EVENTING)
                    {
                        md = 0;
                        down.x = event.getX();
                        down.y = event.getY();
//                        moveLastX = event.getX();
//                        moveLastY = event.getY();

                        downTime = event.getDownTime();
                        eventing = EVENT_DOWN;
                    }
                    if (md < EVENT_EFFECTIVE_DIS)
                    {
                        Log.d(TAG, "ACTION_MOVE   EVENT_DOWN");
                        return;
                    }
                    eventing = EVENT_MOVING;
                    mode = MODE_MOVE;
                }

//                if (eventing == EVENT_MOVING)
//                {
//                    md = Math.abs(moveLastX - moveingX) + Math.abs(moveLastY - moveingY);
//                    if(md < EVENT_EFFECTIVE_DIS)
//                    {
//                        moveLastX = moveingX;
//                        moveLastY = moveingY;
//                        return;
//                    }
//                }

                Log.d(TAG, "ACTION_MOVE" + md + "   " + eventing);

                tempVT.computeCurrentVelocity(1000, maxFlingVelocity);
                velocityX = tempVT.getXVelocity(event.getPointerId(0));
                velocityY = tempVT.getYVelocity(event.getPointerId(0));


                map.getMapController().mapScroll(moveDistanceX, moveDistanceY);
                break;
            }

            case MotionEvent.ACTION_UP:
            {
                //如果按下事间小于失效时间，事件作废
                if (event.getEventTime() - downTime < EVENT_EFFECTIVE_TIME)
                {
                    return;
                }

                if (eventing == EVENT_DOWN)
                {
                    if (event.getEventTime() - downTime < EVENT_CLICK_TIME)
                    {
                        Log.d(TAG, "CLICK ");
                    } else
                    {
                        Log.d(TAG, "LONG CLICK");
                    }

                } else if (eventing == EVENT_MOVING)
                {
                    if (Math.abs(velocityX) > SPEED_CV || Math.abs(velocityY) > SPEED_CV)
                    {
                        Log.d(TAG, "甩开");
                    } else
                    {
                        Log.d(TAG, "移动");
                        //
                    }
                    //把移动的位置变为0
                    map.getMapController().scrollTo(down.x, down.y, event.getX(), event.getY());
                }

                init();
                break;
            }

        }
    }

    /**
     * 出事化值，包括释放VelocityTracker
     */
    private void init()
    {
        mode = DEFAULT_MODE;
        eventing = EVENTING;

        down.setOrdinate(Coordinate.X, 0);
        down.setOrdinate(Coordinate.Y, 0);

        up.setOrdinate(Coordinate.X, 0);
        up.setOrdinate(Coordinate.Y, 0);

        moveingY = 0;
        moveingX = 0;

        releaseVelocityTracker();
    }

    /**
     * @param event 向VelocityTracker添加MotionEvent
     */
    private void initVelocityTracker(final MotionEvent event)
    {
        if (null == velocityTracker)
        {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }


    /**
     * 释放VelocityTracker
     *
     * @see android.view.VelocityTracker#clear()
     * @see android.view.VelocityTracker#recycle()
     */
    private void releaseVelocityTracker()
    {
        if (null != velocityTracker)
        {
            velocityTracker.clear();
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }
}

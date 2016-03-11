package com.snail.gis.ztest.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.snail.gis.geometry.Point;
import com.snail.gis.geometry.LineString;
import com.snail.gis.geometry.Polygon;
import com.snail.gis.geometry.primary.Envelope;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/25
 */
public class DrawView extends View implements View.OnTouchListener
{

    public  Context context = null;
    public  View parentView = null;
    public DrawView(Context context, View view)
    {
        super(context);
        this.context = context;
        this.parentView = view;
        setBackgroundColor(Color.BLUE);
        this.setOnTouchListener(this);
    }

    public int drawState = 0;
    public Point point = null;
    public LineString lineString = null;
    public Polygon polygon = null;
    public Polygon polygon1 = null;
    public View view = null;
    public Envelope envelope = null;
    public Paint paint = new Paint();


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        return false;
    }
}

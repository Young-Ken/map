package com.snail.gis.ztest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.snail.gis.R;
import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.LineSegment;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/30
 */
public class Lines_in_Lines extends DrawView
{
    public LineSegment line1 = null;
    public LineSegment line2 = null;

    public Lines_in_Lines(Context context, View view)
    {
        super(context, view);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint paint1 = new Paint();

        if(line2 != null)
        {
            canvas.drawLine((float)line2.getStartPoint().x,(float)line2.getStartPoint().y, (float)line2.getEndPoint().x,(float)line2.getEndPoint().y, paint);
            DrawTools.drawEnvelope(canvas, line2);
        }

        if(line1 != null)
        {
            canvas.drawLine((float)line1.getStartPoint().x,(float)line1.getStartPoint().y, (float)line1.getEndPoint().x,(float)line1.getEndPoint().y, paint);
            DrawTools.drawEnvelope(canvas, line1);
        }


    }

    public void init()
    {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        Button clearButton = (Button)  parentView.findViewById(R.id.clear);
        clearButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                time= 0 ;
                line2 = null;
                line1 = null;
                invalidate();
            }
        });
    }
    int time = 0;
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {

        Toast.makeText(context, motionEvent.getX() + "  " + motionEvent.getY() + "", Toast.LENGTH_LONG).show();
        Coordinate tempPoint = new Coordinate(motionEvent.getX(), motionEvent.getY());

        if(time == 0)
        {
            line1 = new LineSegment();
            line1.setStartPoint(tempPoint);
        } else if(time == 1)
        {
            line1.setEndPoint(tempPoint);
            invalidate();
        } else if(time == 2)
        {
            line2 = new LineSegment();
            line2.setStartPoint(tempPoint);
        } else if(time == 3)
        {
            line2.setEndPoint(tempPoint);
            invalidate();
        }
        time ++;
        return false;
    }

}

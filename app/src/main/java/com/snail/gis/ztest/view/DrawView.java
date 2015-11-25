package com.snail.gis.ztest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.LineString;
import com.snail.gis.geometry.Polygon;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/25
 */
public class DrawView extends View
{

    private Context context = null;
    public DrawView(Context context)
    {
        super(context);
        this.context = context;
        setBackgroundColor(Color.BLUE);
    }

    public int drawState = 0;
    public Coordinate point = null;
    public LineString lineString = null;
    public Polygon polygon = null;
    public View view = null;
    public Paint paint = new Paint();


    public void init()
    {

        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        this.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                Toast.makeText(context,motionEvent.getX()+"  "+motionEvent.getY()+"",Toast.LENGTH_LONG).show();
                Coordinate tempPoint = new Coordinate(motionEvent.getX(), motionEvent.getY());
                switch (drawState)
                {
                    case 1:
                        point.x = tempPoint.x;
                        point.y = tempPoint.y;
                        break;
                    case 2:
                        lineString.getPointArray().add(tempPoint);
                        break;

                    case 3:
                        polygon.getExteriorRing().getPointArray().add(tempPoint);
                        break;
                }
                return false;
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas)
    {

        paint.setColor(Color.RED);
        super.onDraw(canvas);
        if (point != null)
        {
            canvas.drawCircle((float)point.x, (float)point.y, 10,paint);
        }

        if (lineString != null)
        {
           for (int i = 0; i < lineString.getPointNum()-1; i++)
           {
               Coordinate startPoint = new Coordinate(lineString.getPointArray().get(i));
               Coordinate endPoint = new Coordinate(lineString.getPointArray().get(i+1));
               canvas.drawLine((float)startPoint.x, (float)startPoint.y, (float)endPoint.x, (float)endPoint.y, paint);
           }
        }

        if (polygon != null)
        {
            for (int i = 0; i < polygon.getExteriorRing().getPointArray().size()-1; i++)
            {
                Coordinate startPoint = new Coordinate(polygon.getExteriorRing().getPointArray().get(i));
                Coordinate endPoint = new Coordinate(polygon.getExteriorRing().getPointArray().get(i+1));
                canvas.drawLine((float)startPoint.x, (float)startPoint.y, (float)endPoint.x, (float)endPoint.y, paint);
            }

        }
    }
}

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
import com.snail.gis.geometry.LineString;
import com.snail.gis.geometry.LinearRing;
import com.snail.gis.geometry.Polygon;
import com.snail.gis.geometry.primary.Envelope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/30
 */
public class DrawGeomIntLine extends DrawView
{
    public DrawGeomIntLine(Context context, View view)
    {
        super(context, view);
    }

    public void init()
    {
        initPolygon();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        Button drawPoint = (Button) parentView.findViewById(R.id.draw_point);

        this.setOnTouchListener(this);
        drawPoint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawState = 1;
                point = new Coordinate();
            }
        });


        final Button drawLine = (Button)  parentView.findViewById(R.id.draw_line);
        drawLine.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawState = 2;
                lineString = new LineString();
            }
        });

        Button drawPolygon = (Button)  parentView.findViewById(R.id.draw_polygon);
        drawPolygon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawState = 3;
                polygon = new Polygon();
            }
        });

        Button drawEnvelope = (Button)  parentView.findViewById(R.id.draw_envelope);
        drawEnvelope.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawState = 4;
                envelope = new Envelope();
            }
        });


        Button clearButton = (Button)  parentView.findViewById(R.id.clear);
        clearButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                point = null;
                lineString = null;
                polygon = null;
                envelope = null;
                invalidate();
            }
        });
    }

    public void initPolygon()
    {
        List<Coordinate> list = new ArrayList<>();
        int min = 100;
        int max = 500;
        list.add(new Coordinate(min,min));
        list.add(new Coordinate(max, min));
        list.add(new Coordinate(max,max));
        list.add(new Coordinate(min, max));
        list.add(new Coordinate(min,min));
        polygon = new Polygon(new LinearRing(list));
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

        if (envelope != null)
        {
            canvas.drawRect((float)envelope.getMinX(), (float)envelope.getMinY(), (float)envelope.getMaxX(), (float)envelope.getMaxY(), paint);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        Toast.makeText(context, motionEvent.getX() + "  " + motionEvent.getY() + "", Toast.LENGTH_LONG).show();
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
            case 4:
                envelope.expandToInclude(motionEvent.getX(), motionEvent.getY());
        }
        return false;
    }
}

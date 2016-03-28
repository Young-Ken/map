package com.caobugs.gis.ztest.view;

import android.content.Context;
import android.view.View;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/12/1
 */
public class RectInView extends DrawView
{

    public RectInView(Context context, View view)
    {
        super(context, view);
    }

    public void init()
    {
//        initPolygon();
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(10);
//        paint.setStyle(Paint.Style.STROKE);
//
//        Button drawPoint = (Button) parentView.findViewById(R.id.draw_point);
//
//        this.setOnTouchListener(this);
//        drawPoint.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                drawState = 1;
//                point = new Point();
//            }
//        });
//
//
//        final Button drawLine = (Button)  parentView.findViewById(R.id.draw_line);
//        drawLine.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                drawState = 2;
//                lineString = new LineString();
//            }
//        });
//
//        Button drawPolygon = (Button)  parentView.findViewById(R.id.draw_polygon);
//        drawPolygon.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                drawState = 3;
//                polygon1 = new Polygon();
//            }
//        });
//
//        Button drawEnvelope = (Button)  parentView.findViewById(R.id.draw_envelope);
//        drawEnvelope.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                drawState = 4;
//                envelope = new Envelope();
//            }
//        });
//
//
//        Button clearButton = (Button)  parentView.findViewById(R.id.clear);
//        clearButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                point = null;
//                lineString = null;
//                polygon = null;
//                envelope = null;
//                invalidate();
//            }
//        });
//    }
//
//    public void initPolygon()
//    {
//        List<Point> list = new ArrayList<>();
//        int min = 100;
//        int max = 500;
//        list.add(new Point(min,min));
//        list.add(new Point(max, min));
//        list.add(new Point(max,max));
//        list.add(new Point(min, max));
//        list.add(new Point(min,min));
//        polygon = new Polygon(new LinearRing(list));
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas)
//    {
//
//        paint.setColor(Color.RED);
//        super.onDraw(canvas);
//        if (point != null)
//        {
//            canvas.drawCircle((float)point.x, (float)point.y, 10,paint);
//        }
//
//        if (lineString != null)
//        {
//            for (int i = 0; i < lineString.getPointNum()-1; i++)
//            {
//                Point startPoint = new Point(lineString.getPointArray().get(i));
//                Point endPoint = new Point(lineString.getPointArray().get(i+1));
//                canvas.drawLine((float)startPoint.x, (float)startPoint.y, (float)endPoint.x, (float)endPoint.y, paint);
//            }
//        }
//
//        if (polygon != null)
//        {
//            for (int i = 0; i < polygon.getExteriorRing().getPointArray().size()-1; i++)
//            {
//                Point startPoint = new Point(polygon.getExteriorRing().getPointArray().get(i));
//                Point endPoint = new Point(polygon.getExteriorRing().getPointArray().get(i+1));
//                canvas.drawLine((float)startPoint.x, (float)startPoint.y, (float)endPoint.x, (float)endPoint.y, paint);
//            }
//        }
//
//        if(polygon1 != null)
//        {
//            for (int i = 0; i < polygon1.getExteriorRing().getPointArray().size()-1; i++)
//            {
//                Point startPoint = new Point(polygon1.getExteriorRing().getPointArray().get(i));
//                Point endPoint = new Point(polygon1.getExteriorRing().getPointArray().get(i+1));
//                canvas.drawLine((float)startPoint.x, (float)startPoint.y, (float)endPoint.x, (float)endPoint.y, paint);
//                DrawTools.drawEnvelope(canvas, polygon1);
//            }
//        }
//
//        if (envelope != null)
//        {
//            canvas.drawRect((float)envelope.getMinX(), (float)envelope.getMinY(), (float)envelope.getMaxX(), (float)envelope.getMaxY(), paint);
//        }
//    }
//
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent)
//    {
//        Toast.makeText(context, motionEvent.getX() + "  " + motionEvent.getY() + "", Toast.LENGTH_LONG).show();
//        Point tempPoint = new Point(motionEvent.getX(), motionEvent.getY());
//        switch (drawState)
//        {
//            case 1:
//                point.x = tempPoint.x;
//                point.y = tempPoint.y;
//                break;
//            case 2:
//                lineString.getPointArray().add(tempPoint);
//                break;
//
//            case 3:
//                polygon1.getExteriorRing().getPointArray().add(tempPoint);
//                break;
//            case 4:
//                envelope.expandToInclude(motionEvent.getX(), motionEvent.getY());
//        }
//        invalidate();
//        return false;
    }
}

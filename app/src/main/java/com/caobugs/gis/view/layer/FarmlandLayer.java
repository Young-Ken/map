package com.caobugs.gis.view.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.Log;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.LinearRing;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.tool.TAG;
import com.caobugs.gis.view.map.MapManger;
import com.caobugs.gis.vo.Farmland;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/5
 */
public class FarmlandLayer extends BaseLayer
{

    private ArrayList<Farmland> farmlands = null;
    private Paint defaultPaint = null;
    private Paint selectedPaint = null;
    private Paint textPaint = null;
    private Farmland selected = null;
    public FarmlandLayer()
    {
        defaultPaint = new Paint();
        defaultPaint.setColor(Color.BLACK);
        defaultPaint.setAntiAlias(true);
        defaultPaint.setStrokeWidth(10);

        selectedPaint = new Paint();
        selectedPaint.setColor(Color.RED);
        selectedPaint.setAntiAlias(true);
        selectedPaint.setStrokeWidth(10);

        textPaint = new TextPaint();
        textPaint.setTextSize(20);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setFarmlands(ArrayList<Farmland> farmlands)
    {
        if(farmlands == null)
        {
            return;
        }
        if(this.farmlands == null)
        {
            this.farmlands = farmlands;
        } else
        {
            this.farmlands.addAll(farmlands);
            farmlandSlimming();
        }
    }

    public ArrayList<Farmland> getFarmlands()
    {
        return farmlands;
    }

    public void setSelected(Farmland farmland)
    {
        this.selected = farmland;
    }

    public void farmlandSlimming()
    {
        if (farmlands.size() > 400)
        {
            for(int i = 0; i < farmlands.size() - 400; i++)
            {
                if (selected != null)
                {
                    if(farmlands.get(i).getId() == selected.getId())
                    {
                        continue;
                    }
                    farmlands.remove(i);
                } else
                {
                    farmlands.remove(i);
                }
            }
        }
    }

    @Override
    public void recycle()
    {
        if(farmlands != null)
        {
            this.farmlands.clear();
        }

        selected = null;
    }

    @Override
    void initLayer()
    {

    }

    @Override
    void draw(Canvas canvas, Coordinate offSet)
    {

    }

    @Override
    void draw(Canvas canvas, double x, double y)
    {

    }

    private void drawFarmland(Canvas canvas, double moveX, double moveY)
    {

    }

    public Farmland getSelected()
    {
        return selected;
    }

    private void drawFarmland(Canvas canvas)
    {
        if(farmlands == null)
        {
            return;
        }
        for(Farmland farmland : farmlands)
        {
            LinearRing linearRing = farmland.getFarmGeom();


            float[] points = null;
            points = new float[linearRing.getNumPoints()*2];
            ArrayList<Coordinate> coordinateArrayList = new ArrayList<>();
            for(int i = 0; i < linearRing.getNumPoints(); i++)
            {
                Coordinate point = linearRing.getCoordinateN(i);

                Coordinate coordinate = logLanToScreen(point.x, point.y);
//                Coordinate c = getMap().getProjection().lonLatToMercator(point.x, point.y);
//
//                c = getMap().getProjection().earthTransFormImage(c.x, c.y);
//
//                Coordinate coordinate = getMap().getProjection().toScreenPoint(c.x, c.y);
                coordinateArrayList.add(coordinate);
                Log.e("xxxxxxx", coordinate.x +"    "+ coordinate.y);
                points[i*2] = (float)(coordinate.x);
                points[i*2 + 1] = (float)(coordinate.y);
            }
            float[] tempPoint = points.clone();

            //canvas.drawPoints(tempPoint, paint);

            for(int i = 0; i < coordinateArrayList.size()-1;i++)
            {
                Coordinate start = coordinateArrayList.get(i);
                Coordinate end = coordinateArrayList.get(i+1);
                canvas.drawLine((float) start.x, (float) start.y, (float) end.x, (float) end.y, defaultPaint);
            }

            if(selected != null)
            {
                if(farmland.getId() == selected.getId())
                {


                    for(int i = 0; i < coordinateArrayList.size()-1;i++)
                    {
                        Coordinate start = coordinateArrayList.get(i);
                        Coordinate end = coordinateArrayList.get(i+1);
                        canvas.drawLine((float)start.x, (float)start.y, (float)end.x, (float)end.y, selectedPaint);
                    }
                }
            }
            Envelope envelope = linearRing.getEnvelopeInternal();
            Coordinate coordinate = (Coordinate) envelope.getCentre().clone();
            coordinate = logLanToScreen(coordinate.x, coordinate.y);

            canvas.drawText(farmland.getFarmName(),(float)coordinate.x, (float)coordinate.y, textPaint);


            //canvas.drawLines(tempPoint, paint);

            Log.e("xxxxxxx", "------------------------");
        }
    }

    public void drawLines(Canvas canvas, float[] points, double x, double y)
    {
        float[] tempPoints = points.clone();
        for(int i = 0; i < tempPoints.length; i++)
        {
            if(i % 2 == 0)
            {
                tempPoints[i] = (float)(tempPoints[i] + x);
            } else {
                tempPoints[i] = (float)(tempPoints[i] + y);
            }
        }

        canvas.drawLines(tempPoints, defaultPaint);
    }

    private Coordinate logLanToScreen(double x, double y)
    {
        Coordinate c = getMap().getProjection().lonLatToMercator(x, y);
        c = getMap().getProjection().earthTransFormImage(c.x, c.y);
        c = getMap().getProjection().toScreenPoint(c.x, c.y);
        return c;
    }

    @Override
    void draw(Canvas canvas)
    {
        double moveX = MapManger.getInstance().getMap().getMapInfo().moveX;
        double moveY = MapManger.getInstance().getMap().getMapInfo().moveY;
        Log.e(TAG.TILE, moveX + "    " + moveY);
        if (moveX != 0&& moveY != 0)
        {
            drawFarmland(canvas, moveX, moveY);
        } else
        {
            drawFarmland(canvas);
        }
    }

}

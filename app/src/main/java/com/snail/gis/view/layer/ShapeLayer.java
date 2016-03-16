package com.snail.gis.view.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.snail.gis.data.shapefile.shp.geom.RecordLine;
import com.snail.gis.geometry.Coordinate;
import com.snail.gis.data.shapefile.shp.ShapeFile;
import com.snail.gis.data.shapefile.shp.geom.RecordGeometry;
import com.snail.gis.data.shapefile.shp.geom.RecordPoint;
import com.snail.gis.geometry.LineString;
import com.snail.gis.tool.TAG;
import com.snail.gis.view.map.MapManger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/19
 */
public class ShapeLayer extends BaseLayer
{

    private ShapeFile shapeFile = null;
    private Paint paint = new Paint();
    private List<List<Coordinate>> listCache = new ArrayList<>();

    public ShapeLayer(ShapeFile shapeFile)
    {
        this.shapeFile = shapeFile;
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
    }


    @Override
    void recycle()
    {

    }

    @Override
    void initLayer()
    {

    }

    @Override
    void draw(Canvas canvas, Coordinate coordinate)
    {

    }

    @Override
    void draw(Canvas canvas, double x, double y)
    {

    }

    private void drawShapeFile(Canvas canvas, double x, double y)
    {
        if (shapeFile.getHeader().getShapeType().isPointType())
        {
            drawPoint(canvas, x, y);
        } else if (shapeFile.getHeader().getShapeType().isLineType())
        {
            drawLine(canvas, x, y);
        }
    }

    private void drawShapeFile(Canvas canvas)
    {
        if (shapeFile.getHeader().getShapeType().isPointType())
        {
            drawPoint(canvas);
        } else if (shapeFile.getHeader().getShapeType().isLineType())
        {
            drawLine(canvas);
        }
    }

    @Override
    void draw(Canvas canvas)
    {
        double moveX = MapManger.getInstance().getMap().getMapInfo().moveX;
        double moveY = MapManger.getInstance().getMap().getMapInfo().moveY;
        if (moveX != 0 && moveY != 0)
        {
            drawShapeFile(canvas, moveX, moveY);
        } else
        {
            listCache.clear();
            drawShapeFile(canvas);
        }
    }

    void drawPoint(Canvas canvas, double x, double y)
    {
        List<RecordGeometry> list = shapeFile.getList();
        if (shapeFile.coordinates.size() > 0)
        {
            for (Coordinate coordinate : shapeFile.coordinates)
            {
                canvas.drawCircle((float) (coordinate.x + x), (float) (coordinate.y + y), 2, paint);
            }
            return;
        }

        for (RecordGeometry geometry : list)
        {
            RecordPoint point = (RecordPoint) geometry;

            Coordinate c = getMap().getProjection().lonLatToMercator(point.getX(), point.getY());

            c = getMap().getProjection().earthTransformaImage(c.x, c.y);

            Coordinate coordinate = getMap().getProjection().toScreenPoint(c.x, c.y);

            canvas.drawCircle((float) (coordinate.x + x), (float) (coordinate.y + y), 2, paint);
        }
    }


    private void drawLine(Canvas canvas,double x, double y)
    {
       drawLines(canvas, listCache, x, y);
    }


    void drawPoint(Canvas canvas)
    {
        List<RecordGeometry> list = shapeFile.getList();
        if (shapeFile.coordinates.size() > 0)
        {
            for (Coordinate coordinate : shapeFile.coordinates)
            {
                canvas.drawCircle((float) (coordinate.x), (float) (coordinate.y), 2, paint);
            }
            return;
        }

        for (RecordGeometry geometry : list)
        {
            RecordPoint point = (RecordPoint) geometry;

            Coordinate c = getMap().getProjection().lonLatToMercator(point.getX(), point.getY());

            c = getMap().getProjection().earthTransformaImage(c.x, c.y);

            Coordinate coordinate = getMap().getProjection().toScreenPoint(c.x, c.y);

            canvas.drawCircle((float) (coordinate.x), (float) (coordinate.y), 2, paint);
        }
    }

    int num = 0;
    private void drawLine(Canvas canvas)
    {
        double minX = getMap().getEnvelope().getMinX();
        double minY = getMap().getEnvelope().getMinY();
        double resolution = getMap().getResolution();
        long starTime = System.currentTimeMillis();
        List<RecordGeometry> list = shapeFile.getList();

        for (int i = 0; i < list.size(); i++)
        {
            RecordLine line = (RecordLine) list.get(i);

            Coordinate[] coordinates = line.getPoints();

            List<Coordinate>  coordinateList = new ArrayList<>();

            for(int j = 0; j < coordinates.length; j++)
            {
                Coordinate temp = (Coordinate)coordinates[j].clone();
                temp = getMap().getProjection().lonLatToMercator(temp);
                temp = getMap().getProjection().earthTransformaImage(temp);
                temp = getMap().getProjection().toScreenPoint(temp, minX, minY, resolution);
                coordinateList.add(temp);
                num++;
            }
            listCache.add(coordinateList);
        }
        drawLines(canvas, listCache, 0,0);
        Log.e(TAG.SHAPELAYER,System.currentTimeMillis()-starTime+"   "+num);
        num = 0;
    }

    private void drawLines(Canvas canvas, List<List<Coordinate>> list, double x, double y)
    {
        for(List<Coordinate> coordinateList : list)
        {
            Coordinate start = null;
            if(coordinateList.size() > 0)
            {
                start = coordinateList.get(0);
            }
            for(int i = 1; i < coordinateList.size(); i++)
            {
                Coordinate end = coordinateList.get(i);
                canvas.drawLine((float)(start.x + x), (float)(start.y + y),
                        (float)(end.x + x), (float)(end.y + y), new Paint());

                start = end;
            }
        }
    }
    /**
     * 各种坐标转
     */
    //            for (RecordGeometry geometry : list)
    //            {
    //                RecordPoint point = (RecordPoint) geometry;
    //                Log.e("ssss", point.getX() +"point    "+point.getY());
    //                Coordinate tempPoint = getMap().getProjection().gaussTolnlaPro(point.getX(), point.getY(), true, 120, 0, 0);
    //                Log.e("ssss", tempPoint.x +"tempPoint    "+tempPoint.y);
    //                Coordinate result = getMap().getProjection().lonLatToMercator(tempPoint.x, tempPoint.y);
    //                Log.e("ssss", tempPoint.x +"result    "+tempPoint.y);
    //                result = getMap().getProjection().earthTransformaImage(result.x, result.y);
    //                Log.e("ssss", result.x +" result   "+result.y);
    //                Coordinate coordinate = getMap().getProjection().toScreenPoint(result.x, result.y);
    //                shapeFile.coordinates.add(coordinate);
    //                Log.e("ssss", coordinate.x +"coordinate    "+coordinate.y);
    //
    //                canvas.drawCircle((float) (coordinate.x + x), (float) (coordinate.y+y), 2, paint);
    //            }
}

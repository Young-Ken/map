package com.caobugs.gis.map.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.caobugs.gis.data.shapefile.cache.ShapeCache;
import com.caobugs.gis.data.shapefile.shp.geom.RecordLine;
import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.data.shapefile.shp.ShapeFile;
import com.caobugs.gis.data.shapefile.shp.geom.RecordGeometry;
import com.caobugs.gis.data.shapefile.shp.geom.RecordPoint;
import com.caobugs.gis.geometry.CoordinateArraySequence;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/19
 */
public class ShapeLayer extends BaseLayer
{


    private ShapeFile shapeFile = null;
    private Paint paint = new Paint();

    private ShapeCache shapeCache = new ShapeCache();
    long starTime = 0;

    public ShapeLayer(ShapeFile shapeFile)
    {
        this.shapeFile = shapeFile;
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
    }

    @Override
    public void recycle()
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
//        double moveX = MapManger.getInstance().getMap().getMapInfo().moveX;
//        double moveY = MapManger.getInstance().getMap().getMapInfo().moveY;
//
//        if (moveX != 0 && moveY != 0)
//        {
//            drawShapeFile(canvas, moveX, moveY);
//        } else
//        {
//            shapeCache.desdory();
//            drawShapeFile(canvas);
//        }
    }

    void drawPoint(Canvas canvas, double x, double y)
    {
        List<RecordGeometry> list = shapeFile.getList();
        float[] points = null;
        if (shapeCache.getPoints() != null)
        {
            points = shapeCache.getPoints();
            for(int i = 0; i < points.length; i++)
            {
                if(i % 2 == 0)
                {
                    points[i] = (float)(points[i] + x);
                }else {
                    points[i] = (float)(points[i] + y);
                }
            }
        } else {
            points = new float[list.size() * 2];
            for(int i = 0; i < list.size(); i++)
            {
                RecordPoint point = (RecordPoint) list.get(i);

                Coordinate c = getMap().getProjection().lonLatToMercator(point.getCoordinate().x, point.getCoordinate().y);

                c = getMap().getProjection().earthTransFormImage(c.x, c.y);

                Coordinate coordinate = getMap().getProjection().toScreenPoint(c.x, c.y);

                points[i*2] = (float)(coordinate.x + x);
                points[i*2 + 1] = (float)(coordinate.y + y);
            }
            shapeCache.setPoints(points);
        }

        float[] tempPoint = points.clone();
        canvas.drawPoints(tempPoint, paint);
    }


    private void drawLine(Canvas canvas,double x, double y)
    {
        drawLines(canvas, shapeCache.getLines(), x, y);
    }


    void drawPoint(Canvas canvas)
    {
      drawPoint(canvas, 0, 0);
    }

    int num = 0;
    private void drawLine(Canvas canvas)
    {
        double minX = getMap().getEnvelope().getMinX();
        double minY = getMap().getEnvelope().getMinY();
        double resolution = getMap().getMapInfo().getCurrentResolution();
        starTime = System.currentTimeMillis();
        List<RecordGeometry> list = shapeFile.getList();

        for (int i = 0; i < list.size(); i++)
        {
            RecordLine line = (RecordLine) list.get(i);
            CoordinateArraySequence coordinates = line.getPoints();
            float[] lines = new float[coordinates.size() * 2];
            for(int j = 0; j < coordinates.size(); j++)
            {
                Coordinate temp = (Coordinate)coordinates.getCoordinate(j).clone();
                temp = getMap().getProjection().lonLatToMercator(temp);
                temp = getMap().getProjection().earthTransFormImage(temp);
                temp = getMap().getProjection().toScreenPoint(temp, minX, minY, resolution);
                lines[j * 2] = (float) temp.x;
                lines[j * 2 + 1] = (float) temp.y;
                num++;
            }
            shapeCache.getLines().add(lines);
            drawLines(canvas, lines, 0, 0);

        }

        num = 0;
    }

    private void drawLines(Canvas canvas, ArrayList<float[]> cacheLines, double x, double y)
    {
        for(float[] points : cacheLines)
        {
            drawLines(canvas, points, x, y);
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

        canvas.drawLines(tempPoints, paint);
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
